package com.nphau.caller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_call)
    Button caller;
    @Bind(R.id.txt_phone_number)
    TextView txtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new PhoneCallListener(), PhoneStateListener.LISTEN_CALL_STATE);

        caller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + txtPhone.getText().toString()));
                    startActivity(callIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Your call has failed...", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Monitor phone call activities
    private class PhoneCallListener extends PhoneStateListener {

        int prev_state;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (state == TelephonyManager.CALL_STATE_RINGING) {
                Toast.makeText(getApplicationContext(), "Dang goi den ...", Toast.LENGTH_LONG).show();
            } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                Toast.makeText(getApplicationContext(), "Dang goi di ...", Toast.LENGTH_LONG).show();
            } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                if ((prev_state == TelephonyManager.CALL_STATE_OFFHOOK)) {
                    Toast.makeText(getApplicationContext(), "Ket thuc cuoc goi ...", Toast.LENGTH_LONG).show();
                } else if ((prev_state == TelephonyManager.CALL_STATE_RINGING)) {
                    Toast.makeText(getApplicationContext(), "Tu choi cuoc goi ...", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Da bat may", Toast.LENGTH_LONG).show();
            }

            prev_state = state;
        }
    }
}
