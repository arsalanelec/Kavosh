package com.example.arsalan.kavosh.dialog;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogNewSurveyProjectBinding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.model.Project;
import com.example.arsalan.kavosh.model.SurveyProject;
import com.example.arsalan.kavosh.room.ProjectDao;
import com.example.arsalan.kavosh.room.SurveyProjectDao;
import com.example.arsalan.kavosh.utils.UTM;
import com.example.arsalan.kavosh.wokrmanager.SurveyProjectUploadWorker;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewSurveyProjectDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewSurveyProjectDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewSurveyProjectDialog extends DialogFragment implements View.OnClickListener, Injectable {
    private static final String TAG = "NewSurveyDialog";    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQ_ADD_LOCATION = 1000;
    private static final int REQ_ENABLE_GPS = 1002;


    @Inject
    ProjectDao mProjectDao;

    @Inject
    SurveyProjectDao mSurveyProjectDao;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private PersianDatePickerDialog mDatePicker;
    private PersianCalendar initDate;
    private LatLng mLatLong;
    private Date mStartDate;
    private Date mEndDate;

    private UTM mUtm;
    private Date mLicenseEndDate;
    private DialogNewSurveyProjectBinding binding;
    TextWatcher watcher_register = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable ed) {
            String editable = ed.toString().toUpperCase();
            if (editable.length() > 0) {
                Character lastChar = editable.charAt(editable.length() - 1);
                String sample = editable;
                String patern = sample.substring(0, editable.length() - 1);
                sample += ", ";
                int cnt2 = 0;
                for (int cnt = 1; cnt < 3; cnt++) {
                    if ((lastChar >= 65 && lastChar < 90)) {
                        lastChar++;
                        sample += patern + lastChar + ", ";


                    } else if (lastChar <= 57) {
                        cnt2++;
                        sample += patern + Integer.valueOf(lastChar - 48 + cnt2) + ", ";

                    } else {
                        patern += (char) (lastChar - 1);
                        sample += patern + "1, ";
                        lastChar = '1';
                    }
                }
                sample += "...";

                binding.txtFeatureCodingSample.setText(sample);
            }
            Log.d(TAG, "afterTextChanged: ");
        }
    };
    private String mLicenceNum = "", mRegistrationNum = "";

    public NewSurveyProjectDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewSurveyDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static NewSurveyProjectDialog newInstance(String param1, String param2) {
        NewSurveyProjectDialog fragment = new NewSurveyProjectDialog();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_new_survey_project, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initDate = new PersianCalendar();
        mStartDate = initDate.getGregorianChange();
        mEndDate = initDate.getGregorianChange();
        //  initDate.setTime(new Date());
        // initDate.setPersianDate(1370, 3, 13);

        binding.txtStartDate.setText(initDate.getPersianLongDate());
        binding.txtEndDate.setText(initDate.getPersianLongDate());
        binding.btnCancel.setOnClickListener(this);
        binding.btnCreate.setOnClickListener(this);
        binding.txtStartDate.setOnClickListener(this);
        binding.txtEndDate.setOnClickListener(this);
        binding.etRegistrationCoding.addTextChangedListener(watcher_register);
        binding.etRegistrationCoding.setText("1000");
        binding.etLicenseNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mLicenceNum = editable.toString();
            }
        });
        binding.etCodeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mRegistrationNum = editable.toString();
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
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-08-28 20:36:51 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == binding.btnCreate) {
            boolean error = false;
            if (binding.etName.getText().toString().isEmpty()) {
                binding.etName.setError("نام خالی است");
                binding.etName.requestFocus();
                error = true;
            }

            if (binding.etAddress.getText().toString().isEmpty()) {
                binding.etAddress.setError("نام خالی است");
                binding.etAddress.requestFocus();
                error = true;
            }
            if (binding.etCodeName.getText().toString().isEmpty()) {
                binding.etCodeName.setError("نام خالی است");
                binding.etCodeName.requestFocus();
                error = true;
            }


            if (error) return;
            addNewSurveyProjectWeb(
                    binding.etName.getText().toString()
                    , 2
                    , (binding.etCodeName.getText().toString())
                    , (binding.etRegistrationCoding.getText().toString())
                    , mStartDate
                    , mEndDate
                    , binding.etAddress.getText().toString()
                    , mLicenceNum
                    , mRegistrationNum
            );
            // getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, null);
            dismiss();
        } else if (v == binding.btnCancel) {
            dismiss();
        } else if (v == binding.txtStartDate) {
            mDatePicker = new PersianDatePickerDialog(getContext())
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
                            Log.d(TAG, "onDateSelected: mStartDate:" + mStartDate);
                            binding.txtStartDate.setText(persianCalendar.getPersianLongDate());
                        }

                        @Override
                        public void onDismissed() {

                        }
                    });

            mDatePicker.show();
        } else if (v == binding.txtEndDate) {
            mDatePicker = new PersianDatePickerDialog(getContext())
                    .setPositiveButtonString("تایید")
                    .setNegativeButton("انصراف")
                    .setTodayButton("امروز")
                    .setTodayButtonVisible(true)
                    .setInitDate(initDate)
                    .setMinYear(PersianDatePickerDialog.THIS_YEAR)
                    .setActionTextColor(Color.GRAY)
                    .setListener(new Listener() {
                        @Override
                        public void onDateSelected(PersianCalendar persianCalendar) {
                            Toast.makeText(getContext(), persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();
                            mEndDate = persianCalendar.getTime();
                            Log.d(TAG, "onDateSelected: mEndDate:" + mStartDate);
                            binding.txtEndDate.setText(persianCalendar.getPersianLongDate());
                        }

                        @Override
                        public void onDismissed() {

                        }
                    });

            mDatePicker.show();
        }
    }


    @Override
    public void onStart() {

        super.onStart();
        getDialog().getWindow()
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


    }

    private void addNewSurveyProjectWeb(String name, int type, String codeName, String codeStartNum, Date dateStarted, Date dateEnd, String address, String licenceNo, String registrationNo) {
        Project project = new Project();
        project.setName(name);
        project.setType(2);

        SurveyProject survey = new SurveyProject();
        survey.setId(project.getId());
        survey.setName(name);
        survey.setAddress(address);
        survey.setCodeName(codeName);
        survey.setCodeStartNum(codeStartNum);
        survey.setType(type);
        survey.setLicenseEnd(dateEnd);
        survey.setLicenseStart(dateStarted);
        survey.setLicenseNum(licenceNo);
        survey.setRegistrationNo(registrationNo);
        mSurveyProjectDao.save(survey);

        mProjectDao.saveProject(project);
        /* ,'status'*/
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", new Locale("en", "gb"));
        Data inputData = new Data.Builder()
                .putString("id", project.getId())
                .putString("name", name)
                .putString("type", String.valueOf(type))
                .putString("code_name", codeName)
                .putString("code_start_num", codeStartNum)
                .putString("license_start", fmt.format(dateStarted))
                .putString("license_end", fmt.format(dateEnd))
                .putString("license_num", licenceNo)
                .putString("registration_num", registrationNo)
                .putString("address", address)
                .build();

        OneTimeWorkRequest uploadWork = new OneTimeWorkRequest.Builder(SurveyProjectUploadWorker.class)
                .setConstraints(constraints).setInputData(inputData).build();
        WorkManager.getInstance().enqueue(uploadWork);
        WorkManager.getInstance().getWorkInfoByIdLiveData(uploadWork.getId())
                .observe(NewSurveyProjectDialog.this, workStatus -> {
                    // Do something with the status
                    if (workStatus != null && workStatus.getState().isFinished()) {

                        Log.d(TAG, "addNewSurveyProjectWeb: finished!");
                    }

                });

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
        void refreshProjectList();
    }


}
