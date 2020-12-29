package com.example.dqddu

import com.example.dqddu.ext.containsDigit
import com.example.dqddu.ext.containsLatinLetter
import com.example.dqddu.ext.isAlphanumeric
import com.example.dqddu.ext.isEmailValid
import com.example.dqddu.ext.isPhoneNumber
import com.example.dqddu.ext.lastPathComponent
import com.example.dqddu.ext.md5
import com.example.dqddu.ext.sha1
import org.junit.Test

/**
 * 工具类 StringUtils 的单元测试文件
 *
 * @author DQDana For Olivia
 * @since 4/12/21 11:45 AM
 */
class StringUtilsUnitTest {

    /**
     * 详见[com.example.dqddu.ext.md5]
     */
    @Test
    fun testMd5() {
        "123".run {
            println("\"$this\" 的 md5 值为: ${this.md5}")
        }
    }

    /**
     * 详见[com.example.dqddu.ext.sha1]
     */
    @Test
    fun testSha1() {
        "456".run {
            println("\"$this\" 的 sha1 值为: ${this.sha1}")
        }
    }

    /**
     * 详见[com.example.dqddu.ext.isEmailValid]
     */
    @Test
    fun testIsEmailValid() {
        val emails = listOf("767704339@qq.com", "dqdanavera@gmail")
        emails.forEach {
            it.run {
                println("\"$it\" 是否为有效邮箱地址? -> ${it.isEmailValid()}")
            }
        }
    }

    /**
     * 详见[com.example.dqddu.ext.formatPhoneNumber]&[com.example.dqddu.ext.isPhoneNumber]
     */
    @Test
    fun testPhoneNumber() {
        // 全球的验证,需要 Context
//        val phone = "(202)555-0156"
//        val formattedPhone = phone.formatPhoneNumber(this, "US")
//        val phone = "(86)13263272974"
//        val formattedPhone = phone.formatPhoneNumber(this, "CN")
//        if (formattedPhone == null) {
//            println("Phone number is not valid")
//        } else {
//            println("Sending $formattedPhone to API")
//        }
        // 仅国内
        val emails = listOf("13263272974", "12345678900", "767704339@qq.com")
        emails.forEach {
            it.run {
                println("\"$it\" 是否为有效电话号码? -> ${it.isPhoneNumber()}")
            }
        }
    }

    @Test
    fun testIsPasswordValid() {

        /**
         * 临时的密码规范: 需要至少一位特殊字符 + 数字 + 字母 +  6<length<20
         */
        fun checkPasswordStandards(pwd: String): Boolean {
            return !pwd.isAlphanumeric
                    && pwd.containsDigit
                    && pwd.containsLatinLetter
                    && pwd.length in (7..19)
        }

        val passwords =
            listOf("123456qwerty", "Congratulations", "1234567890", "123qwe", "L0LO,1006")
        passwords.forEach {
            it.run {
                println("\"$this\" 是否符合密码的要求规范? -> ${checkPasswordStandards(this)}")
            }
        }
    }

    @Test
    fun testLastPathComponent() {
        listOf("https://google.com/chrome/", "C:\\Windows\\Fonts\\font.ttf", "/dev/null", "")
            .forEach {
                println("\"$it\" 的最后一部分为: ${it.lastPathComponent}")
            }
    }
}
