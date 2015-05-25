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
import ua.cn.stu.messagetransfer.work.Md5;
import ua.cn.stu.messagetransfer.work.Sender;

public class LoginActivity extends Activity {

    private static final String LOGIN_ACTIVITY_ANSWER_ID = "login_answer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        additionalInit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void additionalInit(){
        Button btnEnter = (Button) findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp(){
        Sender sender;
        try {
            sender = new Sender(getIpAddress(),getPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Toast.makeText(
                    getApplicationContext(),
                    "Can't connect!\nUnknown host name!",
                    Toast.LENGTH_LONG
            ).show();
            close(true);
            return;
        }
        try {
            String login = getLogin();
            String password = getPassword();
            close(!sender.isLoginCorrect(login,password));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(
                    getApplicationContext(),
                    "Can't sign up(IOException)!\nCheck your connection!",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private String getIpAddress(){
        return MainUI.getInstance().getIpAddress();
    }

    private int getPort(){
        return MainUI.getInstance().getPort();
    }

    private void close(boolean loginFail){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(LOGIN_ACTIVITY_ANSWER_ID, loginFail);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private String getLogin() {
        return ((EditText) findViewById(R.id.txtLogin)).getText().toString();
    }

    private String getPassword() {
        String password = ((EditText) findViewById(R.id.txtPassWord)).getText().toString();
        password = Md5.generateMd5(password);
        return password;
    }

}
