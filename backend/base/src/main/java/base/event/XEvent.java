package base.event;

import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

public class XEvent<T_PUBLISHER, T_DATA> implements ResolvableTypeProvider {

    private final T_PUBLISHER publisher;
    private final String eventType;

    private T_DATA data;

    /**
     * @param publisher who
     * @param eventType how
     * @param data      what
     * @see XEventType
     */
    public XEvent(T_PUBLISHER publisher, final String eventType, T_DATA data) {
        this.publisher = publisher;
        this.eventType = eventType;
        this.setData(data);
    }

    public T_PUBLISHER getPublisher() {
        return this.publisher;
    }

    public String getEventType() {
        return eventType;
    }

    public T_DATA getData() {
        return this.data;
    }

    public void setData(T_DATA data) {
        this.data = data;
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(this.getData()));
    }

}