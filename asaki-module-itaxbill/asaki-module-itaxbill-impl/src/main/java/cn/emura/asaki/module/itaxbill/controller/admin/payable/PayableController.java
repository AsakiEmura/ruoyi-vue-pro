package cn.emura.asaki.module.itaxbill.controller.admin.payable;

import cn.emura.asaki.module.itaxbill.controller.admin.payable.vo.*;
import cn.emura.asaki.module.itaxbill.convert.payable.PayableConvert;
import cn.emura.asaki.module.itaxbill.dal.dataobject.payable.PayableDO;
import cn.emura.asaki.module.itaxbill.service.payable.PayableService;
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

@Api(tags = "管理后台 - 发票抬头")
@RestController
@RequestMapping("/itaxbill/payable")
@Validated
public class PayableController {

    @Resource
    private PayableService payableService;

    @PostMapping("/create")
    @ApiOperation("创建发票抬头")
    @PreAuthorize("@ss.hasPermission('itaxbill:payable:create')")
    public CommonResult<Long> createPayable(@Valid @RequestBody PayableCreateReqVO createReqVO) {
        return success(payableService.createPayable(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新发票抬头")
    @PreAuthorize("@ss.hasPermission('itaxbill:payable:update')")
    public CommonResult<Boolean> updatePayable(@Valid @RequestBody PayableUpdateReqVO updateReqVO) {
        payableService.updatePayable(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除发票抬头")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('itaxbill:payable:delete')")
    public CommonResult<Boolean> deletePayable(@RequestParam("id") Long id) {
        payableService.deletePayable(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得发票抬头")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('itaxbill:payable:query')")
    public CommonResult<PayableRespVO> getPayable(@RequestParam("id") Long id) {
        PayableDO payable = payableService.getPayable(id);
        return success(PayableConvert.INSTANCE.convert(payable));
    }

    @GetMapping("/list")
    @ApiOperation("获得发票抬头列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('itaxbill:payable:query')")
    public CommonResult<List<PayableRespVO>> getPayableList(@RequestParam("ids") Collection<Long> ids) {
        List<PayableDO> list = payableService.getPayableList(ids);
        return success(PayableConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得发票抬头分页")
    @PreAuthorize("@ss.hasPermission('itaxbill:payable:query')")
    public CommonResult<PageResult<PayableRespVO>> getPayablePage(@Valid PayablePageReqVO pageVO) {
        PageResult<PayableDO> pageResult = payableService.getPayablePage(pageVO);
        return success(PayableConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出发票抬头 Excel")
    @PreAuthorize("@ss.hasPermission('itaxbill:payable:export')")
    @OperateLog(type = EXPORT)
    public void exportPayableExcel(@Valid PayableExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<PayableDO> list = payableService.getPayableList(exportReqVO);
        // 导出 Excel
        List<PayableExcelVO> datas = PayableConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "发票抬头.xls", "数据", PayableExcelVO.class, datas);
    }

}
