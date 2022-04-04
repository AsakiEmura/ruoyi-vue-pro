package cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

@ApiModel("管理后台 - 发票信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InvoiceRespVO extends InvoiceBaseVO {

    @ApiModelProperty(value = "报销单编号")
    private Long reimburseNumber;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

}
