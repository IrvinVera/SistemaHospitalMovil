package inventarios.uv.mx.apphospital.controllers.activities.di

import inventarios.uv.mx.apphospital.controllers.activities.presenter.MainActivityPresenter
import inventarios.uv.mx.apphospital.controllers.navigation.Navigator
import org.koin.core.KoinContext
import org.koin.core.parameter.ParameterDefinition
import org.koin.core.parameter.emptyParameterDefinition
import org.koin.core.scope.Scope
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext

inline fun <reified T : Any> inject(
        name: String = "",
        scope: Scope? = null,
        noinline parameters: ParameterDefinition = emptyParameterDefinition()
) = lazy { get<T>(name, scope, parameters) }

inline fun <reified T : Any> get(
        name: String = "",
        scope: Scope? = null,
        noinline parameters: ParameterDefinition = emptyParameterDefinition()
): T = getKoin().get(name, scope, parameters)

fun getKoin(): KoinContext = StandAloneContext.getKoin().koinContext

val MiUVMVPModules = module {
    single { MainActivityPresenter() }
}

val NavigatorModules = module{
    single { Navigator() }
}