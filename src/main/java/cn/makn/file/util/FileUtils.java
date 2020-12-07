package cn.makn.file.util;

import java.io.*;

/**
 * @Description:
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/7 16:56
 */
public class FileUtils {

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
}
