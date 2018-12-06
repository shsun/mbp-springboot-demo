package com.as.cyems;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.longge.utils.Constant;
import com.longge.utils.RedisCacheUtils;

/**
 * 獲取用户的session dto
 */
public class LoginUserInfoMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public Object resolveArgument(MethodParameter arg0, ModelAndViewContainer arg1, NativeWebRequest arg2,
                                  WebDataBinderFactory arg3) throws Exception {
        String token = arg2.getHeader(Constant.TOKEN_KEY);
        if(StringUtils.isNotBlank(token)) {
            return RedisCacheUtils.getUserInfo(token);
        }
        return null;
    }

    @Override
    public boolean supportsParameter(MethodParameter arg0) {
        return arg0.getParameterType().equals(UserInfo.class);
    }

}
