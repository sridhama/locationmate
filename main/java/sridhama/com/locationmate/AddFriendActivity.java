package sridhama.com.locationmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
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
        final TextView notif_text = (TextView) findViewById(R.id.notif_text);
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
                                update_hash(STORED_SECRET_KEY,code,notif_text);
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
        EditText friend_code = (EditText) findViewById(R.id.friend_code);
        friend_code.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(6)});


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
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
    public void update_hash(String secret,TextView code, TextView notif_text) {
        Long tsLong = System.currentTimeMillis()/1000;
        Long time_rem = 100 - tsLong%100;
        if(time_rem == 1){
            notif_text.setText("This is your unique pairing code. This code will update in " + time_rem.toString() + " second.");
        }else {
            notif_text.setText("This is your unique pairing code. This code will update in " + time_rem.toString() + " seconds.");
        }
        String ts = tsLong.toString();
        code.setText(generate_hash(secret, ts));
    }

    public void pair(final View v){
        EditText friend_code = (EditText) findViewById(R.id.friend_code);
        String friend_codeText = friend_code.getText().toString();

        SharedPreferences userDetails = getApplicationContext().getSharedPreferences("user_data", MODE_PRIVATE);
        final String STORED_PHONE = userDetails.getString("phone", "");

        String url = "http://"+Constants.DOMAIN+"/LocationMate/add_friend.php?phone="+STORED_PHONE+"&friend_code="+friend_codeText;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("SUCCESS")){
                    runIntent(v);
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid Pairing Code", Toast.LENGTH_SHORT).show();
                }
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
        Intent i = new Intent(this, ViewFriendsActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, ViewFriendsActivity.class);
        startActivity(i);
        return;
    }

}


