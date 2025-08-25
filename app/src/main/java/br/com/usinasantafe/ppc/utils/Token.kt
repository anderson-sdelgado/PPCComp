package br.com.usinasantafe.ppc.utils

import com.google.common.base.Strings
import java.math.BigInteger

import java.security.MessageDigest
import java.util.Locale
import kotlin.text.toByteArray
import kotlin.text.uppercase

fun token(
    number: Long,
    version: String,
    idServ: Int,
): String {
    var token = "PPC-$version-$number-$idServ"
    val messageDigest = MessageDigest.getInstance("MD5")
    messageDigest.update(token.toByteArray(), 0, token.length)
    val bigInteger = BigInteger(1, messageDigest.digest())
    val str = bigInteger.toString(16).uppercase(Locale.getDefault())
    token = Strings.padStart(str, 32, '0')
    return "Bearer $token"
}