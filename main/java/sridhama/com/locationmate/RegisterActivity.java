package sridhama.com.locationmate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        String s = "By signing up you agree to our <u>Terms of Service</u>";
        ((TextView)findViewById(R.id.textView2)).setText(Html.fromHtml(s));

        SharedPreferences userDetails = getApplicationContext().getSharedPreferences("user_data", MODE_PRIVATE);
        final String LOGIN_STATUS = userDetails.getString("is_logged_in", "");
        if (LOGIN_STATUS.equals("1")) {
            Intent intent1 = new Intent(this, ViewFriendsActivity.class);
            startActivity(intent1);
            finish();
        }

    }

    // Check if connected to wifi

        public void signupfunc(View v){

            WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifi_info = wm.getConnectionInfo();
            String raw_BSSID = wifi_info.getBSSID();
            String BSSID = raw_BSSID.substring(0, raw_BSSID.length() - 1);



            final Intent intent = new Intent(getBaseContext(), MainActivity.class);
        EditText name = (EditText)findViewById(R.id.name_reg);
            EditText password = (EditText)findViewById(R.id.password_login);
//        EditText lname = (EditText)findViewById(R.id.lname);
            String gender = "";
        final EditText phone = (EditText)findViewById(R.id.phone_number_login);

            RadioGroup rg = (RadioGroup) findViewById(R.id.genderRadio);
            int selectedId = rg.getCheckedRadioButtonId();
            if(selectedId == -1){
                Toast.makeText(getApplicationContext(), "Select a gender.", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton selectedButton = (RadioButton)findViewById(selectedId);
            if(selectedButton.getText().equals("Female")){
                gender = "1";
            }else{
                gender = "0";
            }
            // START VOLLEY
        String url = "http://"+ Constants.DOMAIN+"/LocationMate/add_user.php?name="+name.getText().toString().replace(" ","%20")+"&phone="+phone.getText()+"&gender="+gender+"&bssid="+BSSID+"&password="+md5(password.getText().toString());
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toString().substring(0,7).equals("SUCCESS")){
                    SharedPreferences sharedPref = getSharedPreferences("user_data",Context.MODE_PRIVATE);
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

    public void login(View v){
        Intent i = new Intent(this,LoginActivity.class);
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

    @Override
    public void onBackPressed() {
        return;
    }

    public void tos_link(View v){
        Intent i = new Intent(this, ToSActivity.class);
        startActivity(i);
    }


}
