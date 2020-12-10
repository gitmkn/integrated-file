package cn.makn.file.xml;

import cn.makn.file.model.xml.Field;
import cn.makn.file.model.xml.FileConvert;
import cn.makn.file.model.xml.FileRow;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: xml转换工具，将xml转为bean；将bean转化为xml
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/9 15:28
 */
public class XmlConvertUtils {
    private static final String XML_NAME = "fileConvert/";

    // 加载过的xml放入内存，第二次从map中获取
    private static Map<String, FileConvert> fileConvertMap = new ConcurrentHashMap<>();

    /**
     * @Description: 加载所有xml，文件元素
     * @author makn
     * @date 2020/12/9 15:58
     * @param
     * @return
     */
    public static void getXMLParse(String xmlName) {
        SAXReader reader = new SAXReader();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream is = classLoader.getResourceAsStream(XML_NAME + xmlName + ".xml");
            Document document = reader.read(is);
            Element element = document.getRootElement();
            FileConvert fileConvert = new FileConvert();
            fileConvert.setName(getElementAttribute("name", element));
            fileConvert.setDesc(getElementAttribute("desc", element));
            fileConvert.setEncoding(getElementAttribute("encoding", element, "UTF-8"));
            fileConvert.setType(getElementAttribute("type", element, "String"));
            fileConvert.setBr(getElementAttribute("br", element, "\r\n"));
            fileConvert.setHead(getFileRow((Element) document.selectSingleNode("/file/head")));
            fileConvert.setBody(getFileRow((Element) document.selectSingleNode("/file/body")));
            fileConvert.setTail(getFileRow((Element) document.selectSingleNode("/file/tail")));
            fileConvert.setSheetName(getElementAttribute("sheetName", element));
            fileConvertMap.put(fileConvert.getName(), fileConvert);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 获取模块元素
     * @author makn
     * @date 2020/12/9 17:31
     * @param element
     * @return
     */
    private static FileRow getFileRow(Element element) {
        if(null == element){
            return null;
        }
        FileRow fileRow = new FileRow();
        fileRow.setSepType(getElementAttribute("sepType", element));
        fileRow.setSeparator(getElementAttribute("separator", element));
        fileRow.setModule(getElementAttribute("module", element, "B"));
        fileRow.setClazz(getElementAttribute("clazz", element));
        fileRow.setRef(getElementAttribute("ref", element));
        List<Field> fields = new ArrayList<>();
        List<Element> elements = element.selectNodes("field");
        if(null != elements){
            for(Element ele : elements){
                fields.add(getField(ele));
            }
        }
        fileRow.setFields(fields);

        return fileRow;
    }

    /**
     * @Description: 获取行元素
     * @author makn
     * @date 2020/12/9 17:46
     * @param element
     * @return
     */
    private static Field getField(Element element){
        if (null == element) {
            return null;
        }
        Field field = new Field();
        field.setName(getElementAttribute("name", element));
        field.setDesc(getElementAttribute("desc", element));
        field.setType(getElementAttribute("type", element));
        field.setLength(getElementAttribute("length", element));
        field.setToEmpty(getElementAttribute("toEmpty", element));
        return field;
    }

    /**
     * @Description: 获取元素值，不带默认值
     * @author makn
     * @date 2020/12/9 17:06
     * @param name
     * @param element
     * @return
     */
    private static String getElementAttribute(String name, Element element) {
        return getElementAttribute(name, element, null);
    }

    /**
     * @Description: 获取元素值，带默认值处理
     * @author makn
     * @date 2020/12/9 17:06
     * @param name
     * @param element
     * @return
     */
    private static String getElementAttribute(String name, Element element, String defaultValue) {
        return element.attribute(name) == null ? defaultValue : (String) element.attribute(name).getData();
    }


    /**
     * @Description: 获取convertName对应的模板
     * @author makn
     * @date 2020/12/10 9:26
     * @param convertName 模板名称
     * @return
     */
    public static FileConvert getFileConvert(String convertName) {
        if(fileConvertMap.get(convertName) == null){
            getXMLParse(convertName);
        }
        return fileConvertMap.get(convertName);
    }
}
