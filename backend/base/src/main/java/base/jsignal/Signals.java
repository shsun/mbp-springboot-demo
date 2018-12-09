package base.jsignal;

/**
 * @author sh
 *
 * Utility class which contains static methods to operate on signal instances.
 * Notably, it can create wrappers to existing signals to add functionality.
 *
 *
 */
public class Signals {

    private Signals() {
    }

    /**
     * Creates a synchronized signal from the given signal.
     * The resultant signal synchronizes on itself.
     *
     * @param signal The unsynchronized signal to make thread safe
     * @return A synchronized signal that wraps the original signal
     */
    public static ISignal synchronizedSignal(ISignal signal) {
        return new SynchronizedSignal(signal);
    }

    /**
     * @see Signals#synchronizedSignal(ISignal)
     */
    public static ISignalOwner synchronizedSignal(ISignalOwner signal) {
        return new SynchronizedSignal(signal);
    }
}

/**
 * @author sh
 */
class SynchronizedSignal implements ISignalOwner {
    /**
     *
     */
    private final ISignal signal;

    /**
     * @param signal
     */
    public SynchronizedSignal(ISignal signal) {
        this.signal = signal;
    }

    /**
     * @param listener the listener object
     * @param callback the callback method
     * @param addOnce  if true, once this signal has dispatched the listener will be unregistered to this signal
     * @return
     */
    @Override
    public synchronized Object add(Object listener, String callback, boolean addOnce) {
        return signal.add(listener, callback, addOnce);
    }

    /**
     * @param listener the listener to remove
     * @return
     */
    @Override
    public synchronized boolean remove(Object listener) {
        return signal.remove(listener);
    }


    /**
     * @param listener the listener to check for
     * @return
     */
    @Override
    public synchronized boolean containsListener(Object listener) {
        return signal.containsListener(listener);
    }

    /**
     * @return
     */
    @Override
    public synchronized int numListeners() {
        return signal.numListeners();
    }

    /**
     *
     */
    @Override
    public synchronized void removeAll() {
        ((ISignalOwner) signal).removeAll();
    }

    /**
     * @param args the arguments to dispatch with
     */
    @Override
    public synchronized void dispatch(Object... args) {
        ((IDispatcher) signal).dispatch(args);
    }
}
