package com.yosta.ycall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.List;

public class MainActivity extends Activity {

    protected TelephonyManager telephonyManager;
    protected Button btnCall;
    protected EditText txtNumber;
    protected String LOG_TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCall = (Button) findViewById(R.id.btn_call);
        txtNumber = (EditText) findViewById(R.id.txt_phone_number);

        OnCreate();
    }

    protected void OnCreate()
    {

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new PhoneCallListener(),
                PhoneStateListener.LISTEN_CALL_STATE | PhoneStateListener.LISTEN_CELL_INFO // Requires API 17
                        | PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
                        | PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR
                        | PhoneStateListener.LISTEN_MESSAGE_WAITING_INDICATOR);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + txtNumber.getText().toString()));
                    startActivity(callIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Your call has failed...", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    // Monitor phone call activities
    private class PhoneCallListener extends PhoneStateListener {

        int prev_state = TelephonyManager.CALL_STATE_IDLE;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING: {
                    Toast.makeText(getApplicationContext(), "Dang goi den ...", Toast.LENGTH_LONG).show();
                }
                case TelephonyManager.CALL_STATE_OFFHOOK: {
                    Toast.makeText(getApplicationContext(), "Dang goi di ...", Toast.LENGTH_LONG).show();
                }
                case TelephonyManager.CALL_STATE_IDLE: {
                    if ((prev_state == TelephonyManager.CALL_STATE_OFFHOOK)) {
                        Toast.makeText(getApplicationContext(), "Ket thuc cuoc goi ...", Toast.LENGTH_LONG).show();
                    } else if ((prev_state == TelephonyManager.CALL_STATE_RINGING)) {
                        Toast.makeText(getApplicationContext(), "Tu choi cuoc goi ...", Toast.LENGTH_LONG).show();
                    }
                }
                default: {
                    Toast.makeText(getApplicationContext(), "Da bat may", Toast.LENGTH_LONG).show();
                }
            }
            if (telephonyManager.getNetworkType() == TelephonyManager.PHONE_TYPE_GSM)
                Toast.makeText(getApplicationContext(), "Da bat may", Toast.LENGTH_LONG).show();

            prev_state = state;
        }

        @Override
        public void onCellInfoChanged(List<CellInfo> cellInfo) {
            super.onCellInfoChanged(cellInfo);
            Toast.makeText(getApplicationContext(), "onCellInfoChanged: " + cellInfo, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCallForwardingIndicatorChanged(boolean cfi) {
            super.onCallForwardingIndicatorChanged(cfi);
            Toast.makeText(getApplicationContext(), "onCallForwardingIndicatorChanged: " + cfi, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onMessageWaitingIndicatorChanged(boolean mwi) {
            super.onMessageWaitingIndicatorChanged(mwi);
            Toast.makeText(getApplicationContext(), "onMessageWaitingIndicatorChanged: " + mwi, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onDataConnectionStateChanged(int state, int networkType) {
            super.onDataConnectionStateChanged(state, networkType);
            switch (state) {
                case TelephonyManager.DATA_DISCONNECTED:
                    Log.i(LOG_TAG, "onDataConnectionStateChanged: DATA_DISCONNECTED");
                    break;
                case TelephonyManager.DATA_CONNECTING:
                    Toast.makeText(getApplicationContext(), "onDataConnectionStateChanged: DATA_CONNECTING", Toast.LENGTH_LONG).show();
                    break;
                case TelephonyManager.DATA_CONNECTED:
                    Toast.makeText(getApplicationContext(), "onDataConnectionStateChanged: DATA_CONNECTED", Toast.LENGTH_LONG).show();
                    break;
                case TelephonyManager.DATA_SUSPENDED:
                    Toast.makeText(getApplicationContext(), "onDataConnectionStateChanged: DATA_SUSPENDED", Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "onDataConnectionStateChanged: UNKNOWN", Toast.LENGTH_LONG).show();
                    break;
            }

            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_CDMA");
                    break;
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_EDGE");
                    break;
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_EVDO_0");
                    break;
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_GPRS");
                    break;
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_HSDPA");
                    break;
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_HSPA");
                    break;
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_IDEN");
                    break;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_LTE");
                    break;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_UMTS");
                    break;
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    Log.i(LOG_TAG, "onDataConnectionStateChanged: NETWORK_TYPE_UNKNOWN");
                    break;
                default:
                    Log.w(LOG_TAG, "onDataConnectionStateChanged: Undefined Network: "
                            + networkType);
                    break;
            }
        }

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            Log.i(LOG_TAG, "onSignalStrengthsChanged: " + signalStrength);
            if (signalStrength.isGsm()) {
                Log.i(LOG_TAG, "onSignalStrengthsChanged: getGsmBitErrorRate "
                        + signalStrength.getGsmBitErrorRate());
                Log.i(LOG_TAG, "onSignalStrengthsChanged: getGsmSignalStrength "
                        + signalStrength.getGsmSignalStrength());
            } else if (signalStrength.getCdmaDbm() > 0) {
                Log.i(LOG_TAG, "onSignalStrengthsChanged: getCdmaDbm "
                        + signalStrength.getCdmaDbm());
                Log.i(LOG_TAG, "onSignalStrengthsChanged: getCdmaEcio "
                        + signalStrength.getCdmaEcio());
            } else {
                Log.i(LOG_TAG, "onSignalStrengthsChanged: getEvdoDbm "
                        + signalStrength.getEvdoDbm());
                Log.i(LOG_TAG, "onSignalStrengthsChanged: getEvdoEcio "
                        + signalStrength.getEvdoEcio());
                Log.i(LOG_TAG, "onSignalStrengthsChanged: getEvdoSnr "
                        + signalStrength.getEvdoSnr());
            }

            // Reflection code starts from here
            try {
                Method[] methods = android.telephony.SignalStrength.class
                        .getMethods();
                for (Method methd : methods) {
                    if (methd.getName().equals("getLteSignalStrength")
                            || methd.getName().equals("getLteRsrp")
                            || methd.getName().equals("getLteRsrq")
                            || methd.getName().equals("getLteRssnr")
                            || methd.getName().equals("getLteCqi")) {
                        Log.i(LOG_TAG,
                                "onSignalStrengthsChanged: " + methd.getName() + " "
                                        + methd.invoke(signalStrength));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
