package cn.emura.asaki.module.itaxbill.service.invoice;

import cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo.InvoiceCreateReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo.InvoiceExportReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo.InvoicePageReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo.InvoiceUpdateReqVO;
import cn.emura.asaki.module.itaxbill.convert.invoice.InvoiceConvert;
import cn.emura.asaki.module.itaxbill.dal.dataobject.invoice.InvoiceDO;
import cn.emura.asaki.module.itaxbill.dal.mysql.invoice.InvoiceMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.emura.asaki.module.itaxbill.enums.ItaxbillErrorCode.INVOICE_NOT_EXISTS;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.emura.asaki.module.itaxbill.enums.ItaxbillErrorCode.INVOICE_NOT_EXISTS;

/**
 * 发票信息 Service 实现类
 *
 * @author Asaki
 */
@Service
@Validated
public class InvoiceServiceImpl implements InvoiceService {

    @Resource
    private InvoiceMapper invoiceMapper;

    @Override
    public Long createInvoice(InvoiceCreateReqVO createReqVO) {
        // 插入
        InvoiceDO invoice = InvoiceConvert.INSTANCE.convert(createReqVO);
        invoiceMapper.insert(invoice);
        // 返回
        return invoice.getId();
    }

    @Override
    public void updateInvoice(InvoiceUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateInvoiceExists(updateReqVO.getId());
        // 更新
        InvoiceDO updateObj = InvoiceConvert.INSTANCE.convert(updateReqVO);
        invoiceMapper.updateById(updateObj);
    }

    @Override
    public void deleteInvoice(Long id) {
        // 校验存在
        this.validateInvoiceExists(id);
        // 删除
        invoiceMapper.deleteById(id);
    }

    private void validateInvoiceExists(Long id) {
        if (invoiceMapper.selectById(id) == null) {
            throw exception(INVOICE_NOT_EXISTS);
        }
    }

    @Override
    public InvoiceDO getInvoice(Long id) {
        return invoiceMapper.selectById(id);
    }

    @Override
    public List<InvoiceDO> getInvoiceList(Collection<Long> ids) {
        return invoiceMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<InvoiceDO> getInvoicePage(InvoicePageReqVO pageReqVO) {
        return invoiceMapper.selectPage(pageReqVO);
    }

    @Override
    public List<InvoiceDO> getInvoiceList(InvoiceExportReqVO exportReqVO) {
        return invoiceMapper.selectList(exportReqVO);
    }

}
