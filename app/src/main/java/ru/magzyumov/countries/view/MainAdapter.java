package ru.magzyumov.countries.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.magzyumov.countries.svg.GlideLoader;
import ru.magzyumov.countries.R;
import ru.magzyumov.countries.model.response.ICountriesParsed;
import ru.magzyumov.countries.model.database.Countries;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private View view;
    private Context context;
    private ICountriesParsed dataSource;
    private OnItemClickListener itemClickListener;
    private GlideLoader glideLoader;

    public MainAdapter(ICountriesParsed dataSource, Context context){
        this.dataSource = dataSource;
        this.context = context;
        this.glideLoader = new GlideLoader(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        if (itemClickListener != null) {
            viewHolder.setOnClickListener(itemClickListener);
        }
        glideLoader.init();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder viewHolder, int position) {
        Countries country = dataSource.getCountry(position);

        glideLoader.clearCache(viewHolder.getImageViewCountryFlag());
        glideLoader.loadNet(country.flagUrl, viewHolder.getImageViewCountryFlag());


        viewHolder.getTextViewCountryName().setText(country.name);
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }


    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewCountryFlag;
        private TextView textViewCountryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCountryFlag = itemView.findViewById(R.id.imageViewCountryFlag);
            textViewCountryName = itemView.findViewById(R.id.textViewCountryName);

            itemView.setOnClickListener(v -> {
                if(itemClickListener != null){
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }

        public void setOnClickListener(final OnItemClickListener listener){
            itemView.setOnClickListener(v -> {
                int adapterPosition = getAdapterPosition();
                if (adapterPosition == RecyclerView.NO_POSITION) return;
                listener.onItemClick(v, adapterPosition);
            });
        }

        public void setData(String countryName, int picture){
            getTextViewCountryName().setText(countryName);
            getImageViewCountryFlag().setImageResource(picture);
        }

        public TextView getTextViewCountryName() { return textViewCountryName; }

        public ImageView getImageViewCountryFlag() { return imageViewCountryFlag; }
    }
}
