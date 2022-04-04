package cn.emura.asaki.module.itaxbill.controller.admin.payable.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

@ApiModel("管理后台 - 发票抬头 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayableRespVO extends PayableBaseVO {

    @ApiModelProperty(value = "抬头Id", required = true)
    private Long id;

    @ApiModelProperty(value = "创建者", required = true)
    private String creator;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

}
