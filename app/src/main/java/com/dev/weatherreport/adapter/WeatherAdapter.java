package com.dev.weatherreport.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.weatherreport.R;
import com.dev.weatherreport.WeatherApp;
import com.dev.weatherreport.network.models.ListItem;
import com.dev.weatherreport.network.models.WeatherItem;
import com.dev.weatherreport.utils.AppUtils;
import com.dev.weatherreport.utils.WeatherTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private final List<ListItem> mValues;

    public WeatherAdapter(List<ListItem> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ListItem mItem = mValues.get(position);
        holder.bind(mItem);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ListItem mItem;
        @BindView(R.id.humidityTv)
        WeatherTextView humidityTv;
        @BindView(R.id.pressureTv)
        WeatherTextView pressureTv;
        @BindView(R.id.descTv)
        WeatherTextView descTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        void bind(ListItem mItem) {
            humidityTv.setText(WeatherApp.getAppContext().getString(R.string.title_humidity) + AppUtils.getInstance().getValueFromData(mItem.getHumidity()));
            pressureTv.setText(WeatherApp.getAppContext().getString(R.string.title_pressure) + (Double) AppUtils.getInstance().getValueFromData(mItem.getPressure()));
            List<WeatherItem> weatherListItem = mItem.getWeather();
            if (weatherListItem != null && weatherListItem.size() > 0) {
                WeatherItem weatherItem = weatherListItem.get(0);
                if (weatherItem != null) {
                    descTv.setText(WeatherApp.getAppContext().getString(R.string.title_desc) + AppUtils.getInstance().getValueFromData(weatherItem.getDescription()).toString());
                }
            }
        }
    }
}
