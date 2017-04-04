package sridhama.com.locationmate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewFriendsActivity extends AppCompatActivity {
    GridView androidGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friends);

if(!wifiStatus()) {
    Intent i= new Intent(this, NoWifiActivity.class);
    startActivity(i);
    finish();
}else{

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Intent addfriendintent = new Intent(this, AddFriendActivity.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(addfriendintent);
            }
        });


        // START OLD CODE
        SharedPreferences userDetails = getApplicationContext().getSharedPreferences("user_data", MODE_PRIVATE);
        final String STORED_PHONE = userDetails.getString("phone", "");
        final TextView tv = (TextView) findViewById(R.id.message);
        final TextView friend_text = (TextView) findViewById(R.id.friend_text);

        String url = "http://" + Constants.DOMAIN + "/LocationMate/view_friends.php?phone=" + STORED_PHONE;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String result = response.toString();
                if (result.equals("NO_FRIENDS")) {
                    tv.setText("Add friends to get started!");
                    friend_text.setVisibility(View.GONE);
                    return;
                }
                // parsing JSON
                try {
                    tv.setVisibility(View.GONE);

                    JSONObject jObject = new JSONObject(result);
                    JSONArray jArray = jObject.getJSONArray("names");
                    ArrayList<String> listdata = new ArrayList<String>();
                    if (jArray != null) {
                        for (int k = 0; k < jArray.length(); k++) {
                            listdata.add(jArray.getString(k));
                        }
                    }
                    friend_text.setText("Friends ("+listdata.size()+")");
                    final String[] gridViewString = listdata.toArray(new String[0]);
                    JSONArray jArray1 = jObject.getJSONArray("phones");
                    ArrayList<String> phonedata = new ArrayList<String>();
                    if (jArray1 != null) {
                        for (int k = 0; k < jArray1.length(); k++) {
                            phonedata.add(jArray1.getString(k));
                        }
                    }
                    final String[] phoneString = phonedata.toArray(new String[0]);


                    JSONArray jArray2 = jObject.getJSONArray("genders");
                    final ArrayList<Integer> genderdata = new ArrayList<Integer>();
                    if (jArray2 != null) {
                        for (int k = 0; k < jArray2.length(); k++) {
                            genderdata.add(jArray2.getInt(k));
                        }
                    }
                    final int[] gridViewImageId = new int[genderdata.size()];

                    for (int k = 0; k < genderdata.size(); k++) {
                        int val = genderdata.get(k);
                        if (val == 0) {
//                    gridViewImageId[k] = R.drawable.male;
                            gridViewImageId[k] = R.drawable.male;
                        } else {
                            gridViewImageId[k] = R.drawable.female;
                        }
                    }


                    CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(ViewFriendsActivity.this, gridViewString, gridViewImageId);
                    androidGridView = (GridView) findViewById(R.id.grid_view_image_text);
                    androidGridView.setAdapter(adapterViewAndroid);
                    androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                            Intent intent = new Intent(getBaseContext(), LocationViewActivity.class);
                            intent.putExtra("name", gridViewString[+i]);
                            intent.putExtra("friend_phone", phoneString[+i]);
                            intent.putExtra("friend_gender", String.valueOf(genderdata.get(+i)));
                            startActivity(intent);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Network Error.", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        // END OLD CODE

    }


}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.refresh) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            return true;
        }

        if(id == R.id.contribute){
            Intent contribute_intent = new Intent(this, BSSIDActivity.class);
            startActivity(contribute_intent);
            return true;
        }

        if(id == R.id.logout){
            SharedPreferences sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("is_logged_in","0");
            editor.remove("phone");
            editor.remove("secret_key");
            editor.commit();
            Intent logout_intent = new Intent(this, LoginActivity.class);
            startActivity(logout_intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    @Override
    public void onBackPressed() {
        return;
    }

}
