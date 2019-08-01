package com.example.arsalan.kavosh.dialog;


import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogNewProjectBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewProjectDialog extends DialogFragment implements View.OnClickListener {


    public NewProjectDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DialogNewProjectBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_new_project, container, false);
        binding.btnKavosh.setOnClickListener(this);
        binding.btnSurvey.setOnClickListener(this);

        return binding.getRoot();
    }

    //remove title bar from this dialog
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnKavosh: {
                NewExcavationDialog dialog = new NewExcavationDialog();
                dialog.show(getFragmentManager(), "");
                break;
            }
            case R.id.btnSurvey: {
                //   NewSurveyDialog dialog = new NewSurveyDialog();
                NewSurveyProjectDialog dialog = new NewSurveyProjectDialog();
                dialog.show(getFragmentManager(), "");
            }
            break;

        }
        dismiss();
    }
}
