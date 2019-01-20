package com.example.arsalan.kavosh.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogAddHillSideBinding;
import com.example.arsalan.kavosh.model.Hillside;
import com.example.arsalan.kavosh.model.MyConst;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddHillSideDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddHillSideDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddHillSideDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mIndex;
    private Hillside mHillSide;

    private OnFragmentInteractionListener mListener;

    public AddHillSideDialog() {
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
    public static AddHillSideDialog newInstance(int index, Hillside hillSide) {
        AddHillSideDialog fragment = new AddHillSideDialog();
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
            mHillSide = getArguments().getParcelable(ARG_PARAM2);
        }
        if (mHillSide == null) mHillSide = new Hillside();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DialogAddHillSideBinding b = DataBindingUtil.inflate(inflater, R.layout.dialog_add_hill_side, container, false);
        b.spnCoordination.setSelection(mHillSide.getCoordination());
        b.spnHillSideLevel.setSelection(mHillSide.getSlope());
        b.btnSubmit.setOnClickListener(btn -> {
            mHillSide.setSlope(b.spnHillSideLevel.getSelectedItemPosition());
            mHillSide.setCoordination(b.spnCoordination.getSelectedItemPosition());
            Intent intent = new Intent();
            intent.putExtra(MyConst.EXTRA_MODEL, mHillSide);
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
