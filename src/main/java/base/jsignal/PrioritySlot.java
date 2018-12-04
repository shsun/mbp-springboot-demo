// http://paulmoore.mit-license.org/

package base.jsignal;

import java.lang.reflect.Method;

final class PrioritySlot <E extends Comparable<E>> extends Slot implements Comparable<PrioritySlot<E>> {
	private final E priority;
	
	public PrioritySlot(Object listener, Method delegate, boolean addOnce, E priority) {
		super(listener, delegate, addOnce);
		this.priority = priority;
	}
	
	public E getPriority() {
		return priority;
	}
	
	@Override
	public int compareTo(PrioritySlot<E> arg0) {
		if (priority == null) {
			return -1;
		}
		return priority.compareTo(arg0.getPriority());
	}
}
