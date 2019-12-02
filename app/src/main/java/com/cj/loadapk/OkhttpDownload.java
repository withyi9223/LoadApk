package com.cj.loadapk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
public class OkhttpDownload implements INetManager {

    private Retrofit retrofit;
    private HttpService service;


    public OkhttpDownload() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://mobiles.kooche.cn")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        if (service == null) {
            service = retrofit.create(HttpService.class);
        }

    }

    @Override
    public void get(final INetCallback callback) {
        service.getAppVersion().subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<UpdateBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callback.failed(e);
                    }

                    @Override
                    public void onNext(UpdateBean bean) {
                        callback.success(bean);
                    }
                });

    }

    @Override
    public void download(String url, final File apkFile, final INetDownLoadCallBack callBack) {
        if (!apkFile.exists()) {
            apkFile.getParentFile().mkdirs();
        }
        service.download(url).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    
                    @Override
                    public void onCompleted() {
                        callBack.success(apkFile);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callBack.failed(e);
                    }

                    @Override
                    public void onNext(final ResponseBody responseBody) {
                        InputStream inputStream = null;
                        OutputStream outputStream = null;
                        try {
                            long total = responseBody.contentLength();
                            inputStream = responseBody.byteStream();
                            outputStream = new FileOutputStream(apkFile);
                            byte[] bytes = new byte[8 * 1024];
                            long curLen = 0;
                            int bufferLen = 0;
                            while ((bufferLen = inputStream.read(bytes)) != -1) {
                                curLen += bufferLen;
                                outputStream.write(bytes, 0, bufferLen);
                                outputStream.flush();
                                callBack.progress((int) (curLen * 1.0f / total * 100));
                            }
                        } catch (Throwable e) {
                            e.printStackTrace();
                            callBack.failed(e);
                        } finally {
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                });

    }
}
