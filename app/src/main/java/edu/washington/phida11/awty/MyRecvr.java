package edu.washington.phida11.awty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by David on 5/21/2015.
 */
public class MyRecvr extends BroadcastReceiver {
    @SuppressWarnings("deprecation")
    SmsManager sms;


    @SuppressWarnings("deprecation")
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Bundle bundle = intent.getExtras();
        String smsNumbr= (String) bundle.getCharSequence("no");
        String smsText = (String) bundle.getCharSequence("text");

        Toast.makeText(context, "MyAlarmService.onStart()", Toast.LENGTH_LONG)
                .show();
        Toast.makeText(
                context,
                "MyAlarmService.onStart() with \n" + "smsNumberToSend = "
                        + smsNumbr + "\n" + "smsTextToSend = "
                        + smsText, Toast.LENGTH_LONG).show();
        sms = SmsManager.getDefault();
        sms.sendTextMessage(smsNumbr, null, smsText, null, null);
}
