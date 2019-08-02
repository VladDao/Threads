package com.plietnov.task.part3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileToByte {

    private boolean flag = false;
    private byte[] bArray = new byte[0];

    public boolean setFile(String input) {
        File file;
        file = new File(input);
        if (!file.isFile()) {
            return false;
        }
        bArray = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(bArray);
        } catch (IOException e) {
            bArray = new byte[0];
        }
        flag = true;
        return true;
    }

    public byte[] getByte() {
        flag = false;
        return bArray;
    }

    public boolean isChanged() {
        return flag;
    }
}
