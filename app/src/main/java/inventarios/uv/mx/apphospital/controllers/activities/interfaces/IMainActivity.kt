package inventarios.uv.mx.apphospital.controllers.activities.interfaces

import inventarios.uv.mx.apphospital.controllers.activities.presenter.MainActivityPresenter

interface IMainActivity {
    fun login()

    fun changeToolbarVisibility(visibility: MainActivityPresenter.ToolbarVisibilityType)
    fun changeDrawerVisibility(visibility: MainActivityPresenter.DrawerVisibilityType)
    fun changeBottomSheepVisibility(visibility: MainActivityPresenter.BottomSheepVisibilityType)
}