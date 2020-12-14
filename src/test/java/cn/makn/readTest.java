package cn.makn;

import cn.makn.api.IFileParse;
import cn.makn.file.model.FileDate;
import cn.makn.file.model.xml.FileConvert;
import cn.makn.file.process.FileParse;
import cn.makn.file.xml.XmlConvertUtils;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/7 15:33
 */
public class readTest {
    @Reference
    IFileParse iFileParse;

    @Test
    public void readTest1() throws IOException {
//        RandomAccessFile raf = BufferedFileUtils.getRAFWithModelR(new File("E:/test1.txt"), -1);
//        // 偏移量
//        raf.seek(4);
//        // 读取下一行
//        System.out.println(raf.readLine());
    }

    @Test
    public void readTest2() {
        // 143014562行 1.7G 7s
//        long start = System.currentTimeMillis();
//        int raf = FileUtils.getRowCount("E:/test1.txt");
//        System.out.println(raf);
//        long end = System.currentTimeMillis();
//        System.out.println((end - start)  / 1000 + "s");
    }

    @Test
    public void readTest3() throws IOException {
//        long start = System.currentTimeMillis();
//        List<String> date = FileUtils.getFileDate("E:/test2.txt", "UTF-8", 0,5000, -1);
//        System.out.println(date.size());
//        long end = System.currentTimeMillis();
//        System.out.println((end - start)  / 1000 + "s");
    }

    @Test
    public void readTest4() throws IOException {
        // 1000万数据5分钟左右
//        long start = System.currentTimeMillis();
//        RandomAccessFile raf = BufferedFileUtils.getRAFWithModelR("E:/test1.txt");
//        int i = 0;
//        while (i < 2000) {
//            System.out.println("line " + i + ": " + raf.readLine());
//            i++;
//        }
//        long end = System.currentTimeMillis();
//        System.out.println((end - start)  / 1000 + "s");
    }


    @Test
    public void readTest5() throws IOException {
        // 143014562行 1.7G 20s
//        long start = System.currentTimeMillis();
//        Map<String, Object> raf = FileUtils.getRowCount("E:/test1.txt", 5000, -1);
//        System.out.println(raf.get("fileParagraphs").toString());
//        System.out.println(raf.get("count").toString());
//        long end = System.currentTimeMillis();
//        System.out.println((end - start)  / 1000 + "s");
    }

    @Test
    public void readTest6() throws IOException {
//        long start = System.currentTimeMillis();
//        List<String> raf = FileUtils.getFileDate(new File("E:/test2.txt"), "UTF-8",0, -1, -1);
//        System.out.println(raf.size());
//        long end = System.currentTimeMillis();
//        System.out.println((end - start)  / 1000 + "s");
    }


    @Test
    public void readTest7() throws IOException {
        // 143014562行 1.7G 48s
//        long start = System.currentTimeMillis();
//        Map<String, Object> raf = FileUtils.getRowCount("E:/test1.txt", 1000000, -1);
//        System.out.println(raf.get("fileParagraphs").toString());
//        System.out.println(raf.get("count").toString());
//        List<String> date = null;
//        for(FileParagraph fileParagraph : (List<FileParagraph>)raf.get("fileParagraphs")){
//            date = FileUtils.getFileDate("E:/test1.txt", "", fileParagraph.getPos(), fileParagraph.getNum(), -1);
////            for(String str : date){
//////                System.out.println(str);
////            }
//            System.out.println("长度：" + date.size());
//        }
//        long end = System.currentTimeMillis();
//        System.out.println((end - start)  / 1000 + "s");
    }

    @Test
    public void readTest8() throws Exception {
        long start = System.currentTimeMillis();
        FileConvert fileConvert = XmlConvertUtils.getFileConvert("txtUser");
        System.out.println(fileConvert.toString());

        long end = System.currentTimeMillis();
        System.out.println((end - start)  + "ms");

        long start2 = System.currentTimeMillis();
        FileConvert fileConvert1 = XmlConvertUtils.getFileConvert("txtUser");
        System.out.println(fileConvert1.toString());
        long end2 = System.currentTimeMillis();
        System.out.println((end2 - start2)  + "ms");
    }

    @Test
    public void readTest9() throws Exception {
//        long start = System.currentTimeMillis();
//        SAXReader reader = new SAXReader();
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        InputStream is = classLoader.getResourceAsStream("src/main/fileConvert/txtUser.xml");
//        Document document = reader.read(is);
//        System.out.println(document.toString());
//        long end = System.currentTimeMillis();
//        System.out.println((end - start)  / 1000 + "s");
    }

    @Test
    public void readTest10() throws Exception {
        long start = System.currentTimeMillis();


        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000 + "s");
    }

    @Test
    public void readTest11() throws Exception {
        long start = System.currentTimeMillis();

//        IFileParse iFileParse = FileParse.getFileParse("txt");
//        System.out.println("IFileParse:"+ iFileParse.getClass().getName());
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000 + "s");
    }

    @Test
    public void readTest12() throws Exception {
        long start = System.currentTimeMillis();
        System.out.println(this.getClass().getPackage().getName());
        Map map = new HashMap();
        System.out.println(map instanceof HashMap);
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");

    }

    @Test
    public void readTest13() throws Exception {
        long start = System.currentTimeMillis();
        FileDate fileDate = FileParse.getFileDate("E:/", "test2.txt", "txtUser", 0, 10000);
//        Map map = (Map)fileDate.getHeadDate();
//        System.out.println("文件头：" + map.get("testHead"));
        System.out.println("文件体：" + fileDate.getBodyDate().toString());
//        Map mapt = (Map)fileDate.getTailDate();
//        System.out.println("文件尾：" + mapt.get("testTail"));
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");

    }


    @Test
    public void readTest14() throws Exception {
        long start = System.currentTimeMillis();
        FileDate fileDate = FileParse.getFileDate("E:/", "test5.txt", "txtUser", 0, 10000);
        Map map = (Map)fileDate.getHeadDate();
        System.out.println("文件头：" + map.get("testHead"));
        System.out.println("文件体：" + fileDate.getBodyDate().toString());
        Map mapt = (Map)fileDate.getTailDate();
        System.out.println("文件尾：" + mapt.get("testTail"));
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");

    }
}
