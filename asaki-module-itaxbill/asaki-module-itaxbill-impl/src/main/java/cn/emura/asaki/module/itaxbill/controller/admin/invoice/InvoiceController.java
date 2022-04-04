package cn.emura.asaki.module.itaxbill.controller.admin.invoice;

import cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo.*;
import cn.emura.asaki.module.itaxbill.convert.invoice.InvoiceConvert;
import cn.emura.asaki.module.itaxbill.dal.dataobject.invoice.InvoiceDO;
import cn.emura.asaki.module.itaxbill.service.invoice.InvoiceService;
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

@Api(tags = "管理后台 - 发票信息")
@RestController
@RequestMapping("/itaxbill/invoice")
@Validated
public class InvoiceController {

    @Resource
    private InvoiceService invoiceService;

    @PostMapping("/create")
    @ApiOperation("创建发票信息")
    @PreAuthorize("@ss.hasPermission('itaxbill:invoice:create')")
    public CommonResult<Long> createInvoice(@Valid @RequestBody InvoiceCreateReqVO createReqVO) {
        return success(invoiceService.createInvoice(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新发票信息")
    @PreAuthorize("@ss.hasPermission('itaxbill:invoice:update')")
    public CommonResult<Boolean> updateInvoice(@Valid @RequestBody InvoiceUpdateReqVO updateReqVO) {
        invoiceService.updateInvoice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除发票信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('itaxbill:invoice:delete')")
    public CommonResult<Boolean> deleteInvoice(@RequestParam("id") Long id) {
        invoiceService.deleteInvoice(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得发票信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('itaxbill:invoice:query')")
    public CommonResult<InvoiceRespVO> getInvoice(@RequestParam("id") Long id) {
        InvoiceDO invoice = invoiceService.getInvoice(id);
        return success(InvoiceConvert.INSTANCE.convert(invoice));
    }

    @GetMapping("/list")
    @ApiOperation("获得发票信息列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('itaxbill:invoice:query')")
    public CommonResult<List<InvoiceRespVO>> getInvoiceList(@RequestParam("ids") Collection<Long> ids) {
        List<InvoiceDO> list = invoiceService.getInvoiceList(ids);
        return success(InvoiceConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得发票信息分页")
    @PreAuthorize("@ss.hasPermission('itaxbill:invoice:query')")
    public CommonResult<PageResult<InvoiceRespVO>> getInvoicePage(@Valid InvoicePageReqVO pageVO) {
        PageResult<InvoiceDO> pageResult = invoiceService.getInvoicePage(pageVO);
        return success(InvoiceConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出发票信息 Excel")
    @PreAuthorize("@ss.hasPermission('itaxbill:invoice:export')")
    @OperateLog(type = EXPORT)
    public void exportInvoiceExcel(@Valid InvoiceExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<InvoiceDO> list = invoiceService.getInvoiceList(exportReqVO);
        // 导出 Excel
        List<InvoiceExcelVO> datas = InvoiceConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "发票信息.xls", "数据", InvoiceExcelVO.class, datas);
    }

}
