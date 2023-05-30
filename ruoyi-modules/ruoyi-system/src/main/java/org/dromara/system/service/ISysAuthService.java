package org.dromara.system.service;

import org.dromara.system.domain.vo.SysAuthVo;
import org.dromara.system.domain.bo.SysAuthBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 授权管理Service接口
 *
 * @author Michelle.Chung
 * @date 2023-05-15
 */
public interface ISysAuthService {

    /**
     * 查询授权管理
     */
    SysAuthVo queryById(Long id);

    /**
     * 查询授权管理列表
     */
    TableDataInfo<SysAuthVo> queryPageList(SysAuthBo bo, PageQuery pageQuery);

    /**
     * 查询授权管理列表
     */
    List<SysAuthVo> queryList(SysAuthBo bo);

    /**
     * 新增授权管理
     */
    Boolean insertByBo(SysAuthBo bo);

    /**
     * 修改授权管理
     */
    Boolean updateByBo(SysAuthBo bo);

    /**
     * 校验并批量删除授权管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
