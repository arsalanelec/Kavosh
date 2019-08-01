package com.example.arsalan.kavosh.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogNewExcavationItem1Binding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.model.ExcavationItem;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.model.User;
import com.example.arsalan.kavosh.retrofit.ApiInterface;
import com.example.arsalan.kavosh.room.ExcavationItemDao;
import com.example.arsalan.kavosh.wokrmanager.ExcavationItemSaveWorker;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewExcavationItem1Dialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewExcavationItem1Dialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewExcavationItem1Dialog extends DialogFragment implements Injectable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private final String TAG = "NewExcavationItem1Dia";
    @Inject
    Retrofit mRetrofit;
    @Inject
    Token mToken;
    @Inject
    ExcavationItemDao mExcavationItemDao;
    // TODO: Rename and change types of parameters
    private String mProjectId;
    private String mSupervisorId = "";
    private OnFragmentInteractionListener mListener;
    private DialogNewExcavationItem1Binding bindin;

    public NewExcavationItem1Dialog() {
        // Required empty public constructor
    }


    public static NewExcavationItem1Dialog newInstance(String projectId) {
        NewExcavationItem1Dialog fragment = new NewExcavationItem1Dialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, projectId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProjectId = getArguments().getString(ARG_PARAM1);
        }
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bindin = DataBindingUtil.inflate(inflater, R.layout.dialog_new_excavation_item1, container, false);

        View view = bindin.getRoot();
        bindin.executePendingBindings();
        bindin.btnSearchUser.setOnClickListener(view1 ->
        {
            mSupervisorId = "";
            if (bindin.etNatId.getText().toString().isEmpty()) {
                bindin.etNatId.setError("کد ملی سرپرست را وارد نمایید");
                return;
            }
            searchUserWeb(bindin.etNatId.getText().toString());
            //
        });


        TextWatcher watcher_layer = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable ed) {
                String editable = ed.toString();
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
                    bindin.txtLayerCodingSample.setText(sample);
                }
                Log.d(TAG, "afterTextChanged: ");
            }
        };
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
                    bindin.txtFeatureCodingSample.setText(sample);
                }
                Log.d(TAG, "afterTextChanged: ");
            }
        };

        bindin.etLayerCoding.addTextChangedListener(watcher_layer);
        //   bindin.etLayerCoding.setFilters(new InputFilter[]{myFilter});
        bindin.etLayerCoding.setText("A");

        bindin.etRegistrationCoding.addTextChangedListener(watcher_register);
        //  bindin.etRegistrationCoding.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        bindin.etRegistrationCoding.setText("1001");

        bindin.btnCreate.setOnClickListener(view13 -> {
            Boolean error = false;
            if (mSupervisorId.isEmpty()) {
                error = true;
                bindin.etNatId.setError("کد ملی سرپرست را وارد کنید");
                bindin.etNatId.requestFocus();
            }
            if (bindin.etTrenchTrialName.getText().toString().isEmpty()) {
                error = true;
                bindin.etTrenchTrialName.setError("یک نام وارد کنید");
                bindin.etTrenchTrialName.requestFocus();
            }
            if (bindin.etWorkshopTrenchTrialName.getText().toString().isEmpty()) {
                error = true;
                bindin.etWorkshopTrenchTrialName.setError("یک نام وارد کنید");
                bindin.etWorkshopTrenchTrialName.requestFocus();
            }
            if (bindin.etLayerCoding.getText().toString().isEmpty()) {
                bindin.etLayerCoding.setText("A");
            }
            if (error) return;

            addNewExcavationProjectWeb(
                    (int) bindin.spnType.getSelectedItemId(),
                    bindin.etWorkshopTrenchTrialName.getText().toString(),
                    (int) bindin.spnSubType.getSelectedItemId(),
                    bindin.etTrenchTrialName.getText().toString(),
                    mSupervisorId,
                    bindin.txtName.getText().toString(),
                    bindin.etLayerCoding.getText().toString(),
                    bindin.etRegistrationCoding.getText().toString()

            );
            dismiss();
        });
        bindin.btnCancel.setOnClickListener(view12 -> {
            dismiss();
        });

        bindin.spnSubType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemSelected: " + l);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
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

    private void searchUserWeb(String nationalId) {
        Call<User> call = mRetrofit.create(ApiInterface.class).findUserByNatId(mToken.getAccessToken(), nationalId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bindin.txtName.setText(response.body().getName());
                    mSupervisorId = response.body().getId();
                } else {
                    bindin.etNatId.setError("کد ملی وارد شده یافت نشد");
                    bindin.etNatId.requestFocus();
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
                Log.d(TAG, "searchUserWeb:onFailure: " + t.getLocalizedMessage());
            }
        });

    }

    private void addNewExcavationProjectWeb(int type, String name, int subType, String subTrenchTrialName, String supervisorId, String superVisorName, String layerFeatureCoding, String registrationCode) {
        ExcavationItem excavationItem = new ExcavationItem();
        excavationItem.setName(name);
        excavationItem.setType(type);
        excavationItem.setSubTrailTrenchType(subType);
        excavationItem.setSubTrailTrenchName(subTrenchTrialName);
        excavationItem.setExcavationProjectId(mProjectId);
        excavationItem.setSupervisorId(supervisorId);
        excavationItem.setSupervisorName(superVisorName);
        excavationItem.setStatus(1);
        excavationItem.setLayerFeatureCoding(layerFeatureCoding);
        excavationItem.setRegistrationCoding(registrationCode);

        mExcavationItemDao.save(excavationItem);

        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        Data inputData = new Data.Builder()
                .putString("token", mToken.getAccessToken())
                .putString("id", excavationItem.getId())
                .putString("name", name)
                .putString("sub_trail_trench_name", subTrenchTrialName)
                .putString("type", String.valueOf(type))
                .putString("sub_trail_trench_type", String.valueOf(subType))
                .putString("excavation_project_id", mProjectId)
                .putString("supervisor_id", supervisorId)
                .putString("layer_feature_coding", layerFeatureCoding)
                .putString("registration_coding", registrationCode)
                .putString("status", "1")

                .build();

        OneTimeWorkRequest uploadWork = new OneTimeWorkRequest.Builder(ExcavationItemSaveWorker.class)
                .setConstraints(constraints).setInputData(inputData).build();
        WorkManager.getInstance().enqueue(uploadWork);
        WorkManager.getInstance().getWorkInfoByIdLiveData(uploadWork.getId())
                .observe(NewExcavationItem1Dialog.this, workStatus -> {
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
