package cn.emura.asaki.module.itaxbill.controller.admin.subject.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

@ApiModel("管理后台 - 发票科目 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubjectRespVO extends SubjectBaseVO {

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

}
