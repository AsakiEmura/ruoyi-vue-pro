package cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 发票报销信息 Excel VO
 *
 * @author Asaki
 */
@Data
public class ReimburseExcelVO {

    @ExcelProperty("报销单名称")
    private String reimburseName;

    @ExcelProperty("报销单金额")
    private String price;

    @ExcelProperty("报销单总金额")
    private String priceAll;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("报销时间")
    private Date reimburseTime;

    @ExcelProperty("创建时间")
    private Date createTime;

}
