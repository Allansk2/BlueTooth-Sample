package com.teamlinkt.bluetooth1;


import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private BlueToothController mController = new BlueToothController();
    private Toast mToast;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_DISABLE_BT = 2;
    private static final int REQUEST_DISCOVERABLE_BT = 3;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
            switch (state) {
                case BluetoothAdapter.STATE_OFF:
                    Log.e(TAG,"STATE_OFF");
                    break;
                case BluetoothAdapter.STATE_ON:
                    Log.e(TAG,"STATE_ON");

                    break;
                case BluetoothAdapter.STATE_TURNING_ON:
                    Log.e(TAG,"STATE_TURNING_ON");

                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    Log.e(TAG,"STATE_TURNING_OFF");

                    break;

                default:
                    Log.e(TAG,"STATE unknown");

                    break;

            }
        }
    };

    static {
        System.loadLibrary("native-lib");
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {


                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();


        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy


    }


    /*
  on click event
  */
    @OnClick({R.id.is_support_blue_tooth, R.id.is_blue_tooth_enable, R.id.turn_on_blue_tooth, R.id.turn_off_blue_tooth})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.is_support_blue_tooth:
                showToast("Blue tooth support " + mController.isSupportBlueTooth());
                break;

            case R.id.is_blue_tooth_enable:
                showToast("Blue tooth enable " + mController.getBlueToothStatus());
                break;

            case R.id.turn_on_blue_tooth:
                mController.turnOnBlueTooth(this, REQUEST_ENABLE_BT);
                break;

            case R.id.turn_off_blue_tooth:
                mController.turnOffBlueTooth(this, REQUEST_DISABLE_BT);
                break;

        }
    }


    private void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            showToast("Open blue tooth success");
        } else {
            showToast("Open blue tooth fail");
        }
    }


}
