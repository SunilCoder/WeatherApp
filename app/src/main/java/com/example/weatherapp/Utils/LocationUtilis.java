package com.example.weatherapp.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class LocationUtilis {

    public FusedLocationProviderClient fusedLocationProviderClient;
    public LocationRequest locationRequest;
    public LocationCallback locationCallback;
    public Activity context;
    public double currentLatitude, currentLongitude;
    public boolean hasLocation = false;

    public LocationUtilis(Activity context) {
        this.context = context;
    }

    public void getLocation(Activity context) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        createLocationRequest();
        createLocationcall();
        checkPermisiion();
    }

    private void checkPermisiion() {
        if (!hasLocationPermision()) {
            requestPermissions(context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, RequestCode.REQUEST_LOCATION);

        } else {
            checkLocationSettings();
        }

    }

    private void checkLocationSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(context);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(context, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLoactionUpdate();

            }
        });

        task.addOnFailureListener(context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                    try {
                        resolvableApiException.startResolutionForResult(context, RequestCode.REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
    }

    public void startLoactionUpdate() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    public void stopLoactionUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private boolean hasLocationPermision() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        }
        return true;
    }

    public void createLocationcall() {
        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult == null) {
                    return;
                }
                if (!hasLocation) {
                    android.location.Location location = locationResult.getLastLocation();
                    // String result = location.getLatitude() + "" + location.getLongitude();
                    currentLatitude =location.getLatitude();
                    currentLongitude = location.getLongitude();
                    hasLocation = true;
                }
            }
        };
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000 * 60 * 10);
        locationRequest.setFastestInterval(1000 * 60 * 5);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    }

    public String getCompleteAdress(double Latitude, double Longtitude) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> adresses = geocoder.getFromLocation(Latitude, Longtitude, 1);
            if (adresses != null) {
                Address returnedAddress = adresses.get(0);
                strAdd = returnedAddress.getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strAdd;


    }


}
