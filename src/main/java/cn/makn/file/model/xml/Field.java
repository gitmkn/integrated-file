package cn.makn.file.model.xml;

/**
 * @Description: 字段对应bean
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/9 16:52
 */
public class Field {

    /**
     * @Description: 字段名称
     * @author makn
     * @date 2020/12/9 16:57
     * @param null
     * @return
     */
    private String name;

    /**
     * @Description: 字段描述
     * @author makn
     * @date 2020/12/9 16:57
     * @param null
     * @return
     */
    private String desc;

    /**
     * @Description: 字段长度
     * @author makn
     * @date 2020/12/9 16:57
     * @param null
     * @return
     */
    private String length;

    /**
     * @Description: 是否字段两端空
     * @author makn
     * @date 2020/12/9 16:58
     * @param null
     * @return
     */
    private String toEmpty;

    /**
     * @Description: 字段类型，默认String
     * @author makn
     * @date 2020/12/9 16:58
     * @param null
     * @return
     */
    private String type = "String";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getLength() {
        return Integer.parseInt(length);
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getToEmpty() {
        return toEmpty;
    }

    public void setToEmpty(String toEmpty) {
        this.toEmpty = toEmpty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
