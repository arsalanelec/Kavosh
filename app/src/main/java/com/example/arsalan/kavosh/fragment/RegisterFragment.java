package com.example.arsalan.kavosh.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.arsalan.kavosh.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends androidx.fragment.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        EditText nameET = v.findViewById(R.id.etName);
        EditText nationalIdET = v.findViewById(R.id.etNationalId);
        EditText emailET = v.findViewById(R.id.etEmail);
        EditText mobileET = v.findViewById(R.id.etMobile);
        EditText passwordET = v.findViewById(R.id.etPassword);
        EditText confirmPassET = v.findViewById(R.id.etConfirmPass);

        Spinner degreeEduSP = v.findViewById(R.id.spnEduDegree);
        Button submitBtn = v.findViewById(R.id.btnSubmit);
        submitBtn.setOnClickListener(view -> {
            boolean hasError = false;
            if (nameET.getText().toString().isEmpty()) {
                nameET.setError("نام و نام خانوادگی خالی است.");
                nameET.requestFocus();
                hasError = true;
            }
            if (nationalIdET.getText().length() < 10) {
                nationalIdET.setError("کد ملی 10 رقمی اشتباه است.");
                nationalIdET.requestFocus();
                hasError = true;
            }

            if (emailET.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailET.getText().toString()).matches()) {
                emailET.setError("آدرس ایمیل را با فرمت صحیح وارد نمایید");
                emailET.requestFocus();
                hasError = true;
            }
            if (mobileET.getText().length() < 11) {
                mobileET.setError("شماره موبایل وارد شده صحیح نیست");
                mobileET.requestFocus();
                hasError = true;
            }
            if (passwordET.getText().length() < 6) {
                passwordET.setError("رمز ورود نباید کمتر از 6 کارکتر باشد");
                passwordET.requestFocus();
                hasError = true;
            } else if (!passwordET.getText().toString().equals(confirmPassET.getText().toString())) {
                confirmPassET.setError("رمز ورود با تکرار آن برابر نیست");
                confirmPassET.requestFocus();
                hasError = true;

            }
            if (hasError) return;
            mListener.doRegistration(
                    nameET.getText().toString(),
                    passwordET.getText().toString(),
                    mobileET.getText().toString(),
                    nationalIdET.getText().toString(),
                    emailET.getText().toString(),
                    (int) degreeEduSP.getSelectedItemId()
            );
        });
        return v;
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
        /*'email', 'name', 'password','mobile','national_id','degree_edu'*/
        void doRegistration(String name, String password, String mobile, String nationalId, String email, int eduDegree);
    }
}
