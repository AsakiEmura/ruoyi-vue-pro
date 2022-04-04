package cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

@ApiModel("管理后台 - 发票报销信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReimburseRespVO extends ReimburseBaseVO {

    @ApiModelProperty(value = "报销单金额", required = true)
    private String price;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

}
