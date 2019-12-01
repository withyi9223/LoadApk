package com.cj.loadapk;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.File;

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


    /*@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }*/

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
                                    public void success(File apkFile) {
                                        Log.e("ddd",apkFile.getAbsolutePath());
                                        mTvOk.setEnabled(true);
                                    }

                                    @Override
                                    public void progress(int pregress) {
                                        Log.e("ddd", pregress + "");
                                        progressBar.setProgress(pregress);
                                        mTvOk.setText(pregress+"%");
                                    }

                                    @Override
                                    public void failed(Throwable throwable) {
                                        mTvOk.setEnabled(true);
                                    }
                                });
            }
        });

    }
}
