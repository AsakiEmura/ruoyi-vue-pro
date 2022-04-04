package cn.emura.asaki.module.itaxbill.service.payable;

import cn.emura.asaki.module.itaxbill.controller.admin.payable.vo.PayableCreateReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.payable.vo.PayableExportReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.payable.vo.PayablePageReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.payable.vo.PayableUpdateReqVO;
import cn.emura.asaki.module.itaxbill.convert.payable.PayableConvert;
import cn.emura.asaki.module.itaxbill.dal.dataobject.payable.PayableDO;
import cn.emura.asaki.module.itaxbill.dal.mysql.payable.PayableMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.emura.asaki.module.itaxbill.enums.ItaxbillErrorCode.*;

/**
 * 发票抬头 Service 实现类
 *
 * @author Asaki
 */
@Service
@Validated
public class PayableServiceImpl implements PayableService {

    @Resource
    private PayableMapper payableMapper;

    @Override
    public Long createPayable(PayableCreateReqVO createReqVO) {
        // 插入
        PayableDO payable = PayableConvert.INSTANCE.convert(createReqVO);
        payableMapper.insert(payable);
        // 返回
        return payable.getId();
    }

    @Override
    public void updatePayable(PayableUpdateReqVO updateReqVO) {
        // 校验存在
        this.validatePayableExists(updateReqVO.getId());
        // 更新
        PayableDO updateObj = PayableConvert.INSTANCE.convert(updateReqVO);
        payableMapper.updateById(updateObj);
    }

    @Override
    public void deletePayable(Long id) {
        // 校验存在
        this.validatePayableExists(id);
        // 删除
        payableMapper.deleteById(id);
    }

    private void validatePayableExists(Long id) {
        if (payableMapper.selectById(id) == null) {
            throw exception(PAYABLE_NOT_EXISTS);
        }
    }

    @Override
    public PayableDO getPayable(Long id) {
        return payableMapper.selectById(id);
    }

    @Override
    public List<PayableDO> getPayableList(Collection<Long> ids) {
        return payableMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<PayableDO> getPayablePage(PayablePageReqVO pageReqVO) {
        return payableMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PayableDO> getPayableList(PayableExportReqVO exportReqVO) {
        return payableMapper.selectList(exportReqVO);
    }

}
