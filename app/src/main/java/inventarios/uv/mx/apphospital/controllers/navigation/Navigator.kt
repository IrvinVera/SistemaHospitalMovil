package inventarios.uv.mx.apphospital.controllers.navigation

import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import inventarios.uv.mx.apphospital.controllers.viewcontrollers.LoginController


class Navigator {

    var router:Router? = null
    fun navigateToLogin(){
        router?.setRoot(RouterTransaction.with(LoginController()))
    }

    /*fun navigateToDependencies(){
        router?.setRoot(RouterTransaction.with(DependenciesController()))
    }*/

    /*fun navigateToStockTakingHistory(dependency: Dependency){
        val stockTakingHistory = StockTakingHistoryController()
        stockTakingHistory.setDependency(dependency)
        router?.pushController(RouterTransaction.with(stockTakingHistory))
    }*/
}