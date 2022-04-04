package cn.emura.asaki.module.itaxbill.dal.mysql.reimburse;

import cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo.ReimburseExportReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo.ReimbursePageReqVO;
import cn.emura.asaki.module.itaxbill.dal.dataobject.reimburse.ReimburseDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 发票报销信息 Mapper
 *
 * @author Asaki
 */
@Mapper
public interface ReimburseMapper extends BaseMapperX<ReimburseDO> {

    default PageResult<ReimburseDO> selectPage(ReimbursePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReimburseDO>()
                .likeIfPresent(ReimburseDO::getReimburseName, reqVO.getReimburseName())
                .eqIfPresent(ReimburseDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ReimburseDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(ReimburseDO::getId));
    }

    default List<ReimburseDO> selectList(ReimburseExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ReimburseDO>()
                .likeIfPresent(ReimburseDO::getReimburseName, reqVO.getReimburseName())
                .eqIfPresent(ReimburseDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ReimburseDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(ReimburseDO::getId));
    }

}
