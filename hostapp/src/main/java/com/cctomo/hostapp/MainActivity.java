package com.cctomo.hostapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.qihoo360.replugin.RePlugin;

public class MainActivity extends AppCompatActivity {

    private Button goPluginOne;
    private Button goPluginOneAlias;

    private Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initEvent() {
        goPluginOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pluginName = "com.cctomo.pluginapp";
                String actName = "com.cctomo.pluginapp.MainActivity";
                RePlugin.startActivity(getActivity(), RePlugin.createIntent(pluginName, actName));
            }
        });
        goPluginOneAlias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pluginName = "pluginapp_one";
                String actName = "com.cctomo.pluginapp.MainActivity";
                RePlugin.startActivity(getActivity(), RePlugin.createIntent(pluginName, actName));
            }
        });
    }

    private void initView() {
        goPluginOne = (Button) findViewById(R.id.go_plugin_one);
        goPluginOneAlias = (Button) findViewById(R.id.go_plugin_one_alias);
    }
}
