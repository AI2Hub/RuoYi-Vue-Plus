package org.dromara.common.core.service;

import org.dromara.common.core.domain.model.AuthBody;

/**
 * 通用 认证服务
 *
 * @author Michelle.Chung
 */
public interface AuthService {

    /**
     * 获取认证授权对象
     *
     * @param clientId 客户端id
     */
    AuthBody getAuthBody(String clientId);

}
