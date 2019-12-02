package com.cj.loadapk;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import java.io.File;

import io.reactivex.disposables.Disposable;

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
public class UpdateDialog extends DialogFragment {

    private ProgressBar progressBar;
    private TextView mTvInfo;
    private TextView mTvOk;
    private UpdateBean bean;

    public static UpdateDialog newInstance(UpdateBean bean) {
        Bundle args = new Bundle();
        args.putParcelable("bean", bean);
        UpdateDialog fragment = new UpdateDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bean = getArguments().getParcelable("bean");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_update_dialog, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        disposable.dispose();
    }

    Disposable disposable;
    
    private void initView(View view) {
        progressBar = view.findViewById(R.id.pb_progress);
        mTvInfo = view.findViewById(R.id.tv_info);
        mTvOk = view.findViewById(R.id.tv_ok);
        if (bean == null) return;
        mTvInfo.setText(bean.data.updateMessage);
        mTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvOk.setEnabled(false);
                AppUpdateUtil.getInstance().getiNetManager()
                        .download(bean.data.downloadAddress, new File(getActivity().getCacheDir().getPath() + "/demo.apk"),
                                new INetDownLoadCallBack() {
                                    @Override
                                    public void success(final File apkFile) {
                                        if (getActivity() == null) return;
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mTvOk.setEnabled(true);
                                                mTvOk.setText("安装");
                                                //去安装
                                                installApk(getActivity(), apkFile);
                                                mTvOk.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        installApk(getActivity(), apkFile);
                                                    }
                                                });
                                            }
                                        });

                                    }

                                    @Override
                                    public void progress(final int pregress) {
                                        if (getActivity() == null) return;
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressBar.setProgress(pregress);
                                                mTvOk.setText(pregress + "%");
                                            }
                                        });
                                    }

                                    @Override
                                    public void failed(Throwable throwable) {
                                        if (getActivity() == null) return;
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mTvOk.setEnabled(true);
                                                mTvOk.setText("下载");
                                            }
                                        });
                                    }

                                    @Override
                                    public void cancel(Disposable d) {
                                        disposable = d;
                                    }
                                });
            }
        });

    }

    private void installApk(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, "com.cj.loadapk.fileProvider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);

    }
}
