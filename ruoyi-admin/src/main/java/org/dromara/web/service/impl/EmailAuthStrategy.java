package org.dromara.web.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.constant.Constants;
import org.dromara.common.core.constant.GlobalConstants;
import org.dromara.common.core.domain.model.EmailLoginBody;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.enums.DeviceType;
import org.dromara.common.core.enums.LoginType;
import org.dromara.common.core.enums.UserStatus;
import org.dromara.common.core.exception.user.CaptchaExpireException;
import org.dromara.common.core.exception.user.UserException;
import org.dromara.common.core.utils.MessageUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.common.tenant.helper.TenantHelper;
import org.dromara.system.domain.SysUser;
import org.dromara.system.domain.vo.SysUserVo;
import org.dromara.system.mapper.SysUserMapper;
import org.dromara.web.service.IAuthStrategy;
import org.dromara.web.service.SysLoginService;
import org.springframework.stereotype.Service;

/**
 * 邮件认证策略
 *
 * @author Michelle.Chung
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailAuthStrategy implements IAuthStrategy<EmailLoginBody> {

    private final SysLoginService loginService;
    private final SysUserMapper userMapper;

    @Override
    public Object getLoginBody(Object loginBody) {
        return BeanUtil.toBean(loginBody, EmailLoginBody.class);
    }

    @Override
    public String login(String clientId, EmailLoginBody loginBody) {
        String tenantId = loginBody.getTenantId();
        String email = loginBody.getEmail();
        String emailCode = loginBody.getEmailCode();

        // 校验租户
        loginService.checkTenant(tenantId);
        // 通过邮箱查找用户
        SysUserVo user = loadUserByEmail(tenantId, email);

        loginService.checkLogin(LoginType.EMAIL, tenantId, user.getUserName(), () -> !validateEmailCode(tenantId, email, emailCode));
        // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
        LoginUser loginUser = loginService.buildLoginUser(user);
        // 生成token
        LoginHelper.loginByDevice(loginUser, DeviceType.APP);

        loginService.recordLogininfor(loginUser.getTenantId(), user.getUserName(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
        loginService.recordLoginInfo(user.getUserId());
        return StpUtil.getTokenValue();
    }

    /**
     * 校验邮箱验证码
     */
    private boolean validateEmailCode(String tenantId, String email, String emailCode) {
        String code = RedisUtils.getCacheObject(GlobalConstants.CAPTCHA_CODE_KEY + email);
        if (StringUtils.isBlank(code)) {
            loginService.recordLogininfor(tenantId, email, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        return code.equals(emailCode);
    }

    private SysUserVo loadUserByEmail(String tenantId, String email) {
        SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
            .select(SysUser::getPhonenumber, SysUser::getStatus)
            .eq(TenantHelper.isEnable(), SysUser::getTenantId, tenantId)
            .eq(SysUser::getEmail, email));
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", email);
            throw new UserException("user.not.exists", email);
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", email);
            throw new UserException("user.blocked", email);
        }
        if (TenantHelper.isEnable()) {
            return userMapper.selectTenantUserByEmail(email, tenantId);
        }
        return userMapper.selectUserByEmail(email);
    }

}
