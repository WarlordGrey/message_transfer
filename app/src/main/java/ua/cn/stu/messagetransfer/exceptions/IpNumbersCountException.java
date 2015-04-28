package ua.cn.stu.messagetransfer.exceptions;

/**
 * Created by WarlordGrey on 23.04.2015.
 */
public class IpNumbersCountException extends Exception{
    /**
     * Constructs a new {@code Exception} that includes the current stack trace.
     */
    public IpNumbersCountException() {
    }

    /**
     * Constructs a new {@code Exception} with the current stack trace and the
     * specified detail message.
     *
     * @param detailMessage the detail message for this exception.
     */
    public IpNumbersCountException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@code Exception} with the current stack trace, the
     * specified detail message and the specified cause.
     *
     * @param detailMessage the detail message for this exception.
     * @param throwable
     */
    public IpNumbersCountException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    /**
     * Constructs a new {@code Exception} with the current stack trace and the
     * specified cause.
     *
     * @param throwable the cause of this exception.
     */
    public IpNumbersCountException(Throwable throwable) {
        super(throwable);
    }
}
