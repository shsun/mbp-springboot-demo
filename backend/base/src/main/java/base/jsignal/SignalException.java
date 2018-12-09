package base.jsignal;

/**
 * @author sh
 *
 * General exception for XSignals.
 */
public final class SignalException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     */
    protected SignalException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param inner
     */
    protected SignalException(String message, Exception inner) {
        super(message, inner);
    }
}
