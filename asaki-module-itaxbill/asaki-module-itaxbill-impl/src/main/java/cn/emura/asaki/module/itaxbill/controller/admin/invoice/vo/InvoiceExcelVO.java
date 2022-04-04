package cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 发票信息 Excel VO
 *
 * @author Asaki
 */
@Data
public class InvoiceExcelVO {

    @ExcelProperty("用户ID")
    private Long userId;

    @ExcelProperty("抬头ID")
    private Long payableId;

    @ExcelProperty("报销单编号")
    private Long reimburseNumber;

    @ExcelProperty("购买方税号(发票产生的公司的税号)")
    private Long invoiceTax;

    @ExcelProperty("事由")
    private String event;

    @ExcelProperty("购买方")
    private String company;

    @ExcelProperty("发票分类")
    private String category;

    @ExcelProperty("发票信息")
    private String content;

    @ExcelProperty("发票金额")
    private BigDecimal price;

    @ExcelProperty("报销科目")
    private String subject;

    @ExcelProperty("报销状态")
    private String reimburseStatus;

    @ExcelProperty("创建时间")
    private Date createTime;

}
