package com.example.arsalan.kavosh.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogAddCompositionBinding;
import com.example.arsalan.kavosh.model.Composition;
import com.example.arsalan.kavosh.model.MyConst;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import static android.app.Activity.RESULT_OK;


public class AddCompositionDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddCompositionDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCompositionDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCompositionDialog newInstance(String param1, String param2) {
        AddCompositionDialog fragment = new AddCompositionDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DialogAddCompositionBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_composition, container, false);
        View view = binding.getRoot();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        binding.btnSubmit.setOnClickListener(view1 -> {
            boolean error = false;
            if (binding.etType.getText().toString().isEmpty()) {
                binding.etType.setError("این فیلد نمی تواند خالی باشد");
                binding.etType.requestFocus();
                error = true;
            }
            if (error) return;
            Composition shape = new Composition();
            shape.setDimension(binding.etDimension.getText().toString());
            shape.setPercent(binding.etPercent.getText().toString());
            shape.setType(binding.etType.getText().toString());
            List<String> shapeList = new ArrayList<>();
            if (binding.rbCylindrical.isChecked()) shapeList.add("استوانه ای");
            if (binding.rbIrregular.isChecked()) shapeList.add("نامنظم");
            if (binding.rbPolygon.isChecked()) shapeList.add("چند وجهی");
            if (binding.rbSphere.isChecked()) shapeList.add("گرد");
            shape.setShapes(shapeList);
            shape.setMeter_mili(binding.spnMeter.getSelectedItemPosition());
            Intent intent = new Intent();
            intent.putExtra(MyConst.EXTRA_MODEL, shape);
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
            dismiss();
        });
        binding.btnCancel.setOnClickListener(view1 -> {
            dismiss();
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    @Override
    public void onStart() {

        super.onStart();
        getDialog().getWindow()
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

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
