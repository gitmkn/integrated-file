package cn.makn.file.process;

import cn.makn.file.api.IFileParse;
import cn.makn.file.except.FileParseExcept;
import cn.makn.file.model.FileDate;
import cn.makn.file.model.FileSegment;
import cn.makn.file.model.xml.Field;
import cn.makn.file.model.xml.FileConvert;
import cn.makn.file.xml.XmlConvertUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/12 10:33
 */
public class FileParse {

    // xml中类
    private static final Map<String, Object> mapObject = new ConcurrentHashMap<>();

    /**
     * @param path 文件路径 + 文件名称
     * @return
     * @Description: 获取文件行数，TXT和EXCEL获取路径后缀，如果为空则
     * @author makn
     * @date 2020/12/24 15:01
     */
    public static int getRowCount(String path) {
        IFileParse iFileParse = getFileParse(getPathSuffix(path));
        return iFileParse.getRowCount(path);
    }

    public static FileSegment getRowCount(String filePath, int num, int size) {
        IFileParse iFileParse = getFileParse(getPathSuffix(filePath));
        return iFileParse.getRowCount(filePath, num, size);
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
        FileConvert fileConvert = XmlConvertUtils.getFileConvert(convertName);
        IFileParse iFileParse = getFileParse(fileConvert.getType());
        FileDate fileDate = iFileParse.getFileDate(path, fileName, fileConvert, pos, num);
        return fileDate;
    }

    /**
     * @param type 文件类型 Txt、Excel
     * @return
     * @Description: 根据类型获取对应的文件类型
     * @author makn
     * @date 2020/12/12 10:08
     */
    private static IFileParse getFileParse(String type) {
        if (type.isEmpty()) {
            throw new FileParseExcept("文件解析时出现异常：[文件类型转换时，文件类型不能为空]");
        } else {
            try {
                // 适配xml模板中文件类型支持大小写
                // 取字符串的首字母并大写
                String CapitalLetter = type.substring(0, 1).toUpperCase();
                // 取字符串除首字母以外的字母
                String typeFirst = type.substring(1);
                String className = FileParse.class.getPackage().getName() + "." + CapitalLetter + typeFirst + "FileParse";
                // 通过类名和类加载器获取Class,然后通过newInstance()获取对象实例
                return (IFileParse) Class.forName(className).newInstance();
            } catch (Exception | NoClassDefFoundError e) {
                e.printStackTrace();
                throw new FileParseExcept("文件解析时出现异常：[文件类型转换时，文件类型<" + type + ">未找到]");
            }
        }
    }

    /**
     * @param className 包路径 + 类名
     * @return
     * @Description: 初始化文件模板中class类
     * @author makn
     * @date 2020/12/12 18:08
     */
    protected static Object newFileObject(String className) {
        // 如果是Map直接返回
        if (className.contains("Map")) {
            return new HashMap<>();
        }
        // 返回类H-文件头 B-文件体 T-文件尾部
        if (mapObject.get(className) != null) {
            return mapObject.get(className);
        }

        Object obj;
        try {
            obj = Class.forName(className).newInstance();
            mapObject.put("className", obj);
        } catch (Exception | NoClassDefFoundError e) {
            e.printStackTrace();
            throw new FileParseExcept("文件解析时出现异常：[文件模板中的对应类<" + className + ">未找到]");
        }
        return obj;
    }

    /**
     * @param obj   对象
     * @param field xml中字段bean
     * @param value 字段值
     * @return
     * @Description: 将xml模板中对应字段的值赋值给对象对应的属性，或值放入map
     * @author makn
     * @date 2020/12/12 18:23
     */
    protected static void setValue(Object obj, Field field, String value) {
        if (null != value) {
            // 去除值两边空格
            if ("Y".equals(field.getToEmpty())) {
                value = value.trim();
            }
            // 赋值
            if (obj instanceof Map) {
                if ("String".equals(field.getType())) {
                    ((Map) obj).put(field.getName(), value);
                } else {
                    // TODO 当为Map是其他类型的暂未处理
                }
            } else {
                try {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), obj.getClass());
                    Method wM = propertyDescriptor.getWriteMethod();
                    wM.invoke(obj, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * @Description: 根据文件路径,获取文件后缀,默认为txt
     * @author makn
     * @date 2020/12/25 10:19
     * @param path 文件路径 + 文件名称
     * @return
     */
    private static String getPathSuffix(String path) {
        String suffix = "txt";
        if (path != null) {
            if (path.endsWith(".xlsx") || path.endsWith(".xls")) {
                suffix = "excel";
            }
        }

        return suffix;
    }
}
