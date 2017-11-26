package com.example.jiaheng.mapsensor;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionFragment extends Fragment {

    private TextView textView;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.photo_text);
        imageView = (ImageView) view.findViewById(R.id.photo_view);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
        int whichPlace = bundle.getInt("whichPlace", -999);
        for (Place place : Global.placeList) {
            if (whichPlace == place.getId()) {
                textView.setText(place.getDescription());
                imageView.setImageResource(place.getImageId());
            }
        }
    }

    private static DescriptionFragment descriptionFragment;

    public static DescriptionFragment getInstance() {
        if (descriptionFragment == null) {
            descriptionFragment = new DescriptionFragment();
        }
        return descriptionFragment;
    }


}
