package com.example.jiaheng.mapsensor;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DataFragment extends Fragment implements SensorService.OnSensorDateTransferListener {

    private TextView status;
    private TextView direction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        status = (TextView) view.findViewById(R.id.phone_status);
        direction = (TextView) view.findViewById(R.id.phone_direction);
        SensorService.registerOnSensorDateTransferListener(this);
    }

    @Override
    public void onSensorDataTransfer(float x, float y, float z) {
        status.setText("Y value: " + y);
        direction.setText("X value: " + x + getDirection(x));
    }

    private String getDirection(double x) {

        if (x < 5 || 355 < x) {
            return " (North)";
        } else if (40 < x && x < 50) {
            return " (NorthEast)";
        } else if (85 < x && x < 95) {
            return " (East)";
        } else if (130 < x && x < 140) {
            return " (SouthEast)";
        } else if (175 < x && x < 185) {
            return " (South)";
        } else if (220 < x && x < 230) {
            return " (SouthWest)";
        } else if (265 < x && x < 275) {
            return " (West)";
        } else if (310 < x && x < 320) {
            return " (NorthWest)";
        } else {
            return "";
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        SensorService.unregisterOnSensorDateTransferListener(this);
    }

    private static DataFragment dataFragment;

    public static DataFragment getInstance() {
        if (dataFragment == null) {
            dataFragment = new DataFragment();
        }
        return dataFragment;
    }

}
