package com.as.base.event;

import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

/**
 * 实体被生成.
 *
 * @author liuxing
 */

public class InsertedEvent<T> implements ResolvableTypeProvider {
    private static final long serialVersionUID = 7099057708107311983L;

    private final T source;

    public InsertedEvent(T fileAttachment) {
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
