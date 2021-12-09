package com.sk.atdocs.app.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/*
 * LocalDate, LocalDateTime 관련 함수
 */
class DateUtils {

    companion object {

        /*
         * LocalDateTime 를 String 형태로 변환 (공통 포맷 yyyy-MM-dd kk:mm)
         */
        fun convertLocalDateTimeToString(localDateTime : LocalDateTime?): String {
            return convertLocalDateTimeToString(localDateTime, "yyyy-MM-dd kk:mm")
        }

        /*
         * LocalDateTime 를 String 형태로 변환 (포맷 입력 가능)
         */
        fun convertLocalDateTimeToString(localDateTime : LocalDateTime?, pattern : String): String {
            return localDateTime!!.format(DateTimeFormatter.ofPattern(pattern))
        }
    }

}