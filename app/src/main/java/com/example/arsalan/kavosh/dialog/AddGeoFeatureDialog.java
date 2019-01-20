package com.example.arsalan.kavosh.dialog;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogAddGeoFeatureBinding;
import com.example.arsalan.kavosh.model.GeoFeature;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import static android.app.Activity.RESULT_OK;
import static com.example.arsalan.kavosh.model.MyConst.EXTRA_INDEX;
import static com.example.arsalan.kavosh.model.MyConst.EXTRA_MODEL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddGeoFeatureDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddGeoFeatureDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddGeoFeatureDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private GeoFeature mGeoFeature;

    private OnFragmentInteractionListener mListener;
    private int mIndex;

    public AddGeoFeatureDialog() {
        // Required empty public constructor
    }

    public static AddGeoFeatureDialog newInstance(GeoFeature geoFeature, int index) {
        AddGeoFeatureDialog fragment = new AddGeoFeatureDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, geoFeature);
        args.putInt(ARG_PARAM2, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGeoFeature = getArguments().getParcelable(ARG_PARAM2);
            mIndex = getArguments().getInt(ARG_PARAM2, 0);
        }
        if (mGeoFeature == null) {
            mGeoFeature = new GeoFeature();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DialogAddGeoFeatureBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_geo_feature, container, false);
        switch (mGeoFeature.getCoordination()) {
            case 0:
                binding.chkNW.setChecked(true);
                break;
            case 1:
                binding.chkNE.setChecked(true);
                break;
            case 2:
                binding.chkSW.setChecked(true);
                break;
            case 3:
                binding.chkSE.setChecked(true);
                break;
            case 4:
                binding.chkE.setChecked(true);
                break;
            case 5:
                binding.chkW.setChecked(true);
                break;


        }

        binding.rgCoordination.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.chkNW:
                    mGeoFeature.setCoordination(0);
                    break;
                case R.id.chkNE:
                    mGeoFeature.setCoordination(1);
                    break;
                case R.id.chkSW:
                    mGeoFeature.setCoordination(2);
                    break;
                case R.id.chkSE:
                    mGeoFeature.setCoordination(3);
                    break;
                case R.id.chkE:
                    mGeoFeature.setCoordination(4);
                    break;
                case R.id.chkW:
                    mGeoFeature.setCoordination(5);
                    break;
            }
        });
        binding.btnSubmit.setOnClickListener(b -> {
            if (binding.etGeoFeatureName.getText().length() < 1) {
                binding.etGeoFeatureName.setError("نام خالی است");
                binding.etGeoFeatureName.requestFocus();
                return;
            }

            if (binding.etGeoFeatureDistance.getText().length() < 1) {
                mGeoFeature.setDistance(0);
            } else {
                mGeoFeature.setDistance(Integer.valueOf(binding.etGeoFeatureDistance.getText().toString()));
            }
            mGeoFeature.setName(binding.etGeoFeatureName.getText().toString());
            Intent intent = new Intent();
            intent.putExtra(EXTRA_MODEL, mGeoFeature);
            intent.putExtra(EXTRA_INDEX, mIndex);
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
            dismiss();

        });

        binding.btnCancel.setOnClickListener(view -> dismiss());

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
