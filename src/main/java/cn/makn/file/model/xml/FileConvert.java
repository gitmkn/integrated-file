package cn.makn.file.model.xml;

/**
 * @Description: xml对应bean
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/9 16:20
 */
public class FileConvert {

    /**
     * @Description: 文件名称，不可重复
     * @author makn
     * @date 2020/12/9 16:23
     * @param null
     * @return
     */
    private String name;

    /**
     * @Description: 文件名称描述
     * @author makn
     * @date 2020/12/9 17:09
     * @param null
     * @return
     */
    private String desc;

    /**
     * @Description: 文件类型，TXT、EXCEL
     * @author makn
     * @date 2020/12/9 16:24
     * @param null
     * @return
     */
    private String type;

    /**
     * @Description: 文件字符编码，默认UTF-8
     * @author makn
     * @date 2020/12/9 16:24
     * @param null
     * @return
     */
    private String encoding = "UTF-8";

    /**
     * @Description: 换行符，默认\r\n 换行
     * @author makn
     * @date 2020/12/9 16:24
     * @param null
     * @return
     */
    private String br = "\r\n";

    /**
     * @Description: 文件头
     * @author makn
     * @date 2020/12/9 16:25
     * @param null
     * @return
     */
    private FileRow head;

    /**
     * @Description: 文件体
     * @author makn
     * @date 2020/12/9 16:25
     * @param null
     * @return
     */
    private FileRow body;

    /**
     * @Description: 文件尾部
     * @author makn
     * @date 2020/12/9 17:13
     * @param null
     * @return
     */
    private FileRow tail;

    /**
     * @Description: EXCEL页签名称
     * @author makn
     * @date 2020/12/9 17:15
     * @param null
     * @return
     */
    private String sheetName;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getBr() {
        return br;
    }

    public void setBr(String br) {
        this.br = br;
    }

    public FileRow getHead() {
        return head;
    }

    public void setHead(FileRow head) {
        this.head = head;
    }

    public FileRow getBody() {
        return body;
    }

    public void setBody(FileRow body) {
        this.body = body;
    }

    public FileRow getTail() {
        return tail;
    }

    public void setTail(FileRow tail) {
        this.tail = tail;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    @Override
    public String toString() {
        return "FileConvert{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", type='" + type + '\'' +
                ", encoding='" + encoding + '\'' +
                ", br='" + br + '\'' +
                ", head=" + head +
                ", body=" + body +
                ", tail=" + tail +
                ", sheetName='" + sheetName + '\'' +
                '}';
    }
}
