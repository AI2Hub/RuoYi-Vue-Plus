package org.dromara.common.core.domain.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 授权管理对象
 *
 * @author Michelle.Chung
 */
@Data
public class AuthBody implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 客户端key
     */
    private String clientKey;

    /**
     * 客户端秘钥
     */
    private String clientSecret;

    /**
     * 授权类型
     */
    private String authType;

}
