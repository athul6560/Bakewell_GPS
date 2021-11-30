package com.acb.bakewellgps.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Tools {
    public static Bitmap getImageBitmapFromBase(String shop_image) {
        byte[] decodedString = Base64.decode(shop_image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
