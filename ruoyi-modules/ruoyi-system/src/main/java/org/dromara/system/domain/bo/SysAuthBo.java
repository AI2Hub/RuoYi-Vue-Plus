package org.dromara.system.domain.bo;

import org.dromara.system.domain.SysAuth;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 授权管理业务对象 sys_auth
 *
 * @author Michelle.Chung
 * @date 2023-05-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysAuth.class, reverseConvertGenerate = false)
public class SysAuthBo extends BaseEntity {

    /**
     * id
     */
    @NotNull(message = "id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 客户端key
     */
    @NotBlank(message = "客户端key不能为空", groups = { AddGroup.class, EditGroup.class })
    private String clientKey;

    /**
     * 客户端秘钥
     */
    @NotBlank(message = "客户端秘钥不能为空", groups = { AddGroup.class, EditGroup.class })
    private String clientSecret;

    /**
     * 授权类型
     */
    @NotBlank(message = "授权类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String authType;

    /**
     * 状态（0正常 1停用）
     */
    private String status;


}
