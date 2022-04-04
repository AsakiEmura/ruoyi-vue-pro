package cn.emura.asaki.module.itaxbill.controller.admin.payable.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 发票抬头 Excel VO
 *
 * @author Asaki
 */
@Data
public class PayableExcelVO {

    @ExcelProperty("抬头Id")
    private Long id;

    @ExcelProperty("公司名称")
    private String companyName;

    @ExcelProperty("创建者")
    private String creator;

    @ExcelProperty("创建时间")
    private Date createTime;

}
