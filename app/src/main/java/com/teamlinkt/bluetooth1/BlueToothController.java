package com.teamlinkt.bluetooth1;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

/**
 * Created by Regina on 2018-04-26.
 */

public class BlueToothController {

    private BluetoothAdapter mAdapter;

    public BlueToothController() {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * If blueTooth supported
     */
    public boolean isSupportBlueTooth() {
        if (mAdapter != null) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * Check bluetooth state
     * @return
     */
    public boolean getBlueToothStatus() {
        assert (mAdapter != null);
        return mAdapter.isEnabled();
    }


    /**
     * Turn on bluetooth
     * @param activity
     * @param requestCode
     */
    public void turnOnBlueTooth(Activity activity, int requestCode) {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBtIntent, requestCode);

    }


    /**
     * Turn off bluetooth
     * @param activity
     * @param requestCode
     */
    public void turnOffBlueTooth(Activity activity, int requestCode) {
        mAdapter.disable();
    }


}
