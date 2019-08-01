package com.example.arsalan.kavosh.dialog;


import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogPhotoReviewBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoReviewFromActivityDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoReviewFromActivityDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mUri;
    private OnFragmentInteractionListener mListener;


    public PhotoReviewFromActivityDialog() {
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
    public static PhotoReviewFromActivityDialog newInstance(String url) {
        PhotoReviewFromActivityDialog fragment = new PhotoReviewFromActivityDialog();
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
            mListener.onPhotoTitleAdded(binding.etTitle.getText().toString());
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onPhotoTitleAdded(String title);
    }
}
