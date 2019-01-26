package com.example.arsalan.kavosh.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogAddNicheBinding;
import com.example.arsalan.kavosh.model.Laver;
import com.example.arsalan.kavosh.model.MyConst;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddEditLaverDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddEditLaverDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditLaverDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mIndex;
    private Laver mLaver;

    private OnFragmentInteractionListener mListener;

    public AddEditLaverDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param index Parameter 1.
     * @param laver Parameter 2.
     * @return A new instance of fragment AddHillSideDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEditLaverDialog newInstance(int index, Laver laver) {
        AddEditLaverDialog fragment = new AddEditLaverDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, index);
        args.putParcelable(ARG_PARAM2, laver);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIndex = getArguments().getInt(ARG_PARAM1);
            mLaver = getArguments().getParcelable(ARG_PARAM2);
        }
        if (mLaver == null) mLaver = new Laver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DialogAddNicheBinding b = DataBindingUtil.inflate(inflater, R.layout.dialog_add_niche, container, false);
        b.spnCoordination.setSelection(mLaver.getCoordination());
        b.etDept.setText(mLaver.getDepth());
        b.etHeight.setText(mLaver.getLength());
        b.etWidth.setText(mLaver.getWidth());
        b.btnSubmit.setOnClickListener(btn -> {
            mLaver.setCoordination(b.spnCoordination.getSelectedItemPosition());
            mLaver.setDepth(b.etDept.getText().toString());
            mLaver.setLength(b.etHeight.getText().toString());
            mLaver.setWidth(b.etWidth.getText().toString());

            Intent intent = new Intent();
            intent.putExtra(MyConst.EXTRA_MODEL, mLaver);
            intent.putExtra(MyConst.EXTRA_INDEX, mIndex);
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
            dismiss();

        });
        b.btnCancel.setOnClickListener(btn -> dismiss());
        return b.getRoot();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }/* else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
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
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
