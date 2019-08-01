package com.example.arsalan.kavosh.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogPhotoReviewBinding;
import com.example.arsalan.kavosh.model.MyConst;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoReviewDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoReviewDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mUri;


    public PhotoReviewDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param url Parameter 1.
     * @return A new instance of fragment PhotoReviewDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static PhotoReviewDialog newInstance(String url) {
        PhotoReviewDialog fragment = new PhotoReviewDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUri = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DialogPhotoReviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_photo_review, container, false);
        binding.imageView.setImageURI(Uri.parse(mUri));
        binding.btnSubmit.setOnClickListener(button -> {
            Intent intent = new Intent();
            intent.putExtra(MyConst.EXTRA_FILE_TITLE, binding.etTitle.getText().toString());
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

            dismiss();
        });
        binding.btnCancel.setOnClickListener(button -> dismiss());
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

}
