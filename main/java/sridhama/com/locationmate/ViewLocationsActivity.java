package sridhama.com.locationmate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewLocationsActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_locations);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences userDetails = getApplicationContext().getSharedPreferences("user_data", MODE_PRIVATE);
        final String STORED_PHONE = userDetails.getString("phone", "");
        String url = "http://" + Constants.DOMAIN + "/LocationMate/view_locations.php?phone=" + STORED_PHONE;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String result = response.toString();
                if (result.equals("NO_FRIENDS")) {
                    finish();
                    return;
                }
                // parsing JSON
                try {
                    JSONObject jObject = new JSONObject(result);
                    JSONArray jArray = jObject.getJSONArray("locations");
                    ArrayList<String> listdata = new ArrayList<String>();
                    if (jArray != null) {
                        for (int k = 0; k < jArray.length(); k++) {
                            listdata.add(jArray.getString(k));
                        }
                    }

                    final String [] locationsString=listdata.toArray(new String[0]);

                    JSONArray jArray1 = jObject.getJSONArray("bssids");
                    final ArrayList<String> bssiddata = new ArrayList<String>();
                    if (jArray1 != null) {
                        for (int k = 0; k < jArray1.length(); k++) {
                            bssiddata.add(jArray1.getString(k));
                        }
                    }
                    final String[] bssidsString = bssiddata.toArray(new String[0]);

                    ArrayAdapter adapter = new ArrayAdapter<String>(ViewLocationsActivity.this, android.R.layout.simple_list_item_1, locationsString);

                    listView = (ListView) findViewById(R.id.locations_listview);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                        @Override
                                                        public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                                                            Intent intent = new Intent(getBaseContext(), ViewFriendsatLocationActivity.class);
                                                            intent.putExtra("location_bssid", bssidsString[+i]);
                                                            startActivity(intent);
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

}
