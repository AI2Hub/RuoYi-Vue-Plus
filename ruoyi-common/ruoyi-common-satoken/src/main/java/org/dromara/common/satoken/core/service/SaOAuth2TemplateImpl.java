package org.dromara.common.satoken.core.service;

import cn.dev33.satoken.oauth2.logic.SaOAuth2Template;
import cn.dev33.satoken.oauth2.model.SaClientModel;
import cn.dev33.satoken.stp.StpUtil;
import org.dromara.common.core.domain.model.AuthBody;
import org.dromara.common.core.service.AuthService;
import org.dromara.common.core.utils.SpringUtils;
import org.springframework.stereotype.Component;

/**
 * Sa-Token-OAuth2 模块实现类
 *
 * @author Michelle.Chung
 */
@Component
public class SaOAuth2TemplateImpl extends SaOAuth2Template {

    /**
     * 重写 Access-Token 生成策略：复用登录会话的Token
     *
     * @param clientId 应用id
     * @param loginId  账号id
     * @param scope    权限
     */
    @Override
    public String randomAccessToken(String clientId, Object loginId, String scope) {
        String tokenValue = StpUtil.createLoginSession(loginId);
        return tokenValue;
    }

    /**
     * 根据 id 获取 Client 信息
     *
     * @param clientId 应用id
     */
    @Override
    public SaClientModel getClientModel(String clientId) {
        AuthService authService = SpringUtils.getBean(AuthService.class);
        AuthBody authBody = authService.getAuthBody(clientId);
        return new SaClientModel()
            .setClientId(clientId)
            .setClientSecret(authBody.getClientSecret())
            .setAllowUrl("*")
            .setIsPassword(true);
    }

    /**
     * 根据ClientId 和 LoginId 获取openid
     *
     * @param clientId 应用id
     * @param loginId  账号id
     */
    @Override
    public String getOpenid(String clientId, Object loginId) {
        // 此为模拟数据，真实环境需要从数据库查询
        return "gr_SwoIN0MC1ewxHX_vfCW3BothWDZMMtx__";
    }

}
