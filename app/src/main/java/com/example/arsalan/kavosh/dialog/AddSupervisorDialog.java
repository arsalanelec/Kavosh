package com.example.arsalan.kavosh.dialog;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogAddSupervisorBinding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.model.User;
import com.example.arsalan.kavosh.retrofit.ApiInterface;

import java.util.Date;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddSupervisorDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddSupervisorDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSupervisorDialog extends DialogFragment implements Injectable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "AddSupervisorDialog";
    @Inject
    Token mToken;
    @Inject
    Retrofit mRetrofit;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private User mUser = new User();
    private DialogAddSupervisorBinding bind;
    private PersianCalendar initDate;
    private Date mStartDate;

    public AddSupervisorDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddSupervisorDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static AddSupervisorDialog newInstance(String param1, String param2) {
        AddSupervisorDialog fragment = new AddSupervisorDialog();
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
        bind = DataBindingUtil.inflate(inflater, R.layout.dialog_add_supervisor, container, false);
        initDate = new PersianCalendar();
        mStartDate = initDate.getGregorianChange();
        bind.txtStartDate.setText(initDate.getPersianLongDate());
        bind.txtStartDate.setOnClickListener(v -> {
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
                            mStartDate = persianCalendar.getTime();
                            bind.txtStartDate.setText(persianCalendar.getPersianLongDate());
                        }

                        @Override
                        public void onDismissed() {

                        }
                    });
            datePicker.show();
        });
        bind.btnSearchUser.setOnClickListener(view1 ->
        {
            mUser.setId("");
            if (bind.etNatId.getText().toString().isEmpty()) {
                bind.etNatId.setError("کد ملی سرپرست را وارد نمایید");
                return;
            }
            bind.txtSupervisorName.setVisibility(View.GONE);
            bind.prgWaiting.setVisibility(View.VISIBLE);

            searchUserWeb(bind.etNatId.getText().toString());
            //
        });
        bind.btnCancel.setOnClickListener(btn -> dismiss());
        bind.btnCreate.setOnClickListener(btn -> {
            if (mUser.getId().isEmpty()) {
                bind.etNatId.setError("کد ملی سرپرست را وارد کنید");
                bind.etNatId.requestFocus();
                return;
            }
            if (mUser.getNationalId() == null || mUser.getNationalId().isEmpty()) {
                bind.etNatId.setError("کد ملی سرپرست را وارد کنید");
                bind.etNatId.requestFocus();
                return;
            }
            Intent i = new Intent();
            i.putExtra(MyConst.EXTRA_ID, mUser.getId());
            i.putExtra(MyConst.EXTRA_NAME, mUser.getName());
            i.putExtra(MyConst.EXTRA_DATE, mStartDate.getTime());
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, i);
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

    private void searchUserWeb(String nationalId) {
        Call<User> call = mRetrofit.create(ApiInterface.class).findUserByNatId(mToken.getAccessToken(), nationalId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                bind.txtSupervisorName.setVisibility(View.VISIBLE);
                bind.prgWaiting.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    bind.txtSupervisorName.setText(response.body().getName());
                    mUser = response.body();
                } else {
                    bind.etNatId.setError("کد ملی وارد شده یافت نشد");
                    bind.etNatId.requestFocus();
                    bind.txtSupervisorName.setVisibility(View.VISIBLE);
                    bind.prgWaiting.setVisibility(View.GONE);
                    new AlertDialog
                            .Builder(getContext())
                            .setTitle("کد ملی یافت نشد")
                            .setMessage("موارد زیر را بررسی نمایید:\nسرپرست مورد نظر باید در سامانه ثبت نام نماید.\nجهت وارد کردن سرپرست کارکاه باید به اینترنت متصل باشید.\nدرصورت عدم ثبت نام سرپرست به ادمین تماس حاصل فرمایید.")
                            .setPositiveButton("متوجه شدم", null)
                            .create().show();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                bind.txtSupervisorName.setVisibility(View.VISIBLE);
                bind.prgWaiting.setVisibility(View.GONE);
                Log.d(TAG, "searchUserWeb:onFailure: " + t.getLocalizedMessage());
            }
        });

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
