package com.example.weatherapp.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.Utils.LocationUtilis;
import com.example.weatherapp.Utils.Pref;
import com.example.weatherapp.Utils.RequestCode;
import com.example.weatherapp.Utils.Utilis;
import com.example.weatherapp.model.WeatherCurrent;
import com.example.weatherapp.model.WeatherForecast;
import com.example.weatherapp.response.WeatherCurrentResponse;
import com.example.weatherapp.response.WeatherForecastResponse;
import com.example.weatherapp.rest.ApiInterface;
import com.example.weatherapp.rest.RestClinet;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentFragment extends Fragment {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private boolean hasLocation = false;
    private LocationUtilis locationUtilis;
    private View view, progress_layout, mainView, empty_layout;
    private TextView tv_locationName, tv_date, tv_temp, tv_feelsLike, tv_weatherdescription, tv_humidity, tv_uv, tv_sunrise, tv_sunset, tv_windSpeed, tv_windDir, tv_cdirFull, tv_max_min;
    private ImageView img_icon;
    private Pref pref;
    private EditText et_location;
    private String enteredLocation;
    private ApiInterface apiInterface;
    private ProgressBar progressBar;
    private ImageButton im_search;


    public CurrentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_current, container, false);
        initView();
        apiInterface = RestClinet.getClient().create(ApiInterface.class);
        pref = new Pref(requireContext());
        //displayProgress(true);
        mainView.setVisibility(View.INVISIBLE);
        progress_layout.setVisibility(View.GONE);


        return view;
    }

    private void initView() {
        tv_locationName = view.findViewById(R.id.tv_location);
        tv_date = view.findViewById(R.id.tv_date);
        tv_temp = view.findViewById(R.id.tv_temperature);
        tv_feelsLike = view.findViewById(R.id.tv_feelsLike);
        tv_weatherdescription = view.findViewById(R.id.tv_weatherMood);
        img_icon = view.findViewById(R.id.img_icon);
        progressBar = view.findViewById(R.id.progress_circular);
        progress_layout = view.findViewById(R.id.progress_linear);
        mainView = view.findViewById(R.id.nestesd);
        tv_uv = view.findViewById(R.id.tv_uvIndex);
        tv_sunrise = view.findViewById(R.id.tv_sunrise);
        tv_sunset = view.findViewById(R.id.tv_sunset);
        tv_humidity = view.findViewById(R.id.tv_humudity);
        tv_windSpeed = view.findViewById(R.id.tv_windSpeed);
        tv_windDir = view.findViewById(R.id.tv_windDir);
        tv_cdirFull = view.findViewById(R.id.tv_cdirFull);
        et_location = view.findViewById(R.id.et_searchAddress);
        im_search = view.findViewById(R.id.iv_locations);
        tv_max_min = view.findViewById(R.id.tv_max_min);
        empty_layout = view.findViewById(R.id.empty_layout);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        checkPermission();
        createLocationRequest();
        createLocationCall();
        im_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataLoad();
            }
        });

        et_location.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    dataLoad();
                }
                return false;
            }
        });
    }

    private void dataLoad() {
        Utilis.hideKeyboard(requireContext(), im_search);
        enteredLocation = et_location.getText().toString();
        if (enteredLocation.isEmpty()) {
            Snackbar.make(view, "Please enter the location..", Snackbar.LENGTH_LONG).show();
        } else {
            displayProgress(true);
            mainView.setVisibility(View.GONE);
            netWorkCall(enteredLocation);
            et_location.setText("");
        }

    }

    private void createLocationCall() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult == null) {
                    return;
                }
                if (!hasLocation) {
                    android.location.Location location = locationResult.getLastLocation();
                    String address = getCompleteAddress(location.getLatitude(), location.getLongitude());
                    Log.d("Mari", "onLocationResult: ooo " + address);
                    displayProgress(true);
                    netWorkCall(address);
                    hasLocation = true;
                } else {
                    Log.d("Mari", "onLocationResult: ooo ");
                }
            }
        };
    }


    private void netWorkCall(String address) {
        empty_layout.setVisibility(View.INVISIBLE);
        Log.d("Sammy", "netWorkCall: " + address);
        final Call<WeatherForecastResponse> weatherCurrentResponseCall = apiInterface.getForecastWeather(address, 7, "35efeec91b474b228f75e97447d1fde4");
        weatherCurrentResponseCall.enqueue(new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                displayProgress(false);
                progress_layout.setVisibility(View.INVISIBLE);
                mainView.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.d("Sammy", "netWorkCall: " + response);
                        WeatherForecastResponse wr = response.body();
                        List<WeatherForecast> weatherCurrents = wr.getData();

                        if (pref != null) {
                            Log.d("kancha", "saveLocationName: ++++" + pref.getForecast());
                            String savedAddress = pref.getLocation();
                            if (savedAddress != null) {
                                if (savedAddress.equals(wr.getCity_name())) {
                                    // pref.clearLocationName();
                                    pref.clearData();
                                    pref.saveLocationName(savedAddress);
                                    pref.saveLastLocation(savedAddress);
                                    pref.saveForeCastWeather(weatherCurrents);
                                } else {
                                    pref.clearData();
                                    // pref.clearLocationName();
                                    pref.saveLocationName(wr.getCity_name());
                                    pref.saveLastLocation(wr.getCity_name());
                                    pref.saveForeCastWeather(weatherCurrents);
                                }
                            } else {
                                pref.saveLocationName(wr.getCity_name());
                                pref.saveLastLocation(wr.getCity_name());
                                pref.saveForeCastWeather(weatherCurrents);
                            }
                        } else {
                            Log.d("kancha", "saveLocationName: ....");
                            pref.saveLocationName(wr.getCity_name());
                            pref.saveLastLocation(wr.getCity_name());
                            pref.saveForeCastWeather(weatherCurrents);
                        }

                        Log.d("SAdee", "onResponse: " + pref.getForecast());

                        setData(weatherCurrents);
                    } else {
                        mainView.setVisibility(View.GONE);
                        progress_layout.setVisibility(View.VISIBLE);
                        pref.clearLocationName();
                        Snackbar.make(progress_layout, "Enter the valid location..", Snackbar.LENGTH_LONG).show();
                    }


                } else {
                    mainView.setVisibility(View.GONE);
                    progress_layout.setVisibility(View.VISIBLE);
                    Snackbar.make(progress_layout, response.message(), Snackbar.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                displayProgress(false);

                progress_layout.setVisibility(View.VISIBLE);
                mainView.setVisibility(View.GONE);
                pref.clearLocationName();
               /* Snackbar.make(progress_layout, "Please check the internet connection", Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //progress_layout.setVisibility(View.INVISIBLE);
                        //displayProgress(true);
                        //netWorkCall(pref.getLastLocation());
                    }
                }).show();*/
                Snackbar.make(progress_layout, "Please check the internet connection", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setData(List<WeatherForecast> weatherCurrents) {
        if (weatherCurrents != null) {

            Log.d("dymna", "setData: " + pref.getForecast());

            tv_locationName.setText(pref.getLocation());

            String icon = weatherCurrents.get(0).getWeather().getIcon();
            String url = "https://www.weatherbit.io/static/img/icons/" + icon + ".png";
            Picasso.get().load(url).into(img_icon);

            int temperature = (int) weatherCurrents.get(0).getTemp();

            tv_temp.setText(temperature + "°");
            int feelsLike = (int) weatherCurrents.get(0).getapp_max_Temp();
            tv_feelsLike.setText(String.valueOf("Feels Like " + feelsLike + "°"));
            tv_weatherdescription.setText(weatherCurrents.get(0).getWeather().getDescription());
            tv_date.setText(Utilis.getFormattedDate(weatherCurrents.get(0).getValid_date()));

            String time = Utilis.convertMilistoTime(weatherCurrents.get(0).getSunrise_ts());

            tv_uv.setText(String.valueOf(weatherCurrents.get(0).getUv()));
            tv_sunrise.setText(time + " am");
            tv_sunset.setText(Utilis.convertMilistoTime(weatherCurrents.get(0).getSunset_ts()) + " pm");

            int hum = (int) weatherCurrents.get(0).getRh();
            int windDirection = (int) weatherCurrents.get(0).getWind_dir();
            int windSpeed = (int) weatherCurrents.get(0).getWind_spd();
            tv_humidity.setText(hum + " %");
            tv_windDir.setText(windDirection + "°");
            tv_windSpeed.setText(windSpeed + " m/s");
            tv_cdirFull.setText(weatherCurrents.get(0).getWind_cdir_full());
            int maxTemp = (int) weatherCurrents.get(0).getMax_temp();
            int minTemp = (int) weatherCurrents.get(0).getMin_temp();

            tv_max_min.setText(maxTemp + "°" + " / " + minTemp + "°");

        }
    }

    private void displayProgress(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

    }

    private String getCompleteAddress(double latitude, double longitude) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                strAdd = returnedAddress.getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strAdd;
    }

    private void checkPermission() {
        if (!hasLocationPermission()) {
            empty_layout.setVisibility(View.VISIBLE);
            mainView.setVisibility(View.GONE);
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, RequestCode.REQUEST_LOCATION);
        } else {
            empty_layout.setVisibility(View.INVISIBLE);
            checkLocalSettings();
        }
    }

    private void checkLocalSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(requireContext());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdate();
            }
        });
        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                    try {
                        resolvableApiException.startResolutionForResult(getActivity(), RequestCode.REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
    }

    private void startLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void stopLocationUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000 * 60 * 10);
        locationRequest.setFastestInterval(1000 * 60 * 5);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private boolean hasLocationPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            return ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.REQUEST_CHECK_SETTINGS) {
            if (resultCode == getActivity().RESULT_OK) {
                Log.d("jari", "onActivityResult: has anda");
                empty_layout.setVisibility(View.INVISIBLE);
                locationUtilis.startLoactionUpdate();
            } else {
                Log.d("jari", "onActivityResult: has danda");
            }
        } else {
            Log.d("jari", "onActivityResult: has banda");

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startLocationUpdate();
        //hasLocation=false;
        //Log.d("mari", "onResume: "+pref.getLocation());
       if(pref.getLocation()!=null){
            displayProgress(true);
            mainView.setVisibility(View.INVISIBLE);
           netWorkCall(pref.getLocation());
           //setData(pref.getForecast());
        }else{
           displayProgress(true);
           mainView.setVisibility(View.INVISIBLE);
           progress_layout.setVisibility(View.INVISIBLE);
           netWorkCall(pref.getLastLocation());

        }

    }

    @Override
    public void onStop() {
        super.onStop();
        stopLocationUpdate();
    }

}
