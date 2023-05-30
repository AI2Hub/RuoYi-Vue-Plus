package org.dromara.system.controller.system;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.system.domain.vo.SysAuthVo;
import org.dromara.system.domain.bo.SysAuthBo;
import org.dromara.system.service.ISysAuthService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 授权管理
 *
 * @author Michelle.Chung
 * @date 2023-05-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/auth")
public class SysAuthController extends BaseController {

    private final ISysAuthService sysAuthService;

    /**
     * 查询授权管理列表
     */
    @SaCheckPermission("system:auth:list")
    @GetMapping("/list")
    public TableDataInfo<SysAuthVo> list(SysAuthBo bo, PageQuery pageQuery) {
        return sysAuthService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出授权管理列表
     */
    @SaCheckPermission("system:auth:export")
    @Log(title = "授权管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysAuthBo bo, HttpServletResponse response) {
        List<SysAuthVo> list = sysAuthService.queryList(bo);
        ExcelUtil.exportExcel(list, "授权管理", SysAuthVo.class, response);
    }

    /**
     * 获取授权管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:auth:query")
    @GetMapping("/{id}")
    public R<SysAuthVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(sysAuthService.queryById(id));
    }

    /**
     * 新增授权管理
     */
    @SaCheckPermission("system:auth:add")
    @Log(title = "授权管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysAuthBo bo) {
        return toAjax(sysAuthService.insertByBo(bo));
    }

    /**
     * 修改授权管理
     */
    @SaCheckPermission("system:auth:edit")
    @Log(title = "授权管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysAuthBo bo) {
        return toAjax(sysAuthService.updateByBo(bo));
    }

    /**
     * 删除授权管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:auth:remove")
    @Log(title = "授权管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(sysAuthService.deleteWithValidByIds(List.of(ids), true));
    }
}
