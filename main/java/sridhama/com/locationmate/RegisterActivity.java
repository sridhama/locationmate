package sridhama.com.locationmate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    // Check if connected to wifi

        public void signupfunc(View v){

            WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifi_info = wm.getConnectionInfo();
            String raw_BSSID = wifi_info.getBSSID();
            String BSSID = raw_BSSID.substring(0, raw_BSSID.length() - 3);


            final Intent intent = new Intent(getBaseContext(), ViewFriendsActivity.class);
        EditText fname = (EditText)findViewById(R.id.fname);
        EditText lname = (EditText)findViewById(R.id.lname);
            String gender = "";
        final EditText username = (EditText)findViewById(R.id.username);
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
        String url = "http://"+ Constants.DOMAIN+"/LocationMate/add_user.php?fname="+fname.getText()+"&lname="+lname.getText()+"&username="+username.getText()+"&gender="+gender+"&bssid="+BSSID;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toString().substring(0,7).equals("SUCCESS")){
                    SharedPreferences sharedPref = getSharedPreferences("user_data",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("username",username.getText().toString());
                    editor.putString("secret_key",response.toString().substring(7));
                    editor.putString("is_registered","1");
                    editor.commit();
                    startActivity(intent);
                    finish();
                }else if(response.toString().equals("USERNAME_EXISTS")){
                    Toast.makeText(getApplicationContext(), "Username already exists.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                }





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
