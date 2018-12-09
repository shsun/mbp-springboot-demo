package base;

import com.google.common.base.Strings;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_UNDERSCORE;

/**
 * 解析排序.
 *
 * @author richard
 */

public class JqueryStyleSortArgumentResolver implements SortArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Sort.class.equals(parameter.getParameterType());
    }

    @Override
    public Sort resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        List<Sort.Order> allOrders = new ArrayList<>();
        for (int i = 0; ; i++) {
            String fieldName = getSortFieldParameterName(i);
            String field = webRequest.getParameter(fieldName);
            if (Strings.isNullOrEmpty(field)) break;

            String dir = webRequest.getParameter(getSortDirParameterName(i));
            if (Strings.isNullOrEmpty(dir)) break;

            String upperField = LOWER_CAMEL.to(UPPER_UNDERSCORE, field);
            allOrders.add(new Sort.Order(Sort.Direction.fromString(dir), upperField));
        }
        return allOrders.isEmpty() ? null : new Sort(allOrders);
    }

    private String getSortFieldParameterName(int i) {
        return String.format("sort[%d][field]", i);
    }

    private String getSortDirParameterName(int i) {
        return String.format("sort[%d][dir]", i);
    }
}

