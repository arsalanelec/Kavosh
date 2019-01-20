package com.example.arsalan.kavosh.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;

import com.example.arsalan.kavosh.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends androidx.fragment.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USERNAME = "param1";
    private static final String ARG_PASSWORD = "param2";

    // TODO: Rename and change types of parameters
    private String mUserName;
    private String mPassword;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, param1);
        args.putString(ARG_PASSWORD, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserName = getArguments().getString(ARG_USERNAME);
            mPassword = getArguments().getString(ARG_PASSWORD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        EditText usernameET = v.findViewById(R.id.etUsername);
        usernameET.setText(mUserName);

        EditText passwordET = v.findViewById(R.id.etPassword);
        passwordET.setText(mPassword);

        Button loginBtn = v.findViewById(R.id.btnSubmit);
        loginBtn.setOnClickListener(view -> {
                    if (usernameET.getText().toString().isEmpty()) {
                        usernameET.setError("نام کاربری خالی است!");
                        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
                        shake.setDuration(500);
                        shake.setInterpolator(new CycleInterpolator(7));
                        usernameET.startAnimation(shake);
                        return;
                    }
                    if (passwordET.getText().toString().isEmpty()) {
                        passwordET.setError("رمز عبور را وارد نمایید.");
                        return;
                    }

                    mListener.doLogin(usernameET.getText().toString(), passwordET.getText().toString());
                }
        );

        Button registerBtn = v.findViewById(R.id.btnRegister);
        registerBtn.setOnClickListener(view -> mListener.goToRegister());
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
        void doLogin(String username, String password);

        void goToRegister();

    }
}
