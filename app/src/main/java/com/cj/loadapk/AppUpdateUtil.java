package com.cj.loadapk;

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * Created by zengyi on 2019/12/1.
 */
public class AppUpdateUtil {


    private static AppUpdateUtil mAppUpdateUtil = null;

    private INetManager iNetManager = new OkhttpDownload();

    public void setiNetManager(INetManager iNetManager) {
        this.iNetManager = iNetManager;
    }

    public INetManager getiNetManager() {
        return iNetManager;
    }

    private AppUpdateUtil() {
    }

    public static AppUpdateUtil getInstance() {
        synchronized (AppUpdateUtil.class) {
            if (mAppUpdateUtil == null) {
                mAppUpdateUtil = new AppUpdateUtil();
            }
        }
        return mAppUpdateUtil;
    }
    
    

}
