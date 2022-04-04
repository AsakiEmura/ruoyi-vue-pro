package cn.emura.asaki.module.itaxbill.controller.admin.payable.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("管理后台 - 发票抬头更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayableUpdateReqVO extends PayableBaseVO {
    @ApiModelProperty(value = "抬头Id", required = true)
    @NotNull(message = "抬头Id不能为空")
    private Long id;

    @ApiModelProperty(value = "税号", required = true)
    @NotNull(message = "税号不能为空")
    private Long taxId;

}
