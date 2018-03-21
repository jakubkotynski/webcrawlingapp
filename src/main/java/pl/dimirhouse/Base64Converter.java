package pl.dimirhouse;

import org.apache.commons.codec.binary.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Base64Converter {

    public static String convertPictureToBase64(File file) throws IOException {
        String encodedfile;

        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fileInputStreamReader.read(bytes);
        encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");

        return encodedfile;
    }

    public static String pictureInBase64(int index) throws IOException {

        File file = new File("picture" + index + ".jpg");

        String pictureInBase64 = Base64Converter.convertPictureToBase64(file);

        return pictureInBase64;
    }
}
