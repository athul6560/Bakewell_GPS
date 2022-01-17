package com.acb.bakewellgps.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Tools {
    public static Bitmap getImageBitmapFromBase(String shop_image) {
        byte[] decodedString = Base64.decode(shop_image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public static String getStringfromBitmap(Bitmap imageBitmap) {
        if (imageBitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            return encoded;
        } else return null;
    }
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
