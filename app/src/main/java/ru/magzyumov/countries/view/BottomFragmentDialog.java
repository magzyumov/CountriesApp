package ru.magzyumov.countries.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ru.magzyumov.countries.Constants;
import ru.magzyumov.countries.R;
import ru.magzyumov.countries.model.database.Countries;
import ru.magzyumov.countries.glide.GlideLoader;


public class BottomFragmentDialog extends BottomSheetDialogFragment implements Constants {
    private Countries country;
    private GlideLoader glideLoader;
    private ImageView imageViewCountryFlag;
    private TextView textViewCountryName;
    private TextView textViewCapitalName;
    private TextView textViewCurrencyName;

    public static BottomFragmentDialog newInstance() {
        return new BottomFragmentDialog();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);

        glideLoader = new GlideLoader(requireContext());
        glideLoader.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog, container, false);

        imageViewCountryFlag = view.findViewById(R.id.imageViewCountryFlag);
        glideLoader.load(country.flagUrl, imageViewCountryFlag, false);

        textViewCountryName = view.findViewById(R.id.textViewCountryName);
        textViewCountryName.setText(country.name);

        textViewCapitalName = view.findViewById(R.id.textViewCapitalName);
        textViewCapitalName.setText(country.capital);

        textViewCurrencyName = view.findViewById(R.id.textViewCurrencyName);
        textViewCurrencyName.setText(country.currency);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //country = null;
        //glideLoader = null;
        imageViewCountryFlag = null;
        textViewCountryName = null;
        textViewCapitalName = null;
        textViewCurrencyName = null;
    }

    public void setData(Countries country){
        this.country = country;
    }
}

