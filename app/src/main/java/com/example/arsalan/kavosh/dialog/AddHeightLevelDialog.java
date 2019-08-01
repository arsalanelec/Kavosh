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

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogAddHeightLevelBinding;
import com.example.arsalan.kavosh.model.HeightLevel;
import com.example.arsalan.kavosh.model.MyConst;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddHeightLevelDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddHeightLevelDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddHeightLevelDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private HeightLevel mHeightLevel;

    private OnFragmentInteractionListener mListener;
    private int mIndex;

    public AddHeightLevelDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param heightLevel Parameter 1.
     * @return A new instance of fragment AddHeightLevelDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static AddHeightLevelDialog newInstance(HeightLevel heightLevel, int index) {
        AddHeightLevelDialog fragment = new AddHeightLevelDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, heightLevel);
        args.putInt(ARG_PARAM2, 0);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHeightLevel = getArguments().getParcelable(ARG_PARAM1);
            mIndex = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DialogAddHeightLevelBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_height_level, container, false);
        if (mHeightLevel == null) {
            mHeightLevel = new HeightLevel();
        } else {
            binding.etValue.setText(String.valueOf(mHeightLevel.getValue()));
            binding.spnCoordination.setSelection(mHeightLevel.getCoordination() - 1);
            binding.spnReference.setSelection(mHeightLevel.getReference() - 1);
        }
        binding.btnSubmit.setOnClickListener(view -> {
            if (binding.etValue.getText().toString().isEmpty()) {
                binding.etValue.setError("مقدار خالی است.");
                binding.etValue.requestFocus();
                return;
            }

            mHeightLevel.setValue(Double.parseDouble(binding.etValue.getText().toString()));
            mHeightLevel.setCoordination(binding.spnCoordination.getSelectedItemPosition() + 1);
            mHeightLevel.setReference(binding.spnReference.getSelectedItemPosition() + 1);
            Intent intent = new Intent();
            intent.putExtra(MyConst.EXTRA_MODEL, mHeightLevel);
            intent.putExtra(MyConst.EXTRA_INDEX, mIndex);
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
            dismiss();
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
