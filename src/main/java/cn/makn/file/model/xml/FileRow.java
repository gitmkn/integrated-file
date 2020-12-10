package cn.makn.file.model.xml;

import java.util.List;

/**
 * @Description: 解析后每行数据模型
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/8 9:10
 */
public class FileRow {

    /**
     * @Description: 分割类型，F-定长；S-分割符分割
     * @author makn
     * @date 2020/12/9 16:34
     * @param null
     * @return
     */
    private String sepType;

    /**
     * @Description: 分割符
     * @author makn
     * @date 2020/12/9 16:35
     * @param null
     * @return
     */
    private String separator;

    /**
     * @Description: 模块标识 H-文件头 B-文件体 T-文件尾部
     * @author makn
     * @date 2020/12/9 16:35
     * @param null
     * @return
     */
    private String module;

    /**
     * @Description: 对应模块java类
     * @author makn
     * @date 2020/12/9 16:37
     * @param null
     * @return
     */
    private String clazz;

    /**
     * @Description: 引用格式
     * @author makn
     * @date 2020/12/9 16:37
     * @param null
     * @return
     */
    private String ref;

    /**
     * @Description: 数据
     * @author makn
     * @date 2020/12/9 17:28
     * @param null
     * @return
     */
    private List<Field> fields;

    public String getSepType() {
        return sepType;
    }

    public void setSepType(String sepType) {
        this.sepType = sepType;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
