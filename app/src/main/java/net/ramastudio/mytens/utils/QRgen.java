package net.ramastudio.mytens.utils;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRgen {

    public static Bitmap bitmap(String text, BarcodeFormat format, int w, int h ){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(text, format,w,h);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        return barcodeEncoder.createBitmap(bitMatrix);
    }
}
