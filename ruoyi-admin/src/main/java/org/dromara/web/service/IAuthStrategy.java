package org.dromara.web.service;


/**
 * 授权策略
 *
 * @author Michelle.Chung
 */
public interface IAuthStrategy<T> {

    /**
     * 获取登录实体
     */
    Object getLoginBody(Object loginBody);

    /**
     * 登录
     */
    String login(String clientId, T loginBody);

}
