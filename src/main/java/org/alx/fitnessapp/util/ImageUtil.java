package org.alx.fitnessapp.util;

import org.alx.fitnessapp.exception.ImageConvertorException;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

public class ImageUtil {
    public static String convertBlobToBase64(Blob imageBlob) {
        if (imageBlob == null) {
            return null;
        }
        try {
            byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (SQLException e) {
            throw new ImageConvertorException(e.getMessage());
        }
    }

    public static byte[] convertBase64ToBlob(String base64Image) {
        if (base64Image == null) {
            return null;
        }
        return Base64.getDecoder().decode(base64Image);
    }

    public static Blob createBlob(byte[] bytes) {
        try {
            return new SerialBlob(bytes);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating Blob from byte array", e);
        }
    }
}
