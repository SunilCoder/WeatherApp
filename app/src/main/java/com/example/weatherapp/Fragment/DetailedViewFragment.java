package com.example.weatherapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.Utils.Utilis;
import com.example.weatherapp.model.WeatherForecast;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailedViewFragment extends Fragment {
    private View view,LinearView;
    private Bundle bundle;
    private WeatherForecast weatherDetails;
   private Toolbar mtoolbar;
    private String cityName;
    private NavController navController;
    private TextView tv_mdate,tv_mtem,tv_mmax_min,tv_mfeelsLike,tv_mweatherDescription,tv_muv,tv_msunrise,tv_msunset,tv_mhumudity,tv_mwind_spd,tv_wind_dir,tv_mcdir;
    private ImageView weatherImage;

    public DetailedViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view  = inflater.inflate(R.layout.fragment_detailed_view, container, false);
        initView();
        return  view;
    }

    private void getData() {
        bundle=getArguments();
        weatherDetails= (WeatherForecast) bundle.getSerializable("data");
        cityName = (String) bundle.get("CityName");

    }

    private void initView() {
        mtoolbar = view.findViewById(R.id.detailedToolbar);
        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        LinearView = view.findViewById(R.id.progress_linear);
        tv_mdate=view.findViewById(R.id.tv_date);
        tv_mtem = view.findViewById(R.id.tv_temperature);
        tv_mmax_min = view.findViewById(R.id.tv_max_min);
        tv_mfeelsLike = view.findViewById(R.id.tv_feelsLike);
        tv_mweatherDescription = view.findViewById(R.id.tv_weatherMood);
        weatherImage = view.findViewById(R.id.img_icon);
        tv_muv = view.findViewById(R.id.tv_uvIndex);
        tv_msunrise = view.findViewById(R.id.tv_sunrise);
        tv_msunset = view.findViewById(R.id.tv_sunset);
        tv_mhumudity = view.findViewById(R.id.tv_humudity);
        tv_mwind_spd = view.findViewById(R.id.tv_windSpeed);
        tv_wind_dir =view.findViewById(R.id.tv_windDir);
        tv_mcdir = view.findViewById(R.id.tv_cdirFull);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavigationUI.setupWithNavController(mtoolbar,navController);
        if(weatherDetails!=null){
            mtoolbar.setTitle(cityName);
            LinearView.setVisibility(View.INVISIBLE);
            tv_mdate.setText(Utilis.getFormattedDate(weatherDetails.getValid_date()));
            int temp= (int) weatherDetails.getTemp();
            tv_mtem.setText(temp+"°");
            int max= (int) weatherDetails.getMax_temp();
            int min = (int) weatherDetails.getMin_temp();
            tv_mmax_min.setText(max+"°"+" / "+min+"°");
            tv_mweatherDescription.setText(weatherDetails.getWeather().getDescription());
            String icon = weatherDetails.getWeather().getIcon();
            String url = "https://www.weatherbit.io/static/img/icons/" + icon + ".png";
            Picasso.get().load(url).into(weatherImage);

            String time = Utilis.convertMilistoTime(weatherDetails.getSunrise_ts());

            tv_muv.setText(String.valueOf(weatherDetails.getUv()));
            tv_msunrise.setText(time + " am");
            tv_msunset.setText(Utilis.convertMilistoTime(weatherDetails.getSunset_ts()) + " pm");

            int hum = (int) weatherDetails.getRh();
            int windDirection = (int) weatherDetails.getWind_dir();
            int windSpeed = (int) weatherDetails.getWind_spd();
            tv_mwind_spd.setText(windSpeed+" m/s");
            tv_mhumudity.setText(hum + " %");
            tv_wind_dir.setText(windDirection + "°");
            tv_mcdir.setText(weatherDetails.getWind_cdir_full());
        }else{
            mtoolbar.setTitle("Weather");
            LinearView.setVisibility(View.VISIBLE);
        }
    }
}
