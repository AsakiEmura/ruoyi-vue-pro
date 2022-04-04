package cn.emura.asaki.module.itaxbill.dal.mysql.payable;

import cn.emura.asaki.module.itaxbill.controller.admin.payable.vo.PayableExportReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.payable.vo.PayablePageReqVO;
import cn.emura.asaki.module.itaxbill.dal.dataobject.payable.PayableDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 发票抬头 Mapper
 *
 * @author Asaki
 */
@Mapper
public interface PayableMapper extends BaseMapperX<PayableDO> {

    default PageResult<PayableDO> selectPage(PayablePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PayableDO>()
                .likeIfPresent(PayableDO::getCompanyName, reqVO.getCompanyName())
                .betweenIfPresent(PayableDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(PayableDO::getId));
    }

    default List<PayableDO> selectList(PayableExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PayableDO>()
                .likeIfPresent(PayableDO::getCompanyName, reqVO.getCompanyName())
                .betweenIfPresent(PayableDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(PayableDO::getId));
    }

}
