package com.lixin.carclassstore.activity;


import com.lixin.carclassstore.bean.Photo;

import java.util.ArrayList;

/**
 * 图片选择Activity的基类。<br/>
 */
public class ImageBaseActivity extends BaseActivity
{
    protected static ArrayList<Photo> checkList = new ArrayList<Photo>();
    /** 表示通过Intent传递到下一个Activity的图片列表 */
    public static final String ARG_PHOTO_LIST = "com.chepinstore.chooseimages.PHOTO_LIST";
    /** 表示通过Intent传递到上一个Activity的图片列表 */
    public static final String RES_PHOTO_LIST = "com.chepinstore.chooseimages.PHOTO_LIST";
    /** 表示选择的图片发生了变化 */
    public static final int RESULT_CHANGE = 10010;

}
