package cn.makn.file.process;

import cn.makn.api.IFileParse;
import cn.makn.file.except.FileParseExcept;
import cn.makn.file.model.FileDate;
import cn.makn.file.model.xml.Field;
import cn.makn.file.model.xml.FileConvert;
import cn.makn.file.xml.XmlConvertUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Description:
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/12 10:33
 */
public class FileParse {

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
                String CapitalLetter = type.substring(0,1).toUpperCase();
                // 取字符串除首字母以外的字母
                String typeFirst= type.substring(1);
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
}
