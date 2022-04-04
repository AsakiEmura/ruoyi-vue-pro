package cn.emura.asaki.module.itaxbill.service.reimburse;

import cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo.ReimburseCreateReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo.ReimburseExportReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo.ReimbursePageReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo.ReimburseUpdateReqVO;
import cn.emura.asaki.module.itaxbill.convert.reimburse.ReimburseConvert;
import cn.emura.asaki.module.itaxbill.dal.dataobject.reimburse.ReimburseDO;
import cn.emura.asaki.module.itaxbill.dal.mysql.reimburse.ReimburseMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.emura.asaki.module.itaxbill.enums.ItaxbillErrorCode.REIMBURSE_NOT_EXISTS;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 发票报销信息 Service 实现类
 *
 * @author Asaki
 */
@Service
@Validated
public class ReimburseServiceImpl implements ReimburseService {

    @Resource
    private ReimburseMapper reimburseMapper;

    @Override
    public Long createReimburse(ReimburseCreateReqVO createReqVO) {
        // 插入
        ReimburseDO reimburse = ReimburseConvert.INSTANCE.convert(createReqVO);
        reimburseMapper.insert(reimburse);
        // 返回
        return reimburse.getId();
    }

    @Override
    public void updateReimburse(ReimburseUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateReimburseExists(updateReqVO.getId());
        // 更新
        ReimburseDO updateObj = ReimburseConvert.INSTANCE.convert(updateReqVO);
        reimburseMapper.updateById(updateObj);
    }

    @Override
    public void deleteReimburse(Long id) {
        // 校验存在
        this.validateReimburseExists(id);
        // 删除
        reimburseMapper.deleteById(id);
    }

    private void validateReimburseExists(Long id) {
        if (reimburseMapper.selectById(id) == null) {
            throw exception(REIMBURSE_NOT_EXISTS);
        }
    }

    @Override
    public ReimburseDO getReimburse(Long id) {
        return reimburseMapper.selectById(id);
    }

    @Override
    public List<ReimburseDO> getReimburseList(Collection<Long> ids) {
        return reimburseMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ReimburseDO> getReimbursePage(ReimbursePageReqVO pageReqVO) {
        return reimburseMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ReimburseDO> getReimburseList(ReimburseExportReqVO exportReqVO) {
        return reimburseMapper.selectList(exportReqVO);
    }

}
