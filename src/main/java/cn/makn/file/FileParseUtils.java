package cn.makn.file;

import cn.makn.file.model.FileDate;
import cn.makn.file.process.FileParse;

/**
 * @Description:
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/14 18:12
 */
public class FileParseUtils {

    /**
     * @param path        文件路径
     * @param fileName    文件名称
     * @param convertName 文件模板
     * @param pos         偏移量
     * @param num         读取条数
     * @return
     * @Description: 获取文件数据
     * @author makn
     * @date 2020/12/12 15:11
     */
    public static FileDate getFileDate(String path, String fileName, String convertName, long pos, int num) {
        return FileParse.getFileDate(path, fileName, convertName, pos, num);
    }
}
