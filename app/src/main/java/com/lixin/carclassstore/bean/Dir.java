package com.lixin.carclassstore.bean;

/**
 * 含有图片目录。<br/>
 * <br/>
 */
public class Dir
{
    public String id;
    public String text;
    public String path;
    /** 目录名称 */
    public String name;
    /** 该目录中图片的个数（不包含子文件夹中的图片个数） */
    public int length;
}
