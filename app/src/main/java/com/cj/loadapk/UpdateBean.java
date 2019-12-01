package com.cj.loadapk;

import android.os.Parcel;
import android.os.Parcelable;

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
public class UpdateBean implements Parcelable {

    public int code;
    public String msg;
    public DataBean data;

    public static class DataBean implements Parcelable {

        
        public int id;
        public String currentVersion;
        public String currentVersionNo;
        public int currentUpdateType;
        public int currentMessage;
        public String newReleaseVersionName;
        public String newReleaseVersionNo;
        public int updateType;
        public String updateMessage;
        public int type;
        public String downloadAddress;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.currentVersion);
            dest.writeString(this.currentVersionNo);
            dest.writeInt(this.currentUpdateType);
            dest.writeInt(this.currentMessage);
            dest.writeString(this.newReleaseVersionName);
            dest.writeString(this.newReleaseVersionNo);
            dest.writeInt(this.updateType);
            dest.writeString(this.updateMessage);
            dest.writeInt(this.type);
            dest.writeString(this.downloadAddress);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.currentVersion = in.readString();
            this.currentVersionNo = in.readString();
            this.currentUpdateType = in.readInt();
            this.currentMessage = in.readInt();
            this.newReleaseVersionName = in.readString();
            this.newReleaseVersionNo = in.readString();
            this.updateType = in.readInt();
            this.updateMessage = in.readString();
            this.type = in.readInt();
            this.downloadAddress = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.msg);
        dest.writeParcelable(this.data, flags);
    }

    public UpdateBean() {
    }

    protected UpdateBean(Parcel in) {
        this.code = in.readInt();
        this.msg = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<UpdateBean> CREATOR = new Parcelable.Creator<UpdateBean>() {
        @Override
        public UpdateBean createFromParcel(Parcel source) {
            return new UpdateBean(source);
        }

        @Override
        public UpdateBean[] newArray(int size) {
            return new UpdateBean[size];
        }
    };
}
