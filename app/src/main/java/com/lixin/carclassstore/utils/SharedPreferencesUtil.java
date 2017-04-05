package com.lixin.carclassstore.utils;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * 类说明 SharedPreferences封装类
 * 0、用户名（手机号码） userTel
 * 1、用户id 字段 uId String类型
 * 2、 是否登陆 字段 isLogin boolean类型
 *8、用户昵称 nickName String 类型
 * 9、用户头像url userIcon String 类型
 * 10、用户会员类型（普通、VIP）memberOrder String类型，0 普通会员、1 VIP
 * 11、用户积分 userScore String类型
 *
 *
 *
 * 3、定位城市 cityName String类型
 * 4、城市码 cityCode String类型
 * 5、详细地址 Address String类型
 * 6、维度 lat String类型
 * 7、经度 lon String类型
 * 8、是否首次安装程序（启动欢迎页）字段 isFirst boolean类型
 *
 *
 * */

public class SharedPreferencesUtil {

	private static String NAME="ZhongShiTong";

	/**
	 * @param mContext
	 *            上下文，来区别哪一个activity调用的
	 *
	 *            使用的SharedPreferences的名字
	 * @param field
	 *            SharedPreferences的哪一个字段
	 * @return
	 */

	// 取出whichSp中field字段对应的string类型的值
	public static String getSharePreStr(Context mContext,
										String field) {
		SharedPreferences sp =  mContext
				.getSharedPreferences(NAME, 0);
		String s = sp.getString(field, "");// 如果该字段没对应值，则取出字符串“”
		return s;
	}

	// 取出whichSp中field字段对应的int类型的值
	public static int getSharePreInt(Context mContext,
			String field) {
		SharedPreferences sp =  mContext
				.getSharedPreferences(NAME, 0);
		int i = sp.getInt(field, 0);// 如果该字段没对应值，则取出0
		return i;
	}

	// 取出whichSp中field字段对应的boolean类型的值
	public static boolean getSharePreBoolean(Context mContext,
			String field) {
		SharedPreferences sp =  mContext
				.getSharedPreferences(NAME, 0);
		boolean b = sp.getBoolean(field, true);// 如果该字段没对应值，则取出false
		return b;
	}

	// 保存string类型的value到whichSp中的field字段
	public static void putSharePre(Context mContext,
								   String field, String value) {
		SharedPreferences sp =  mContext
				.getSharedPreferences(NAME, 0);
		sp.edit().putString(field, value).commit();
	}

	// 保存int类型的value到whichSp中的field字段
	public static void putSharePre(Context mContext,
								   String field, int value) {
		SharedPreferences sp =  mContext
				.getSharedPreferences(NAME, 0);
		sp.edit().putInt(field, value).commit();
	}

	// 保存booble类型的value到whichSp中的field字段
	public static void putSharePre(Context mContext,
								   String field, Boolean value) {
		SharedPreferences sp =  mContext
				.getSharedPreferences(NAME, 0);
		sp.edit().putBoolean(field, value).commit();
	}

}
