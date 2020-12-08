package cn.makn.file.model;

/**
 * @Description: 文件段落
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/8 10:52
 */
public class FileParagraph {

    public FileParagraph(int num, long pos) {
        this.num = num;
        this.pos = pos;
    }

    /**
     * @Description: 段落总行数
     * @author makn
     * @date 2020/12/8 10:53
     * @param null
     * @return
     */
    private int num;

    /**
     * @Description: 段落总偏移量
     * @author makn
     * @date 2020/12/8 10:54
     * @param null
     * @return
     */
    private long pos;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getPos() {
        return pos;
    }

    public void setPos(long pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "FileParagraph{" +
                "num=" + num +
                ", pos=" + pos +
                '}';
    }
}
