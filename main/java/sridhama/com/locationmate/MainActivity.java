package sridhama.com.locationmate;

import android.app.IntentService;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // SERVICE START
        Intent i= new Intent(this, LMService.class);
        startService(i);
        // SERVICE END


//        String result = "{\"names\":[\"Swaran M\",\"Yashwanth Soodini\",\"John Doe\"],\"usernames\":[\"arsenal\",\"yashwanth\",\"john\"],\"genders\":[\"0\",\"1\",\"0\"]}";
//        try {
//            JSONObject jObject = new JSONObject(result);
//
//            JSONArray jArray = jObject.getJSONArray("names");
//            ArrayList<String> listdata = new ArrayList<String>();
//            if (jArray != null) {
//                for (int k=0;k<jArray.length();k++){
//                    listdata.add(jArray.getString(k));
//                }
//            }
//            String[] gridViewString = listdata.toArray(new String[0]);
//            JSONArray jArray1 = jObject.getJSONArray("usernames");
//            ArrayList<String> usernamedata = new ArrayList<String>();
//            if (jArray1 != null) {
//                for (int k=0;k<jArray1.length();k++){
//                    usernamedata.add(jArray1.getString(k));
//                }
//            }
//            String[] usernameString = usernamedata.toArray(new String[0]);
//
//
//            JSONArray jArray2 = jObject.getJSONArray("genders");
//            ArrayList<Integer> genderdata = new ArrayList<Integer>();
//            if (jArray2 != null) {
//                for (int k=0;k<jArray2.length();k++){
//                    genderdata.add(jArray2.getInt(k));
//                }
//            }
////            int[] genderarray = genderdata.toArray(new int[0]);
//            int[] gridViewImageId = new int[genderdata.size()];
//
//            for(int k = 0; k < genderdata.size();k++){
//                int val = genderdata.get(k);
//                if(val == 0){
//                    gridViewImageId[k] = R.drawable.male;
//                }else{
//                    gridViewImageId[k] = R.drawable.female;
//                }
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }





    }

    public void tempIntent(View v) {
        Intent i = new Intent(this, BSSIDActivity.class);
        startActivity(i);
    }

    public void tempIntent1(View v) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);

    }

    public void addFriendIntent(View v) {
        Intent i = new Intent(this, AddFriendActivity.class);
        startActivity(i);

    }

    public void homeIntent(View v) {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);

    }

}


