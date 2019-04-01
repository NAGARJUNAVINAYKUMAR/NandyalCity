package com.gathratechnal.nandyalcity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gathratechnal.nandyalcity.login.LoginActivity;

public class LogOutActivity extends AppCompatActivity {
    TextView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);
        logout=(TextView)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
                SharedPreferences.Editor editor=mPreferences.edit();
                editor.remove("UserName");
                editor.remove("PassWord");
                editor.commit();
                Message myMessage=new Message();
                myMessage.obj="NOTSUCCESS";
                handler.sendMessage(myMessage);
                finish();
            }
        });
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String loginmsg=(String)msg.obj;
            if(loginmsg.equals("NOTSUCCESS")) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("LoginMessage", "Logged Out");
                startActivity(intent);
                removeDialog(0);
            }
        }
    };
}
