package com.example.arsalan.kavosh.dialog;

import android.app.Dialog;
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
import com.example.arsalan.kavosh.databinding.DialogAddCompositionBinding;
import com.example.arsalan.kavosh.model.Composition;
import com.example.arsalan.kavosh.model.MyConst;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import static android.app.Activity.RESULT_OK;


public class AddCompositionDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Composition mComposition;
    private int mIndex;

    private OnFragmentInteractionListener mListener;

    public AddCompositionDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddCompositionDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCompositionDialog newInstance(Composition composition, int index) {
        AddCompositionDialog fragment = new AddCompositionDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, composition);
        args.putInt(ARG_PARAM2, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mComposition = getArguments().getParcelable(ARG_PARAM1);
            mIndex = getArguments().getInt(ARG_PARAM2, 0);
        }
        if (mComposition == null) mComposition = new Composition();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DialogAddCompositionBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_composition, container, false);
        View view = binding.getRoot();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        binding.etDimension.setText(mComposition.getDimension());
        binding.etPercent.setText(mComposition.getPercent());
        binding.etType.setText(mComposition.getType());
        binding.spnMeter.setSelection(mComposition.getMeter_mili());
        if (mComposition.getShapes()[0]) binding.rbIrregular.setChecked(true);
        if (mComposition.getShapes()[1]) binding.rbSphere.setChecked(true);
        if (mComposition.getShapes()[2]) binding.rbPolygon.setChecked(true);
        if (mComposition.getShapes()[3]) binding.rbCylindrical.setChecked(true);
        
        binding.btnSubmit.setOnClickListener(view1 -> {
            boolean error = false;
            if (binding.etType.getText().toString().isEmpty()) {
                binding.etType.setError("این فیلد نمی تواند خالی باشد");
                binding.etType.requestFocus();
                error = true;
            }
            if (error) return;
            mComposition.setDimension(binding.etDimension.getText().toString());
            mComposition.setPercent(binding.etPercent.getText().toString());
            mComposition.setType(binding.etType.getText().toString());
            if (binding.rbCylindrical.isChecked()) mComposition.getShapes()[3] = true;
            if (binding.rbIrregular.isChecked()) mComposition.getShapes()[0] = true;
            if (binding.rbPolygon.isChecked()) mComposition.getShapes()[2] = true;
            if (binding.rbSphere.isChecked()) mComposition.getShapes()[1] = true;

            mComposition.setMeter_mili(binding.spnMeter.getSelectedItemPosition());
            Intent intent = new Intent();
            intent.putExtra(MyConst.EXTRA_INDEX, mIndex);
            intent.putExtra(MyConst.EXTRA_MODEL, mComposition);
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
            dismiss();
        });
        binding.btnCancel.setOnClickListener(view1 -> {
            dismiss();
        });

        return view;
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
