package com.example.jiaheng.mapsensor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

public class MainActivity extends FragmentActivity implements SensorService.OnShowFragmentListener {

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        checkUserPermission();

    }

    private void init() {
        serviceIntent = new Intent(this, SensorService.class);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                DataFragment.getInstance()).commit();
    }

    private void checkUserPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET},
                    99);
        } else {
            startService(serviceIntent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 99) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                finish();
            } else {
                startService(serviceIntent);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SensorService.registerOnShowFragmentListener(this);
    }

    @Override
    protected void onPause() {
        SensorService.unregisterOnShowFragmentListener(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        SensorService.unregisterOnShowFragmentListener(this);
        super.onDestroy();
        if (serviceIntent != null)
            stopService(serviceIntent);
    }

    @Override
    public void onShowFragment(int whichFragment, int whichPlace) {

        Fragment fragment;
        if (whichFragment == Global.FragmentType.DESCRIPTION_FRAGMENT) {
            Bundle bundle = new Bundle();
            bundle.putInt("whichPlace", whichPlace);
            fragment = DescriptionFragment.getInstance();
            fragment.setArguments(bundle);
        } else if (whichFragment == Global.FragmentType.MAP_FRAGMENT) {
            Bundle bundle = new Bundle();
            bundle.putInt("whichPlace", whichPlace);
            fragment = GoogleMapFragment.getInstance();
            fragment.setArguments(bundle);
        } else {
            fragment = DataFragment.getInstance();
        }

        if (!fragment.isResumed()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    fragment).commit();
        }
    }


}
