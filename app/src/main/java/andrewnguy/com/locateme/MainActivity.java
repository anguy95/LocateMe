package andrewnguy.com.locateme;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


import io.fabric.sdk.android.Fabric;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "1uiglW6XwdfzIXaWOIOPR8NAR";
    private static final String TWITTER_SECRET = "WHm0iKU72vBK7yfQmq8OBR8NSNYtRCeljLcMeqWlHoPWY02aa2";



    LocationManager locationManager;
    Location location;
    private ShareDialog share;
    private ShareLinkContent content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Twitter Fabric SDK Setup
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        //Facebook SDK Set
        FacebookSdk.sdkInitialize(getApplicationContext());


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton textShare = (ImageButton) findViewById(R.id.text_button);
        textShare.setOnClickListener(this);

        ImageButton fbShare = (ImageButton) findViewById(R.id.facebook_button);
        fbShare.setOnClickListener(this);

        ImageButton twShare = (ImageButton) findViewById(R.id.twitter_button);
        twShare.setOnClickListener(this);
    }


    public String getLatLng() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String longitude = String.valueOf(location.getLongitude());
        String latitude = String.valueOf(location.getLatitude());
        String loc = latitude + "," + longitude;
        return loc;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            //Opens the settings preferences
            Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
            MainActivity.this.startActivity(settings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.text_button) {
            String loc = getLatLng();
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("sms:"));
            sendIntent.putExtra("sms_body", "http://maps.google.com/?q=" + loc);
            startActivity(sendIntent);
        } else if (R.id.facebook_button == id) {
            String loc = getLatLng();
            ShareLinkContent locLink = new ShareLinkContent.Builder().setContentTitle("My Location").setContentUrl(Uri.parse("http://maps.google.com/?q=" + loc)).build();
            ShareDialog fbShareDialog = new ShareDialog(this);
            fbShareDialog.show(locLink);
        }else if(id == R.id.twitter_button){
            TweetComposer.Builder builder = new TweetComposer.Builder(this)
                    .text("just setting up my Fabric.");
            builder.show();
        }
    }
}
