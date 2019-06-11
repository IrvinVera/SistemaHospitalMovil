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
import androidx.recyclerview.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.bluelinelabs.conductor.Controller
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.analytics.FirebaseAnalytics
import inventarios.uv.mx.apphospital.R
import inventarios.uv.mx.apphospital.controllers.activities.di.inject
import inventarios.uv.mx.apphospital.controllers.events.GenericDownloadEvent
import inventarios.uv.mx.apphospital.controllers.navigation.Navigator
import inventarios.uv.mx.apphospital.model.managers.GenericManager
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


abstract class GenericListController: Controller() {
    open var debugTag = this.javaClass.simpleName
    open var controllerId = R.layout.controller_generic_list

    var firebaseAnalytics: FirebaseAnalytics? = null

    var unbinder: Unbinder? = null

    private val viewJob = Job()
    private val bgJob = Job()

    val ui = CoroutineScope(Dispatchers.Main + viewJob)
    val bg = CoroutineScope(Dispatchers.IO + bgJob)

    val navigator : Navigator by inject()

    @BindView(R.id.swipe_refresh)
    @JvmField
    var swipeRefresh: SwipeRefreshLayout? = null

    @BindView(R.id.list_items)
    @JvmField
    var list: RecyclerView? = null

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

    @BindView(R.id.fbuttonNewStock)
    @JvmField
    var fbuttonNewStock: FloatingActionButton? = null

    open val manager: GenericManager? = null

    //var searchQuery: SearchQuery? = null

    override fun onCreateView(@NonNull inflater: LayoutInflater, @NonNull container: ViewGroup): View {
        val view = inflater.inflate(controllerId, container, false)
        unbinder = ButterKnife.bind(this, view)

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
        showLoadingUi()
        //loadContentAsync()
    }

    override fun onDetach(view: View) {
        EventBus.getDefault().unregister(this)
        super.onDetach(view)
    }

    override fun onDestroyView(view: View) {
        //list?.adapter = null

        list?.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                // no-op
            }

            override fun onViewDetachedFromWindow(v: View) {
                list?.adapter = null
            }
        })

        unbinder?.unbind()
        super.onDestroyView(view)
    }

    @UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
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
        fbuttonNewStock?.hide()
        list?.layoutManager = StaggeredGridLayoutManager(1, 1)

        val dividerItemDecoration = DividerItemDecoration(
                list?.context,
                (list?.layoutManager as StaggeredGridLayoutManager).orientation
        )
        //list.addItemDecoration(dividerItemDecoration)
        list?.removeItemDecoration(dividerItemDecoration)

        list?.layoutManager = LinearLayoutManager(activity)
        list?.itemAnimator = DefaultItemAnimator()
    }

    open fun setupStyles() {
        swipeRefresh?.setColorSchemeResources(
                R.color.colorPrimaryDark,
                R.color.colorAccentDark
        )
    }

    open fun setupListeners() {
        swipeRefresh?.setOnRefreshListener {
            forceFetchContentAsync()
        }
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

   /* fun searchContentAsync(searchQuery: SearchQuery?) {
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
        showContentUi()
    }

    fun showEmptyUi() {
        if (isAttached) {
            ui.launch {
                fbuttonNewStock?.show()
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
                swipeRefresh?.isRefreshing = false
                viewProgress?.visibility = View.GONE
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

    fun onGenericEvent(event: GenericDownloadEvent) {
        if (isAttached && event.success) {
            loadContentAsync()
        } else if (isAttached && !event.success) {
            updateContentUi()
        }
    }
}