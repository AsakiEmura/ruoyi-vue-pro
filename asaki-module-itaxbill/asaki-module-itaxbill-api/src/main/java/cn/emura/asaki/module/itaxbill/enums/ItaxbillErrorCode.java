package cn.emura.asaki.module.itaxbill.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
/**
 * 发票管理 错误码枚举类
 *
 * 发票管理，使用 1-010-000-000 段
 */
public interface ItaxbillErrorCode {
    ErrorCode INVOICE_NOT_EXISTS = new ErrorCode(1010000001, "发票信息不存在");
    ErrorCode PAYABLE_NOT_EXISTS = new ErrorCode(1010000002, "发票抬头不存在");
    ErrorCode REIMBURSE_NOT_EXISTS = new ErrorCode(1010000003, "发票报销信息不存在");
    ErrorCode SUBJECT_NOT_EXISTS = new ErrorCode(1010000004, "发票科目不存在");

}
