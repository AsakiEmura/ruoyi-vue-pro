package cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("管理后台 - 发票报销信息创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReimburseCreateReqVO extends ReimburseBaseVO {

    @ApiModelProperty(value = "发票id", required = true)
    @NotNull(message = "发票id不能为空")
    private String invoiceId;

    @ApiModelProperty(value = "报销单金额", required = true)
    @NotNull(message = "报销单金额不能为空")
    private String price;

    @ApiModelProperty(value = "报销单科目", required = true)
    @NotNull(message = "报销单科目不能为空")
    private String subject;

    @ApiModelProperty(value = "发票张数", required = true)
    @NotNull(message = "发票张数不能为空")
    private Integer invoiceNumber;

    @ApiModelProperty(value = "发票总张数", required = true)
    @NotNull(message = "发票总张数不能为空")
    private Integer invoiceNumberAll;

    @ApiModelProperty(value = "用户id", required = true)
    @NotNull(message = "用户id不能为空")
    private Long userId;

}
