package inventarios.uv.mx.apphospital.controllers.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.google.android.material.appbar.AppBarLayout
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import inventarios.uv.mx.apphospital.R
import inventarios.uv.mx.apphospital.controllers.activities.interfaces.IMainActivity
import inventarios.uv.mx.apphospital.controllers.activities.presenter.MainActivityPresenter
import inventarios.uv.mx.apphospital.controllers.navigation.DrawerBuilder
import inventarios.uv.mx.apphospital.controllers.navigation.Navigator
import inventarios.uv.mx.apphospital.model.managers.SessionManager
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), IMainActivity {

    private val viewJob = Job()
    private val bgJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewJob)
    private val bgScope = CoroutineScope(Dispatchers.IO + bgJob)

    private var router: Router? = null


    var loggedIn = false

    //var user: User? = null

    private var isActivityVisible = false

    val presenter = MainActivityPresenter()
    private val sessionManager = SessionManager()

    //private val userManager: UserManager by inject()

    private val navigator = Navigator()

    private var savedInstanceStateNull = true

    @BindView(R.id.controller_container)
    lateinit var controllerContainer: ChangeHandlerFrameLayout

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.appbar_layout)
    lateinit var appbar: AppBarLayout

    private var drawerBuilder: com.mikepenz.materialdrawer.DrawerBuilder? = null
    private var accountHeader: AccountHeader? = null
    private var drawer: Drawer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        setupUi(savedInstanceState)
        setupRouter(savedInstanceState)

        savedInstanceStateNull = savedInstanceState == null
    }

    override fun onResume() {
        //EventBus.getDefault().register(this)
        super.onResume()
        navigator.router = router

        presenter.activity = this

        isActivityVisible = true

        loggedIn = sessionManager.isLoggedIn()
        //user = userManager.getUser()

        /*ui.launch {
            bg.async {
                campusesManager.load()?.forEach { campus ->
                    campus.id?.let { employeesManager.fetch(it) }
                }
            }
        }*/

        setupUiProfile()
    }

    override fun onPause() {
        navigator.router = null
        presenter.activity = null
        isActivityVisible = false
        //EventBus.getDefault().unregister(this)
        super.onPause()
    }

    fun setupUi(savedInstanceState: Bundle?){
        setupToolbar()
        setupLateralMenu(savedInstanceState)
        //setupBottomBar()
        //setupListeners()
        //setupLateralMenu(savedInstanceState)
    }

    fun setupRouter(savedInstanceState: Bundle?){
        router = controllerContainer?.let { Conductor.attachRouter(this, it, savedInstanceState) }
        navigator.router = router
    }

    fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        toolbar.title = getString(R.string.app_name)
    }


    override fun onBackPressed() {
        if (!router!!.handleBack()){
            super.onBackPressed()
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean{
        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var intent: Intent? = null
        if (item != null) {
            when(item.itemId){
                R.id.logout-> {
                    uiScope.launch {
                        bgScope.async {
                            SessionManager().logout()
                        }.await()
                        router?.setRoot(RouterTransaction.with(LoginController()))
                    }
                    return true
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }*/

    override fun login() {
        loggedIn = sessionManager.isLoggedIn()
        //user = userManager.getUser()

        setupUiProfile()
    }

    private fun logout() {
        uiScope.launch {
            bgScope.async {
                SessionManager().logout()
            }.await()
            disableTopBar()
            disableDrawer()
            navigator.navigateToLogin()
        }
    }

    fun setupUiProfile(){
        if (loggedIn){
            setupToolbar()
            setupLateralMenu(null)
            enableTopBar()
            enableDrawer()
            //navigator.navigateToDependencies()
        }else{
            disableTopBar()
            disableDrawer()
            navigator.navigateToLogin()
        }
    }

    private fun setupLateralMenu(savedInstanceState: Bundle?) {
        setupLateralContent()
        createDrawer(savedInstanceState)
    }

    private fun setupLateralContent() {
        accountHeader = AccountHeaderBuilder()
            .withActivity(this)
            .withHeaderBackground(R.drawable.img_header)
            .build()
    }

    fun createDrawer(savedInstanceState: Bundle?) {
        val profileDrawerBuilder = DrawerBuilder()
        drawerBuilder =  profileDrawerBuilder.setupLateralMenu(this, savedInstanceState, toolbar)
            .withOnDrawerItemClickListener { view, position, drawerItem ->
                appbar.setExpanded(true)
                when (drawerItem.identifier) {
                    1L -> logout()
                }
                false
            }

        accountHeader?.let {
            drawerBuilder?.withAccountHeader(it)
        }

        drawer = drawerBuilder?.build()
    }

    private fun enableTopBar() {
        changeToolbarVisibility(MainActivityPresenter.ToolbarVisibilityType.VISIBLE)
    }

    private fun  disableTopBar() {
        changeToolbarVisibility(MainActivityPresenter.ToolbarVisibilityType.HIDDEN)
    }

    override fun changeToolbarVisibility(visibility: MainActivityPresenter.ToolbarVisibilityType) {
        when (visibility) {
            MainActivityPresenter.ToolbarVisibilityType.VISIBLE -> {
                supportActionBar?.show()
            }
            MainActivityPresenter.ToolbarVisibilityType.HIDDEN -> {
                supportActionBar?.hide()
            }
        }
    }


    private fun enableDrawer() {
        changeDrawerVisibility(MainActivityPresenter.DrawerVisibilityType.COLLAPSED)
    }

    private fun disableDrawer() {
        changeDrawerVisibility(MainActivityPresenter.DrawerVisibilityType.HIDDEN)
    }

    override fun changeDrawerVisibility(visibility: MainActivityPresenter.DrawerVisibilityType) {
        when (visibility) {
            MainActivityPresenter.DrawerVisibilityType.COLLAPSED -> {
                appbar.setExpanded(true)

                val drawerLayout = drawer?.drawerLayout
                drawerLayout?.setDrawerLockMode(androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED)

                toolbar.visibility = View.VISIBLE
            }
            MainActivityPresenter.DrawerVisibilityType.HIDDEN -> {
                supportActionBar?.hide()
            }
        }
    }

    override fun changeBottomSheepVisibility(visibility: MainActivityPresenter.BottomSheepVisibilityType) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
