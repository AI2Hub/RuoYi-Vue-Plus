package org.dromara.common.core.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 小程序登录对象
 *
 * @author Michelle.Chung
 */
@Data
public class XcxLoginBody {

    @NotBlank(message = "{xcx.code.not.blank}")
    private String xcxCode;

}
