package sridhama.com.locationmate;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // SERVICE START
        Intent service_LM = new Intent(this, LMService.class);
        startService(service_LM);
        // SERVICE END

        // check wifi status
        if(!wifiStatus()){
            Intent i= new Intent(this, NoWifiActivity.class);
            startActivity(i);
            finish();
        }else {
            SharedPreferences userDetails = getApplicationContext().getSharedPreferences("user_data", MODE_PRIVATE);
            final String LOGIN_STATUS = userDetails.getString("is_logged_in", "");
            if (LOGIN_STATUS.equals("1")) {
                Intent intent1 = new Intent(this, ViewFriendsActivity.class);
                startActivity(intent1);
                finish();
            } else {
                Intent intent2 = new Intent(this, RegisterActivity.class);
                startActivity(intent2);
                finish();
            }

        }
        finish();

    }

    public void tempIntent1(View v) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    public void viewfriends(View view){
        Intent i = new Intent(this, ViewFriendsActivity.class);
        startActivity(i);
    }

    public boolean wifiStatus(){
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi.isConnected()) {
            return true;
        }else{
            return false;
        }
    }


}


