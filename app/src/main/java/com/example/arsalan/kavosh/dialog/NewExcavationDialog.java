package com.example.arsalan.kavosh.dialog;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.model.Project;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.room.ProjectDao;
import com.example.arsalan.kavosh.utils.UTM;
import com.example.arsalan.kavosh.utils.WGS84;
import com.example.arsalan.kavosh.wokrmanager.ExcavationUploadWorker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewExcavationDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewExcavationDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewExcavationDialog extends DialogFragment implements View.OnClickListener, Injectable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQ_ADD_LOCATION = 1000;
    private static final int REQ_ENABLE_GPS = 1002;
    private static final String TAG = "NewExcavationDialog";
    @Inject
    Retrofit mRetrofit;

    @Inject
    SharedPreferences mPreferences;

    @Inject
    Token mToken;

    @Inject
    ProjectDao mProjectDao;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EditText etName;
    private EditText etLicenseNo;
    private TextView txtLicenseStartDate;
    private TextView txtLicenseEndDate;

    private EditText etLocationE;
    private EditText etLocationN;
    private EditText etLocationEL;
    private RadioButton radHiSeas;
    private RadioButton radBenchmark;
    private Button btnCreate;
    private Button btnCancel;
    private FloatingActionButton btnAddLocation;


    private int mElevationRef = 0;
    private PersianDatePickerDialog mDatePicker;
    private PersianDatePickerDialog mDateEndPicker;
    private PersianCalendar initDate;
    private LatLng mLatLong;
    private Date mLicenseStartDate;
    private UTM mUtm;

    private Date mLicenseEndDate;

    public NewExcavationDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewExcavationDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static NewExcavationDialog newInstance(String param1, String param2) {
        NewExcavationDialog fragment = new NewExcavationDialog();
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
        PersianCalendar persianCalendar = new PersianCalendar();
        mLicenseEndDate = persianCalendar.getGregorianChange();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_excavation, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initDate = new PersianCalendar();
        mLicenseStartDate = initDate.getTime();
        //  initDate.setTime(new Date());
        // initDate.setPersianDate(1370, 3, 13);
        findViews(v);

        etLocationE.setOnFocusChangeListener((view, b) -> {
            if (!b && !etLocationE.getText().toString().isEmpty()) {
                if (etLocationE.getText().toString().indexOf(".") == 0) {
                    etLocationE.setText("0" + etLocationE.getText());
                } else if (etLocationE.getText().toString().indexOf(".") == etLocationE.getText().length() - 1) {
                    etLocationE.setText(etLocationE.getText().delete(etLocationE.getText().length() - 1, etLocationE.getText().length()));
                }
            }
        });

        etLocationN.setOnFocusChangeListener((view, b) -> {
            if (!b && !etLocationN.getText().toString().isEmpty()) {
                if (etLocationN.getText().toString().indexOf(".") == 0) {
                    etLocationN.setText("0" + etLocationN.getText());
                } else if (etLocationN.getText().toString().indexOf(".") == etLocationN.getText().length() - 1) {
                    etLocationN.setText(etLocationN.getText().delete(etLocationN.getText().length() - 1, etLocationN.getText().length()));
                }
            }
        });
        etLocationEL.setOnFocusChangeListener((view, b) -> {
            if (!b && !etLocationEL.getText().toString().isEmpty()) {
                if (etLocationEL.getText().toString().indexOf(".") == 0) {
                    etLocationEL.setText("0" + etLocationEL.getText());
                } else if (etLocationEL.getText().toString().indexOf(".") == etLocationEL.getText().length() - 1) {
                    etLocationEL.setText(etLocationEL.getText().delete(etLocationEL.getText().length() - 1, etLocationEL.getText().length()));
                }
            }
        });
        txtLicenseStartDate.setText(initDate.getPersianLongDate());
        txtLicenseEndDate.setText(initDate.getPersianLongDate());
        return v;
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
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-08-28 20:36:51 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews(View v) {
        etName = v.findViewById(R.id.etName);
        etLicenseNo = v.findViewById(R.id.etLicenseNo);
        txtLicenseStartDate = v.findViewById(R.id.txt_start_date);
        txtLicenseEndDate = v.findViewById(R.id.txtLicenseEndDate);

        etLocationE = v.findViewById(R.id.etLocationE);
        etLocationN = v.findViewById(R.id.etLocationN);
        etLocationEL = v.findViewById(R.id.etLocationEL);
        radHiSeas = v.findViewById(R.id.radHiSeas);
        radBenchmark = v.findViewById(R.id.radBenchmark);
        btnCreate = v.findViewById(R.id.btnCreate);
        btnCancel = v.findViewById(R.id.btnCancel);
        btnAddLocation = v.findViewById(R.id.btnAddLocation);

        radHiSeas.setOnClickListener(this);
        radBenchmark.setOnClickListener(this);
        btnCreate.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        txtLicenseStartDate.setOnClickListener(this);
        btnAddLocation.setOnClickListener(this);

        radHiSeas.setSelected(true);

    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-08-28 20:36:51 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == radHiSeas) {
            mElevationRef = 0;
        } else if (v == radBenchmark) {
            mElevationRef = 1;
        } else if (v == btnCreate) {
            boolean error = false;
            if (etName.getText().toString().isEmpty()) {
                etName.setError("نام خالی است");
                etName.requestFocus();
                error = true;
            }
            if (etLicenseNo.getText().toString().isEmpty()) {
                etLicenseNo.setError("شماره مجوز خالی است");
                etLicenseNo.requestFocus();
                error = true;
            }

            if (etLocationE.getText().toString().isEmpty()) {
                etLocationE.setError("خالی است");
                etLocationE.requestFocus();
                error = true;
            }
            if (etLocationN.getText().toString().isEmpty()) {
                etLocationN.setError("خالی است");
                etLocationN.requestFocus();
                error = true;
            }
            if (etLocationEL.getText().toString().isEmpty()) {
                etLocationEL.setError("خالی است");
                etLocationEL.requestFocus();
                error = true;
            }
            if (error) return;
            addNewExcavationProjectWeb(
                    etName.getText().toString()
                    , etLicenseNo.getText().toString()
                    , mLicenseStartDate
                    , mLicenseEndDate
                    , mUtm == null ? Double.parseDouble(etLocationE.getText().toString()) : mUtm.getEasting()
                    , mUtm == null ? Double.parseDouble(etLocationN.getText().toString()) : mUtm.getNorthing()
                    , Double.parseDouble(etLocationEL.getText().toString().isEmpty() ? "0.0" : etLocationEL.getText().toString())
                    , mElevationRef);
            // getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, null);
            dismiss();
        } else if (v == btnCancel) {
            dismiss();
        } else if (v == txtLicenseStartDate) {
            mDatePicker = new PersianDatePickerDialog(getContext())
                    .setPositiveButtonString("تایید")
                    .setNegativeButton("انصراف")
                    .setTodayButton("امروز")
                    .setTodayButtonVisible(true)
                    .setInitDate(initDate)
                    .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                    .setMinYear(1300)
                    .setActionTextColor(Color.GRAY)
                    .setListener(new Listener() {
                        @Override
                        public void onDateSelected(PersianCalendar persianCalendar) {
                            Toast.makeText(getContext(), persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();
                            mLicenseStartDate = persianCalendar.getGregorianChange();
                            txtLicenseStartDate.setText(persianCalendar.getPersianLongDate());
                        }

                        @Override
                        public void onDismissed() {

                        }
                    });

            mDatePicker.show();
        } else if (v == txtLicenseEndDate) {
            mDateEndPicker = new PersianDatePickerDialog(getContext())
                    .setPositiveButtonString("تایید")
                    .setNegativeButton("انصراف")
                    .setTodayButton("امروز")
                    .setTodayButtonVisible(true)
                    .setInitDate(initDate)
                    .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                    .setMinYear(1300)
                    .setActionTextColor(Color.GRAY)
                    .setListener(new Listener() {
                        @Override
                        public void onDateSelected(PersianCalendar persianCalendar) {
                            Toast.makeText(getContext(), persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();
                            mLicenseEndDate = persianCalendar.getGregorianChange();
                            txtLicenseEndDate.setText(persianCalendar.getPersianLongDate());
                        }

                        @Override
                        public void onDismissed() {

                        }
                    });
            mDateEndPicker.show();
        } else if (v == btnAddLocation) {
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
        locationDialog.setTargetFragment(NewExcavationDialog.this, REQ_ADD_LOCATION);
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
                etLocationE.setText(String.valueOf(mUtm.getEasting()));
                etLocationN.setText(String.valueOf(mUtm.getNorthing()));
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

    private void addNewExcavationProjectWeb(String name, String licenseNum, Date licenseDateStart, Date licenseDateEnd, double easting, double northing, double elevation, int elRefId) {
        Project project = new Project();
        project.setName(name);
        project.setType(1);
        mProjectDao.saveProject(project);

        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", new Locale("en", "gb"));

        Data inputData = new Data.Builder()
                .putString("token", mToken.getAccessToken())
                .putString("id", project.getId())
                .putString("name", name)
                .putString("license_num", licenseNum)
                .putString("license_date", fmt.format(licenseDateStart))
                .putString("license_duration", fmt.format(licenseDateEnd))
                .putString("easting", String.valueOf(easting))
                .putString("northing", String.valueOf(northing))

                .putString("elevation", String.valueOf(elevation))
                .putString("elevationRef", String.valueOf(elRefId))
                .build();

        OneTimeWorkRequest uploadWork = new OneTimeWorkRequest.Builder(ExcavationUploadWorker.class)
                .setConstraints(constraints).setInputData(inputData).build();
        WorkManager.getInstance().enqueue(uploadWork);
        WorkManager.getInstance().getWorkInfoByIdLiveData(uploadWork.getId())
                .observe(NewExcavationDialog.this, workStatus -> {
                    // Do something with the status
                    if (workStatus != null && workStatus.getState().isFinished()) {

                        Log.d(TAG, "addNewExcavationProjectWeb: finished!");
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
