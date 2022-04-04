package cn.emura.asaki.module.itaxbill.controller.admin.reimburse;

import cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo.*;
import cn.emura.asaki.module.itaxbill.convert.reimburse.ReimburseConvert;
import cn.emura.asaki.module.itaxbill.dal.dataobject.reimburse.ReimburseDO;
import cn.emura.asaki.module.itaxbill.service.reimburse.ReimburseService;
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

@Api(tags = "管理后台 - 发票报销信息")
@RestController
@RequestMapping("/itaxbill/reimburse")
@Validated
public class ReimburseController {

    @Resource
    private ReimburseService reimburseService;

    @PostMapping("/create")
    @ApiOperation("创建发票报销信息")
    @PreAuthorize("@ss.hasPermission('itaxbill:reimburse:create')")
    public CommonResult<Long> createReimburse(@Valid @RequestBody ReimburseCreateReqVO createReqVO) {
        return success(reimburseService.createReimburse(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新发票报销信息")
    @PreAuthorize("@ss.hasPermission('itaxbill:reimburse:update')")
    public CommonResult<Boolean> updateReimburse(@Valid @RequestBody ReimburseUpdateReqVO updateReqVO) {
        reimburseService.updateReimburse(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除发票报销信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('itaxbill:reimburse:delete')")
    public CommonResult<Boolean> deleteReimburse(@RequestParam("id") Long id) {
        reimburseService.deleteReimburse(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得发票报销信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('itaxbill:reimburse:query')")
    public CommonResult<ReimburseRespVO> getReimburse(@RequestParam("id") Long id) {
        ReimburseDO reimburse = reimburseService.getReimburse(id);
        return success(ReimburseConvert.INSTANCE.convert(reimburse));
    }

    @GetMapping("/list")
    @ApiOperation("获得发票报销信息列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('itaxbill:reimburse:query')")
    public CommonResult<List<ReimburseRespVO>> getReimburseList(@RequestParam("ids") Collection<Long> ids) {
        List<ReimburseDO> list = reimburseService.getReimburseList(ids);
        return success(ReimburseConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得发票报销信息分页")
    @PreAuthorize("@ss.hasPermission('itaxbill:reimburse:query')")
    public CommonResult<PageResult<ReimburseRespVO>> getReimbursePage(@Valid ReimbursePageReqVO pageVO) {
        PageResult<ReimburseDO> pageResult = reimburseService.getReimbursePage(pageVO);
        return success(ReimburseConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出发票报销信息 Excel")
    @PreAuthorize("@ss.hasPermission('itaxbill:reimburse:export')")
    @OperateLog(type = EXPORT)
    public void exportReimburseExcel(@Valid ReimburseExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<ReimburseDO> list = reimburseService.getReimburseList(exportReqVO);
        // 导出 Excel
        List<ReimburseExcelVO> datas = ReimburseConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "发票报销信息.xls", "数据", ReimburseExcelVO.class, datas);
    }

}
