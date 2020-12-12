package cn.makn.file.util;

import cn.makn.file.model.FileParagraph;

import java.io.*;
import java.util.*;

/**
 * @Description:
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/7 16:56
 */
public class FileUtils {

    /**
     * @param filePath 文件路径 +文件名称
     * @return
     * @Description: 获取文件总行数（只获取文件行数）
     * @author makn
     * @date 2020/12/7 19:52
     */
    public static int getRowCount(String filePath) {
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
    public static Map<String, Object> getRowCount(String filePath, int num, int size) throws IOException {
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
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("count", count);
        map1.put("fileParagraphs", fileParagraphs);
        return map1;
    }

    /**
     * @Description: 获取文件数据
     * @param file 文件路径 + 文件名称
     * @param pos      偏移量
     * @param num      读取条数 -1:读取所有(慎用，要防止内存溢出)
     * @param size     缓冲 -1默认缓冲
     * @return List<String>
     * @author makn
     * @date 2020/12/7 21:21
     */
    public static List<String> getFileDate(File file, String encoding, long pos, int num, int size) {
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
        }finally {
            try {
                if(raf != null){
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
     * @Description: 根据偏移量读取下一段落（外部不可用）
     * @author makn
     * @date 2020/12/8 15:41
     */
    private static FileParagraph getRowCountAndPos(File file, int num, long pos, int size) throws IOException {
        if (size < 0) {
            size = -1;
        }
        // 读取R
        BufferedFileUtils raf = BufferedFileUtils.getRAFWithModelR(file, size);
        // 偏移段落
        raf.seek(pos);
        int count = 0;
        while (raf.readLine() != null && (count < num || num == -1)) {
            count++;
        }
        // 偏移量
        long countPos = raf.getFilePointer();
        // 关闭流
        raf.close();

        // 返回总行数和偏移量
        return new FileParagraph(count, countPos);
    }
}
