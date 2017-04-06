package sridhama.com.locationmate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        String friend_phone = getIntent().getStringExtra("friend_phone");
        String gender_bool = getIntent().getStringExtra("friend_gender");


        ImageView iv = (ImageView)findViewById(R.id.gender_img);
        if(gender_bool.equals("0")) {
            iv.setImageResource(R.drawable.male);
        }else{
            iv.setImageResource(R.drawable.female);
        }

        final Intent i = new Intent(this, ViewFriendsActivity.class);

        SharedPreferences userDetails = getApplicationContext().getSharedPreferences("user_data", MODE_PRIVATE);
        final String STORED_PHONE = userDetails.getString("phone", "");

        String url = "http://"+Constants.DOMAIN+"/LocationMate/data.php?phone="+STORED_PHONE+"&friend_phone="+friend_phone;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                TextView location = (TextView)findViewById(R.id.location);
                TextView last_seen = (TextView)findViewById(R.id.last_seen);
                TextView name_view = (TextView)findViewById(R.id.name);
                name_view.setText(friend_name);
                int start = response.indexOf("<br>");
                location.setText(response.substring(0,start));
                last_seen.setText(response.substring(start+4));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "LocationMate Server Down", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        // END VOLLEY



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void call(View v) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", getIntent().getStringExtra("friend_phone"), null)));
    }

}
