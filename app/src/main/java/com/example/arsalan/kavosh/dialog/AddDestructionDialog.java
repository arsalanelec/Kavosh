package com.example.arsalan.kavosh.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogAddDestructionBinding;
import com.example.arsalan.kavosh.model.Destruction;
import com.example.arsalan.kavosh.model.MyConst;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddDestructionDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddDestructionDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDestructionDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mIndex;
    private Destruction mDestruction;

    private OnFragmentInteractionListener mListener;

    public AddDestructionDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param index       Parameter 1.
     * @param destruction Parameter 2.
     * @return A new instance of fragment AddDestructionDialog.
     */
    public static AddDestructionDialog newInstance(int index, Destruction destruction) {
        AddDestructionDialog fragment = new AddDestructionDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, index);
        args.putParcelable(ARG_PARAM2, destruction);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIndex = getArguments().getInt(ARG_PARAM1);
            mDestruction = getArguments().getParcelable(ARG_PARAM2);
        }
        if (mDestruction == null) mDestruction = new Destruction();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DialogAddDestructionBinding b = DataBindingUtil.inflate(inflater, R.layout.dialog_add_destruction, container, false);
        b.setDestruction(mDestruction);
        ArrayAdapter<String> textAdapter1 = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.array_destruction_factor));
        b.etDestruction.setAdapter(textAdapter1);
        b.btnSubmit.setOnClickListener(btn -> {
            mDestruction.setCoordination(b.spnCoordination.getSelectedItemPosition());
            mDestruction.setFactorName(b.etDestruction.getText().toString());
            mDestruction.setFactorType(b.spnDestructionType.getSelectedItemPosition());
            mDestruction.setLevel(b.spnDestructionLevel.getSelectedItemPosition());
            Intent intent = new Intent();
            intent.putExtra(MyConst.EXTRA_MODEL, mDestruction);
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
            dismiss();
        });
        b.btnCancel.setOnClickListener(btn -> dismiss());


        return b.getRoot();
    }

    @Override
    public void onStart() {

        super.onStart();
        getDialog().getWindow()
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
