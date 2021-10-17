package com.kelompokx.todonote;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.kelompokx.todonote.Model.ToDoModel;
import com.kelompokx.todonote.Utility.DBHelper;

public class TambahNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";
    private EditText tugasBaruText;
    private Button newTaskSaveButton;
    private DBHelper db;

    public static TambahNewTask newInstance() {
        return new TambahNewTask();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tugas_baru, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tugasBaruText = getView().findViewById(R.id.tugasBaruText);
        newTaskSaveButton = getView().findViewById(R.id.tugasBaruButton);

        boolean isUpdate = false;

        final Bundle bundle = getArguments(); //ini error knp
        if (bundle != null) {
            isUpdate = true;
            String tugas = bundle.getString("tugas");
            tugasBaruText.setText(tugas);
            assert tugas != null;
            if (tugas.length() > 0)
                newTaskSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        }

        db = new DBHelper(getActivity());
        db.openDatabase();

        tugasBaruText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    newTaskSaveButton.setEnabled(false);
                    newTaskSaveButton.setTextColor(Color.GRAY);
                } else {
                    newTaskSaveButton.setEnabled(true);
                    newTaskSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final boolean finalIsUpdate = isUpdate;
        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = tugasBaruText.getText().toString();
                if (finalIsUpdate) {
                    db.updateTugas(bundle.getInt("id"), text);
                } else {
                    ToDoModel tugas = new ToDoModel();
                    tugas.setTugas(text);
                    tugas.setStatus(0);
                    db.insertTugas(tugas);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener)
            ((DialogCloseListener)activity).handleDialogClose(dialog);
    }
}
