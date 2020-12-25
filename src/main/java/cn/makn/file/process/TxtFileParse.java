package cn.makn.file.process;

import cn.makn.file.api.IFileParse;
import cn.makn.file.except.FileParseExcept;
import cn.makn.file.model.FileDate;
import cn.makn.file.model.FileParagraph;
import cn.makn.file.model.FileSegment;
import cn.makn.file.model.xml.Field;
import cn.makn.file.model.xml.FileConvert;
import cn.makn.file.model.xml.FileRow;
import cn.makn.file.util.BufferedFileUtils;

import java.io.*;
import java.util.*;

public class TxtFileParse implements IFileParse {

    /**
     * @param filePath 文件路径 +文件名称
     * @return
     * @Description: 获取文件总行数（只获取文件行数）
     * @author makn
     * @date 2020/12/7 19:52
     */
    @Override
    public int getRowCount(String filePath) {
        int count = 0;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = new FileInputStream(new File(filePath));
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (bufferedReader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            count = -1;
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    count = -1;
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    count = -1;
                    e.printStackTrace();
                }
            }
        }

        return count;
    }

    /**
     * @param filePath 文件路径 + 文件名称
     * @param num      段行数
     * @param size     缓冲
     * @return
     * @Description: 获取文件从条数，及段落偏移量
     * @author makn
     * @date 2020/12/8 15:39
     */
    @Override
    public FileSegment getRowCount(String filePath, int num, int size) {
        // 单行偏移量
        long pos = 0L;
        // 总体偏移量
        long count = 0L;
        // 文件
        File file = new File(filePath);
        // 读取返回
        List<FileParagraph> fileParagraphs = new LinkedList<FileParagraph>();
        while (true) {
            // 读取偏移量后的行数
            FileParagraph fileParagraph = getRowCountAndPos(file, num, pos, size);

            // 当前段落条数
            int total = fileParagraph.getNum();
            // 行数
            count += total;

            // 段落信息
            fileParagraphs.add(new FileParagraph(total, pos));

            // 读取无结果或不满足段数（最后一段），退出读取
            if (total == 0 || total < num) {
                break;
            }
            // 偏移量
            pos = fileParagraph.getPos();
        }

        // 返回总行数和段落
        FileSegment fileSegment = new FileSegment();
        fileSegment.setCount(count);
        fileSegment.setFileParagraphs(fileParagraphs);
        return fileSegment;
    }

    /**
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param fileConvert 文件模板
     * @param pos 偏移量
     * @param num 段行数
     * @return
     * @Description: 获取TXT数据，并赋值到对应类
     * @author makn
     * @date 2020/12/12 18:15
     */
    @Override
    public FileDate getFileDate(String filePath, String fileName, FileConvert fileConvert, long pos, int num) {
        FileDate fileDate = new FileDate();
        FileRow head = fileConvert.getHead();
        FileRow body = fileConvert.getBody();
        FileRow tail = fileConvert.getTail();
        File file = new File(filePath + "/" + fileName);
        // 按模板读取文件
        List<String> rows = getFileDate(file, fileConvert.getEncoding(), pos, num, -1);
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
            FileParagraph fileParagraph = getRowCountAndPos(file, num + 1, pos, -1);
            if (num == fileParagraph.getNum()) {
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
     * @param file 文件路径 + 文件名称
     * @param pos  偏移量
     * @param num  读取条数 -1:读取所有(慎用，要防止内存溢出)
     * @param size 缓冲 -1默认缓冲
     * @return List<String>
     * @Description: 获取偏移量的文件数据
     * @author makn
     * @date 2020/12/7 21:21
     */
    private List<String> getFileDate(File file, String encoding, long pos, int num, int size) {
        List<String> date = new ArrayList<>();
        if (size < 0) {
            size = -1;
        }
        BufferedFileUtils raf = null;
        try {
            // 读取R
            raf = BufferedFileUtils.getRAFWithModelR(file, size);
            // 偏移段落
            raf.seek(pos);
            int count = 0;
            String strLine;
            while (count < num || num == -1) {
                strLine = raf.readLine();
                if (strLine != null) {
                    date.add(new String(strLine.getBytes("8859_1"), encoding));
                    count++;
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (raf != null) {
                    // 关闭流
                    raf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        // 返回总行数和偏移量
        return date;
    }

    /**
     * @param file 文件
     * @param num  段行数
     * @param pos  偏移量
     * @param size 缓冲
     * @return FileParagraph 当前段落行数及偏移量
     * @Description: 根据偏移量读取下一段落
     * @author makn
     * @date 2020/12/8 15:41
     */
    private FileParagraph getRowCountAndPos(File file, int num, long pos, int size) {
        if (size < 0) {
            size = -1;
        }

        BufferedFileUtils raf = null;
        // 偏移量
        long countPos = 0L;
        // 总行数
        int count = 0;
        try {
            // 读取R
            raf = BufferedFileUtils.getRAFWithModelR(file, size);
            // 偏移段落
            raf.seek(pos);
            while (raf.readLine() != null && (count < num || num == -1)) {
                count++;
            }
            // 偏移量
            countPos = raf.getFilePointer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (raf != null) {
                    // 关闭流
                    raf.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 返回总行数和偏移量
        return new FileParagraph(count, countPos);
    }


    /**
     * @param row     解析的行数据
     * @param fileRow 文件模块
     * @return
     * @Description: 遍历对象属性，并把文件解析的值赋值给对象属性
     * @author makn
     * @date 2020/12/12 18:29
     */
    private static Object getRowDate(String row, FileRow fileRow) {
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
