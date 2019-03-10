package com.dev.weatherreport.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.weatherreport.BaseFragment;
import com.dev.weatherreport.R;
import com.dev.weatherreport.adapter.WeatherAdapter;
import com.dev.weatherreport.network.controllers.RetrofitService;
import com.dev.weatherreport.network.interfaces.Api;
import com.dev.weatherreport.network.models.WeatherResponseModel;
import com.dev.weatherreport.utils.Constants;
import com.dev.weatherreport.utils.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Response;

public class DelhiFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;

    public DelhiFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoading();
        Call<WeatherResponseModel> dlCall = RetrofitService.getInstance().builder().create(Api.class).getWeatherResports("delhi", Constants.APP_WEATHER_KEY);
        pWebServiceExecutor.execute(pContext, dlCall, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onNetworkSuccess(Call call, Response response, Bundle bundle) {
        super.onNetworkSuccess(call, response, bundle);
        if (response != null) {
            if (response.body() instanceof WeatherResponseModel) {
                WeatherResponseModel model = (WeatherResponseModel) response.body();
                setupRecyclerView(model);
            }
        }
    }

    private void setupRecyclerView(WeatherResponseModel model) {
        WeatherAdapter adapter = new WeatherAdapter(model.getList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
