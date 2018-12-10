package com.as.base.jsignal;

import java.lang.reflect.Method;

/**
 * @author sh
 */
interface ISlot {

    /**
     * observer
     *
     * @return
     */
    Object getListener();

    /**
     * method would be fired
     *
     * @return
     */
    Method getDelegate();

    /**
     * true indicate addOnce, otherwise not.
     *
     * @return
     */
    boolean getAddOnce();
}
