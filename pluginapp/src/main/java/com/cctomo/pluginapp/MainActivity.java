package com.cctomo.pluginapp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cctomo.user.UserModel;
import com.qihoo360.replugin.RePlugin;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private TextView timeInfo;
    private TextView userInfo;

    @Override
    public void setTitle(CharSequence title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("PluginOne");
        initView();
        initTime();
        initUser();
    }

    private void initView() {
        timeInfo = (TextView) findViewById(R.id.time_info);
        userInfo = (TextView) findViewById(R.id.user_info);
    }

    private void initTime() {
        String t = "现在时间是:";
        ClassLoader c = RePlugin.getHostClassLoader();
        if (c != null) {
            try {
                Class cz = c.loadClass("com.cctomo.hostapp.util.DateUtil");
                Method m = cz.getDeclaredMethod("getNow");
                String o = (String) m.invoke(null);
                t += o;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        timeInfo.setText(t);
    }

    private void initUser() {
        String u = "用户信息:\n";
        UserModel userModel = new UserModel("嘻嘻", 18);
        u += userModel.toString();
        userInfo.setText(u);
    }

}
