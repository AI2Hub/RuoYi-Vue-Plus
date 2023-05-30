package org.dromara.web.enumd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.dromara.web.service.impl.EmailAuthStrategy;
import org.dromara.web.service.impl.PasswordAuthStrategy;
import org.dromara.web.service.impl.SmsAuthStrategy;
import org.dromara.web.service.impl.XcxAuthStrategy;

/**
 * 授权类型枚举
 *
 * @author Michelle.Chung
 */
@Getter
@AllArgsConstructor
public enum AuthTypeEnumd {

    /**
     * 密码认证
     */
    PASSWORD("password", PasswordAuthStrategy.class),

    /**
     * 短信认证
     */
    SMS("sms", SmsAuthStrategy.class),

    /**
     * 邮件认证
     */
    EMAIL("email", EmailAuthStrategy.class),

    /**
     * 邮件认证
     */
    XCX("xcx", XcxAuthStrategy.class)
    ;

    private final String type;

    private final Class<?> strategyClass;

    public static Class<?> getStrategyClass(String type) {
        for (AuthTypeEnumd clazz : values()) {
            if (clazz.getType().equals(type)) {
                return clazz.getStrategyClass();
            }
        }
        return null;
    }

}
