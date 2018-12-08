package base.jsignal;

import java.lang.reflect.Method;

interface ISlot {

    Object getListener();

    Method getDelegate();

    boolean getAddOnce();
}
