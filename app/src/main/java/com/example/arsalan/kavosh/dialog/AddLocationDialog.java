package com.example.arsalan.kavosh.dialog;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.arsalan.kavosh.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import static android.app.Activity.RESULT_OK;


public class AddLocationDialog extends DialogFragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_LATLNG = "param3";
    private static final String[] MAP_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int REQUEST_MAP_PERMISSIONS_REQUEST = 1000;
    private final String TAG = this.getClass().getSimpleName();
    GoogleMap map;
    // TODO: Rename and change types of parameters
    private String mCityName;
    private String mProvinceName;
    private OnFragmentInteractionListener mListener;
    private MapView m;
    private Button OkBtn;
    private LatLng mLatLng;

    public AddLocationDialog() {
        // Required empty public constructor
    }

    public static AddLocationDialog newInstance(String cityName, String provinceName) {
        AddLocationDialog fragment = new AddLocationDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, cityName);
        args.putString(ARG_PARAM2, provinceName);
        fragment.setArguments(args);
        return fragment;
    }

    public static AddLocationDialog newInstance(LatLng latLng) {
        AddLocationDialog fragment = new AddLocationDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARG_LATLNG, latLng);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().getParcelable(ARG_LATLNG) != null) {
                mLatLng = getArguments().getParcelable(ARG_LATLNG);
            } else {
                mCityName = getArguments().getString(ARG_PARAM1);
                mProvinceName = getArguments().getString(ARG_PARAM2);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_location_dialog, container, false);
        OkBtn = v.findViewById(R.id.btnOk);
        m = v.findViewById(R.id.mapView);
        m.onCreate(savedInstanceState);
        m.getMapAsync(this);
        // statusCheck();
        return v;
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        m.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        m.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        m.onDestroy();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: ");

        map = googleMap;
        map.setOnMapClickListener(latLng -> {
            Log.d(TAG, "onMapClick: mLatLng:" + latLng.toString());

            mLatLng = latLng;


            // Creating a marker
            MarkerOptions markerOptions = new MarkerOptions();

            // Setting the position for the marker
            markerOptions.position(latLng);

            // Setting the title for the marker.
            // This will be displayed on taping the marker
            markerOptions.title(latLng.latitude + " : " + latLng.longitude);

            // Clears the previously touched position
            map.clear();

            // Animating to the touched position
            map.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            // Placing a marker on the touched position
            map.addMarker(markerOptions);
        });
        //  map.getUiSettings().setMyLocationButtonEnabled(false);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(MAP_PERMISSIONS, REQUEST_MAP_PERMISSIONS_REQUEST);
            return;
        }
        map.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        // if (location != null) {
        // Creating a marker

        MarkerOptions markerOptions = new MarkerOptions();
        if (location != null) {
            mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        } else {
            mLatLng = new LatLng(29.612940, 52.511665);
        }
        // Setting the position for the marker
        markerOptions.position(mLatLng);
        // Setting the title for the marker.
        // This will be displayed on taping the marker
        map.addMarker(markerOptions);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mLatLng)      // Sets the center of the map to location user
                //.target(getLocationFromAddress(mContext,"شیراز"))
                .zoom(17)                   // Sets the zoom
                // .bearing(90)                // Sets the orientation of the camera to east
                // .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        OkBtn.setOnClickListener(view -> {
            //  mListener.setLatLng(mLatLng);
            Intent intent = new Intent();
            intent.putExtra("Lng", mLatLng.longitude);
            intent.putExtra("Lat", mLatLng.latitude);

            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
            dismiss();
        });

    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("جی پی اس گوشی شما خاموش است. آیا مایلید برای پیدا کردن موقعیت فعلی آن را روشن کنید؟")
                .setCancelable(false)
                .setPositiveButton("بلی", (dialog, id) -> {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);

                })
                .setNegativeButton("خیر", (dialog, id) -> {
                    dialog.cancel();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_MAP_PERMISSIONS_REQUEST) {
            m.getMapAsync(this);

        }
    }


    @Override
    public void onStart() {

        super.onStart();
        getDialog().getWindow()
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

    }

    public interface OnFragmentInteractionListener {
        void setLatLng(LatLng latLng);
    }
}
