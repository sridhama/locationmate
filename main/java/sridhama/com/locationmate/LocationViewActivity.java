package sridhama.com.locationmate;

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

public class LocationViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_view);

        get_data("Sridhama Prakhya", "yashwanth", "sridhama");
    }


    public void get_data(final String friend_name, String username, String friend_username){
        String url = "http://10.7.20.61/LocationMate/data.php?username="+username+"&friend_username="+friend_username;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                TextView location = (TextView)findViewById(R.id.location);
                TextView last_seen = (TextView)findViewById(R.id.last_seen);
                TextView name_view = (TextView)findViewById(R.id.name);
                name_view.setText(friend_name);
                int start = response.indexOf("<br>");
                location.setText("Last Known Location: "+response.substring(0,start));
                last_seen.setText("Last Seen: "+response.substring(start+4));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Network Error.", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        // END VOLLEY
    }


}
