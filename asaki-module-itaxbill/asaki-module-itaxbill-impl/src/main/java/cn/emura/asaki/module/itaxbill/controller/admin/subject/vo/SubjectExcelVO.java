package cn.emura.asaki.module.itaxbill.controller.admin.subject.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 发票科目 Excel VO
 *
 * @author Asaki
 */
@Data
public class SubjectExcelVO {

    @ExcelProperty("科目名称")
    private String subjectName;

    @ExcelProperty("科目英文名称")
    private String subjectEnglish;

    @ExcelProperty("创建时间")
    private Date createTime;

}
