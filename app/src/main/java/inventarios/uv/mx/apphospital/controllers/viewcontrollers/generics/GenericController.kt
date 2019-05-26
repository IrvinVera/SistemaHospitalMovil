package inventarios.uv.mx.apphospital.controllers.viewcontrollers.generics

import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.bluelinelabs.conductor.Controller
import com.google.firebase.analytics.FirebaseAnalytics
import inventarios.uv.mx.apphospital.R
import inventarios.uv.mx.apphospital.controllers.activities.presenter.MainActivityPresenter
import inventarios.uv.mx.apphospital.controllers.navigation.Navigator
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class GenericController: Controller() {
    open var debugTag = this.javaClass.simpleName
    open var controllerId = R.layout.controller_generic

    var firebaseAnalytics: FirebaseAnalytics? = null

    var unbinder: Unbinder? = null

    private val viewJob = Job()
    private val bgJob = Job()

    val ui = CoroutineScope(Dispatchers.Main + viewJob)
    val bg = CoroutineScope(Dispatchers.IO + bgJob)

    val presenter = MainActivityPresenter()
    val studentsNavigator = Navigator()

    @BindView(R.id.swipe_refresh)
    @JvmField
    var swipeRefresh: SwipeRefreshLayout? = null

    @BindView(R.id.view_progress)
    @JvmField
    var viewProgress: LinearLayout? = null

    @BindView(R.id.img_progress_empty)
    @JvmField
    var imgProgress: ImageView? = null

    @BindView(R.id.progress_bar)
    @JvmField
    var progressBar: ProgressBar? = null

    @BindView(R.id.txt_progress)
    @JvmField
    var txtProgress: TextView? = null



    override fun onCreateView(@NonNull inflater: LayoutInflater, @NonNull container: ViewGroup): View {
        val view = inflater.inflate(controllerId, container, false)
        unbinder = ButterKnife.bind(this, view)

        preloadContent()
        setupToolbar()
        setupUI()
        setupStyles()
        setupListeners()
        setupFirebase()

        return view
    }

    override fun onAttach(view: View) {
        EventBus.getDefault().register(this)
        super.onAttach(view)
        fetchContentAsync()
        loadContentAsync()
    }

    override fun onDetach(view: View) {
        EventBus.getDefault().unregister(this)
        super.onDetach(view)
    }

    override fun onDestroyView(view: View) {
        unbinder?.unbind()
        super.onDestroyView(view)
    }

    override fun onDestroy() {
        bg.cancel()
        ui.cancel()
        super.onDestroy()
    }

    fun setupFirebase() {
        this.activity?.let {
            firebaseAnalytics = FirebaseAnalytics.getInstance(it)
            firebaseAnalytics?.setCurrentScreen(it, debugTag, null)
        }
    }

    open fun setupToolbar() {
    }

    open fun setupUI() {
    }

    open fun setupStyles() {
    }

    open fun setupListeners() {
    }

    open fun preloadContent() {
    }

    fun loadContentAsync(firstLoading: Boolean = true) {
        ui.launch {
            bg.async {
                loadContentExtension(firstLoading)
            }
        }
    }

    open fun loadContentExtension(firstLoading: Boolean = false) {}

    /*fun searchContentAsync(searchQuery: SearchQuery?) {
        ui.launch {
            bg.async {
                searchContentExtension(searchQuery)
            }
        }
    }

    open fun searchContentExtension(searchQuery: SearchQuery?) {}*/


    fun reloadContentAsync() {
        loadContentAsync(false)
    }

    fun fetchContentAsync() {
        if (isAttached) {
            ui.launch {
                bg.async {
                    fetchContentExtension()
                }
            }
        }
    }

    open fun fetchContentExtension() {}

    fun forceFetchContentAsync() {
        if (isAttached) {
            ui.launch {
                bg.async {
                    forceFetchContentExtension()
                }
            }
        }
    }

    open fun forceFetchContentExtension() {}

    fun showLoadingUi() {
        if (isAttached) {
            ui.launch {
                swipeRefresh?.isRefreshing = false
                txtProgress?.visibility = View.GONE
                imgProgress?.visibility = View.GONE
                progressBar?.visibility = View.VISIBLE
                viewProgress?.visibility = View.VISIBLE
            }
        }
    }

    fun showContentUi() {
        if (isAttached) {
            ui.launch {
                swipeRefresh?.isRefreshing = false
                viewProgress?.visibility = View.GONE
                showContentExtension()
            }
        }
    }

    open fun showContentExtension() {}

    fun updateContentUi() {
        showContentExtension()
    }

    fun showEmptyUi() {
        if (isAttached) {
            ui.launch {
                swipeRefresh?.isRefreshing = false
                progressBar?.visibility = View.GONE
                txtProgress?.visibility = View.VISIBLE
                txtProgress?.setText(R.string.txt_content_unavailable)
                //imgProgress?.loadImage(R.drawable.img_empty_320dp_color)
                imgProgress?.visibility = View.VISIBLE
                viewProgress?.visibility = View.VISIBLE

                showEmptyExtension()
            }
        }
    }

    open fun showEmptyExtension() {}

    fun hideLoadingUi() {
        if (isAttached) {
            ui.launch {

            }
        }
    }

    fun showNoInternetUi() {
        if (isAttached) {
            ui.launch {

                viewProgress?.visibility = View.VISIBLE
                progressBar?.visibility = View.GONE
                txtProgress?.setText(R.string.txt_no_internet)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onEvent(event: CalendarContract.Events) {

    }

    /*fun onGenericEvent(event: GenericDownloadEvent) {
        if (isAttached && event.success) {
            loadContentAsync()
        } else if (isAttached && !event.success) {
            updateContentUi()
        }
    }*/
}