package com.example.arsalan.kavosh.dialog;

import android.app.Activity;
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
import com.example.arsalan.kavosh.databinding.DialogAddEditStairDetailBinding;
import com.example.arsalan.kavosh.model.FeatureStairDetail;
import com.example.arsalan.kavosh.model.MyConst;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddEditStairDetailDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddEditStairDetailDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditStairDetailDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mIndex;
    private FeatureStairDetail mFeatureStairDetail;

    private OnFragmentInteractionListener mListener;

    public AddEditStairDetailDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param index    Parameter 1.
     * @param hillSide Parameter 2.
     * @return A new instance of fragment AddHillSideDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEditStairDetailDialog newInstance(int index, FeatureStairDetail hillSide) {
        AddEditStairDetailDialog fragment = new AddEditStairDetailDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, index);
        args.putParcelable(ARG_PARAM2, hillSide);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIndex = getArguments().getInt(ARG_PARAM1);
            mFeatureStairDetail = getArguments().getParcelable(ARG_PARAM2);
        }
        if (mFeatureStairDetail == null) mFeatureStairDetail = new FeatureStairDetail();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DialogAddEditStairDetailBinding b = DataBindingUtil.inflate(inflater, R.layout.dialog_add_edit_stair_detail, container, false);
        b.etLength.setText(mFeatureStairDetail.getLength());
        b.etHeight.setText(mFeatureStairDetail.getHeight());
        b.etWidth.setText(mFeatureStairDetail.getWidth());
        b.etNumber.setText(mFeatureStairDetail.getNumber());
        b.btnSubmit.setOnClickListener(btn -> {
            mFeatureStairDetail.setLength(b.etLength.getText().toString());
            mFeatureStairDetail.setHeight(b.etHeight.getText().toString());
            mFeatureStairDetail.setWidth(b.etWidth.getText().toString());
            mFeatureStairDetail.setNumber(b.etNumber.getText().toString());
            Intent intent = new Intent();
            intent.putExtra(MyConst.EXTRA_MODEL, mFeatureStairDetail);
            intent.putExtra(MyConst.EXTRA_INDEX, mIndex);
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
            dismiss();

        });
        b.btnCancel.setOnClickListener(btn -> dismiss());
        return b.getRoot();
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
        }/* else {
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
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


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
