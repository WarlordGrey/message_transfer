package ua.cn.stu.messagetransfer.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.UnknownHostException;

import ua.cn.stu.messagetransfer.R;
import ua.cn.stu.messagetransfer.work.Sender;


public class MainUI extends Activity {

    private static final int LOGIN_ACTIVITY_CALL = 1;
    private static final String LOGIN_ACTIVITY_ANSWER_ID = "login_answer";
    private static MainUI me = null;
    private String ipAddr = null;
    private boolean loginConfirmed = false;
    //private int port = -1;

    public static MainUI getInstance() {
        return me;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        additionalInit();
        //goLogin();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPrefs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_ui, menu);
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
            goSettings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (LOGIN_ACTIVITY_CALL): {
                if (resultCode == Activity.RESULT_OK) {
                    boolean loginFail = data.getBooleanExtra(
                            LOGIN_ACTIVITY_ANSWER_ID, true
                    );
                    if (!loginFail){
                        loginConfirmed = true;
                    }
                }
                break;
            }
        }
    }

    private void sendMessage() {
        if (!loginConfirmed){
            Toast.makeText(
                    getApplicationContext(),
                    "Sign up at first!!!",
                    Toast.LENGTH_LONG
            ).show();
            return;
        }
        Sender sender = null;
        try {
            sender = new Sender(getIpAddress(), getPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Toast.makeText(
                    getApplicationContext(),
                    "Can't connect!\nUnknown host name!",
                    Toast.LENGTH_LONG
            ).show();
            return;
        }
        String msg = getMessageText();
        try {
            sender.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(
                    getApplicationContext(),
                    "Can't send message(IOException)!\nCheck your connection!",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void goSettings() {
        Intent settingsActivity = new Intent(
                getBaseContext(),
                SettingsActivity.class
        );
        startActivity(settingsActivity);
    }

    private void goLogin() {
        Intent loginActivity = new Intent(
                getBaseContext(),
                LoginActivity.class
        );
        startActivityForResult(loginActivity,LOGIN_ACTIVITY_CALL);
    }

    private void initButtons() {
        Button btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        Button btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin();
            }
        });
    }

    private void additionalInit() {
        me = this;
        StrictMode.setThreadPolicy(
                new StrictMode.ThreadPolicy.Builder()
                        .detectDiskReads().detectDiskWrites()
                        .detectNetwork().penaltyLog().build()
        );
        initButtons();
    }

    private void getPrefs() {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());
        ipAddr = prefs.getString(
                this.getString(R.string.edit_ip_address),
                this.getString(R.string.default_ip)
        );
        Toast.makeText(
                getApplicationContext(),
                "Current ip :" + ipAddr,
                Toast.LENGTH_LONG
        ).show();
    }

    public String getIpAddress() {
        return ipAddr;
    }

    public int getPort() {
        return 33333;
    }

    public String getMessageText() {
        return ((EditText) findViewById(R.id.editMessage)).getText().toString();
    }

}
