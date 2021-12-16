package com.sk.atdocs.app.exception

enum class ErrorCode(
    var code : String,
    var message : String
) {
    ERROR("E001", "error.message.unknown"),
    ERROR_FAIL_SEARCH("E002","데이터 조회에 실패했습니다."),
    ERROR_NOT_MODIFY_OBJECT("E003","데이터 저장에 실패했습니다."),
    ERROR_SEARCH_CODE_INFO("E004","코드정보를 조회하지 못했습니다.")
}