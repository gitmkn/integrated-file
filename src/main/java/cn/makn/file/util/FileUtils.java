package cn.makn.file.util;

import java.io.*;

/**
 * @Description:
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/7 16:56
 */
public class FileUtils {

    /**
     * @Description: 创建路径
     * @author makn
     * @date 2020/12/14 17:46
     * @param path
     * @return
     */
    public static boolean creatDir(String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        } else {
            return file.mkdirs();
        }
    }
}
