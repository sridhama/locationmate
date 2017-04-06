package sridhama.com.locationmate;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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

public class ViewFriendsatLocationActivity extends AppCompatActivity {

    GridView androidGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friendsat_location);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if(!wifiStatus()) {
            Intent i= new Intent(this, NoWifiActivity.class);
            startActivity(i);
            finish();
        }else{

            // START OLD CODE
            SharedPreferences userDetails = getApplicationContext().getSharedPreferences("user_data", MODE_PRIVATE);
            final String STORED_PHONE = userDetails.getString("phone", "");
            String LOCATION_BSSID = getIntent().getStringExtra("location_bssid");
            final TextView tv = (TextView) findViewById(R.id.message);
            final TextView friend_text = (TextView) findViewById(R.id.friend_text);
            SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            boolean hidebool = SP.getBoolean("hide_location",false);
            if(hidebool == true){
                tv.setText("Enable Location to View Friends");
                friend_text.setVisibility(View.GONE);
                return;
            }

            String url = "http://" + Constants.DOMAIN + "/LocationMate/view_friends_at.php?phone=" + STORED_PHONE+"&bssid="+LOCATION_BSSID;
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
                                gridViewImageId[k] = R.drawable.male;
                            } else {
                                gridViewImageId[k] = R.drawable.female;
                            }
                        }


                        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(ViewFriendsatLocationActivity.this, gridViewString, gridViewImageId);
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
                                                               }
                        );

                        androidGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                                                       @Override
                                                                       public boolean onItemLongClick(AdapterView<?> parent, View view, final int i, long id) {
                                                                           AlertDialog.Builder a_builder = new AlertDialog.Builder(ViewFriendsatLocationActivity.this);
                                                                           a_builder.setCancelable(false).setPositiveButton("Unfriend",new DialogInterface.OnClickListener() {
                                                                               @Override
                                                                               public void onClick(DialogInterface dialog, int which) {

                                                                                   String url = "http://"+Constants.DOMAIN+"/LocationMate/unfriend.php?phone="+STORED_PHONE+"&friend_phone="+phoneString[+i];
                                                                                   StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                                                                                       @Override
                                                                                       public void onResponse(String response) {
                                                                                           Toast.makeText(getApplicationContext(), "Friend Removed", Toast.LENGTH_SHORT).show();
                                                                                           Intent intent = getIntent();
                                                                                           finish();
                                                                                           startActivity(intent);
                                                                                       }
                                                                                   }, new Response.ErrorListener() {
                                                                                       @Override
                                                                                       public void onErrorResponse(VolleyError error) {
                                                                                           Toast.makeText(getApplicationContext(), "Error Removing Friend.", Toast.LENGTH_SHORT).show();
                                                                                       }
                                                                                   });
                                                                                   MySingleton.getInstance(ViewFriendsatLocationActivity.this).addToRequestQueue(stringRequest);
                                                                                   // END VOLLEY

                                                                               }
                                                                           })
                                                                                   .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                                                                       @Override
                                                                                       public void onClick(DialogInterface dialog, int which) {
                                                                                           dialog.cancel();
                                                                                       }
                                                                                   }) ;
                                                                           AlertDialog alert = a_builder.create();
                                                                           alert.setTitle("Unfriend "+gridViewString[+i]);
                                                                           alert.show();

                                                                           return true;
                                                                       }
                                                                   }
                        );


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "LocationMate Server Down", Toast.LENGTH_SHORT).show();
                }
            });
            MySingleton.getInstance(this).addToRequestQueue(stringRequest);
            // END OLD CODE

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
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

}
