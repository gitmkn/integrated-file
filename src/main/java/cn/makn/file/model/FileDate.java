package cn.makn.file.model;

/**
 * @Description:
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/7 11:20
 */
public class FileDate<H, B, T> {

    /**
     * @Description: 文件数据头部
     * @author makn
     * @date 2020/12/7 12:05
     * @param null
     * @return
     */
    private H headDate;

    /**
     * @Description: 文件数据体
     * @author makn
     * @date 2020/12/7 12:06
     * @param null
     * @return
     */
    private B bodyDate;

    /**
     * @Description: 文件尾部
     * @author makn
     * @date 2020/12/7 12:06
     * @param null
     * @return
     */
    private T tailDate;

    public H getHeadDate() {
        return headDate;
    }

    public void setHeadDate(H headDate) {
        this.headDate = headDate;
    }

    public B getBodyDate() {
        return bodyDate;
    }

    public void setBodyDate(B bodyDate) {
        this.bodyDate = bodyDate;
    }

    public T getTailDate() {
        return tailDate;
    }

    public void setTailDate(T tailDate) {
        this.tailDate = tailDate;
    }
}
