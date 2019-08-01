package com.example.arsalan.kavosh.dialog;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogAddVegetationBinding;
import com.example.arsalan.kavosh.model.Vegetation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import static android.app.Activity.RESULT_OK;
import static com.example.arsalan.kavosh.model.MyConst.EXTRA_INDEX;
import static com.example.arsalan.kavosh.model.MyConst.EXTRA_MODEL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddVegetationDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddVegetationDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddVegetationDialog extends DialogFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";


    private Vegetation mVegetation;

    private OnFragmentInteractionListener mListener;
    private int mIndex;

    public AddVegetationDialog() {
        // Required empty public constructor
    }

    public static AddVegetationDialog newInstance(Vegetation vegetation, int index) {
        AddVegetationDialog fragment = new AddVegetationDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM2, vegetation);
        args.putInt(ARG_PARAM3, index);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mVegetation = getArguments().getParcelable(ARG_PARAM2);
            mIndex = getArguments().getInt(ARG_PARAM3, 0);
        }
        if (mVegetation == null) {
            mVegetation = new Vegetation();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DialogAddVegetationBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_vegetation, container, false);
        binding.chkN.setChecked(mVegetation.getCoordinations()[0]);
        binding.chkS.setChecked(mVegetation.getCoordinations()[1]);
        binding.chkNW.setChecked(mVegetation.getCoordinations()[2]);
        binding.chkNE.setChecked(mVegetation.getCoordinations()[3]);
        binding.chkSW.setChecked(mVegetation.getCoordinations()[4]);
        binding.chkSE.setChecked(mVegetation.getCoordinations()[5]);
        binding.chkE.setChecked(mVegetation.getCoordinations()[6]);
        binding.chkW.setChecked(mVegetation.getCoordinations()[7]);

        binding.chkAll.setOnCheckedChangeListener((compoundButton, b) -> {
            binding.chkN.setEnabled(!b);
            binding.chkN.setChecked(b);
            binding.chkS.setEnabled(!b);
            binding.chkS.setChecked(b);
            binding.chkNE.setEnabled(!b);
            binding.chkNE.setChecked(b);
            binding.chkNW.setChecked(b);
            binding.chkNW.setEnabled(!b);
            binding.chkSW.setEnabled(!b);
            binding.chkSW.setChecked(b);
            binding.chkSE.setChecked(b);
            binding.chkSE.setEnabled(!b);
            binding.chkW.setEnabled(!b);
            binding.chkW.setChecked(b);
            binding.chkE.setChecked(b);
            binding.chkE.setEnabled(!b);
        });
        binding.btnSubmit.setOnClickListener(view -> {

            boolean[] coordinations = new boolean[8];
            if (binding.chkN.isChecked()) coordinations[0] = true;
            if (binding.chkS.isChecked()) coordinations[1] = true;
            if (binding.chkNW.isChecked()) coordinations[2] = true;
            if (binding.chkNE.isChecked()) coordinations[3] = true;
            if (binding.chkSW.isChecked()) coordinations[4] = true;
            if (binding.chkSE.isChecked()) coordinations[5] = true;
            if (binding.chkE.isChecked()) coordinations[6] = true;
            if (binding.chkW.isChecked()) coordinations[7] = true;
            if (binding.chkAll.isChecked()) {
                coordinations[0] = true;
                coordinations[1] = true;
                coordinations[2] = true;
                coordinations[3] = true;
                coordinations[4] = true;
                coordinations[5] = true;
                coordinations[6] = true;
                coordinations[7] = true;
            }
            mVegetation.setDescription(binding.etDescription.getText().toString());
            mVegetation.setType(binding.spnVegetationType.getSelectedItemPosition());
            mVegetation.setCoordinations(coordinations);
            Intent intent = new Intent();
            intent.putExtra(EXTRA_MODEL, mVegetation);
            intent.putExtra(EXTRA_INDEX, mIndex);
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
            dismiss();
        });
        binding.btnCancel.setOnClickListener(view -> dismiss());

        int cnt = 0;
        for (boolean b : mVegetation.getCoordinations()) {
            if (b) cnt++;
        }
        if (cnt == 6) {
            binding.chkAll.setChecked(true);
        }

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
        } /*else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onStart() {

        super.onStart();
        getDialog().getWindow()
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


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
