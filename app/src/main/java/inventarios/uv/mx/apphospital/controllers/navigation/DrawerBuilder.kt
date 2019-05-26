package inventarios.uv.mx.apphospital.controllers.navigation;

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import inventarios.uv.mx.apphospital.R

public class DrawerBuilder {

    private var drawerItemLogout: PrimaryDrawerItem? = null

    fun setupLateralMenu(activity: AppCompatActivity, savedInstanceState: Bundle?, toolbar: Toolbar): DrawerBuilder {
        drawerItemLogout = PrimaryDrawerItem()
                .withIdentifier(1)
                .withIconTintingEnabled(true)
                .withIcon(R.drawable.ic_logut_black_24dp)
                .withSelectedIconColorRes(R.color.primaryTextDark)
                .withSelectedTextColorRes(R.color.primaryTextDark)
                .withTextColorRes(R.color.primaryTextDark)
                .withIconColorRes(R.color.secondaryTextDark)
                .withSelectable(false)
                .withName(R.string.drawer_item_logout)

        val drawerBuilder = DrawerBuilder()
                .withActivity(activity)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentStatusBar(true)
                .withToolbar(toolbar)
                .withHeader(R.layout.drawer_header)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(-1)
                .withSliderBackgroundColorRes(R.color.cardBackground)
                .addDrawerItems(
                        drawerItemLogout
                )

        return drawerBuilder
    }
}
