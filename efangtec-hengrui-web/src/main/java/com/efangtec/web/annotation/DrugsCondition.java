package com.efangtec.web.annotation;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Slf4j
public class DrugsCondition implements RequestCondition<DrugsCondition> {

    /**
     * 接口路径中的药品名称，如: efangtec-aiduo
     */
    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile("^efangtec-.*");

    private String drugsType;

    public DrugsCondition(String drugsType) {
        this.drugsType = drugsType;
    }

    /**
     * 将不同的筛选条件合并ParamsRequestCondition
     * @param other
     * @return
     */
    @Override
    public DrugsCondition combine(DrugsCondition other) {
        // 最近优先原则，方法定义的 @ApiVersion > 类定义的 @ApiVersion
        return new DrugsCondition(other.getDrugsType());
    }

    /**
     * 根据request查找匹配到的筛选条件
     * @param request
     * @return
     */
    @Override
    public DrugsCondition getMatchingCondition(HttpServletRequest request) {
        Matcher m = VERSION_PREFIX_PATTERN.matcher(request.getHeader("type"));
        if (m.matches()) {
            // 获得符合匹配条件
            String type = m.group(0);
            if (type.equals(getDrugsType())) {
                return this;
            }

        }
        return null;
    }

    /**
     *  不同筛选条件比较,用于排序
     * @param other
     * @param request
     * @return
     */
    @Override
    public int compareTo(DrugsCondition other, HttpServletRequest request) {
        // 当出现多个符合匹配条件的ApiVersionCondition，优先匹配版本号较大的
        return 0;
    }

}