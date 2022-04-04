package cn.emura.asaki.module.itaxbill.dal.dataobject.subject;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 发票科目 DO
 *
 * @author Asaki
 */
@TableName("itaxbill_subject")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDO extends BaseDO {

    /**
     * 科目id
     */
    @TableId
    private Integer id;
    /**
     * 科目名称
     */
    private String subjectName;
    /**
     * 科目英文名称
     */
    private String subjectEnglish;

}
