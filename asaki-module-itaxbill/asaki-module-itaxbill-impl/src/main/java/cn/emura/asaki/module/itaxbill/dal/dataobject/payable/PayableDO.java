package cn.emura.asaki.module.itaxbill.dal.dataobject.payable;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 发票抬头 DO
 *
 * @author Asaki
 */
@TableName("itaxbill_payable")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayableDO extends BaseDO {

    /**
     * 抬头Id
     */
    @TableId
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 税号
     */
    private Long taxId;
    /**
     * 公司名称
     */
    private String companyName;

}
