package cn.emura.asaki.module.itaxbill.service.subject;

import cn.emura.asaki.module.itaxbill.controller.admin.subject.vo.SubjectCreateReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.subject.vo.SubjectExportReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.subject.vo.SubjectPageReqVO;
import cn.emura.asaki.module.itaxbill.controller.admin.subject.vo.SubjectUpdateReqVO;
import cn.emura.asaki.module.itaxbill.convert.subject.SubjectConvert;
import cn.emura.asaki.module.itaxbill.dal.dataobject.subject.SubjectDO;
import cn.emura.asaki.module.itaxbill.dal.mysql.subject.SubjectMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.emura.asaki.module.itaxbill.enums.ItaxbillErrorCode.SUBJECT_NOT_EXISTS;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 发票科目 Service 实现类
 *
 * @author Asaki
 */
@Service
@Validated
public class SubjectServiceImpl implements SubjectService {

    @Resource
    private SubjectMapper subjectMapper;

    @Override
    public Integer createSubject(SubjectCreateReqVO createReqVO) {
        // 插入
        SubjectDO subject = SubjectConvert.INSTANCE.convert(createReqVO);
        subjectMapper.insert(subject);
        // 返回
        return subject.getId();
    }

    @Override
    public void updateSubject(SubjectUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateSubjectExists(updateReqVO.getId());
        // 更新
        SubjectDO updateObj = SubjectConvert.INSTANCE.convert(updateReqVO);
        subjectMapper.updateById(updateObj);
    }

    @Override
    public void deleteSubject(Integer id) {
        // 校验存在
        this.validateSubjectExists(id);
        // 删除
        subjectMapper.deleteById(id);
    }

    private void validateSubjectExists(Integer id) {
        if (subjectMapper.selectById(id) == null) {
            throw exception(SUBJECT_NOT_EXISTS);
        }
    }

    @Override
    public SubjectDO getSubject(Integer id) {
        return subjectMapper.selectById(id);
    }

    @Override
    public List<SubjectDO> getSubjectList(Collection<Integer> ids) {
        return subjectMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<SubjectDO> getSubjectPage(SubjectPageReqVO pageReqVO) {
        return subjectMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SubjectDO> getSubjectList(SubjectExportReqVO exportReqVO) {
        return subjectMapper.selectList(exportReqVO);
    }

}
