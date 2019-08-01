package com.example.arsalan.kavosh.dialog;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogRemoveSupervisorBinding;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Supervisor;
import com.example.arsalan.kavosh.model.User;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExitSupervisorDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExitSupervisorDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExitSupervisorDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "AddSupervisorDialog";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private User mUser = new User();
    private DialogRemoveSupervisorBinding bind;
    private PersianCalendar initDate;
    private Date mExitDate;
    private Supervisor mSupervisor;

    public ExitSupervisorDialog() {
        // Required empty public constructor
    }


    public static ExitSupervisorDialog newInstance(Supervisor supervisor) {
        ExitSupervisorDialog fragment = new ExitSupervisorDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, supervisor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSupervisor = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bind = DataBindingUtil.inflate(inflater, R.layout.dialog_remove_supervisor, container, false);
        bind.txtSupervisorName.setText(mSupervisor.getName());
        initDate = new PersianCalendar();
        mExitDate = initDate.getTime();
        bind.txtDate.setText(initDate.getPersianLongDate());
        bind.txtDate.setOnClickListener(v -> {
            PersianDatePickerDialog datePicker = new PersianDatePickerDialog(getContext())
                    .setPositiveButtonString("تایید")
                    .setNegativeButton("انصراف")
                    .setTodayButton("امروز")
                    .setTodayButtonVisible(true)
                    .setInitDate(initDate)
                    .setMinYear(1300)
                    .setActionTextColor(Color.GRAY)
                    .setListener(new Listener() {
                        @Override
                        public void onDateSelected(PersianCalendar persianCalendar) {
                            Toast.makeText(getContext(), persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();
                            mExitDate = persianCalendar.getTime();
                            bind.txtDate.setText(persianCalendar.getPersianLongDate());
                        }

                        @Override
                        public void onDismissed() {

                        }
                    });
            datePicker.show();
        });

        bind.btnCancel.setOnClickListener(btn -> dismiss());
        bind.btnOk.setOnClickListener(btn -> {

            Intent i = new Intent();
            mSupervisor.setDateRemoved(mExitDate);
            mSupervisor.setStatus(0);
            i.putExtra(MyConst.EXTRA_MODEL, mSupervisor);
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, i);
            dismiss();
        });
        return bind.getRoot();
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
