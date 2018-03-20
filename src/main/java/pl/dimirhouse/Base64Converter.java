package pl.dimirhouse;

import org.apache.commons.codec.binary.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Base64Converter {

    public static String encodeFileToBase64Binary(File file) throws IOException {
        String encodedfile = null;
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fileInputStreamReader.read(bytes);
        encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");

        return encodedfile;
    }
}
