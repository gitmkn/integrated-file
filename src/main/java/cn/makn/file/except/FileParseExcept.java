package cn.makn.file.except;

import java.io.Serializable;

/**
 * @Description:
 * @author: makn
 * @version: V1.0
 * @date: 2020/12/12 9:27
 */
public class FileParseExcept extends RuntimeException implements Serializable {
    public FileParseExcept() {
        super();
    }

    public FileParseExcept(String message) {
        super(message);
    }

    public FileParseExcept(Throwable cause) {
        super(cause);
    }

    public FileParseExcept(String message, Throwable cause) {
        super(message, cause);
    }
}
