package br.com.usinasantafe.ppc

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import kotlin.jvm.java

//class CustomTestRunner : AndroidJUnitRunner() {
//    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
//        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
//    }
//}

class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl,  PPCTestApplication_Application::class.java.name, context)
    }
}
