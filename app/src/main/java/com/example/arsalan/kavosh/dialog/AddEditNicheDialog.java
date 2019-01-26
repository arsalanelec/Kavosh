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
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Niche;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddEditNicheDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddEditNicheDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditNicheDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mIndex;
    private Niche mNiche;

    private OnFragmentInteractionListener mListener;

    public AddEditNicheDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param index    Parameter 1.
     * @param hillSide Parameter 2.
     * @return A new instance of fragment AddHillSideDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEditNicheDialog newInstance(int index, Niche hillSide) {
        AddEditNicheDialog fragment = new AddEditNicheDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, index);
        args.putParcelable(ARG_PARAM2, hillSide);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIndex = getArguments().getInt(ARG_PARAM1);
            mNiche = getArguments().getParcelable(ARG_PARAM2);
        }
        if (mNiche == null) mNiche = new Niche();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DialogAddNicheBinding b = DataBindingUtil.inflate(inflater, R.layout.dialog_add_niche, container, false);
        b.spnCoordination.setSelection(mNiche.getCoordination());
        b.etDept.setText(mNiche.getDepth());
        b.etHeight.setText(mNiche.getHeight());
        b.etWidth.setText(mNiche.getWidth());
        b.btnSubmit.setOnClickListener(btn -> {
            mNiche.setCoordination(b.spnCoordination.getSelectedItemPosition());
            mNiche.setDepth(b.etDept.getText().toString());
            mNiche.setHeight(b.etHeight.getText().toString());
            mNiche.setWidth(b.etWidth.getText().toString());

            Intent intent = new Intent();
            intent.putExtra(MyConst.EXTRA_MODEL, mNiche);
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
