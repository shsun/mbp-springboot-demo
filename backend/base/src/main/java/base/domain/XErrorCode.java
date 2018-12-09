package base.domain;

import com.baomidou.mybatisplus.extension.api.IErrorCode;

public enum XErrorCode implements IErrorCode {
    TEST(1000, "测试错误编码");

    private long code;
    private String msg;

    XErrorCode(final long code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
