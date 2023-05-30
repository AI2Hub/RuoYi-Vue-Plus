package org.dromara.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import org.dromara.common.core.domain.model.AuthBody;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.exception.user.AuthTypeErrorException;
import org.dromara.common.core.service.AuthService;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.system.domain.bo.SysAuthBo;
import org.dromara.system.domain.vo.SysAuthVo;
import org.dromara.system.domain.SysAuth;
import org.dromara.system.mapper.SysAuthMapper;
import org.dromara.system.service.ISysAuthService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 授权管理Service业务层处理
 *
 * @author Michelle.Chung
 * @date 2023-05-15
 */
@RequiredArgsConstructor
@Service
public class SysAuthServiceImpl implements ISysAuthService, AuthService {

    private final SysAuthMapper baseMapper;

    /**
     * 查询授权管理
     */
    @Override
    public SysAuthVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询授权管理列表
     */
    @Override
    public TableDataInfo<SysAuthVo> queryPageList(SysAuthBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysAuth> lqw = buildQueryWrapper(bo);
        Page<SysAuthVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询授权管理列表
     */
    @Override
    public List<SysAuthVo> queryList(SysAuthBo bo) {
        LambdaQueryWrapper<SysAuth> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysAuth> buildQueryWrapper(SysAuthBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysAuth> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getClientId()), SysAuth::getClientId, bo.getClientId());
        lqw.eq(StringUtils.isNotBlank(bo.getClientKey()), SysAuth::getClientKey, bo.getClientKey());
        lqw.eq(StringUtils.isNotBlank(bo.getClientSecret()), SysAuth::getClientSecret, bo.getClientSecret());
        lqw.eq(StringUtils.isNotBlank(bo.getAuthType()), SysAuth::getAuthType, bo.getAuthType());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysAuth::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增授权管理
     */
    @Override
    public Boolean insertByBo(SysAuthBo bo) {
        SysAuth add = MapstructUtils.convert(bo, SysAuth.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改授权管理
     */
    @Override
    public Boolean updateByBo(SysAuthBo bo) {
        SysAuth update = MapstructUtils.convert(bo, SysAuth.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysAuth entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除授权管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public AuthBody getAuthBody(String clientId) {
        SysAuth sysAuth = baseMapper.selectOne(
            new LambdaQueryWrapper<SysAuth>().eq(SysAuth::getClientId, clientId));
        if (ObjectUtil.isEmpty(sysAuth)) {
            throw new ServiceException("clientId 错误");
        }
        return BeanUtil.toBean(sysAuth, AuthBody.class);
    }
}
