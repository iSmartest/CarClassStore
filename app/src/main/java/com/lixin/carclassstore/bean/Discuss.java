package com.lixin.carclassstore.bean;

import android.os.Parcel;
import android.os.Parcelable;

/***
 * 评论
 * 
 * @author Administrator
 * 
 */
public class Discuss implements Parcelable {
	/** 评论id */
	public String DiscussId;
	/** 评论来源 学生id */
	public String DiscussFromStudentId;
	/** 评论来源 用户id */
	public String DiscussFromUserId;
	/** 评论来源 用户Telephone */
	public String DiscussFromUserTel;
	/** 评论来源 用户身份 */
	public String DiscussFromUserIdentity;
	/** 回复id */
	public String ReplyId;
	/** 回复谁 学生id */
	public String DiscussToStudentId;
	/** 回复谁 用户id */
	public String DiscussToUserId;
	/** 回复谁 用户Telephone */
	public String DiscussToUserTel;
	/** 回复谁 用户身份 */
	public String DiscussToUserIdentity;
	/** 回复内容 */
	public String DiscussContent;
	/** 回复时间 */
	public String CreatDate;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(DiscussId);
		dest.writeString(DiscussFromStudentId);
		dest.writeString(DiscussFromUserId);
		dest.writeString(DiscussFromUserTel);
		dest.writeString(DiscussFromUserIdentity);
		dest.writeString(ReplyId);
		dest.writeString(DiscussToStudentId);
		dest.writeString(DiscussToUserId);
		dest.writeString(DiscussToUserTel);
		dest.writeString(DiscussToUserIdentity);
		dest.writeString(DiscussContent);
		dest.writeString(CreatDate);

	}

	public static final Creator<Discuss> CREATOR = new Creator<Discuss>() {

		@Override
		public Discuss[] newArray(int size) {
			return new Discuss[size];
		}

		@Override
		public Discuss createFromParcel(Parcel source) {
			Discuss d = new Discuss();
			d.DiscussId = source.readString();
			d.DiscussFromStudentId = source.readString();
			d.DiscussFromUserId = source.readString();
			d.DiscussFromUserTel = source.readString();
			d.DiscussFromUserIdentity = source.readString();
			d.ReplyId = source.readString();
			d.DiscussToStudentId = source.readString();
			d.DiscussToUserId = source.readString();
			d.DiscussToUserTel = source.readString();
			d.DiscussToUserIdentity = source.readString();
			d.DiscussContent = source.readString();
			d.CreatDate = source.readString();
			return d;
		}
	};
}
