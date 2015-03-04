package co.uk.sentinelweb.gumtree.test.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

/**
 * Various launch intents.
 * Created by robert on 04/03/2015.
 */
public class SendIntent {

    /**
     * Launch email intent
     * @param c {@link android.content.Context}
     * @param subject subject line
     * @param content email body
     * @param to address to send to (optional)
     */
    public static void launchEmailIntent(final Context c,final String subject, final String content, final String to) {
        String toURL = "mailto://";
        String[] addressList = null;
        if (to!=null) {
            toURL+=to;
            addressList= new String[] {to};
        }
        final Intent myIntent = new Intent(Intent.ACTION_SEND, Uri.parse(toURL));
        myIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (addressList!=null) {
            myIntent.putExtra(Intent.EXTRA_EMAIL ,addressList);
        }
        myIntent.putExtra(Intent.EXTRA_TEXT, content);
        myIntent.setType("message/rfc822");
        c.startActivity(myIntent);
    }

    /**
     * Launch SMS intent
     * @param c{@link android.content.Context}
     * @param number phone number
     * @param body sms body (not working)
     */
    public static void launchSmsIntent(final Context c, final String number, final String body) {
        final Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null));
        smsIntent.putExtra("sms_body", body);
        c.startActivity(smsIntent);
        //SmsManager.getDefault().sendTextMessage(number, null, body, null, null);
    }

    /**
     * Launch dial intent
     * @param c {@link android.content.Context}
     * @param number phone number
     */
    public static void launchDialIntent(final Context c, final String number) {
        final Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + number));
        c.startActivity(callIntent);
    }
}
