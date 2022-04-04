package cn.emura.asaki.module.itaxbill.dal.mysql.subject;

import java.util.*;

import cn.emura.asaki.module.itaxbill.controller.admin.subject.vo.SubjectExportReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.subject.vo.SubjectPageReqVO;
import cn.emura.asaki.module.itaxbill.dal.dataobject.subject.SubjectDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;

import org.apache.ibatis.annotations.Mapper;


/**
 * 发票科目 Mapper
 *
 * @author Asaki
 */
@Mapper
public interface SubjectMapper extends BaseMapperX<SubjectDO> {

    default PageResult<SubjectDO> selectPage(SubjectPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SubjectDO>()
                .likeIfPresent(SubjectDO::getSubjectName, reqVO.getSubjectName())
                .eqIfPresent(SubjectDO::getSubjectEnglish, reqVO.getSubjectEnglish())
                .betweenIfPresent(SubjectDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(SubjectDO::getId));
    }

    default List<SubjectDO> selectList(SubjectExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SubjectDO>()
                .likeIfPresent(SubjectDO::getSubjectName, reqVO.getSubjectName())
                .eqIfPresent(SubjectDO::getSubjectEnglish, reqVO.getSubjectEnglish())
                .betweenIfPresent(SubjectDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(SubjectDO::getId));
    }

}
