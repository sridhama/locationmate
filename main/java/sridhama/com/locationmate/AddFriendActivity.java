package sridhama.com.locationmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Timer;
import java.util.TimerTask;

public class AddFriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        final TextView code = (TextView) findViewById(R.id.code);
        // Update Code
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferences userDetails = getApplicationContext().getSharedPreferences("user_data", MODE_PRIVATE);
                                final String STORED_SECRET_KEY = userDetails.getString("secret_key", "");
                                update_hash(STORED_SECRET_KEY,code);
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
        // Ends here

    }

    public String generate_hash (String secret, String timestamp){
        int salt = Integer.parseInt(timestamp.substring(7, 8));
        char[] hash = new char[secret.length()];
        int ascii_val;
        int sum;
        char temp;
        int temp_val;
        for (int i = 0; i < secret.length(); i++) {
            ascii_val = (int) secret.charAt(i);
            if (ascii_val < 65) {
                temp_val = salt + ((int) secret.charAt(i) - 48);
//                System.out.println(temp_val);
                temp = (char) (48 + temp_val % 10);
            } else {
                sum = salt + ascii_val;
                if (sum > 91) {
                    sum += 64;
                }
                temp = (char) sum;
            }
            hash[i] = temp;
        }
        return String.valueOf(hash);
    }
    public void update_hash(String secret,TextView code) {
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        code.setText(generate_hash(secret, ts));
    }

    public void pair(final View v){
        EditText friend_code = (EditText) findViewById(R.id.friend_code);
        String friend_codeText = friend_code.getText().toString();

        SharedPreferences userDetails = getApplicationContext().getSharedPreferences("user_data", MODE_PRIVATE);
        final String STORED_USERNAME = userDetails.getString("username", "");

        String url = "http://"+Constants.DOMAIN+"/LocationMate/add_friend.php?username="+STORED_USERNAME+"&friend_code="+friend_codeText;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                runIntent(v);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error Adding Friend.", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        // END VOLLEY
    }

    public void runIntent(View v){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

}


