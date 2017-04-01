package sridhama.com.locationmate;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class SignupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

//    public void signupfunc(View v){
//        EditText fname = (EditText)findViewById(R.id.fname);
//        EditText lname = (EditText)findViewById(R.id.lname);
//        EditText username = (EditText)findViewById(R.id.username);
//        // START VOLLEY
//        String url = "http://"+ Constants.DOMAIN+"/LocationMate/add_user.php?fname="+fname.getText()+"&lname="+lname.getText()+"&username="+username.getText()+"&gender=0";
//        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if(response.toString().equals("SUCCESS")){
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "Network Error.", Toast.LENGTH_SHORT).show();
//            }
//        });
//        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
//        // END VOLLEY
//    }

    public void tempIntent(View v) {
        Intent i = new Intent(this, AddFriendActivity.class);
        startActivity(i);
    }

}
