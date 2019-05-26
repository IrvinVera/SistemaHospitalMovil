package inventarios.uv.mx.apphospital.controllers.activities.presenter

import inventarios.uv.mx.apphospital.controllers.activities.interfaces.IMainActivity

class MainActivityPresenter {
    var activity: IMainActivity? = null

    var toolbarVisibilityType: ToolbarVisibilityType? =
            ToolbarVisibilityType.VISIBLE
    var drawerVisibilityType: DrawerVisibilityType? =
            DrawerVisibilityType.HIDDEN
    var bottomSheepVisibilityType: BottomSheepVisibilityType? =
            BottomSheepVisibilityType.COLLAPSED

    fun restoreToolbarVisibility() {
        toolbarVisibilityType?.let { changeToolbarVisibility(it) }
    }

    fun changeToolbarVisibility(visibility: ToolbarVisibilityType) {
        toolbarVisibilityType = visibility
        activity?.changeToolbarVisibility(visibility)
    }

    fun restoreBottomSheepVisibility() {
        bottomSheepVisibilityType?.let { changeBottomSheepVisibility(it) }
    }

    fun changeBottomSheepVisibility(visibility: BottomSheepVisibilityType) {
        bottomSheepVisibilityType = visibility
        activity?.changeBottomSheepVisibility(visibility)
    }


    fun login() {
        activity?.login()
    }

    enum class ToolbarVisibilityType {
        HIDDEN,
        VISIBLE
    }

    enum class DrawerVisibilityType {
        HIDDEN,
        COLLAPSED,
        EXPANDED
    }

    enum class BottomSheepVisibilityType {
        HIDDEN,
        COLLAPSED,
        EXPANDED
    }

    enum class BottomNavigationVisibilityType {
        HIDDEN,
        VISIBLE
    }
}