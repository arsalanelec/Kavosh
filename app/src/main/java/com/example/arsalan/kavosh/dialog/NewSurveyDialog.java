package com.example.arsalan.kavosh.dialog;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.activities.SurveyActivity;
import com.example.arsalan.kavosh.databinding.DialogNewSurveyBinding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Survey;
import com.example.arsalan.kavosh.room.ProjectDao;
import com.example.arsalan.kavosh.room.SurveyDao;
import com.example.arsalan.kavosh.utils.UTM;
import com.example.arsalan.kavosh.utils.WGS84;
import com.example.arsalan.kavosh.wokrmanager.SurveyUploadWorker;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

import javax.inject.Inject;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewSurveyDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewSurveyDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewSurveyDialog extends DialogFragment implements View.OnClickListener, Injectable {
    private static final String TAG = "NewSurveyDialog";    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQ_ADD_LOCATION = 1000;
    private static final int REQ_ENABLE_GPS = 1002;
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";


    @Inject
    ProjectDao mProjectDao;

    @Inject
    SurveyDao mSurveyDao;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private PersianDatePickerDialog mDatePicker;
    private PersianCalendar initDate;
    private LatLng mLatLong;
    private Date mStartDate;
    private UTM mUtm;
    private Date mLicenseEndDate;
    private DialogNewSurveyBinding binding;
    private String mProjectId;
    private String mProjectName;
    private String registrationNum;
    private String codeName;

    public NewSurveyDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param projectId Parameter 1.
     * @return A new instance of fragment NewSurveyDialog.
     */
    public static NewSurveyDialog newInstance(String projectId, String registrarionCode, String codeName, String projectName) {
        NewSurveyDialog fragment = new NewSurveyDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, projectId);
        args.putString(ARG_PARAM2, registrarionCode);
        args.putString(ARG_PARAM3, codeName);
        args.putString(ARG_PARAM4, projectName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProjectId = getArguments().getString(ARG_PARAM1);
            registrationNum = getArguments().getString(ARG_PARAM2);
            codeName = getArguments().getString(ARG_PARAM3);
            mProjectName = getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_new_survey, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initDate = new PersianCalendar();
        mStartDate = initDate.getGregorianChange();
        //  initDate.setTime(new Date());
        // initDate.setPersianDate(1370, 3, 13);
        binding.etLocationE.setOnFocusChangeListener((view, b) -> {
            if (!b && !binding.etLocationE.getText().toString().isEmpty()) {
                if (binding.etLocationE.getText().toString().indexOf(".") == 0) {
                    binding.etLocationE.setText("0" + binding.etLocationE.getText());
                } else if (binding.etLocationE.getText().toString().indexOf(".") == binding.etLocationE.getText().length() - 1) {
                    binding.etLocationE.setText(binding.etLocationE.getText().delete(binding.etLocationE.getText().length() - 1, binding.etLocationE.getText().length()));
                }
            }
        });

        binding.etLocationN.setOnFocusChangeListener((view, b) -> {
            if (!b && !binding.etLocationN.getText().toString().isEmpty()) {
                if (binding.etLocationN.getText().toString().indexOf(".") == 0) {
                    binding.etLocationN.setText("0" + binding.etLocationN.getText());
                } else if (binding.etLocationN.getText().toString().indexOf(".") == binding.etLocationN.getText().length() - 1) {
                    binding.etLocationN.setText(binding.etLocationN.getText().delete(binding.etLocationN.getText().length() - 1, binding.etLocationN.getText().length()));
                }
            }
        });
        binding.etLocationEL.setOnFocusChangeListener((view, b) -> {
            if (!b && !binding.etLocationEL.getText().toString().isEmpty()) {
                if (binding.etLocationEL.getText().toString().indexOf(".") == 0) {
                    binding.etLocationEL.setText("0" + binding.etLocationEL.getText());
                } else if (binding.etLocationEL.getText().toString().indexOf(".") == binding.etLocationEL.getText().length() - 1) {
                    binding.etLocationEL.setText(binding.etLocationEL.getText().delete(binding.etLocationEL.getText().length() - 1, binding.etLocationEL.getText().length()));
                }
            }
        });
        //  binding.txtStartDate.setText(initDate.getPersianLongDate());
        binding.btnAddLocation.setOnClickListener(this);
        binding.btnCancel.setOnClickListener(this);
        binding.btnCreate.setOnClickListener(this);
        // binding.txtStartDate.setOnClickListener(this);
        binding.txtCodeMin.setText(registrationNum);
        binding.txtCodeMaj.setText(codeName);
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

           /* if (binding.etAddress.getText().toString().isEmpty()) {
                binding.etAddress.setError("نام خالی است");
                binding.etAddress.requestFocus();
                error = true;
            }
            if (binding.etCodeName.getText().toString().isEmpty()) {
                binding.etCodeName.setError("نام خالی است");
                binding.etCodeName.requestFocus();
                error = true;
            }*/

            if (binding.etLocationE.getText().toString().isEmpty()) {
                binding.etLocationE.setError("خالی است");
                binding.etLocationE.requestFocus();
                error = true;
            }
            if (binding.etLocationN.getText().toString().isEmpty()) {
                binding.etLocationN.setError("خالی است");
                binding.etLocationN.requestFocus();
                error = true;
            }
            if (binding.etLocationEL.getText().toString().isEmpty()) {
                binding.etLocationEL.setError("خالی است");
                binding.etLocationEL.requestFocus();
                error = true;
            }
            if (error) return;
            addNewSurveyItemWeb(
                    mProjectId
                    , binding.etName.getText().toString()
                    , binding.spnSurveyType.getSelectedItemPosition()
                    , mUtm == null ? Double.parseDouble(binding.etLocationE.getText().toString()) : mUtm.getEasting()
                    , mUtm == null ? Double.parseDouble(binding.etLocationN.getText().toString()) : mUtm.getNorthing()
                    , Double.parseDouble(binding.etLocationEL.getText().toString().isEmpty() ? "0.0" : binding.etLocationEL.getText().toString())
            );
            // getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, null);
            dismiss();
        } else if (v == binding.btnCancel) {
            dismiss();

        } else if (v == binding.btnAddLocation) {
            statusCheck();


        }
    }

    private void showAddLocationDialog() {

        AddLocationDialog locationDialog;
        if (mLatLong != null && mLatLong.latitude > 0.0) { //by lat lng
            Log.d(TAG, "add location by lat lng: " + mLatLong.latitude + " lng:" + mLatLong.longitude);
            locationDialog = AddLocationDialog.newInstance(mLatLong);

        } else { //by city name
            Log.d(TAG, "add location by city");
            locationDialog = new AddLocationDialog();
        }
        locationDialog.setTargetFragment(NewSurveyDialog.this, REQ_ADD_LOCATION);
        locationDialog.show(getFragmentManager(), "");

    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d(TAG, "statusCheck: provider not available");

            buildAlertMessageNoGps();
        } else {
            showAddLocationDialog();

            Log.d(TAG, "statusCheck: provider is available");
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("جی پی اس گوشی شما خاموش است. آیا مایلید برای پیدا کردن موقعیت فعلی آن را روشن کنید؟")
                .setCancelable(false)
                .setPositiveButton("بلی", (dialog, id) -> {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQ_ENABLE_GPS);
                })
                .setNegativeButton("خیر", (dialog, id) -> {
                    showAddLocationDialog();
                    dialog.cancel();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_ADD_LOCATION) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: lat lang:" + data.getDoubleExtra("Lat", 0.0) + " , " + data.getDoubleExtra("Lng", 0.0));
                mLatLong = new LatLng(data.getDoubleExtra("Lat", 0.0), data.getDoubleExtra("Lng", 0.0));
                WGS84 wgs84 = new WGS84(mLatLong.latitude, mLatLong.longitude);
                mUtm = new UTM(wgs84);
                binding.etLocationE.setText(String.valueOf(mUtm.getEasting()));
                binding.etLocationN.setText(String.valueOf(mUtm.getNorthing()));
            }
        } else if (requestCode == REQ_ENABLE_GPS) {
            showAddLocationDialog();
        }
    }

    @Override
    public void onStart() {

        super.onStart();
        getDialog().getWindow()
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


    }

    private void addNewSurveyItemWeb(String projectId, String name, int type, double easting, double northing, double elevation) {


        Survey survey = new Survey();
        survey.setName(name);
        survey.setType(type);
        survey.setNorthing(northing);
        survey.setEasting(easting);
        survey.setElevation(elevation);
        survey.setRegistrationNum(registrationNum);
        survey.setSurveyProjectId(projectId);
        mSurveyDao.save(survey);

        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        Data inputData = new Data.Builder()
                .putString("id", survey.getId())
                .putString("name", name)
                .putString("type", String.valueOf(type))
                .putString("survey_project_id", projectId)
                .putString("registration_num", survey.getRegistrationNum())
                .putString("easting", String.valueOf(easting))
                .putString("northing", String.valueOf(northing))
                .putString("elevation", String.valueOf(elevation))
                .build();

        Log.d(TAG, "addNewSurveyItemWeb: id:" + survey.getId() + " name:" + name + " surProId:" + projectId);
        OneTimeWorkRequest uploadWork = new OneTimeWorkRequest.Builder(SurveyUploadWorker.class)
                .setConstraints(constraints).setInputData(inputData).build();
        WorkManager.getInstance().enqueue(uploadWork);
        WorkManager.getInstance().getWorkInfoByIdLiveData(uploadWork.getId())
                .observe(NewSurveyDialog.this, workStatus -> {
                    // Do something with the status
                    if (workStatus != null && workStatus.getState().isFinished()) {

                        Log.d(TAG, "addNewSurveyItemWeb: finished!");
                    }

                });

        Intent intent = new Intent();
        intent.putExtra(MyConst.EXTRA_ID, survey.getId());
        intent.putExtra(MyConst.EXTRA_PROJECT_NAME, mProjectName);
        intent.setClass(getActivity(), SurveyActivity.class);
        startActivity(intent);
        dismiss();
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
        void onAddNewSurvey(Survey survey);

    }


}
