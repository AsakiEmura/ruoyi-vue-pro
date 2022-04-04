package cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 发票信息更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InvoiceUpdateReqVO extends InvoiceBaseVO {
    @ApiModelProperty(value = "发票ID", required = true)
    @NotNull(message = "发票ID不能为空")
    private Long id;

    @ApiModelProperty(value = "报销单编号")
    private Long reimburseNumber;

    @ApiModelProperty(value = "开票日期", required = true)
    @NotNull(message = "开票日期不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date date;

}
