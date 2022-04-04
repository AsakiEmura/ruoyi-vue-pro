package cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 发票报销信息更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReimburseUpdateReqVO extends ReimburseBaseVO {

    @ApiModelProperty(value = "报销单ID", required = true)
    @NotNull(message = "报销单ID不能为空")
    private Long id;

    @ApiModelProperty(value = "报销单科目", required = true)
    @NotNull(message = "报销单科目不能为空")
    private String subject;

    @ApiModelProperty(value = "发票总张数", required = true)
    @NotNull(message = "发票总张数不能为空")
    private Integer invoiceNumberAll;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "创建时间", required = true)
    @NotNull(message = "创建时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date createTime;

}
