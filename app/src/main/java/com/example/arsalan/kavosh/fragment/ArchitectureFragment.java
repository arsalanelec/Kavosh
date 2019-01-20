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
import com.example.arsalan.kavosh.databinding.FragmentArchitectureBinding;
import com.example.arsalan.kavosh.model.SurFoundArchitecture;
import com.google.gson.Gson;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArchitectureFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArchitectureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArchitectureFragment extends androidx.fragment.app.Fragment {

    private static final String TAG = "ArchitectureFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mJsonContent;

    private OnFragmentInteractionListener mListener;
    private SurFoundArchitecture mArchitecture;

    public ArchitectureFragment() {
        // Required empty public constructor
    }

    public static ArchitectureFragment newInstance(String param1) {
        ArchitectureFragment fragment = new ArchitectureFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mJsonContent = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentArchitectureBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_architecture, container, false);
        try {
            Gson gson = new Gson();
            mArchitecture = gson.fromJson(mJsonContent, SurFoundArchitecture.class);
        } catch (Exception e) {

        }
        if (mArchitecture == null) mArchitecture = new SurFoundArchitecture();

        ArrayAdapter<String> textAdapter1 = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.array_arc_condition));
        binding.txtCondition.setAdapter(textAdapter1);
        binding.txtCondition.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        binding.txtCondition.setOnFocusChangeListener((view, b) -> {
            if (b) binding.txtCondition.showDropDown();
        });

        ArrayAdapter<String> textAdapter2 = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.array_arc_material));
        binding.txtMaterial.setAdapter(textAdapter2);
        binding.txtMaterial.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        binding.txtMaterial.setOnFocusChangeListener((view, b) -> {
            if (b) binding.txtMaterial.showDropDown();
        });

        ArrayAdapter<String> textAdapter3 = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.array_arc_usage));
        binding.txtUsage.setAdapter(textAdapter3);
        binding.txtUsage.setOnFocusChangeListener((view, b) -> {
            if (b) binding.txtUsage.showDropDown();
        });

        binding.setArchitecture(mArchitecture);
        mArchitecture.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Gson gson = new Gson();
                mListener.onUpdateSurFound(gson.toJson(mArchitecture));
                Log.d(TAG, "onPropertyChanged: ");
            }
        });

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
        void onUpdateSurFound(String Json);
    }
}
