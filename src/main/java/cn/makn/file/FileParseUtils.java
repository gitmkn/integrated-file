package cn.makn.file;

import cn.makn.file.model.FileDate;
import cn.makn.file.process.FileParse;
import cn.makn.file.util.FileUtils;

/**
 * @Description:
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/14 18:12
 */
public class FileParseUtils {

    /**
     * @Description: 创建路径
     * @author makn
     * @date 2020/12/14 17:46
     * @param path 文件路径
     * @return
     */
    public static boolean creatDir(String path){
        return FileUtils.creatDir(path);
    }

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
