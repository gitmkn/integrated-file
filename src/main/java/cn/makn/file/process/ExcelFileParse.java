package cn.makn.file.process;

import cn.makn.file.api.IFileParse;
import cn.makn.file.except.FileParseExcept;
import cn.makn.file.model.FileDate;
import cn.makn.file.model.FileSegment;
import cn.makn.file.model.xml.FileConvert;
import cn.makn.file.model.xml.FileRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelFileParse implements IFileParse {

    /**
     * @Description: 获取EXCEL文件行数，默认sheet为0
     * @author makn
     * @date 2020/12/24 16:22
     * @param filePath 文件路径 + 文件名称
     * @return
     */
    @Override
    public int getRowCount(String filePath) {
        // Excel使用FileInputStream
        FileInputStream fileInputStream = null;
        try {
            // 得到地址流
            fileInputStream = new FileInputStream(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileParseExcept("文件解析初始化时出现异常：[文件<" + filePath + ">不存在]");
        }
        // 得到工作簿(poi包需apache下载)
        Workbook workbook = null;
        POIFSFileSystem poifsFileSystem;
        // 计数行
        int count = 0;
        try {
            if (filePath.endsWith(".xlsx")) {
                // Excel 2007
                workbook = new XSSFWorkbook(fileInputStream);
            } else if (filePath.endsWith(".xls")) {
                // Excel 2003
                poifsFileSystem = new POIFSFileSystem(fileInputStream);
                workbook = new HSSFWorkbook(poifsFileSystem);
            } else {
                throw new FileParseExcept("文件解析时出现异常：[文件<" + filePath + ">后缀必须是.xlsx或者.xls]");
            }

            // 得到一个工作表
            Sheet sheet = workbook.getSheetAt(0);

            // 总行数,0开始
            int countRow = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i <= countRow; i++) {
                if(sheet.getRow(i).getCell(0).toString() == null || "".equals(sheet.getRow(i).getCell(0).toString())){
                    continue;
                }
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileParseExcept("文件解析时出现异常：[文件<" + filePath + ">不可读]");
        } finally {
            try {
                // 关闭流
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    /**
     * @Description:  获取EXCEL文件行数,返回结果分段
     * @author makn
     * @date 2020/12/25 11:24
     * @param filePath 文件路径 + 文件名称
     * @param num 读取条数 -1:读取所有(慎用，要防止内存溢出)
     * @param size 缓冲 -1默认缓冲
     * @return
     */
    @Override
    public FileSegment getRowCount(String filePath, int num, int size) {
        return new FileSegment();
    }

    /**
     * @param filePath    文件路径
     * @param fileName    文件名称
     * @param fileConvert 文件模板
     * @param pos         文件偏移量，excel偏移行数
     * @param num         文件行数
     * @return
     * @Description: 获取txt文件数据
     * @author makn
     * @date 2020/12/7 12:07
     */
    @Override
    public FileDate getFileDate(String filePath, String fileName, FileConvert fileConvert, long pos, int num) {
        FileDate fileDate = new FileDate();
        FileRow head = fileConvert.getHead();
        FileRow body = fileConvert.getBody();
        FileRow tail = fileConvert.getTail();
        // Excel使用FileInputStream
        FileInputStream fileInputStream = null;
        try {
            // 得到地址流
            fileInputStream = new FileInputStream(filePath + "/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileParseExcept("文件解析初始化时出现异常：[文件<" + filePath + "/" + fileName + ">不存在]");
        }
        // 得到工作簿(poi包需apache下载)
        Workbook workbook = null;
        POIFSFileSystem poifsFileSystem;
        try {
            if (fileName.endsWith(".xlsx")) {
                // Excel 2007
                workbook = new XSSFWorkbook(fileInputStream);
            } else if (fileName.endsWith(".xls")) {
                // Excel 2003
                poifsFileSystem = new POIFSFileSystem(fileInputStream);
                workbook = new HSSFWorkbook(poifsFileSystem);
            } else {
                throw new FileParseExcept("文件解析时出现异常：[文件<" + filePath + fileName + ">后缀必须是.xlsx或者.xls]");
            }

            // 得到一个工作表
            Sheet sheet;
            // 获取定义的sheet
            String sheetName = fileConvert.getSheetName();
            // 如果定义了sheet读取对应的sheet,如果未定义，默认读第一个sheet
            if (sheetName != null && sheetName.trim().length() != 0) {
                // 根据定义的sheet读取
                sheet = workbook.getSheet(sheetName);
            } else {
                // 默认读取第一个sheet
                sheet = workbook.getSheetAt(0);
            }

            // 总行数,0开始
            int countRow = sheet.getLastRowNum();
            int count = 0;
            for (int i = 0; i <= countRow; i++) {
                String value = sheet.getRow(i).getCell(0).getRow().getCell(0).toString();
                if((fileConvert.getEndFlag() != null && value == fileConvert.getEndFlag()) || value == null || "".equals(value)){
                    continue;
                }
                count++;
            }
            System.out.println("行数：" + count);


        } catch (Exception e) {
            e.printStackTrace();
            throw new FileParseExcept("文件解析时出现异常：[文件<" + filePath + fileName + ">不可读]");
        } finally {
            try {
                // 关闭流
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileDate;
    }

}
