package com.as.base.event;

import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

/**
 * 实体被更新.
 *
 * @author liuxing
 */

public class UpdatedEvent<T> implements ResolvableTypeProvider {
    private static final long serialVersionUID = 7099057708107052015L;

    private final T source;

    public UpdatedEvent(T fileAttachment) {

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
