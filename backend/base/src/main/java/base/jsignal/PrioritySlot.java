package base.jsignal;

import java.lang.reflect.Method;

/**
 * @author sh
 * @param <E>
 */
final class PrioritySlot<E extends Comparable<E>> extends Slot implements Comparable<PrioritySlot<E>> {
    private final E priority;

    /**
     *
     * @param listener
     * @param delegate
     * @param addOnce
     * @param priority
     */
    public PrioritySlot(Object listener, Method delegate, boolean addOnce, E priority) {
        super(listener, delegate, addOnce);
        this.priority = priority;
    }

    /**
     *
     * @return
     */
    public E getPriority() {
        return priority;
    }

    /**
     *
     * @param arg0
     * @return
     */
    @Override
    public int compareTo(PrioritySlot<E> arg0) {
        if (priority == null) {
            return -1;
        }
        return priority.compareTo(arg0.getPriority());
    }
}
