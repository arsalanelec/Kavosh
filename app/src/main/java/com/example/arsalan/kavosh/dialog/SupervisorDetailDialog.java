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
import android.view.WindowManager;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogSupervisorDetailBinding;
import com.example.arsalan.kavosh.model.Supervisor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SupervisorDetailDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SupervisorDetailDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SupervisorDetailDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Supervisor mSupervisor;

    private OnFragmentInteractionListener mListener;

    public SupervisorDetailDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param supervisor Parameter 1.
     * @return A new instance of fragment SupervisorDetailDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static SupervisorDetailDialog newInstance(Supervisor supervisor) {
        SupervisorDetailDialog fragment = new SupervisorDetailDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, supervisor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSupervisor = getArguments().getParcelable(ARG_PARAM1);
        }
        if (mSupervisor == null)
            throw new RuntimeException("supervisor dialog:supervisor must sent");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DialogSupervisorDetailBinding bind = DataBindingUtil.inflate(inflater, R.layout.dialog_supervisor_detail, container, false);
        bind.setSupervisor(mSupervisor);
        bind.btnClose.setOnClickListener(v -> dismiss());
        return bind.getRoot();
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
        } /*else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow()
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
