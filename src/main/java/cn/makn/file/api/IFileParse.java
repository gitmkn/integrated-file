package cn.makn.file.api;

import cn.makn.file.model.FileDate;
import cn.makn.file.model.FileSegment;
import cn.makn.file.model.xml.FileConvert;

import java.util.Map;

/**
 * @Description: 文件解析接口
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/7 11:51
 */
public interface IFileParse {

    /**
     * @param filePath 文件路径
     * @return
     * @Description: 获取行数，返回整数
     * @author makn
     * @date 2020/12/14 17:51
     */
    int getRowCount(String filePath);

    /**
     * @param filePath 文件路径 + 文件名称
     * @param num      段行数
     * @param size     缓冲
     * @return
     * @Description: 获取文件从条数，及段落偏移量
     * @author makn
     * @date 2020/12/8 15:39
     */
    FileSegment getRowCount(String filePath, int num, int size);

    /**
     * @param filePath    文件路径
     * @param fileName    文件名称
     * @param fileConvert 文件模板
     * @param pos         文件偏移量，excel偏移行数
     * @param num         文件行数
     * @return
     * @Description: 读取文件数据
     * @author makn
     * @date 2020/12/7 11:53
     */
    FileDate getFileDate(String filePath, String fileName, FileConvert fileConvert, long pos, int num);


}
