package net.shenru.wifi;


import android.text.TextUtils;
import android.util.Log;

import net.shenru.http.NanoHTTPD;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WebServer extends NanoHTTPD {

    private static final String TAG = WebServer.class.getSimpleName();
    private String cacheStr;

    public WebServer(int port) {
        super(port);
    }

    public WebServer(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        //ftp://a:a@dygod18.com:21/[电影天堂www.dy2018.com]釜山行HD高清韩语中字.mp4
        String url = session.getUri();
        Log.i(TAG,"WebServer request url:" + url);
        if (url.contains("freeWifi_34879278673.apk")) {
            try {
                MainActivity.addDownCount();
                String appPath = AppCongfig.getAppPath();
                FileInputStream fis = new FileInputStream(appPath);
                Response response = new Response(Response.Status.OK, "application/octet-stream", fis);
                return response;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if(cacheStr == null){
            try {
                String htmlPath = AppCongfig.getHtmlPath();
                byte[] bytes = FileUtils.readFileToByteArray(new File(htmlPath));
                cacheStr = new String(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(TextUtils.isEmpty(cacheStr)){
            return new Response(Response.Status.BAD_REQUEST, NanoHTTPD.MIME_HTML, "");
        }

        return new Response(Response.Status.OK, NanoHTTPD.MIME_HTML, cacheStr);
    }


}
