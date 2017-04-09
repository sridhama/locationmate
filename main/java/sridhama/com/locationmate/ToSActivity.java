  package sridhama.com.locationmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

  public class ToSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String s = "&#8226; LocationMate DOES NOT provide the exact location of your friend. It only provides the approximate location based on the Access Point they are connected to, within the campus.<br>&#8226; LocationMate MUST NOT be used for any unauthorized purpose.<br>&#8226; The app's data is hosted on the Intranet and passwords are encrypted using 128-Bit encryption.<br>&#8226; Your account may be terminated due to unauthorised use of the app.<br><br>DISCLAIMER: THE APP DEVELOPERS ARE NOT LIABLE TO ANY DAMAGE, INJURY OR HARM CAUSED DUE TO MISUSE OF THE MOBILE APP (LocationMate).<br><br>As developers, we hope that LocationMate is used for locating your friends quicker and other constructive purposes only.<br><br>By registering, you hereby accept the Terms of Service of LocationMate.";
        ((TextView)findViewById(R.id.tos)).setText(Html.fromHtml(s));
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
