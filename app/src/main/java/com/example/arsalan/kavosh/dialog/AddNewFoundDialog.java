package com.example.arsalan.kavosh.dialog;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogAddNewFoundBinding;
import com.example.arsalan.kavosh.model.Found;
import com.example.arsalan.kavosh.model.FoundDetail;
import com.example.arsalan.kavosh.model.MyConst;
import com.google.gson.Gson;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNewFoundDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewFoundDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    // TODO: Rename and change types of parameters
    private String mExcavationItemId;
    private String mLayerName;

    private String mRegistrationNum;
    private int mType;

    public AddNewFoundDialog() {
        // Required empty public constructor
    }


    public static AddNewFoundDialog newInstance(String excavationItemId, String layerName, String registrationNum, int type) {
        AddNewFoundDialog fragment = new AddNewFoundDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, excavationItemId);
        args.putString(ARG_PARAM2, layerName);
        args.putString(ARG_PARAM3, registrationNum);
        args.putInt(ARG_PARAM4, type);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mExcavationItemId = getArguments().getString(ARG_PARAM1);
            mLayerName = getArguments().getString(ARG_PARAM2);
            mRegistrationNum = getArguments().getString(ARG_PARAM3);
            mType = getArguments().getInt(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DialogAddNewFoundBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_new_found, container, false);

        binding.btnSubmit.setOnClickListener(b -> {
            if (binding.etFoundName.getText().length() == 0) {
                binding.etFoundName.setError("نام خالی است!");
                binding.etFoundName.requestFocus();
                return;
            }
            FoundDetail foundDetail = new FoundDetail();
            foundDetail.setRegisterNum(mRegistrationNum);
            foundDetail.setType(mType);
            foundDetail.setLayerName(mLayerName);
            foundDetail.setName(binding.etFoundName.getText().toString());

            Gson gson = new Gson();
            String foundJson = gson.toJson(foundDetail);
            Found found = new Found();
            found.setContentJson(foundJson);
            found.setType(mType);
            found.setExcavationItemId(mExcavationItemId);
            Intent intent = new Intent();
            intent.putExtra(MyConst.EXTRA_MODEL, found);
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
            dismiss();
        });
        binding.btnCancel.setOnClickListener(b -> dismiss());
        return binding.getRoot();
    }

}
