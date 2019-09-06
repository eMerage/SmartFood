package emerge.project.onmeal.service.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;


import emerge.project.onmeal.ui.activity.numbervalidate.NumberValidateCodeListner;


public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static NumberValidateCodeListner mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();
        Object[] pdus = (Object[]) data.get("pdus");
        for(int i=0; i<pdus.length; i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String message =smsMessage.getDisplayMessageBody();

            try {
                mListener.messageReceived(message);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

    }


    public static void bindListener(NumberValidateCodeListner listener){
        mListener = listener;
    }
}
