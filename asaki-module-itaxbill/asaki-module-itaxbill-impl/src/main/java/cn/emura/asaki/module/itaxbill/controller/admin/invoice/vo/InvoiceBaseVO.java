package cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
* 发票信息 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class InvoiceBaseVO {

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @ApiModelProperty(value = "抬头ID", required = true)
    @NotNull(message = "抬头ID不能为空")
    private Long payableId;

    @ApiModelProperty(value = "购买方税号(发票产生的公司的税号)", required = true)
    @NotNull(message = "购买方税号(发票产生的公司的税号)不能为空")
    private Long invoiceTax;

    @ApiModelProperty(value = "事由", required = true)
    @NotNull(message = "事由不能为空")
    private String event;

    @ApiModelProperty(value = "购买方", required = true)
    @NotNull(message = "购买方不能为空")
    private String company;

    @ApiModelProperty(value = "发票分类", required = true)
    @NotNull(message = "发票分类不能为空")
    private String category;

    @ApiModelProperty(value = "发票信息", required = true)
    @NotNull(message = "发票信息不能为空")
    private String content;

    @ApiModelProperty(value = "发票金额", required = true)
    @NotNull(message = "发票金额不能为空")
    private BigDecimal price;

    @ApiModelProperty(value = "报销科目", required = true)
    @NotNull(message = "报销科目不能为空")
    private String subject;

    @ApiModelProperty(value = "报销状态", required = true)
    @NotNull(message = "报销状态不能为空")
    private String reimburseStatus;

}
