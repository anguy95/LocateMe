package andrewnguy.com.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;


/**
 * Created by anguy95 on 11/28/15.
 *Settings page for the onOptionItemSelected
 *
 *
 */
public class SettingsActivity extends AppCompatActivity implements FacebookCallback{
    LoginButton loginBtn;
    CallbackManager callbackManager;
    TwitterLoginButton twitterLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        //Initializes FacebookSDK
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.settings_activity);


        //Sets up toolbar with back button
        Toolbar toolbar = (Toolbar) findViewById(R.id.setting_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Opens a PreferenceScreen in frame layout in XML
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();

        loginBtn = (LoginButton) findViewById(R.id.login_button);
        loginBtn.setReadPermissions("user_friends");
        callbackManager = CallbackManager.Factory.create();
        loginBtn.registerCallback(callbackManager, this);


        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Toast.makeText(SettingsActivity.this, result.data.getUserName(), Toast.LENGTH_LONG ).show();
            }

            @Override
            public void failure(TwitterException e) {
                Toast.makeText(SettingsActivity.this, "Incorrect Login, Please try again.", Toast.LENGTH_LONG).show();
            }
        });



    }

    @Override
    public void onSuccess(Object o) {
        LoginResult result = (LoginResult) o;
        String userId = result.getAccessToken().getUserId();
        Toast.makeText(SettingsActivity.this, userId, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onCancel() {
        Toast.makeText(SettingsActivity.this, "If you want to share location on Facebook, please log in.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onError(FacebookException error) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode, data);
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);


    }



    //Settings fragment for the SettingsPage
    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
