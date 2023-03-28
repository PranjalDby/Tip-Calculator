package com.android.tip;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

public class NetworkChange extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(isNetworkChange(context.getApplicationContext())){
            Toast.makeText(context,"Airplane mode is on",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,"Aiplane mode is OFF",Toast.LENGTH_SHORT).show();
        }
    }
    private static boolean isNetworkChange(Context context){
        return Settings.System.getInt(context.getContentResolver(),Settings.Global.AIRPLANE_MODE_ON,0) != 0;

    }
}
