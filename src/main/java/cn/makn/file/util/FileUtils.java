package cn.makn.file.util;

import cn.makn.file.model.FileDate;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description:
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/7 16:56
 */
public class FileUtils {

    /**
     * @Description: 获取文件总行数
     * @author makn
     * @date 2020/12/7 19:52
     * @param filePath 文件路径 +文件名称
     * @return
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
        }finally {
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    count = -1;
                    e.printStackTrace();
                }
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                }catch (IOException e){
                    count = -1;
                    e.printStackTrace();
                }
            }
        }

        return count;
    }

    /**
     * @Description: 获取文件数据
     * @author makn
     * @date 2020/12/7 21:21
     * @param filePath 文件路径 + 文件名称
     * @param num 读取条数
     * @return
     */
    public static FileDate getFileDate(String filePath, int num) throws IOException {
        FileDate fileDate = new FileDate();
        List<String> bodyDate = new LinkedList<String>();
        // 以只读的方式打开文本
        RandomAccessFile raf = BufferedFileUtils.getRAFWithModelR(filePath);
        // 每行数据
        String tempString = null;
        // 行数标
        int line = 0;
        // 小于行数时读取数据
        while (line < num) {
            // 读取下一行数据
            tempString = raf.readLine();
            // 不为空加入body中
            if(tempString != null){
                bodyDate.add(tempString);
                line++;
            }
        }
        // 关闭流
        raf.close();
        fileDate.setBodyDate(bodyDate);

        return fileDate;
    }
}
