package com.example.arsalan.kavosh.dialog;

import android.app.Fragment;
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
import com.example.arsalan.kavosh.databinding.DialogAddLayerPositionBinding;
import com.example.arsalan.kavosh.model.LayerCoordination;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import static android.app.Activity.RESULT_OK;
import static com.example.arsalan.kavosh.model.MyConst.EXTRA_INDEX;
import static com.example.arsalan.kavosh.model.MyConst.EXTRA_MODEL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddLayerPositionDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddLayerPositionDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddLayerPositionDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    // TODO: Rename and change types of parameters
    private String mTitle;
    private LayerCoordination mLayerCoordination;

    private OnFragmentInteractionListener mListener;
    private int mIndex;
    private String[] mLayerNames;

    public AddLayerPositionDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AddLayerPositionDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static AddLayerPositionDialog newInstance(String param1, LayerCoordination layerCoordination, int index, String[] layerNames) {
        AddLayerPositionDialog fragment = new AddLayerPositionDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putParcelable(ARG_PARAM2, layerCoordination);
        args.putInt(ARG_PARAM3, index);
        args.putStringArray(ARG_PARAM4, layerNames);

        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static AddLayerPositionDialog newInstance(String param1, String[] layerNames) {
        AddLayerPositionDialog fragment = new AddLayerPositionDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putStringArray(ARG_PARAM4, layerNames);

        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_PARAM1);
            mLayerCoordination = getArguments().getParcelable(ARG_PARAM2);
            mIndex = getArguments().getInt(ARG_PARAM3, 0);
            mLayerNames = getArguments().getStringArray(ARG_PARAM4);
        }
        if (mLayerCoordination == null) {
            mLayerCoordination = new LayerCoordination();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DialogAddLayerPositionBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_layer_position, container, false);
        binding.txtTitle.setText(mTitle);
        ArrayAdapter<String> arrAdapter1 = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line, mLayerNames);
        binding.spnLayerNames.setAdapter(arrAdapter1);

        for (int i = 0; i < mLayerNames.length; i++) {
            if (mLayerNames[i].equals(mLayerCoordination.getName())) {
                binding.spnLayerNames.setSelection(i);
                break;
            }
        }

        binding.chkN.setChecked(mLayerCoordination.getCoordinations()[0]);
        binding.chkS.setChecked(mLayerCoordination.getCoordinations()[1]);
        binding.chkNW.setChecked(mLayerCoordination.getCoordinations()[2]);
        binding.chkNE.setChecked(mLayerCoordination.getCoordinations()[3]);
        binding.chkSW.setChecked(mLayerCoordination.getCoordinations()[4]);
        binding.chkSE.setChecked(mLayerCoordination.getCoordinations()[5]);
        binding.chkE.setChecked(mLayerCoordination.getCoordinations()[6]);
        binding.chkW.setChecked(mLayerCoordination.getCoordinations()[7]);

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
            mLayerCoordination.setName((String) binding.spnLayerNames.getSelectedItem());
            mLayerCoordination.setCoordinations(coordinations);
            Intent intent = new Intent();
            intent.putExtra(EXTRA_MODEL, mLayerCoordination);
            intent.putExtra(EXTRA_INDEX, mIndex);
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
            dismiss();
        });
        binding.btnCancel.setOnClickListener(view -> dismiss());

        int cnt = 0;
        for (boolean b : mLayerCoordination.getCoordinations()) {
            if (b) cnt++;
        }
        if (cnt == 6) {
            binding.chkAll.setChecked(true);
        }

        return binding.getRoot();
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
