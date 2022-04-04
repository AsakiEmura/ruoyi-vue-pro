package cn.emura.asaki.module.itaxbill.service.invoice;

import cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo.InvoiceCreateReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo.InvoiceExportReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo.InvoicePageReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo.InvoiceUpdateReqVO;
import cn.emura.asaki.module.itaxbill.dal.dataobject.invoice.InvoiceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;


import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 发票信息 Service 接口
 *
 * @author Asaki
 */
public interface InvoiceService {

    /**
     * 创建发票信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInvoice(@Valid InvoiceCreateReqVO createReqVO);

    /**
     * 更新发票信息
     *
     * @param updateReqVO 更新信息
     */
    void updateInvoice(@Valid InvoiceUpdateReqVO updateReqVO);

    /**
     * 删除发票信息
     *
     * @param id 编号
     */
    void deleteInvoice(Long id);

    /**
     * 获得发票信息
     *
     * @param id 编号
     * @return 发票信息
     */
    InvoiceDO getInvoice(Long id);

    /**
     * 获得发票信息列表
     *
     * @param ids 编号
     * @return 发票信息列表
     */
    List<InvoiceDO> getInvoiceList(Collection<Long> ids);

    /**
     * 获得发票信息分页
     *
     * @param pageReqVO 分页查询
     * @return 发票信息分页
     */
    PageResult<InvoiceDO> getInvoicePage(InvoicePageReqVO pageReqVO);

    /**
     * 获得发票信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 发票信息列表
     */
    List<InvoiceDO> getInvoiceList(InvoiceExportReqVO exportReqVO);

}
