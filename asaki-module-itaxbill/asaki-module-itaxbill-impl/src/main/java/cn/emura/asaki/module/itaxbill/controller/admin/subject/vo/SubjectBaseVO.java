package cn.emura.asaki.module.itaxbill.controller.admin.subject.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
* 发票科目 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class SubjectBaseVO {

    @ApiModelProperty(value = "科目名称", required = true)
    @NotNull(message = "科目名称不能为空")
    private String subjectName;

    @ApiModelProperty(value = "科目英文名称", required = true)
    @NotNull(message = "科目英文名称不能为空")
    private String subjectEnglish;

}
