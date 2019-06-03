package inventarios.uv.mx.apphospital

import androidx.multidex.MultiDexApplication
import inventarios.uv.mx.apphospital.controllers.activities.di.MiUVMVPModules
import inventarios.uv.mx.apphospital.controllers.activities.di.NavigatorModules
import org.koin.android.ext.android.startKoin

class App: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(MiUVMVPModules, NavigatorModules))
    }
}