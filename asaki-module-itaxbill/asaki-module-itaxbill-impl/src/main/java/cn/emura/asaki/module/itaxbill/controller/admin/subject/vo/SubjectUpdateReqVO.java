package cn.emura.asaki.module.itaxbill.controller.admin.subject.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("管理后台 - 发票科目更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubjectUpdateReqVO extends SubjectBaseVO {
    @ApiModelProperty(value = "科目id", required = true)
    @NotNull(message = "科目id不能为空")
    private Integer id;
}
