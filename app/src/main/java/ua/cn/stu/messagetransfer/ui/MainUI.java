package ua.cn.stu.messagetransfer.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

    private static MainUI me = null;

    private String ipAddr = null;
    //private int port = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        additionalInit();
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

    private void sendMessage(){
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
                    "Can't send message(IOException)!\nCheck what\'s up with host!",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void goSettings(){
        Intent settingsActivity = new Intent(
                getBaseContext(),
                SettingsActivity.class
        );
        startActivity(settingsActivity);
    }

    private void initButtons(){
        Button btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void additionalInit(){
        me = this;
        initButtons();
    }

    public static MainUI getInstance(){
        return me;
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

    public String getIpAddress(){
        return ipAddr;
    }

    public int getPort(){
        return 33333;
    }

    public String getMessageText(){
        return ((EditText) findViewById(R.id.editMessage)).getText().toString();
    }

}
