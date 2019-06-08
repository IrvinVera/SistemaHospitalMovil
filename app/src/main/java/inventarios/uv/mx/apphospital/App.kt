package inventarios.uv.mx.apphospital

import androidx.multidex.MultiDexApplication
import inventarios.uv.mx.apphospital.controllers.activities.di.MiUVMVPModules
import inventarios.uv.mx.apphospital.controllers.activities.di.NavigatorModules
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.android.startKoin

class App: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("app-inventarios.realm")
            .deleteRealmIfMigrationNeeded().build()
        Realm.getInstance(config)
        Realm.setDefaultConfiguration(config)
        startKoin(this, listOf(MiUVMVPModules, NavigatorModules))
    }
}