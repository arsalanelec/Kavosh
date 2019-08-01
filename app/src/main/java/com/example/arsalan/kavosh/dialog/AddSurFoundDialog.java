package com.example.arsalan.kavosh.dialog;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogAddSurFoundBinding;
import com.example.arsalan.kavosh.model.SurFound;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddSurFoundDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddSurFoundDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSurFoundDialog extends DialogFragment {

    private OnFragmentInteractionListener mListener;

    public AddSurFoundDialog() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DialogAddSurFoundBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_sur_found, container, false);
        binding.btnCancel.setOnClickListener(b -> dismiss());
        binding.btnCreate.setOnClickListener(b -> {
            SurFound surFound = new SurFound();
            surFound.setType(binding.spnSurFoundType.getSelectedItemPosition());
            mListener.onAddSurFound(surFound);
            dismiss();
        });
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

    @Override
    public void onStart() {

        super.onStart();
        getDialog().getWindow()
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onAddSurFound(SurFound surFound);
    }
}
