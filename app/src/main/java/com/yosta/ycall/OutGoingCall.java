package com.yosta.ycall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by nphau on 12/24/2015.
 */
public class OutGoingCall extends BroadcastReceiver {

    private static boolean noCallListenerYet = true;

    @Override

    public void onReceive(final Context context, Intent intent) {

        if (noCallListenerYet) {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(
                    Context.TELEPHONY_SERVICE);
            tm.listen(new PhoneStateListener() {
                @Override
                public void onCallStateChanged(int state, String incomingNumber) {
                    switch (state) {
                        case TelephonyManager.CALL_STATE_RINGING:
                            Toast.makeText(context, "RINGING", Toast.LENGTH_LONG).show();
                            break;
                        case TelephonyManager.CALL_STATE_OFFHOOK:
                            Toast.makeText(context, "OFFHOOK", Toast.LENGTH_LONG).show();
                            break;
                        case TelephonyManager.CALL_STATE_IDLE:
                            Toast.makeText(context, "IDLE", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(context, "Default" + state, Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }, PhoneStateListener.LISTEN_CALL_STATE);
            noCallListenerYet = false;
        }
    }

}