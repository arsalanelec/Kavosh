package com.example.arsalan.kavosh.dialog;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogAddFeatureBinding;
import com.example.arsalan.kavosh.model.Feature;
import com.example.arsalan.kavosh.model.MyConst;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFeatureDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFeatureDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFeatureDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddFeatureDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFeatureDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFeatureDialog newInstance(String param1, String param2) {
        AddFeatureDialog fragment = new AddFeatureDialog();
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
        // Inflate the layout for this fragment
        DialogAddFeatureBinding bind = DataBindingUtil.inflate(inflater, R.layout.dialog_add_feature, container, false);
        bind.spnStructure.setAdapter(new ArrayAdapter<Feature.MyEnum>(getContext(), android.R.layout.simple_spinner_item, Feature.MyEnum.values()));
        bind.btnCancel.setOnClickListener(b -> dismiss());
        bind.btnSubmit.setOnClickListener(b -> {
            Intent intent = new Intent();
            intent.putExtra(MyConst.EXTRA_INDEX, bind.spnStructure.getSelectedItemPosition());
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
            dismiss();
        });
        return bind.getRoot();
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
