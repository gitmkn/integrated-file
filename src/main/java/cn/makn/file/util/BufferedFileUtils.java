package cn.makn.file.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @Description:
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/7 21:13
 */
public class BufferedFileUtils extends BufferedRandomAccessFile {
    private static final String [] model = {"r","rw","rws","rwd"};

    /**
     * @param name
     * @param mode r	以只读的方式打开文本，也就意味着不能用write来操作文件
     *             rw	读操作和写操作都是允许的
     *             rws	每当进行写操作，同步的刷新到磁盘，刷新内容和元数据
     *             rwd	每当进行写操作，同步的刷新到磁盘，刷新内容
     * @return
     * @Description:
     * @author makn
     * @date 2020/12/7 15:20
     */
    protected BufferedFileUtils(String name, String mode) throws IOException {
        super(name, mode);
    }

    protected BufferedFileUtils(File file, String mode) throws IOException {
        super(file, mode);
    }

    protected BufferedFileUtils(String name, String mode, int size) throws IOException {
        super(name, mode, size);
    }

    protected BufferedFileUtils(File file, String mode, int size) throws IOException {
        super(file, mode, size);
    }

    public static RandomAccessFile getRAFWithModelR(String filePath) throws FileNotFoundException {
        RandomAccessFile raf = new RandomAccessFile(new File(filePath), model[0]);
        return raf;
    }

    public static RandomAccessFile getRAFWithModelRW(String filePath) throws FileNotFoundException {
        RandomAccessFile raf = new RandomAccessFile(new File(filePath), model[1]);
        return raf;
    }

    public static RandomAccessFile getRAFWithModelRWS(String filePath) throws FileNotFoundException {
        RandomAccessFile raf = new RandomAccessFile(new File(filePath), model[2]);
        return raf;
    }

    public static RandomAccessFile getRAFWithModelRWD(String filePath) throws FileNotFoundException {
        RandomAccessFile raf = new RandomAccessFile(new File(filePath), model[3]);
        return raf;
    }

}
