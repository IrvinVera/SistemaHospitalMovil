package inventarios.uv.mx.apphospital.model.utils

import inventarios.uv.mx.apphospital.BuildConfig


object LogUtils {

    @JvmStatic
    fun Debug(tag: String, exception: Exception) {
        Log(tag, exception, android.util.Log.DEBUG)
    }

    @JvmStatic
    fun Error(tag: String, exception: Exception) {
        Log(tag, exception, android.util.Log.ERROR)
    }

    @JvmStatic
    fun Info(tag: String, exception: Exception) {
        Log(tag, exception, android.util.Log.INFO)
    }

    @JvmStatic
    fun Verbose(tag: String, exception: Exception) {
        Log(tag, exception, android.util.Log.VERBOSE)
    }

    @JvmStatic
    fun Warn(tag: String, exception: Exception) {
        Log(tag, exception, android.util.Log.WARN)
    }

    @JvmStatic
    fun Log(tag: String, exception: Exception, severity: Int) {
        if (BuildConfig.DEBUG) {
            if (exception.message != null) {
                exception.printStackTrace()
                android.util.Log.println(severity, tag, exception.message)
            }
        }
    }

    @JvmStatic
    fun Message(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            android.util.Log.i(tag, message)
        }
    }

    @JvmStatic
    fun errorMessage(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            android.util.Log.e(tag, message)
        }
    }
}