package cn.makn.file.util;

import java.io.File;
import java.io.IOException;

/**
 * @Description: 读写文件
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/7 21:13
 */
public class BufferedFileUtils extends BufferedRandomAccessFile {
    private static final String [] model = {"r","rw","rws","rwd"};

    /**
     *
     * @param mode r	以只读的方式打开文本，也就意味着不能用write来操作文件
     *             rw	读操作和写操作都是允许的
     *             rws	每当进行写操作，同步的刷新到磁盘，刷新内容和元数据
     *             rwd	每当进行写操作，同步的刷新到磁盘，刷新内容
     * @return
     * @Description:
     * @author makn
     * @date 2020/12/7 15:20
     */
    private BufferedFileUtils(File file, String mode, int size) throws IOException {
        super(file, mode, size);
    }

    public static BufferedFileUtils getRAFWithModelR(File file, int size) throws IOException {
        return new BufferedFileUtils(file, model[0], size);
    }

    public static BufferedFileUtils getRAFWithModelRW(File file, int size) throws IOException {
        return new BufferedFileUtils(file, model[2], size);
    }

    public static BufferedFileUtils getRAFWithModelRWS(File file, int size) throws IOException {
        return new BufferedFileUtils(file, model[3], size);
    }

    public static BufferedFileUtils getRAFWithModelRWD(File file, int size) throws IOException {
        return new BufferedFileUtils(file, model[4], size);
    }

}
