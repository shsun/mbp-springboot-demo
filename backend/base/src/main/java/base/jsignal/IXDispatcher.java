package base.jsignal;

/**
 * Defines an interface for event dispatching types.
 *
 * @author sh
 */
public interface IXDispatcher {

    /**
     * Dispatches the arguments to all listeners registered to the dispatcher.
     *
     * @param args the arguments to dispatch with
     * @throws SignalException if a slot callback method could not be invoked
     */
    void dispatch(Object... args);
}
