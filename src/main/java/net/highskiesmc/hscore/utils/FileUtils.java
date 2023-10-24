package net.highskiesmc.hscore.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    public static void copy(InputStream paramInputStream, File paramFile) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(paramFile);
            byte[] arrayOfByte = new byte[1024];
            int i;
            while ((i = paramInputStream.read(arrayOfByte)) > 0)
                fileOutputStream.write(arrayOfByte, 0, i);
            fileOutputStream.close();
            paramInputStream.close();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public static void mkdir(File paramFile) {
        try {
            paramFile.mkdir();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
