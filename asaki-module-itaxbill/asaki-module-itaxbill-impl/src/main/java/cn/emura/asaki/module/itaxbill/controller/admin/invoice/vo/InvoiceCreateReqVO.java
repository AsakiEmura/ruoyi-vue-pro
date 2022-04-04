package cn.emura.asaki.module.itaxbill.controller.admin.invoice.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 发票信息创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InvoiceCreateReqVO extends InvoiceBaseVO {

    @ApiModelProperty(value = "开票日期", required = true)
    @NotNull(message = "开票日期不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date date;

    @ApiModelProperty(value = "图片地址", required = true)
    @NotNull(message = "图片地址不能为空")
    private String invoiceImgPath;

}
