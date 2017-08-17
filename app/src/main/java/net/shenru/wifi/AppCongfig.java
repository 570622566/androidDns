package net.shenru.wifi;

/**
 * Created by xtdhwl on 3/19/17.
 */

public class AppCongfig {

    private static String appPath;
    private static String htmlPath;
    public static String getAppPath() {
        return appPath;
    }

    public static void _setAppPath(String path){
        appPath = path;
    }

    public static String getHtmlPath() {
        return htmlPath;
    }

    public static void _setHtmlPath(String path){
        htmlPath = path;
    }
}
