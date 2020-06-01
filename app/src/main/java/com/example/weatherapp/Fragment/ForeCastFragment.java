package com.example.weatherapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.weatherapp.R;
import com.example.weatherapp.Utils.Pref;
import com.example.weatherapp.Utils.Utilis;
import com.example.weatherapp.model.WeatherForecast;
import com.example.weatherapp.response.WeatherForecastResponse;
import com.example.weatherapp.rest.ApiInterface;
import com.example.weatherapp.rest.RestClinet;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForeCastFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter madapter;
    private RecyclerView.LayoutManager layoutManager;
    private View view, progress_layout, mainLayout;
    private List<WeatherForecast> weatherForecasts = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private Pref pref;
    private TextView tv_addressName;
    String address;
    private ApiInterface apiInterface;
    private Timer timer;

    public ForeCastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fore_cast, container, false);
        initView();
        mainLayout.setVisibility(View.GONE);
        apiInterface = RestClinet.getClient().create(ApiInterface.class);
        loadData();
        return view;
    }


    private void loadData() {
         pref=new Pref(requireContext());
        if (pref.getLocation() != null) {
            displayProgress(false);
            mainLayout.setVisibility(View.VISIBLE);
            progress_layout.setVisibility(View.GONE);
            if (swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
            address = pref.getLocation();
            weatherForecasts = pref.getForecast();
            madapter = new ForeCastAdapter(weatherForecasts, requireContext());
            recyclerView.setAdapter(madapter);
        } else {
            Log.d("mayalu", "loadData: ppp");
            progress_layout.setVisibility(View.VISIBLE);
            mainLayout.setVisibility(View.INVISIBLE);
            pref.clearLocationName();
            //madapter.notifyDataSetChanged();


        }
    }


    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    /* private void networkCall(final String address) {

         if (count < 0) {
             displayProgress(true);
         }

         Call<WeatherForecastResponse> call = apiInterface.getForecastWeather(address, 7, "35efeec91b474b228f75e97447d1fde4");
         call.enqueue(new Callback<WeatherForecastResponse>() {
             @Override
             public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                 displayProgress(false);
                 progress_layout.setVisibility(View.GONE);
                 mainLayout.setVisibility(View.VISIBLE);
                 if (swipeRefreshLayout.isRefreshing())
                     swipeRefreshLayout.setRefreshing(false);
                 if (response.isSuccessful()) {
                     if (response.code() == 200) {
                         Log.d("Aammy", "onResponse: " + response);
                         WeatherForecastResponse wr = response.body();
                         List<WeatherForecast> weatherForecastResponses = wr.getData();
                         madapter = new ForeCastAdapter(weatherForecastResponses, requireContext());
                         count = count + 1;
                         recyclerView.setAdapter(madapter);
                     } else {
                         progress_layout.setVisibility(View.VISIBLE);
                         mainLayout.setVisibility(View.GONE);
                         Snackbar.make(progress_layout, "Enter the correct location", Snackbar.LENGTH_LONG).show();
                     }
                 } else {
                     progress_layout.setVisibility(View.VISIBLE);
                     mainLayout.setVisibility(View.GONE);
                     Snackbar.make(progress_layout, response.message(), Snackbar.LENGTH_LONG).show();
                 }
             }

             @Override
             public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                 displayProgress(false);
                 progress_layout.setVisibility(View.VISIBLE);
                 mainLayout.setVisibility(View.GONE);
                 Snackbar.make(progress_layout, "Please check the internet connection", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         //networkCall(address);
                     }
                 }).show();

             }
         });
     }
 */
    private void displayProgress(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);

        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.forecastRecycler);
        swipeRefreshLayout = view.findViewById(R.id.ForeCastSwipeLayout);
        progressBar = view.findViewById(R.id.progress_circular);
        progress_layout = view.findViewById(R.id.progress_linear);
        mainLayout = view.findViewById(R.id.forecastConstraint);

        recyclerView.setHasFixedSize(true);
        mainLayout.setVisibility(View.VISIBLE);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public class ForeCastAdapter extends RecyclerView.Adapter<ForeCastAdapter.ViewHolder> {
        private List<WeatherForecast> responses;
        private Context context;

        public ForeCastAdapter(List<WeatherForecast> forecastResponses, Context ctx) {
            this.responses = forecastResponses;
            this.context = ctx;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.recyler_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tv_day.setText(Utilis.getDaysofTheweek(responses.get(position).getValid_date()));
            String icon = responses.get(position).getWeather().getIcon();
            String Url = "https://www.weatherbit.io/static/img/icons/" + icon + ".png";
            Picasso.get().load(Url).into(holder.img);
            int maxTemp = (int) responses.get(position).getMax_temp();
            int minTemp = (int) responses.get(position).getMin_temp();
            String temperature = (maxTemp + "°" + " / " + minTemp + "°");
            holder.tv_max_min.setText(temperature);
            int temp = (int) responses.get(position).getTemp();
            holder.tv_max.setText((temp + "°"));
            holder.tv_feelsLike.setText(responses.get(position).getWeather().getDescription());

            holder.tv_cityName.setText(pref.getLocation());
        }

        @Override
        public int getItemCount() {
            if (responses != null) {
                return responses.size();
            } else {
                return 0;
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_max, tv_max_min, tv_feelsLike, tv_day, tv_cityName;
            ImageView img;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_max = itemView.findViewById(R.id.tv_max);
                tv_max_min = itemView.findViewById(R.id.tv_max_mintemp);
                tv_day = itemView.findViewById(R.id.tv_day);
                img = itemView.findViewById(R.id.img_icon);
                tv_feelsLike = itemView.findViewById(R.id.tv_feelsLike);
                tv_cityName = itemView.findViewById(R.id.tv_addressName);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data",responses.get(getAdapterPosition()));
                        bundle.putString("CityName",pref.getLocation());
                        Navigation.findNavController(view).navigate(R.id.detailedViewFragment,bundle);
                    }
                });

            }
        }
    }
}
