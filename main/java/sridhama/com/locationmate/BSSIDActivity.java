package sridhama.com.locationmate;

import android.app.ActionBar;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class BSSIDActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bssid);
//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void setvals(View view){
        TextView bssid = (TextView)findViewById(R.id.bssid);
        TextView strength = (TextView)findViewById(R.id.strength);

        WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifi_info = wm.getConnectionInfo();
        int RSSI = wifi_info.getRssi();
        String BSSID = wifi_info.getBSSID();
//        String BSSID = raw_BSSID.substring(0, raw_BSSID.length() - 1);
        int signal_level = wm.calculateSignalLevel(RSSI, 100) + 1;
        bssid.setText("BSSID: "+BSSID);
        strength.setText("Signal Strength: "+String.valueOf(signal_level));
    }

    public void upload(View v){
        EditText location = (EditText)findViewById(R.id.location);
        String val = location.getText().toString().replace(" ","%20");
        WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifi_info = wm.getConnectionInfo();
        String raw_BSSID = wifi_info.getBSSID();
        String BSSID = raw_BSSID.substring(0, raw_BSSID.length() - 1);
        String url = "http://"+Constants.DOMAIN+"/LocationMate/upload.php?bssid="+BSSID+"&location="+val;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                TextView bssid = (TextView)findViewById(R.id.bssid);
                bssid.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "LocationMate Server Down", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        // END VOLLEY

    }


}
