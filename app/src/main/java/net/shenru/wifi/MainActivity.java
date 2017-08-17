package net.shenru.wifi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.shenru.dns.DnsRunnable;
import net.shenru.dns.R;
import net.shenru.dns.WifiAp;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    private DnsRunnable runnable;
    private WebServer webServer;

    @BindView(R.id.apView)
    public EditText apView;
    @BindView(R.id.htmlView)
    public EditText htmlView;
    @BindView(R.id.logView)
    public TextView logView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        _logView = logView;
    }


    @OnClick(R.id.stopView)
    public void onStop(View view) {
        WifiAp.setWifiApEnabled(this, "", false);
        if (runnable != null) {
            runnable.stopReceive();
            runnable = null;
        }
        if (webServer != null) {
            webServer.stop();
            webServer = null;
        }
    }


    @OnClick(R.id.startView)
    public void onStart(View view) {
        String apName = apView.getText().toString().trim();
        String htmlPath = htmlView.getText().toString().trim();
        if (TextUtils.isEmpty(apName)) {
            Toast.makeText(this, "Wifi名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }


        if (TextUtils.isEmpty(htmlPath)) {
            Toast.makeText(this, "Html路径不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        AppCongfig._setHtmlPath(htmlPath);

        File file = new File(htmlPath);
        if (file.isDirectory() || !file.exists()) {
            Toast.makeText(this, "Html路径不是有效文件或为空", Toast.LENGTH_SHORT).show();
            return;
        }

        AppCongfig._setAppPath("/sdcard/freewifi/wifi.apk");

        boolean isAP = WifiAp.setWifiApEnabled(this, apName, true);
        if (!isAP) {
            Toast.makeText(this, "启动热点失败", Toast.LENGTH_SHORT).show();
            return;
        }


        if (runnable != null) {
            runnable.stopReceive();
        }
        runnable = new DnsRunnable();
        new Thread(runnable).start();
        shell("su -c iptables -t nat -I PREROUTING  -p udp --dport 53 -j DNAT --to-destination 192.168.43.1:5300");


        if (webServer != null) {
            webServer.stop();
        }
        try {
            //iptables -t nat -A PREROUTING -d 0.0.0.0/0 -p tcp --dport 80 -j DNAT --to 192.168.43.1:8080
            webServer = new WebServer(8080);
            webServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        shell("su -c iptables -t nat -A PREROUTING -d 0.0.0.0/0 -p tcp --dport 80 -j DNAT --to 192.168.43.1:8080");
        Toast.makeText(this, "启动成功!", Toast.LENGTH_SHORT).show();
    }

    private boolean shell(String shell) {
        try {
            Runtime.getRuntime().exec(shell);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static TextView _logView = null;
    private static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int count = Integer.valueOf(_logView.getText().toString().trim()) + 1;
            _logView.setText(count + "");
        }
    };

    public static void addDownCount(){
        Message message = handler.obtainMessage();
        message.what = 1;
        handler.sendMessage(message);
    }
}
