package com.as.base.event;

import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

/**
 * 实体被删除.
 *
 * @author richard
 */
public class RemovedEvent<T> implements ResolvableTypeProvider {

    private static final long serialVersionUID = 7099057708183171937L;

    private final T source;

    public RemovedEvent(T fileAttachment) {
        this.source = fileAttachment;
    }

    public T getSource() {
        return source;
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(source));
    }
}
