package com.example.arsalan.kavosh.dialog;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogAddFoundBinding;
import com.example.arsalan.kavosh.model.FoundDetail;
import com.example.arsalan.kavosh.model.MyConst;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFoundDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFoundDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFoundDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mLayerName;
    private int mFoundType;

    private OnFragmentInteractionListener mListener;

    public AddFoundDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param layerName Parameter 1.
     * @param type      Parameter 2.
     * @return A new instance of fragment AddFoundDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFoundDialog newInstance(String layerName, int type) {
        AddFoundDialog fragment = new AddFoundDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, layerName);
        args.putInt(ARG_PARAM2, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLayerName = getArguments().getString(ARG_PARAM1);
            mFoundType = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DialogAddFoundBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_found, container, false);
        binding.txtRegistrationNum.setText(mListener.getNewRegistrationCode());
        binding.btnSubmit.setOnClickListener(view -> {
            FoundDetail foundDetail = new FoundDetail();
            foundDetail.setRegisterNum(mListener.getNewRegistrationCode());
            foundDetail.setName(binding.etName.getText().toString());
            foundDetail.setDescription(binding.etDescription.getText().toString());
            foundDetail.setX(binding.etX.getText().toString().isEmpty() ? 0.0 : Double.parseDouble(binding.etX.getText().toString()));
            foundDetail.setY(binding.etY.getText().toString().isEmpty() ? 0.0 : Double.parseDouble(binding.etY.getText().toString()));
            foundDetail.setZ(binding.etZ.getText().toString().isEmpty() ? 0.0 : Double.parseDouble(binding.etZ.getText().toString()));
            foundDetail.setQuantity(binding.etQuantity.getText().toString().isEmpty() ? 0 : Integer.valueOf(binding.etQuantity.getText().toString()));
            foundDetail.setWeight(binding.etWeight.getText().toString().isEmpty() ? 0 : Double.parseDouble(binding.etWeight.getText().toString()));
            foundDetail.setFoundType(binding.spnFoundType.getSelectedItemPosition() + 1);
            foundDetail.setContainerType(binding.spnContainerType.getSelectedItemPosition() + 1);
            foundDetail.setType(mFoundType);

            foundDetail.setLayerName(mLayerName);
            Gson gson = new Gson();
            String foundJson = gson.toJson(foundDetail);
            Intent intent = new Intent();
            intent.putExtra(MyConst.EXTRA_MODEL, foundJson);
            intent.putExtra(MyConst.EXTRA_TYPE, mFoundType);
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
            dismiss();
            // foundDetail.setRegisterNum(mListener.getNewRegistrationCode());
        });
        binding.btnCancel.setOnClickListener(view -> dismiss());
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
        String getNewRegistrationCode();

    }
}
