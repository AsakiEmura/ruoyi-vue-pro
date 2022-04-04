package cn.emura.asaki.module.itaxbill.convert.payable;

import cn.emura.asaki.module.itaxbill.controller.admin.payable.vo.PayableCreateReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.payable.vo.PayableExcelVO;
import cn.emura.asaki.module.itaxbill.controller.admin.payable.vo.PayableRespVO;
import cn.emura.asaki.module.itaxbill.controller.admin.payable.vo.PayableUpdateReqVO;
import cn.emura.asaki.module.itaxbill.dal.dataobject.payable.PayableDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 发票抬头 Convert
 *
 * @author Asaki
 */
@Mapper
public interface PayableConvert {

    PayableConvert INSTANCE = Mappers.getMapper(PayableConvert.class);

    PayableDO convert(PayableCreateReqVO bean);

    PayableDO convert(PayableUpdateReqVO bean);

    PayableRespVO convert(PayableDO bean);

    List<PayableRespVO> convertList(List<PayableDO> list);

    PageResult<PayableRespVO> convertPage(PageResult<PayableDO> page);

    List<PayableExcelVO> convertList02(List<PayableDO> list);

}
