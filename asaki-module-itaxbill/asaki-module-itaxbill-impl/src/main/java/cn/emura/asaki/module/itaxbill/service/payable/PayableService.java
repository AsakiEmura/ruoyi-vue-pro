package cn.emura.asaki.module.itaxbill.service.payable;

import cn.emura.asaki.module.itaxbill.controller.admin.payable.vo.PayableCreateReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.payable.vo.PayableExportReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.payable.vo.PayablePageReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.payable.vo.PayableUpdateReqVO;
import cn.emura.asaki.module.itaxbill.dal.dataobject.payable.PayableDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 发票抬头 Service 接口
 *
 * @author Asaki
 */
public interface PayableService {

    /**
     * 创建发票抬头
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPayable(@Valid PayableCreateReqVO createReqVO);

    /**
     * 更新发票抬头
     *
     * @param updateReqVO 更新信息
     */
    void updatePayable(@Valid PayableUpdateReqVO updateReqVO);

    /**
     * 删除发票抬头
     *
     * @param id 编号
     */
    void deletePayable(Long id);

    /**
     * 获得发票抬头
     *
     * @param id 编号
     * @return 发票抬头
     */
    PayableDO getPayable(Long id);

    /**
     * 获得发票抬头列表
     *
     * @param ids 编号
     * @return 发票抬头列表
     */
    List<PayableDO> getPayableList(Collection<Long> ids);

    /**
     * 获得发票抬头分页
     *
     * @param pageReqVO 分页查询
     * @return 发票抬头分页
     */
    PageResult<PayableDO> getPayablePage(PayablePageReqVO pageReqVO);

    /**
     * 获得发票抬头列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 发票抬头列表
     */
    List<PayableDO> getPayableList(PayableExportReqVO exportReqVO);

}
