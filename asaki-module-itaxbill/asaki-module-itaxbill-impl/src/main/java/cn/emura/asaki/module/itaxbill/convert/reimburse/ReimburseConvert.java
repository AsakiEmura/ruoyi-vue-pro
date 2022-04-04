package cn.emura.asaki.module.itaxbill.convert.reimburse;

import cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo.ReimburseCreateReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo.ReimburseExcelVO;
import cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo.ReimburseRespVO;
import cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo.ReimburseUpdateReqVO;
import cn.emura.asaki.module.itaxbill.dal.dataobject.reimburse.ReimburseDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 发票报销信息 Convert
 *
 * @author Asaki
 */
@Mapper
public interface ReimburseConvert {

    ReimburseConvert INSTANCE = Mappers.getMapper(ReimburseConvert.class);

    ReimburseDO convert(ReimburseCreateReqVO bean);

    ReimburseDO convert(ReimburseUpdateReqVO bean);

    ReimburseRespVO convert(ReimburseDO bean);

    List<ReimburseRespVO> convertList(List<ReimburseDO> list);

    PageResult<ReimburseRespVO> convertPage(PageResult<ReimburseDO> page);

    List<ReimburseExcelVO> convertList02(List<ReimburseDO> list);

}
