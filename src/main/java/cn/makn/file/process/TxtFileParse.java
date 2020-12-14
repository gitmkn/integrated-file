package cn.makn.file.process;

import cn.makn.api.IFileParse;
import cn.makn.file.except.FileParseExcept;
import cn.makn.file.model.FileDate;
import cn.makn.file.model.FileParagraph;
import cn.makn.file.model.xml.Field;
import cn.makn.file.model.xml.FileConvert;
import cn.makn.file.model.xml.FileRow;
import cn.makn.file.util.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TxtFileParse implements IFileParse {

    /**
     * @param filePath
     * @param fileName
     * @param fileConvert
     * @param pos
     * @param num
     * @return
     * @Description: 获取TXT数据，并赋值到对应类
     * @author makn
     * @date 2020/12/12 18:15
     */
    public FileDate getFileDate(String filePath, String fileName, FileConvert fileConvert, long pos, int num) {
        FileDate fileDate = new FileDate();
        FileRow head = fileConvert.getHead();
        FileRow body = fileConvert.getBody();
        FileRow tail = fileConvert.getTail();
        File file = new File(filePath + "/" + fileName);
        // 按模板读取文件
        List<String> rows = FileUtils.getFileDate(file, fileConvert.getEncoding(), pos, num, -1);
        // 获取读取的行数
        int rowCount = rows.size();
        // 如果读取空文件，返回null
        if (rows == null || rowCount <= 0) {
            return null;
        }
        // 遍历数据
        // body数据list
        List bodes = new ArrayList<>();
        fileDate.setBodyDate(bodes);
        // 判断是否最后一行
        boolean tailFlag = false;
        if (num > rowCount) {
            // 如果读取的行数小于分段的段数，说明是最后一行
            tailFlag = true;
        } else if (tail != null) {
            // 如果模板定义文件尾部，则判断当前段后一行
            FileParagraph fileParagraph = FileUtils.getRowCountAndPos(file, num + 1, pos, -1);
            if(num == fileParagraph.getNum()){
                tailFlag = true;
            }
        }
        for (int i = 0; i < rowCount; i++) {
            String row = rows.get(i);
            try {
                // 文件头数据处理
                // 第一行、偏移量为0、模板文件头不为空、
                if (i == 0 && pos == 0 && head != null && (head.getModule() == null || "H".equals(head.getModule()) || row.startsWith(head.getModule()))) {
                    Object obj = getRowDate(row, head);
                    fileDate.setHeadDate(obj);
                } else if (i == rowCount - 1 && tail != null && (tail.getModule() == null || "T".equals(tail.getModule()) || row.startsWith(tail.getModule())) && (num > rowCount || tailFlag)) {
                    Object obj = getRowDate(row, tail);
                    fileDate.setTailDate(obj);
                } else if (body == null || body.getModule() == null || "B".equals(body.getModule()) || row.startsWith(body.getModule())) {
                    Object obj = getRowDate(row, body);
                    bodes.add(obj);
                }

            } catch (Exception e) {

            }
        }
        return fileDate;
    }


    /**
     * @param row     解析的行数据
     * @param fileRow 文件模块
     * @return
     * @Description: 遍历对象属性，并把文件解析的值赋值给对象属性
     * @author makn
     * @date 2020/12/12 18:29
     */
    public static Object getRowDate(String row, FileRow fileRow) {
        // 返回类H-文件头 B-文件体 T-文件尾部
        Object obj = FileParse.newFileObject(fileRow.getClazz());

        // F-定长；S-分割符分割
        if ("F".equals(fileRow.getSepType())) {
            // 根据长度截取字符
            // 截取开始位置
            int start = 0;
            for (Field field : fileRow.getFields()) {
                if (field.getLength() <= 0) {
                    throw new FileParseExcept("文件解析数据处理时出现异常：[字段定长分隔时，长度必须配置]");
                }
                String value = row.substring(start, start + field.getLength());
                FileParse.setValue(obj, field, value);
                start = start + field.getLength();
            }
        } else if ("S".equals(fileRow.getSepType())) {
            String separator = fileRow.getSeparator();
            if (fileRow.getSeparator() == null) {
                throw new FileParseExcept("文件解析数据处理时出现异常：[文件模板中字段分隔符不能为空]");
            }
            // 分隔符
            separator = separator.replace("\\", "\\\\")
                    .replace("|", "\\|");
            // 处理后数据
            String[] values = row.split(separator);
            // 遍历
            int i = 0;
            for (Field field : fileRow.getFields()) {
                if (i < values.length) {
                    String value = values[i];
                    FileParse.setValue(obj, field, value);
                } else {
                    // 数量不够时赋 “”
                    FileParse.setValue(obj, field, "");
                }
                i++;
            }
        } else {
            throw new FileParseExcept("文件解析数据处理时出现异常：[文件模板中字段分隔类型不正确]");
        }
        return obj;
    }
}
