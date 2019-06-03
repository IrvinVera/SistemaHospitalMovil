package inventarios.uv.mx.apphospital.controllers.navigation

import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import inventarios.uv.mx.apphospital.controllers.viewcontrollers.AppointmentController
import inventarios.uv.mx.apphospital.controllers.viewcontrollers.AppointmentCreationController
import inventarios.uv.mx.apphospital.controllers.viewcontrollers.LoginController


class Navigator {

    var router:Router? = null
    fun navigateToLogin(){
        router?.setRoot(RouterTransaction.with(LoginController()))
    }

    fun navigateToAppointment(){
        router?.setRoot(RouterTransaction.with(AppointmentController()))
    }

    fun navigateToAppointmentCreation(){
        router?.pushController(RouterTransaction.with(AppointmentCreationController()))
    }

    /*fun navigateToStockTakingHistory(dependency: Dependency){
        val stockTakingHistory = StockTakingHistoryController()
        stockTakingHistory.setDependency(dependency)
        router?.pushController(RouterTransaction.with(stockTakingHistory))
    }*/
}