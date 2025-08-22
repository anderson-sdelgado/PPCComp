package br.com.usinasantafe.ppc.utils

import kotlin.collections.indices
import kotlin.text.contains
import kotlin.text.endsWith
import kotlin.text.isBlank
import kotlin.text.startsWith
import kotlin.text.substringAfter
import kotlin.text.substringAfterLast
import kotlin.text.substringBefore

fun getClassAndMethod(): String {
    val stackTrace = Thread.currentThread().stackTrace
    val utilClassName = "br.com.usinasantafe.$BASE_SHARE_PREFERENCES.utils.ClassAndMethodKt"

    for (i in stackTrace.indices) {
        val element = stackTrace[i]

        if (element.className.startsWith("java.lang.Thread") ||
            element.methodName == "getStackTrace" ||
            element.methodName == "getClassAndMethod" ||
            element.className == utilClassName) {
            continue
        }

        if (element.className.startsWith("kotlin.coroutines") ||
            element.className.contains("Continuation") ||
            element.className.contains("SuspendLambda") ||
            element.methodName.contains("\$resumeWith") ||
            element.methodName.endsWith("\$suspendImpl")) {
            continue
        }

        if (element.className.contains("br.com.usinasantafe") && element.className != utilClassName) {
            val rawClassName = element.className.substringAfterLast('.')
            val className = rawClassName.substringBefore('$')

            var methodName = if (rawClassName.contains('$')) {
                rawClassName.substringAfter("$className$", "")
                    .substringBefore('$')
            } else {
                element.methodName
            }

            if (methodName.isBlank() || methodName == "invoke" || methodName == "invokeSuspend") {
                methodName = element.methodName
            }

            methodName = methodName.substringBefore('$')
            methodName = methodName.substringBefore('-')

            val methodPart = if (methodName == "invoke" || methodName == "invokeSuspend") {
                if (className == methodName) ""
                else ""
            } else {
                ".$methodName"
            }

            return "$className$methodPart"
        }
    }
    return "Classe.MetodoDesconhecido"
}