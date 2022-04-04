package cn.emura.asaki.module.itaxbill.convert.subject;

import cn.emura.asaki.module.itaxbill.controller.admin.subject.vo.SubjectCreateReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.subject.vo.SubjectExcelVO;
import cn.emura.asaki.module.itaxbill.controller.admin.subject.vo.SubjectRespVO;
import cn.emura.asaki.module.itaxbill.controller.admin.subject.vo.SubjectUpdateReqVO;
import cn.emura.asaki.module.itaxbill.dal.dataobject.subject.SubjectDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 发票科目 Convert
 *
 * @author Asaki
 */
@Mapper
public interface SubjectConvert {

    SubjectConvert INSTANCE = Mappers.getMapper(SubjectConvert.class);

    SubjectDO convert(SubjectCreateReqVO bean);

    SubjectDO convert(SubjectUpdateReqVO bean);

    SubjectRespVO convert(SubjectDO bean);

    List<SubjectRespVO> convertList(List<SubjectDO> list);

    PageResult<SubjectRespVO> convertPage(PageResult<SubjectDO> page);

    List<SubjectExcelVO> convertList02(List<SubjectDO> list);

}
