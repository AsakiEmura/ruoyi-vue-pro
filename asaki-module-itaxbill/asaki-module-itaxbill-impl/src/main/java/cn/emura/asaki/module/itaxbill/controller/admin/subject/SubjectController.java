package cn.emura.asaki.module.itaxbill.controller.admin.subject;

import cn.emura.asaki.module.itaxbill.controller.admin.subject.vo.*;
import cn.emura.asaki.module.itaxbill.convert.subject.SubjectConvert;
import cn.emura.asaki.module.itaxbill.dal.dataobject.subject.SubjectDO;
import cn.emura.asaki.module.itaxbill.service.subject.SubjectService;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;


@Api(tags = "管理后台 - 发票科目")
@RestController
@RequestMapping("/itaxbill/subject")
@Validated
public class SubjectController {

    @Resource
    private SubjectService subjectService;

    @PostMapping("/create")
    @ApiOperation("创建发票科目")
    @PreAuthorize("@ss.hasPermission('itaxbill:subject:create')")
    public CommonResult<Integer> createSubject(@Valid @RequestBody SubjectCreateReqVO createReqVO) {
        return success(subjectService.createSubject(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新发票科目")
    @PreAuthorize("@ss.hasPermission('itaxbill:subject:update')")
    public CommonResult<Boolean> updateSubject(@Valid @RequestBody SubjectUpdateReqVO updateReqVO) {
        subjectService.updateSubject(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除发票科目")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('itaxbill:subject:delete')")
    public CommonResult<Boolean> deleteSubject(@RequestParam("id") Integer id) {
        subjectService.deleteSubject(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得发票科目")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('itaxbill:subject:query')")
    public CommonResult<SubjectRespVO> getSubject(@RequestParam("id") Integer id) {
        SubjectDO subject = subjectService.getSubject(id);
        return success(SubjectConvert.INSTANCE.convert(subject));
    }

    @GetMapping("/list")
    @ApiOperation("获得发票科目列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('itaxbill:subject:query')")
    public CommonResult<List<SubjectRespVO>> getSubjectList(@RequestParam("ids") Collection<Integer> ids) {
        List<SubjectDO> list = subjectService.getSubjectList(ids);
        return success(SubjectConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得发票科目分页")
    @PreAuthorize("@ss.hasPermission('itaxbill:subject:query')")
    public CommonResult<PageResult<SubjectRespVO>> getSubjectPage(@Valid SubjectPageReqVO pageVO) {
        PageResult<SubjectDO> pageResult = subjectService.getSubjectPage(pageVO);
        return success(SubjectConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出发票科目 Excel")
    @PreAuthorize("@ss.hasPermission('itaxbill:subject:export')")
    @OperateLog(type = EXPORT)
    public void exportSubjectExcel(@Valid SubjectExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<SubjectDO> list = subjectService.getSubjectList(exportReqVO);
        // 导出 Excel
        List<SubjectExcelVO> datas = SubjectConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "发票科目.xls", "数据", SubjectExcelVO.class, datas);
    }

}
