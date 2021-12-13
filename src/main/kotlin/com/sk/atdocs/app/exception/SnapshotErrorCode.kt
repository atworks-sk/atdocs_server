package com.sk.atdocs.app.exception

enum class SnapshotErrorCode(
    var code : String,
    var message : String
) {
    ERROR_NOT_JAVA_FILE("S001", "JAVA 파일이 아닙니다."),
    ERROR_FAIL_PARSE("S002","JAVA Parsing 도중 애러가 발생하였습니다.")
}