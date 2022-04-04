package cn.emura.asaki.module.itaxbill.dal.dataobject.invoice;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 发票信息 DO
 *
 * @author Asaki
 */
@TableName("itaxbill_invoice")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDO extends BaseDO {

    /**
     * 发票ID
     */
    @TableId
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 抬头ID
     */
    private Long payableId;
    /**
     * 报销单编号
     */
    private Long reimburseNumber;
    /**
     * 购买方税号(发票产生的公司的税号)
     */
    private Long invoiceTax;
    /**
     * 事由
     */
    private String event;
    /**
     * 购买方
     */
    private String company;
    /**
     * 发票分类
     */
    private String category;
    /**
     * 发票信息
     */
    private String content;
    /**
     * 发票金额
     */
    private BigDecimal price;
    /**
     * 开票日期
     */
    private Date date;
    /**
     * 报销科目
     */
    private String subject;
    /**
     * 报销状态
     */
    private String reimburseStatus;
    /**
     * 图片地址
     */
    private String invoiceImgPath;

}
