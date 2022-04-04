package cn.emura.asaki.module.itaxbill.dal.dataobject.reimburse;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 发票报销信息 DO
 *
 * @author Asaki
 */
@TableName("itaxbill_reimburse")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReimburseDO extends BaseDO {

    /**
     * 报销单ID
     */
    @TableId
    private Long id;
    /**
     * 发票id
     */
    private String invoiceId;
    /**
     * 报销单名称
     */
    private String reimburseName;
    /**
     * 报销单金额
     */
    private String price;
    /**
     * 报销单总金额
     */
    private String priceAll;
    /**
     * 报销单科目
     */
    private String subject;
    /**
     * 发票张数
     */
    private Integer invoiceNumber;
    /**
     * 发票总张数
     */
    private Integer invoiceNumberAll;
    /**
     * 状态
     */
    private String status;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 报销时间
     */
    private Date reimburseTime;

}
