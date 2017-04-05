package sridhama.com.locationmate;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sridhama on 3/31/17.
 */

public class LMService extends Service {

    int hidden = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SharedPreferences userDetails = getApplicationContext().getSharedPreferences("user_data", MODE_PRIVATE);
                String login_status = userDetails.getString("is_logged_in", "");
                SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean hidebool = SP.getBoolean("hide_location",false);
                final String STORED_PHONE = userDetails.getString("phone", "");
                if(login_status.equals("1") && hidebool == false) {
                    update_bssid(STORED_PHONE);
                }else{
                    hide_bssid(STORED_PHONE);
                }
            }
        }, 1000,60000);
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }

    public String getBSSID() {
        WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifi_info = wm.getConnectionInfo();
        String raw_BSSID = wifi_info.getBSSID();
        String BSSID = raw_BSSID.substring(0, raw_BSSID.length() - 1);
        return BSSID;
    }

    public void update_bssid(String phone){
        String bssid;
        try {
            bssid = getBSSID();
        }catch (Exception e){
            return;
        }
        SharedPreferences userDetails = getApplicationContext().getSharedPreferences("user_data", MODE_PRIVATE);
        final String STORED_PHONE = userDetails.getString("phone", "");
        String url = "http://"+Constants.DOMAIN+"/LocationMate/update.php?phone="+STORED_PHONE+"&bssid="+bssid;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(hidden != 0){
                    hidden = 0;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        // END VOLLEY
    }

    public void hide_bssid(String phone){
        if(hidden == 0) {
            SharedPreferences userDetails = getApplicationContext().getSharedPreferences("user_data", MODE_PRIVATE);
            final String STORED_PHONE = userDetails.getString("phone", "");
            String url = "http://" + Constants.DOMAIN + "/LocationMate/update.php?phone=" + STORED_PHONE + "&bssid=HIDDEN";
            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    hidden = 1;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            MySingleton.getInstance(this).addToRequestQueue(stringRequest);
            // END VOLLEY
        }
    }

}