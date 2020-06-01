package com.example.weatherapp.Fragment;


import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.Utils.AlarmReceiver;
import com.example.weatherapp.Utils.LocationUtilis;
import com.example.weatherapp.Utils.PageAdapter;
import com.example.weatherapp.Utils.Pref;
import com.example.weatherapp.Utils.RequestCode;
import com.example.weatherapp.Utils.Utilis;
import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.Locations;
import com.example.weatherapp.model.WeatherCurrent;
import com.example.weatherapp.model.WeatherForecast;
import com.example.weatherapp.model.location;
import com.example.weatherapp.response.CurrentWeatherResponse;
import com.example.weatherapp.response.WeatherCurrentResponse;
import com.example.weatherapp.response.WeatherForecastResponse;
import com.example.weatherapp.rest.ApiInterface;
import com.example.weatherapp.rest.RestClinet;
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
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.content.ContextCompat.getSystemServiceName;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> days = new ArrayList<>();
    private ArrayList<Integer> images = new ArrayList<>();
    private ArrayList<String> temperature = new ArrayList<>();

    private TabLayout tabLayout;
    private TabItem tb_home,tb_forecast;
    private ViewPager viewPager;
    private NavController navController;
    private Toolbar toolbar;

    private List<WeatherForecast> weatherForecasts;
    RecyclerView.Adapter mAdapter;
    private LocationUtilis locationUtilis;

    private TextView tv_locationName, tv_date, tv_temperature, tv_temp_desc, tv_wind, tv_humidity, tv_maximum;
    private ImageView iv_temp_icon,iv_search;
    private ProgressBar progressBar;
    private ConstraintLayout constraintLayout;
    private EditText et_searchAddress;


    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private boolean hasLocation = false;

    private Pref pref;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout = view.findViewById(R.id.homeTablayout);
        tb_home = view.findViewById(R.id.Current);
        tb_forecast =view.findViewById(R.id.ForeCast);
        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        viewPager = view.findViewById(R.id.currentViewPager);
        toolbar = view.findViewById(R.id.weatherToolbar);



      /*  recyclerView = view.findViewById(R.id.comingdays);
        tv_locationName = view.findViewById(R.id.tv_locationName);
        tv_date = view.findViewById(R.id.tv_date);
        tv_temperature = view.findViewById(R.id.tv_temperature);
        tv_temp_desc = view.findViewById(R.id.tv_description);
        tv_wind = view.findViewById(R.id.tv_wind);
        tv_humidity = view.findViewById(R.id.tv_humidity);
        tv_maximum = view.findViewById(R.id.tv_maximum);
        iv_temp_icon = view.findViewById(R.id.iv_temperatureIcon);
        tv_date = view.findViewById(R.id.tv_date);
        constraintLayout = view.findViewById(R.id.layout_constraint);
        progressBar = view.findViewById(R.id.progress_circular);
        et_searchAddress = view.findViewById(R.id.et_searchAdress);
        iv_search = view.findViewById(R.id.iv_locations);*/
        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PagerAdapter pagerAdapter = new PageAdapter(getChildFragmentManager(),tabLayout.getTabCount());
        int limit = pagerAdapter.getCount()>1?pagerAdapter.getCount()-1:1;
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(limit);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==1){
                    pref= new Pref(requireContext());
                    //loadData();
                    Log.d("jumboo", "onPageSelected: "+pref.getLocation());

                }

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
               // Log.d("jumboo", "onPageSelected: "+tabLayout.getTabAt(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


       // fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        //createLocationRequest();
        //createLocationcall();
        //checkPermisiion();

      /*  iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
                displayProgress(true);
            }
        })*/;



        //displayProgress(true);


        days.add("Friday");
        days.add("Saturday");
        days.add("Sunday");
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");

        temperature.add("29°");
        temperature.add("29°");
        temperature.add("29°");
        temperature.add("29°");
        temperature.add("29°");
        temperature.add("29°");

        images.clear();

        for (int i = 0; i < 7; i++) {
            images.add(R.drawable.sun);
        }

        //buildRecyclerView();


    }

    private void search() {
        String enteredLocations = et_searchAddress.getText().toString();
        //tv_locationName.setText(enteredLocations);
        //networkcall(enteredLocations);


    }

  /*  private void displayProgress(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            constraintLayout.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            constraintLayout.setVisibility(View.VISIBLE);
        }

    }*/

   /* public String getCompleteAdress(double Latitude, double Longtitude) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
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
*/
   /* private void checkPermisiion() {
        if (!hasLocationPermision()) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, RequestCode.REQUEST_LOCATION);

        } else {
            checkLocationSettings();
        }

    }*/
    /*private void checkLocationSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(getContext());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
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
    }*/

   /* public void startLocationUpdate() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    public void stopLocationUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private boolean hasLocationPermision() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        }
        return true;
    }
*/
    /*public void createLocationcall() {
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
                    String address = getCompleteAdress(location.getLatitude(), location.getLongitude());
                    //pref.saveLocationName(address);
                   // tv_locationName.setText(address);
                    networkcall(address);
                    hasLocation = true;
                }
            }
        };
    }
*/
   /* private void networkcall(final String address) {
        Retrofit retrofit = RestClinet.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<WeatherForecastResponse> currentWeatherCall = apiInterface.getForecastWeather(address, 7, "35efeec91b474b228f75e97447d1fde4");

        currentWeatherCall.enqueue(new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                displayProgress(false);
                if (response.isSuccessful()) {
                    if(response.code() == 200){
                        Log.d("soso", "onResponse: " + response);
                        tv_locationName.setText(address);

                        WeatherForecastResponse rs = response.body();

                        List<WeatherForecast> data = rs.getData();
                        //setData(data);
                        setRecylerView(data);
                    }else{
                        Snackbar.make(tv_locationName, "Enter the correct location", Snackbar.LENGTH_LONG).show();
                    }



                } else {
                    Snackbar.make(tv_locationName, response.message(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                displayProgress(false);
                Log.d("sunil", "onFailure: " + t.getMessage());
                  Snackbar.make(tv_locationName, t.getMessage(), Snackbar.LENGTH_LONG).show();
                Snackbar.make(tv_locationName, t.getMessage(), Snackbar.LENGTH_LONG).show();

            }
        });

    }*/

  /*  private void setRecylerView(List<WeatherForecast> data) {
        mAdapter = new mAdapter(data, getContext());
        recyclerView.setAdapter(mAdapter);


    }*/

    /*private void setData(List<WeatherForecast> weahterForcast) {
        if (weahterForcast != null) {


            String icon = weahterForcast.get(0).getWeather().getIcon();
            String Url = "https://www.weatherbit.io/static/img/icons/" + icon + ".png";

            Picasso.get().load(Url).into(iv_temp_icon);
            int temperature = (int) weahterForcast.get(0).getTemp();

            tv_temperature.setText(temperature + "°");
            tv_temp_desc.setText(weahterForcast.get(0).getWeather().getDescription());

            int wind_speed = (int) weahterForcast.get(0).getWind_spd();

            tv_wind.setText(String.valueOf(wind_speed) + "km/h");

            tv_humidity.setText(weahterForcast.get(0).getapp_max_Temp()+"°");

            int maximum = (int) weahterForcast.get(0).getMax_temp();
            tv_maximum.setText(String.valueOf(maximum) + "°");

            Log.d("yoyo", "setData: " + weahterForcast.get(1).getapp_max_Temp());

            String date = Utilis.getFormattedDate(weahterForcast.get(0).getValid_date());
            Log.d("ton", "setData: " + date);

            tv_date.setText(Utilis.getFormattedDate(weahterForcast.get(0).getValid_date()));
            //tv_date.setText(Utilis.convertMIllistoDate(location.getLocaltime_epoch()));
        }

    }*/

   /* private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000 * 60 * 10);
        locationRequest.setFastestInterval(1000 * 60 * 5);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    }
*/

    //@Override
  /*  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.REQUEST_CHECK_SETTINGS) {
            if (resultCode == getActivity().RESULT_OK) {
                locationUtilis.startLoactionUpdate();

            }
        }
    }
*/

 /*   private void buildRecyclerView() {

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);


    }

    public class mAdapter extends RecyclerView.Adapter<mAdapter.ViewHolder> {
        private List<WeatherForecast> weatherForecasts;
        private Context context;

        public mAdapter(List<WeatherForecast> weatherForecasts, Context context) {
            this.context = context;
            this.weatherForecasts = weatherForecasts;
        }

        @NonNull
        @Override
        public mAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.recyler_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull mAdapter.ViewHolder holder, int position) {


            //holder.tv_days.setText(days.get(position));

           holder.tv_days.setText(Utilis.getDaysofTheweek(weatherForecasts.get(position).getValid_date()));
            String temperature = String.valueOf(weatherForecasts.get(position).getMax_temp()+"°"+"/"+weatherForecasts.get(position).getMin_temp()+"°");


            String icon = weatherForecasts.get(position).getWeather().getIcon();
            String Url = "https://www.weatherbit.io/static/img/icons/" + icon + ".png";

            Picasso.get().load(Url).into(holder.im_pic);
            holder.tv_temperature.setText(temperature);

        }

        @Override
        public int getItemCount() {
            return weatherForecasts.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_days, tv_temperature;
            ImageView im_pic;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_days = itemView.findViewById(R.id.days);
                tv_temperature = itemView.findViewById(R.id.tv_temp);
                im_pic = itemView.findViewById(R.id.pic);


            }
        }
    }*/
}
