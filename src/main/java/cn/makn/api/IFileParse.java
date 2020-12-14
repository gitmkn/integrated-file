package cn.makn.api;

import cn.makn.file.model.FileDate;
import cn.makn.file.model.xml.FileConvert;

import java.io.IOException;

/**
 * @Description: 文件解析接口
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/7 11:51
 */
public interface IFileParse {

    /**
     * @Description: 读取文件数据
     * @author makn
     * @date 2020/12/7 11:53
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param fileConvert 文件模板
     * @param pos 文件偏移量，excel偏移行数
     * @param num 文件行数
     * @return
     */
    FileDate getFileDate(String filePath, String fileName, FileConvert fileConvert, long pos, int num);



}
