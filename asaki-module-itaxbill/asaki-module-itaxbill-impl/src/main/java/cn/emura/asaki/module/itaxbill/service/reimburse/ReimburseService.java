package cn.emura.asaki.module.itaxbill.service.reimburse;

import cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo.ReimburseCreateReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo.ReimburseExportReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo.ReimbursePageReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo.ReimburseUpdateReqVO;
import cn.emura.asaki.module.itaxbill.dal.dataobject.reimburse.ReimburseDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;


/**
 * 发票报销信息 Service 接口
 *
 * @author Asaki
 */
public interface ReimburseService {

    /**
     * 创建发票报销信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReimburse(@Valid ReimburseCreateReqVO createReqVO);

    /**
     * 更新发票报销信息
     *
     * @param updateReqVO 更新信息
     */
    void updateReimburse(@Valid ReimburseUpdateReqVO updateReqVO);

    /**
     * 删除发票报销信息
     *
     * @param id 编号
     */
    void deleteReimburse(Long id);

    /**
     * 获得发票报销信息
     *
     * @param id 编号
     * @return 发票报销信息
     */
    ReimburseDO getReimburse(Long id);

    /**
     * 获得发票报销信息列表
     *
     * @param ids 编号
     * @return 发票报销信息列表
     */
    List<ReimburseDO> getReimburseList(Collection<Long> ids);

    /**
     * 获得发票报销信息分页
     *
     * @param pageReqVO 分页查询
     * @return 发票报销信息分页
     */
    PageResult<ReimburseDO> getReimbursePage(ReimbursePageReqVO pageReqVO);

    /**
     * 获得发票报销信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 发票报销信息列表
     */
    List<ReimburseDO> getReimburseList(ReimburseExportReqVO exportReqVO);

}
