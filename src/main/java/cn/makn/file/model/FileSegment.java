package cn.makn.file.model;

import java.util.List;

/**
 * @Description: 段落信息, 段行数, 段偏移量
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/25 11:28
 */
public class FileSegment {

    /**
     * @Description: 段落信息
     * @author makn
     * @date 2020/12/25 11:31
     * @param null
     * @return
     */
    private List<FileParagraph> fileParagraphs;

    /**
     * @Description: 总行数
     * @author makn
     * @date 2020/12/25 11:32
     * @param null
     * @return
     */
    private long count;

    public List<FileParagraph> getFileParagraphs() {
        return fileParagraphs;
    }

    public void setFileParagraphs(List<FileParagraph> fileParagraphs) {
        this.fileParagraphs = fileParagraphs;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
