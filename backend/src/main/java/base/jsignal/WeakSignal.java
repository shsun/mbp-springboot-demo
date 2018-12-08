package base.jsignal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author sh
 *
 * The WeakSignal class is functionally the same as the Signal class, however,
 * it maintains weak references to its listeners that are automatically cleaned up.
 * It is useful for memory sensitive systems where Signal's can't be responsible
 * for removing their listeners.
 * <p>
 * This class logs to the </code>"com.paulm.jsignal"</code> Logger potential problems.
 * <p>
 * This is a port of Robert Penner's Signals for ActionScript 3.0
 *
 * @see Signal
 */
final class WeakSignal extends Signal {

    /**
     * @param listener the listener object to add
     * @param callback the callback method, as a String, to invoke when this signal is dispatched
     * @param addOnce  if true, once this signal has dispatched the listener is removed from the listener map
     * @return
     */
    @Override
    public Object add(Object listener, String callback, boolean addOnce) {
        Method delegate;
        try {
            delegate = listener.getClass().getMethod(callback, params);
        } catch (SecurityException e) {
            throw new SignalException("Could not access method `" + listener.getClass().getName() + "." + callback + "`", e);
        } catch (NoSuchMethodException e) {
            throw new SignalException("Could not find method `" + listener.getClass().getName() + "." + callback + "`", e);
        }
        ISlot previous = listenerMap.put(listener, new WeakSlot(listener, delegate, addOnce));
        return previous == null ? null : previous.getListener();
    }

    /**
     * @param args the argument list to dispatch to listenerMap.  The argument
     *             list must have the same signature as the parameter list this Signal
     *             was constructed with
     */
    @Override
    public void dispatch(Object... args) {
        Iterator<ISlot> iterator = listenerMap.values().iterator();
        while (iterator.hasNext()) {
            ISlot slot = iterator.next();
            Object listener;
            try {
                listener = slot.getListener();
                if (listener != null) {
                    slot.getDelegate().invoke(listener, args);
                } else {
                    iterator.remove();
                }
            } catch (IllegalArgumentException e) {
                throw new SignalException("Method " + slot.getDelegate() + " received an invalid argument " + Arrays.deepToString(args), e);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new SignalException("Could not invoke method " + slot.getDelegate(), e);
            }
            if (slot.getAddOnce() && listener != null) {
                iterator.remove();
            }
        }
    }
}
