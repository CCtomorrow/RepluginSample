package com.cctomo.hostapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.RePluginCallbacks;
import com.qihoo360.replugin.RePluginConfig;
import com.qihoo360.replugin.RePluginEventCallbacks;

/**
 * <b>Project:</b> RepluginSample <br>
 * <b>Create Date:</b> 2018/8/5 <br>
 * <b>@author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> application <br>
 */
public class HostApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        RePluginConfig c = createConfig();
        if (c == null) {
            c = new RePluginConfig();
        }
        RePluginCallbacks cb = createCallbacks();
        if (cb != null) {
            c.setCallbacks(cb);
        }
        RePlugin.App.attachBaseContext(this, c);
        RePlugin.enableDebugger(base, true);
    }

    /**
     * RePlugin允许提供各种“自定义”的行为，让您“无需修改源代码”，即可实现相应的功能
     * 子类可以复写此方法来自定义RePluginConfig。请参见 RePluginConfig 的说明
     *
     * @return 新的RePluginConfig对象
     * @see RePluginConfig
     */
    protected RePluginConfig createConfig() {
        RePluginConfig c = new RePluginConfig();
        //允许“插件使用宿主类”。默认为“关闭”
        c.setUseHostClassIfNotFound(true);
        //FIXME RePlugin默认会对安装的外置插件进行签名校验，这里先关掉，避免调试时出现签名错误
        c.setVerifySign(false);
        //针对“安装失败”等情况来做进一步的事件处理
        c.setEventCallbacks(new HostEventCallbacks(this));
        //FIXME 若宿主为Release，则此处应加上您认为"合法"的插件的签名，例如，可以写上"宿主"自己的。
        //RePlugin.addCertSignature("AAAAAAAAA");
        //在Art上，优化第一次loadDex的速度
        //c.setOptimizeArtLoadDex(true);
        return c;
    }

    private class HostEventCallbacks extends RePluginEventCallbacks {

        private static final String TAG = "HostEventCallbacks";

        public HostEventCallbacks(Context context) {
            super(context);
        }

        @Override
        public void onInstallPluginFailed(String path, InstallResult code) {
            // FIXME 当插件安装失败时触发此逻辑。您可以在此处做“打点统计”，也可以针对安装失败情况做“特殊处理”
            // 大部分可以通过RePlugin.install的返回值来判断是否成功
        }

        @Override
        public void onStartActivityCompleted(String plugin, String activity, boolean result) {
            // FIXME 当打开Activity成功时触发此逻辑，可在这里做一些APM、打点统计等相关工作
        }
    }

    /**
     * 子类可以复写此方法来自定义RePluginCallbacks。请参见 RePluginCallbacks 的说明 <p>
     * 注意：若在createConfig的RePluginConfig内同时也注册了Callbacks，则以这里创建出来的为准
     *
     * @return 新的RePluginCallbacks对象，可以为空
     * @see RePluginCallbacks
     */
    protected RePluginCallbacks createCallbacks() {
        return new HostCallbacks(this);
    }

    /**
     * 宿主针对RePlugin的自定义行为
     */
    private class HostCallbacks extends RePluginCallbacks {

        private static final String TAG = "HostCallbacks";

        private HostCallbacks(Context context) {
            super(context);
        }

        @Override
        public boolean onPluginNotExistsForActivity(Context context, String plugin, Intent intent, int process) {
            // FIXME 当插件"没有安装"时触发此逻辑，可打开您的"下载对话框"并开始下载。
            // FIXME 其中"intent"需传递到"对话框"内，这样可在下载完成后，打开这个插件的Activity
            return super.onPluginNotExistsForActivity(context, plugin, intent, process);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RePlugin.App.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        // 如果App的minSdkVersion >= 14，该方法可以不调用
        RePlugin.App.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        // 如果App的minSdkVersion >= 14，该方法可以不调用
        RePlugin.App.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 如果App的minSdkVersion >= 14，该方法可以不调用
        RePlugin.App.onConfigurationChanged(newConfig);
    }
}
