package cn.emura.asaki.module.itaxbill.controller.admin.reimburse.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
* 发票报销信息 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class ReimburseBaseVO {

    @ApiModelProperty(value = "报销单名称", required = true)
    @NotNull(message = "报销单名称不能为空")
    private String reimburseName;

    @ApiModelProperty(value = "报销单总金额", required = true)
    @NotNull(message = "报销单总金额不能为空")
    private String priceAll;

    @ApiModelProperty(value = "状态", required = true)
    @NotNull(message = "状态不能为空")
    private String status;

    @ApiModelProperty(value = "报销时间", required = true)
    @NotNull(message = "报销时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date reimburseTime;

}
