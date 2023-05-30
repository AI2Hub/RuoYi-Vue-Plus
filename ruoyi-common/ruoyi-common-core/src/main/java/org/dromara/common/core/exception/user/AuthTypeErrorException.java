package org.dromara.common.core.exception.user;

import java.io.Serial;

/**
 * 认证权限类型异常类
 *
 * @author Michelle.Chung
 */
public class AuthTypeErrorException extends UserException {

    @Serial
    private static final long serialVersionUID = 1L;

    public AuthTypeErrorException() {
        super("auth.type.error");
    }

}
