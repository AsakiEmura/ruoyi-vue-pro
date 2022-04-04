package cn.emura.asaki.module.itaxbill.convert.invoice;

import cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo.InvoiceCreateReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo.InvoiceExcelVO;
import cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo.InvoiceRespVO;
import cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo.InvoiceUpdateReqVO;
import cn.emura.asaki.module.itaxbill.dal.dataobject.invoice.InvoiceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 发票信息 Convert
 *
 * @author Asaki
 */
@Mapper
public interface InvoiceConvert {

    InvoiceConvert INSTANCE = Mappers.getMapper(InvoiceConvert.class);

    InvoiceDO convert(InvoiceCreateReqVO bean);

    InvoiceDO convert(InvoiceUpdateReqVO bean);

    InvoiceRespVO convert(InvoiceDO bean);

    List<InvoiceRespVO> convertList(List<InvoiceDO> list);

    PageResult<InvoiceRespVO> convertPage(PageResult<InvoiceDO> page);

    List<InvoiceExcelVO> convertList02(List<InvoiceDO> list);

}
