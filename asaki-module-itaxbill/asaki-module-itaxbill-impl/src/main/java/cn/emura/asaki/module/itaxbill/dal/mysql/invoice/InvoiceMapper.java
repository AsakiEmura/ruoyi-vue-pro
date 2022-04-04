package cn.emura.asaki.module.itaxbill.dal.mysql.invoice;

import cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo.InvoiceExportReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo.InvoicePageReqVO;
import cn.emura.asaki.module.itaxbill.dal.dataobject.invoice.InvoiceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 发票信息 Mapper
 *
 * @author Asaki
 */
@Mapper
public interface InvoiceMapper extends BaseMapperX<InvoiceDO> {

    default PageResult<InvoiceDO> selectPage(InvoicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<InvoiceDO>()
                .eqIfPresent(InvoiceDO::getPayableId, reqVO.getPayableId())
                .betweenIfPresent(InvoiceDO::getDate, reqVO.getBeginDate(), reqVO.getEndDate())
                .eqIfPresent(InvoiceDO::getSubject, reqVO.getSubject())
                .betweenIfPresent(InvoiceDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(InvoiceDO::getId));
    }

    default List<InvoiceDO> selectList(InvoiceExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<InvoiceDO>()
                .eqIfPresent(InvoiceDO::getPayableId, reqVO.getPayableId())
                .betweenIfPresent(InvoiceDO::getDate, reqVO.getBeginDate(), reqVO.getEndDate())
                .eqIfPresent(InvoiceDO::getSubject, reqVO.getSubject())
                .betweenIfPresent(InvoiceDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(InvoiceDO::getId));
    }

}
