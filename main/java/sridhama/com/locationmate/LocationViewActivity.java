package sridhama.com.locationmate;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
        final String friend_name = getIntent().getStringExtra("name");
        String friend_username = getIntent().getStringExtra("friend_username");
//        String gender_bool = getIntent().getStringExtra("friend_gender");
//        char x = gender_bool.charAt(0);
//        System.out.println("GENDERBOOL: "+ gender_bool);

        ImageView iv = new ImageView(R.id.gender_img);
        iv.setImageDrawable();

        final Intent i = new Intent(this, HomeActivity.class);

        String url = "http://"+Constants.DOMAIN+"/LocationMate/data.php?username="+Constants.USERNAME+"&friend_username="+friend_username;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                TextView location = (TextView)findViewById(R.id.location);
                TextView last_seen = (TextView)findViewById(R.id.last_seen);
                TextView name_view = (TextView)findViewById(R.id.name);
                name_view.setText(friend_name);
                int start = response.indexOf("<br>");
                location.setText("Last Known Location: "+response.substring(0,start));
                last_seen.setText(response.substring(start+4));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Network Error.", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        // END VOLLEY



    }


//    public void get_data(final String friend_name, String friend_username){
//        final View v;
//        String url = "http://"+Constants.DOMAIN+"/LocationMate/data.php?username="+Constants.USERNAME+"&friend_username="+friend_username;
//        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                TextView location = (TextView)findViewById(R.id.location);
//                TextView last_seen = (TextView)findViewById(R.id.last_seen);
//                TextView name_view = (TextView)findViewById(R.id.name);
//                name_view.setText(friend_name);
//                int start = response.indexOf("<br>");
//                location.setText("Last Known Location: "+response.substring(0,start));
//                last_seen.setText("Last Seen: "+response.substring(start+4));
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "Network Error.", Toast.LENGTH_SHORT).show();
//                runIntent(v);
//            }
//        });
//        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
//        // END VOLLEY
//    }

//    public void runIntent(View v){
//        Intent i = new Intent(this, HomeActivity.class);
//        startActivity(i);
//    }

}
