package com.mm.shanai.common.model

/**
 * 隐私协议内容
 * @Author: lzm
 * @Date: 2024-09-14 22:07
 * @Description: TODO
 */
data class PrivacyAgreement (

    val title : String,

    val bgSplash : String,
    //文本内容
    val protocolPrivacyAlert : String,
    //隐私协议地址
    val privacyPolicyUrl : String,
    //服务协议地址
    val protocolUrl : String,
    //隐私协议版本
    val privacyAgreementVersion : Int

)