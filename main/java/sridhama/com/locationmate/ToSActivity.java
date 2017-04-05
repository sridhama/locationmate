  package sridhama.com.locationmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ToSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
