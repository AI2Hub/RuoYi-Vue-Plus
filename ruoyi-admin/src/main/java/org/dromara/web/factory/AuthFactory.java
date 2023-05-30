package org.dromara.web.factory;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.web.enumd.AuthTypeEnumd;
import org.dromara.web.service.IAuthStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 授权 Factory
 *
 * @author Michelle.Chung
 */
@Slf4j
public class AuthFactory {

    private static final Map<String, IAuthStrategy> AUTH_STRATEGY_MAP = new ConcurrentHashMap<>();

    /**
     * 实例化
     *
     * @param authType 认证类型
     */
    public static IAuthStrategy instance(String authType) {
        IAuthStrategy authStrategy = AUTH_STRATEGY_MAP.get(authType);
        if (ObjectUtil.isNull(authStrategy)) {
            authStrategy = (IAuthStrategy) SpringUtils.getBean(AuthTypeEnumd.getStrategyClass(authType));
            AUTH_STRATEGY_MAP.put(authType, authStrategy);
        }
        return authStrategy;
    }

}
