package cn.emura.asaki.module.itaxbill.service.subject;

import cn.emura.asaki.module.itaxbill.controller.admin.subject.vo.SubjectCreateReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.subject.vo.SubjectExportReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.subject.vo.SubjectPageReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.subject.vo.SubjectUpdateReqVO;
import cn.emura.asaki.module.itaxbill.dal.dataobject.subject.SubjectDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 发票科目 Service 接口
 *
 * @author Asaki
 */
public interface SubjectService {

    /**
     * 创建发票科目
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createSubject(@Valid SubjectCreateReqVO createReqVO);

    /**
     * 更新发票科目
     *
     * @param updateReqVO 更新信息
     */
    void updateSubject(@Valid SubjectUpdateReqVO updateReqVO);

    /**
     * 删除发票科目
     *
     * @param id 编号
     */
    void deleteSubject(Integer id);

    /**
     * 获得发票科目
     *
     * @param id 编号
     * @return 发票科目
     */
    SubjectDO getSubject(Integer id);

    /**
     * 获得发票科目列表
     *
     * @param ids 编号
     * @return 发票科目列表
     */
    List<SubjectDO> getSubjectList(Collection<Integer> ids);

    /**
     * 获得发票科目分页
     *
     * @param pageReqVO 分页查询
     * @return 发票科目分页
     */
    PageResult<SubjectDO> getSubjectPage(SubjectPageReqVO pageReqVO);

    /**
     * 获得发票科目列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 发票科目列表
     */
    List<SubjectDO> getSubjectList(SubjectExportReqVO exportReqVO);

}
