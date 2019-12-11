package com.lx.lxyd.bean;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Description:
 * Data：2019/12/8-16:36
 * Author: fushuaige
 */
@SuppressLint("ParcelCreator")
public class colBean extends DataSupport  implements Parcelable {

    private String icon_url;
    private String file_url;
    private String create_time;
    private String file_name;
    private String remark;
    private String title;
    private String file_auffix;
    private String thumbnail_Url;
    private String uuid;
    private String res_code;
    private int res_type;
    private int is_hot;
    private int is_charge;
    private String creater;
    private String audio_url;
    private String tag;
    private int status;
    private String topId;
    private String createTime;

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFile_auffix() {
        return file_auffix;
    }

    public void setFile_auffix(String file_auffix) {
        this.file_auffix = file_auffix;
    }

    public String getThumbnail_Url() {
        return thumbnail_Url;
    }

    public void setThumbnail_Url(String thumbnail_Url) {
        this.thumbnail_Url = thumbnail_Url;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRes_code() {
        return res_code;
    }

    public void setRes_code(String res_code) {
        this.res_code = res_code;
    }

    public int getRes_type() {
        return res_type;
    }

    public void setRes_type(int res_type) {
        this.res_type = res_type;
    }

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    public int getIs_charge() {
        return is_charge;
    }

    public void setIs_charge(int is_charge) {
        this.is_charge = is_charge;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getAudio_url() {
        return audio_url;
    }

    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTopId() {
        return topId;
    }

    public void setTopId(String topId) {
        this.topId = topId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.icon_url);
        dest.writeString(this.file_url);
        dest.writeString(this.create_time);
        dest.writeString(this.file_name);
        dest.writeString(this.remark);
        dest.writeString(this.title);
        dest.writeString(this.file_auffix);
        dest.writeString(this.thumbnail_Url);
        dest.writeString(this.uuid);
        dest.writeString(this.res_code);
        dest.writeInt(this.res_type);
        dest.writeInt(this.is_hot);
        dest.writeInt(this.is_charge);
        dest.writeString(this.creater);
        dest.writeString(this.audio_url);
        dest.writeString(this.tag);
        dest.writeInt(this.status);
        dest.writeString(this.topId);
        dest.writeString(this.createTime);
    }

    public colBean() {
    }

    protected colBean(Parcel in) {
        this.icon_url = in.readString();
        this.file_url = in.readString();
        this.create_time = in.readString();
        this.file_name = in.readString();
        this.remark = in.readString();
        this.title = in.readString();
        this.file_auffix = in.readString();
        this.thumbnail_Url = in.readString();
        this.uuid = in.readString();
        this.res_code = in.readString();
        this.res_type = in.readInt();
        this.is_hot = in.readInt();
        this.is_charge = in.readInt();
        this.creater = in.readString();
        this.audio_url = in.readString();
        this.tag = in.readString();
        this.status = in.readInt();
        this.topId = in.readString();
        this.createTime = in.readString();
    }

    public static final Creator<colBean> CREATOR = new Creator<colBean>() {
        @Override
        public colBean createFromParcel(Parcel source) {
            return new colBean(source);
        }

        @Override
        public colBean[] newArray(int size) {
            return new colBean[size];
        }
    };
}
