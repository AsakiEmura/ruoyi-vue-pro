package cn.emura.asaki.module.itaxbill.controller.admin.payable.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
* 发票抬头 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class PayableBaseVO {

    @ApiModelProperty(value = "公司名称", required = true)
    @NotNull(message = "公司名称不能为空")
    private String companyName;

}
