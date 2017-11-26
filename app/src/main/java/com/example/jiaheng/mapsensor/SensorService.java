package com.example.jiaheng.mapsensor;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;


/**
 * Created by Jiaheng on 4/2/17.
 */

public class SensorService extends Service {

    private SensorManager sensorManager;
    private int previousFragmentType = Global.FragmentType.DATE_FRAGMENT;
    private int currentFragmentType = Global.FragmentType.DATE_FRAGMENT;
    private int whichPlace = Global.NONE_PLACE;
    private int previousPlace = Global.NONE_PLACE;


    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(listener, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_NORMAL);
        LocationProvider.getInstance(this).start();
    }

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];
            if (onSensorDateTransferListener != null) {
                onSensorDateTransferListener.onSensorDataTransfer(x, y, z);
            }

            if (onShowFragmentListener == null)
                return;

            if (-110 < y && y < 20) {

                checkCanShow(x, y);

            } else {
                if (whichPlace == Global.NONE_PLACE) {
                    showFragment(Global.FragmentType.DATE_FRAGMENT, -1);
                } else {
                    checkIsVertical(y, whichPlace);
                }
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void checkCanShow(float x, float y) {
        for (Place place : Global.placeList) {
            if (Math.abs(place.getAngle() - x) < 20) {
                whichPlace = place.getId();
                checkIsVertical(y, whichPlace);
                return;
            }
        }
        whichPlace = Global.NONE_PLACE;
        showFragment(Global.FragmentType.DATE_FRAGMENT, -1);
    }

    private void checkIsVertical(float y, int whichPlace) {
        if (-110 < y && y < -40) {
            showFragment(Global.FragmentType.DESCRIPTION_FRAGMENT, whichPlace);
        } else if (-30 < y && y < 20) {
            showFragment(Global.FragmentType.MAP_FRAGMENT, whichPlace);
        }
    }

    private void showFragment(int whichFragment, int whichPlace) {
        currentFragmentType = whichFragment;
        if (previousFragmentType != currentFragmentType) {
            onShowFragmentListener.onShowFragment(whichFragment, whichPlace);
        }
        previousPlace = whichPlace;
        previousFragmentType = currentFragmentType;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(listener);
        LocationProvider.getInstance(this).stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private static OnSensorDateTransferListener onSensorDateTransferListener;

    public static void registerOnSensorDateTransferListener(OnSensorDateTransferListener onSensorDateTransferListener) {
        SensorService.onSensorDateTransferListener = onSensorDateTransferListener;
    }

    public static void unregisterOnSensorDateTransferListener(OnSensorDateTransferListener onSensorDateTransferListener) {
        SensorService.onSensorDateTransferListener = null;
    }

    private static OnShowFragmentListener onShowFragmentListener;

    public static void registerOnShowFragmentListener(OnShowFragmentListener onShowFragmentListener) {
        SensorService.onShowFragmentListener = onShowFragmentListener;
    }

    public static void unregisterOnShowFragmentListener(OnShowFragmentListener onShowFragmentListener) {
        SensorService.onShowFragmentListener = null;
    }

    public interface OnSensorDateTransferListener {
        void onSensorDataTransfer(float x, float y, float z);
    }

    public interface OnShowFragmentListener {
        void onShowFragment(int whichFragment, int whichPlace);
    }

}
