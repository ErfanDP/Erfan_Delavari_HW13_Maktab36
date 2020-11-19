package com.example.erfan_delavari_hw14_maktab36.controller.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

public class PictureUtils {

    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(path, bmOptions);

        int srcWidth = bmOptions.outWidth;
        int srcHeight = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(srcWidth/destWidth, srcHeight/destHeight));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        return bitmap;
    }

    public static Bitmap getScaledBitmap(String path, View view) {
        return getScaledBitmap(path,view.getWidth(), view.getHeight());
    }
}
