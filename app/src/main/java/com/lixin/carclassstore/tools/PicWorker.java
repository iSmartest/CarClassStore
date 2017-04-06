package com.lixin.carclassstore.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Base64;


import com.lixin.carclassstore.bean.Constants;
import com.lixin.carclassstore.utils.CommonLog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PicWorker {
    private PicWorker() {
    }

    /**
     * 横竖屏图片的压缩
     *
     * @param source
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap source) {
        float width = source.getWidth();
        float height = source.getHeight();
        float scale = 0;
        if (width >= height) {
            scale = 400 / width;
        } else {
            scale = 300 / height;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(source, 0, 0, (int) width, (int) height,
                matrix, true);

    }

    /**
     * 压缩图片
     *
     * @param path 图片的路径
     * @param h    压缩后图片的高度
     * @param w    压缩后图片的宽度
     * @return 压缩后的图片
     */
    public static Bitmap compressImage(String path, int h, int w) {
        if (path == null)
            return null;
        if (path.startsWith("file://"))
            path = path.replaceFirst("file://", "");

        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opt);

        if (w > 0 || h > 0) {
            final int outHeight = opt.outHeight;
            final int outWidth = opt.outWidth;

            if (w > 0 && h <= 0) {
                opt.inSampleSize = Math.round((float) outWidth / (float) w);
            }

            if (w <= 0 && h > 0) {
                opt.inSampleSize = Math.round((float) outHeight / (float) h);
            }

            if (w > 0 && h > 0) {
                int heightRatio = (int) Math
                        .ceil((float) outHeight / (float) h);
                int widthRatio = (int) Math.ceil((float) outWidth / (float) w);

                if (heightRatio > widthRatio)
                    opt.inSampleSize = widthRatio;
                else
                    opt.inSampleSize = heightRatio;
            }

            int roundedSize;
            if (opt.inSampleSize <= 8) {
                roundedSize = 1;
                while (roundedSize < opt.inSampleSize) {
                    roundedSize <<= 1;
                }
            } else {
                roundedSize = (opt.inSampleSize + 7) / 8 * 8;
            }
            opt.inSampleSize = roundedSize;
        }

        opt.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, opt);
        int degree = readPictureDegree(path);
        if (degree != 0) {
            bitmap = rotaingImageView(degree, bitmap);
        }

        return bitmap;
    }

    /**
     * 压缩图片,压缩后的图片将按照比例来进行缩放
     *
     * @param path 图片的路径
     * @return 压缩后的图片
     */
    public static Bitmap compressImage(String path) {
        return compressImage(path, 800, 480);
    }

    /**
     * 通过Base64转码，将Bitmap转化为String
     *
     * @param bitmap
     * @return
     */
    @SuppressLint("NewApi")
    public static String bitmap2Str(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            CommonLog.e(e);
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 剪裁图片
     * @param context
     * @param uri
     */
    public static void startPhotoZoom(Context context, Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        ((Activity) context).startActivityForResult(intent, Constants.TAILOR_PHOTO);
    }
}
