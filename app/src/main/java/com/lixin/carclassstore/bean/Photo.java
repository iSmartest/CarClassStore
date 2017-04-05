package com.lixin.carclassstore.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Photo implements Parcelable, Comparable<Photo> {
    /**相册id*/
    public String pid;
    /**
     * 照片（原图）路径
     */
    public String path;
    /**
     * 该照片是否被选择
     */
    public boolean isChoice;
    /**
     * 该照片匹配的学生数量
     */
    public int preStuNum;
    /**
     * 相册中照片的数量
     */
    public int num;
    /**
     * 相册的名称
     */
    public String name;
    /**
     * 相册的ID
     */
    public String id;
    /**
     * 中图路径
     */
    public String path2;
    /**
     * 小图路径
     */
    public String path3;
    /**
     * 点赞数量
     */
    public int praiseNum;
    /**
     * 学生列表
     */
    public String stus;
    /**
     * 评论数量
     */
    public int discussNum;
    /**
     * 评论列表
     */
    public List<Discuss> discussLs;
    /**
     * 相册描述
     */
    public String info;
    /**
     * 未读提醒信息个数
     */
    public int unReadLenth;


    public String time;
    /**
     * 是否显示原图
     */
    public boolean isShowOriginal;
    /**已赞用户名称*/
    public String PraiseUserInfos;
    public boolean isZan;
    public boolean isDisucss;
    /**该照片是否上传过*/
    public boolean isUpload;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pid);
        dest.writeString(path);
        dest.writeInt(isChoice ? 1 : 0);
        dest.writeInt(preStuNum);
        dest.writeInt(num);
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(path2);
        dest.writeString(path3);
        dest.writeInt(praiseNum);
        dest.writeString(stus);
        dest.writeInt(discussNum);
        dest.writeList(discussLs);
        dest.writeInt(unReadLenth);
        dest.writeString(time);
        dest.writeInt(isShowOriginal ? 1 : 0);
        dest.writeString(PraiseUserInfos);
        dest.writeInt(isZan ? 1 : 0);
        dest.writeInt(isDisucss ? 1 : 0);
        dest.writeInt(isUpload ? 1 : 0);
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @SuppressWarnings("unchecked")
        public Photo createFromParcel(Parcel in) {
            Photo photo = new Photo();
            photo.pid=in.readString();
            photo.path = in.readString();
            photo.isChoice = 1 == in.readInt();
            photo.preStuNum = in.readInt();
            photo.num = in.readInt();
            photo.name = in.readString();
            photo.id = in.readString();
            photo.path2 = in.readString();
            photo.path3 = in.readString();
            photo.praiseNum = in.readInt();
            photo.stus = in.readString();
            photo.discussNum = in.readInt();
            photo.discussLs = in.readArrayList(Discuss.class.getClassLoader());
            photo.unReadLenth = in.readInt();
            photo.time = in.readString();
            photo.isShowOriginal = (in.readInt() == 1);
            photo.PraiseUserInfos=in.readString();
            photo.isZan = in.readInt() == 1;
            photo.isDisucss = in.readInt() == 1;
            photo.isUpload = in.readInt() == 1;
            return photo;
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (((Object) this).getClass() != obj.getClass()) {
            return false;
        }
        Photo other = (Photo) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else {
            if (!id.equals(other.id)) {
                return false;
            }
        }
        if (path == null) {
            if (other.path != null) {
                return false;
            }
        } else {
            if (!path.equals(other.path)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int compareTo(Photo another) {
        if (another == null) {
            return 1;
        }

        return hashCode() - another.hashCode();
    }
}
