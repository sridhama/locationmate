package sridhama.com.locationmate;

import android.app.IntentService;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // SERVICE START
        Intent i= new Intent(this, LMService.class);
        startService(i);
        // SERVICE END
    }

    public void tempIntent(View v) {
        Intent i = new Intent(this, LocationViewActivity.class);
        startActivity(i);
    }

    public void tempIntent1(View v) {
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);

    }

    public void addFriendIntent(View v) {
        Intent i = new Intent(this, AddFriendActivity.class);
        startActivity(i);

    }

}


