package com.example.arsalan.kavosh.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.fragment.LoginFragment;
import com.example.arsalan.kavosh.fragment.ProjectListFragment;
import com.example.arsalan.kavosh.fragment.RegisterFragment;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.model.User;
import com.example.arsalan.kavosh.retrofit.ApiInterface;

import java.io.IOException;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.arsalan.kavosh.model.MyConst.KEY_PASSWORD;
import static com.example.arsalan.kavosh.model.MyConst.KEY_TOKEN;
import static com.example.arsalan.kavosh.model.MyConst.KEY_USERNAME;

public class MainActivity extends AppCompatActivity implements Injectable, HasSupportFragmentInjector,
        LoginFragment.OnFragmentInteractionListener,
        RegisterFragment.OnFragmentInteractionListener {
    private static final String TAG = "MainActivity";
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Inject
    Retrofit mRetrofit;

    @Inject
    SharedPreferences mPreferences;

    @Inject
    Token mToken;
    Context mContext;
    private MutableLiveData<Token> tokenLive = new MutableLiveData<>();
    private MutableLiveData<User> mCurrentUser = new MutableLiveData<>();

    public MainActivity() {
        mContext = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        String savedUsername = mPreferences.getString(KEY_USERNAME, "");

        String savedPassword = mPreferences.getString(MyConst.KEY_PASSWORD, "");

        if (mToken.getAccessToken().isEmpty() || mToken.getAccessToken().length() < 20) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, LoginFragment.newInstance(savedUsername, savedPassword))
                    .commit(); //login
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, ProjectListFragment.newInstance("", ""))
                    .commit(); //project fragment
        }

        tokenLive.observe(MainActivity.this, token -> {
            Toast.makeText(mContext, "Token: " + token.getAccessToken(), Toast.LENGTH_LONG).show();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, new ProjectListFragment())
                    .commit(); //project fragment

        });
        Log.d(TAG, "onCreate: token:" + mToken.getAccessToken());
        mCurrentUser.observe(this, user ->
                Toast.makeText(mContext, user.getName() + " " + user.getEmail() + " خوش آمدید!", Toast.LENGTH_LONG).show());


    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void doLogin(String username, String password) {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("در حال ورود...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        Call<Token> call = mRetrofit.create(ApiInterface.class).getToken(
                "password"
                , 3
                , "cfZqDtp3NbLWSb1GWJY7qnJYEMMtOxllhu4bw6md"
                , "*"
                , username, password);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.message());
                    tokenLive.setValue(response.body());
                    mPreferences.edit().putString(KEY_USERNAME, username).apply();
                    mPreferences.edit().putString(KEY_PASSWORD, password).apply();
                    mPreferences.edit().putString(KEY_TOKEN, response.body().getAccessTokenWOBearer()).apply();
                    mToken.setAccessToken(response.body().getAccessTokenWOBearer());

                    getUserDetail(response.body().getAccessToken());
                } else if (response.code() == 401) {
                    Toast.makeText(mContext, "خطا در ورود!\nلطفا نام کاربری و رمز عبور را بررسی نمایید.", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Log.d(TAG, "onResponse: " + response.errorBody().string() + "\n code" + response.code());
                        Toast.makeText(MainActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                dialog.dismiss();
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void getUserDetail(String token) {
        Call<User> call = mRetrofit.create(ApiInterface.class).getCurrentUserDetail("Bearer " + token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    mCurrentUser.setValue(response.body());
                } else {
                    try {
                        Log.d(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });

    }

    @Override
    public void goToRegister() {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new RegisterFragment())
                .commit(); //login
    }

    @Override
    public void doRegistration(String name, String password, String mobile, String nationalId, String email, int eduDegree) {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("در حال ثبت نام...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        Call<Token> call = mRetrofit.create(ApiInterface.class).registerUser(name, password, email, mobile, nationalId, eduDegree);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Log.d(TAG, "onResponse: " + response.message());
                    tokenLive.setValue(response.body());
                    mPreferences.edit().putString(KEY_USERNAME, nationalId).apply();
                    mPreferences.edit().putString(KEY_PASSWORD, password).apply();
                    mPreferences.edit().putString(KEY_TOKEN, response.body().getAccessToken()).apply();
                    getUserDetail(response.body().getAccessToken());

                } else {
                    dialog.dismiss();
                    try {
                        Log.d(TAG, "onResponse: error:" + response.errorBody().string());
                        Toast.makeText(MainActivity.this, "خطا:" + response.errorBody().string(), Toast.LENGTH_LONG);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                dialog.dismiss();
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(MainActivity.this, "خطا:" + t.getLocalizedMessage(), Toast.LENGTH_LONG);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");
        mPreferences.edit().putString(KEY_USERNAME, "").apply();
        mPreferences.edit().putString(KEY_PASSWORD, "").apply();
        mToken.setAccessToken("");
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, MainActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
