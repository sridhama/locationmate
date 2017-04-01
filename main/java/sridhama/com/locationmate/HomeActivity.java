package sridhama.com.locationmate;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
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

public class HomeActivity extends AppCompatActivity {

    GridView androidGridView;

//    String[] gridViewString = {"Yashwanth Soodini","Swaran Mudumbi"};
//    String[] usernameString = {"yashwanth", "arsenal"};
//    int[] gridViewImageId = {R.drawable.female, R.drawable.male};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        String url = "http://"+Constants.DOMAIN+"/LocationMate/view_friends.php?username="+Constants.USERNAME;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
        String result = response.toString();
//        String result = "{\"names\":[\"Swaran M\",\"Yashwanth Soodini\",\"John Doe\"],\"usernames\":[\"arsenal\",\"yashwanth\",\"john\"],\"genders\":[\"0\",\"1\",\"0\"]}";
        // parsing JSON
        try {
            JSONObject jObject = new JSONObject(result);
            JSONArray jArray = jObject.getJSONArray("names");
            ArrayList<String> listdata = new ArrayList<String>();
            if (jArray != null) {
                for (int k = 0; k < jArray.length(); k++) {
                    listdata.add(jArray.getString(k));
                }
            }
            final String[] gridViewString = listdata.toArray(new String[0]);
            JSONArray jArray1 = jObject.getJSONArray("usernames");
            ArrayList<String> usernamedata = new ArrayList<String>();
            if (jArray1 != null) {
                for (int k = 0; k < jArray1.length(); k++) {
                    usernamedata.add(jArray1.getString(k));
                }
            }
            final String[] usernameString = usernamedata.toArray(new String[0]);


            JSONArray jArray2 = jObject.getJSONArray("genders");
            ArrayList<Integer> genderdata = new ArrayList<Integer>();
            if (jArray2 != null) {
                for (int k = 0; k < jArray2.length(); k++) {
                    genderdata.add(jArray2.getInt(k));
                }
            }
            int[] gridViewImageId = new int[genderdata.size()];

            for (int k = 0; k < genderdata.size(); k++) {
                int val = genderdata.get(k);
                if (val == 0) {
                    gridViewImageId[k] = R.drawable.male;
                } else {
                    gridViewImageId[k] = R.drawable.female;
                }
            }


            CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(HomeActivity.this, gridViewString, gridViewImageId);
            androidGridView = (GridView) findViewById(R.id.grid_view_image_text);
            androidGridView.setAdapter(adapterViewAndroid);
            androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    Intent intent = new Intent(getBaseContext(), LocationViewActivity.class);
                    intent.putExtra("name", gridViewString[+i]);
                    intent.putExtra("friend_username", usernameString[+i]);
                    // change username dynamically
//                    intent.putExtra("username", "sridhama");
                    startActivity(intent);
                }
            });
        }catch (JSONException e) {
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
//        // END VOLLEY
    }


//    public String view_friends(){
//         // START VOLLEY
//        String url = "http://"+Constants.DOMAIN+"/LocationMate/view_friends.php?username="+Constants.USERNAME;
//        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "Network Error.", Toast.LENGTH_SHORT).show();
//            }
//        });
//        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
////        // END VOLLEY
//
//    }


}
