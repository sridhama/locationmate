package sridhama.com.locationmate;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
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
        Intent i= new Intent(this, LMService.class);
        startService(i);
        // SERVICE END

        SharedPreferences userDetails = getApplicationContext().getSharedPreferences("user_data", MODE_PRIVATE);
        final String REGISTER_STATUS = userDetails.getString("is_registered", "");
        if(REGISTER_STATUS.equals("1")){
            Intent intent1 = new Intent(this, HomeActivity.class);
            startActivity(intent1);
//            finish();
        }else{
            Intent intent2 = new Intent(this, RegisterActivity.class);
            startActivity(intent2);
            finish();
        }
    }

    public void tempIntent(View v) {
        Intent i = new Intent(this, BSSIDActivity.class);
        startActivity(i);
    }

    public void tempIntent1(View v) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);

    }

    public void addFriendIntent(View v) {
        Intent i = new Intent(this, AddFriendActivity.class);
        startActivity(i);

    }

    public void homeIntent(View v) {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                System.out.println("SETTINGS_BUTTON");
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


}


