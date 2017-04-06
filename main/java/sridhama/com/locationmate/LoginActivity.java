package sridhama.com.locationmate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences userDetails = getApplicationContext().getSharedPreferences("user_data", MODE_PRIVATE);
        final String LOGIN_STATUS = userDetails.getString("is_logged_in", "");
        if (LOGIN_STATUS.equals("1")) {
            Intent intent1 = new Intent(this, ViewFriendsActivity.class);
            startActivity(intent1);
            finish();
        }

    }

    public void register(View v){
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public void loginfunc(View v){

        final Intent intent = new Intent(getBaseContext(), MainActivity.class);
        final EditText phone = (EditText) findViewById(R.id.phone_number_login);
        EditText password = (EditText) findViewById(R.id.password_login);
        String pass_encrypt = md5(password.getText().toString());

        // START VOLLEY
        String url = "http://"+ Constants.DOMAIN+"/LocationMate/login.php?phone="+phone.getText().toString()+"&password="+pass_encrypt;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toString().substring(0,7).equals("SUCCESS")){
                    SharedPreferences sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("phone",phone.getText().toString());
                    editor.putString("secret_key",response.toString().substring(7));
                    editor.putString("is_logged_in","1");
                    editor.commit();
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                }

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

    @Override
    public void onBackPressed() {
        return;
    }

}
