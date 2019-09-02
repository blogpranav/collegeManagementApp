package nicapp.nic.bihar.nicapp.Model;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class ImageModel {
    private String User_ID;
    private Bitmap image;
    private byte[] data;

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public byte[] getImage() {
        return data;
    }

    public void setImage(Bitmap image) {
        data = getBitmapAsByteArray(image);
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
