package com.example.arsalan.kavosh.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.FragmentFeaturePositionBinding;
import com.example.arsalan.kavosh.model.FeaturePosition;
import com.google.gson.Gson;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeaturePositionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeaturePositionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeaturePositionFragment extends androidx.fragment.app.Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FeaturePosition mFeaturePosition;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static final String TAG = "FeaturePositionFragment";
    private FragmentFeaturePositionBinding bind;

    public FeaturePositionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param featurePositionJson Parameter 1.
     * @return A new instance of fragment FeaturePositionFragment.
     */

    public static FeaturePositionFragment newInstance(String featurePositionJson) {
        FeaturePositionFragment fragment = new FeaturePositionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, featurePositionJson);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Gson gson = new Gson();
            String json = getArguments().getString(ARG_PARAM1);
            mFeaturePosition = gson.fromJson(json, FeaturePosition.class);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (mFeaturePosition == null) {
            mFeaturePosition = new FeaturePosition();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            Log.d(TAG, "setUserVisibleHint: ");
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        bind.rdGroup.setVisibility(View.VISIBLE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_feature_position, container, false);
        bind.setFeaturePosition(mFeaturePosition);
        ArrayAdapter<String> textAdapter2 = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.array_tools));
        bind.txtTools.setAdapter(textAdapter2);
        bind.txtTools.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        bind.txtTools.setOnFocusChangeListener((view, b) -> {
            if (b) bind.txtTools.showDropDown();
        });


        bind.rdGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbType1:
                    mFeaturePosition.setContextureType(0);
                    break;
                case R.id.rbType2:
                    mFeaturePosition.setContextureType(1);
                    break;
                case R.id.rbType3:
                    mFeaturePosition.setContextureType(2);
                    break;
            }
            Gson gson = new Gson();

            mListener.onUpdatePosition(gson.toJson(mFeaturePosition));
        });
        mFeaturePosition.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Log.d(TAG, "onPropertyChanged: ");
                Gson gson = new Gson();
                mListener.onUpdatePosition(gson.toJson(mFeaturePosition));
            }
        });
        return bind.getRoot();
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
        // TODO: Update argument type and name
        void onUpdatePosition(String positionJson);
    }
}
