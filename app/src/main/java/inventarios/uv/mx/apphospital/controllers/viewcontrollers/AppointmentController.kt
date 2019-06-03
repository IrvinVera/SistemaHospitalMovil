package inventarios.uv.mx.apphospital.controllers.viewcontrollers

import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import inventarios.uv.mx.apphospital.R
import inventarios.uv.mx.apphospital.controllers.adapters.AppointmentItem
import inventarios.uv.mx.apphospital.controllers.events.AppointmentDownloadEvent
import inventarios.uv.mx.apphospital.controllers.viewcontrollers.generics.GenericListController
import inventarios.uv.mx.apphospital.model.entities.Appointment
import inventarios.uv.mx.apphospital.model.managers.SessionManager
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class AppointmentController : GenericListController() {

    override var debugTag = this.javaClass.simpleName

    //....................override val manager: DependencyManager by inject()
    val sessionManager = SessionManager()

    var fastAdapter: FastAdapter<AppointmentItem>? = null
    var itemAdapter: ItemAdapter<AppointmentItem>? = null
    var adapterItems: MutableList<AppointmentItem>? = null
    var content: MutableList<Appointment>? = null

    var swipeRefreshEnabled: Boolean = true

    fun setupFloatingButton(){
        fbuttonNewStock?.show()
        fbuttonNewStock?.setOnClickListener { view ->
            /*dependency?.let {
                navigator.navigateToCreateStockTaking(it)
            }*/
            navigator.navigateToAppointmentCreation()
        }
    }

    override fun setupUI() {
        super.setupUI()
        setupFloatingButton()
        content = ArrayList()
        adapterItems = ArrayList()

        itemAdapter = ItemAdapter()
        fastAdapter = FastAdapter.with(itemAdapter)

        fastAdapter?.setHasStableIds(true)

        list?.adapter = fastAdapter
        list?.setHasFixedSize(false)

        if (list?.itemDecorationCount ?: 0 > 0) {
            list?.removeItemDecorationAt(0)
        }
    }


    override fun setupListeners() {
        super.setupListeners()
        //swipeRefresh?.isEnabled = swipeRefreshEnabled

        fastAdapter?.withSelectable(true)
        fastAdapter?.withOnClickListener { v, adapter, item, position ->
            adapterItems?.getOrNull(position)?.item?.let {
                //navigator.navigateToStockTakingHistory(it)
            }
            true
        }
    }

    override fun fetchContentExtension() {
        /*val existsContent= manager.load() as ArrayList<Dependency>
        if (existsContent.isNullOrEmpty()) {
            manager.fetchAll()
        }else{
            loadContentExtension(false)
        }*/
        loadContentExtension(false)
    }

    override fun forceFetchContentExtension() {
        //manager.fetchAll()
    }

    override fun loadContentExtension(firstLoading: Boolean) {
        /*adapterItems?.clear()

        content = manager.load() as MutableList<Dependency>

        content?.forEach { item ->

            var adapterItem = AppointmentItem()
            adapterItem.item = item

            item?.id?.let { adapterItem.withIdentifier(it) }

            adapterItems?.add(adapterItem)
        }

        adapterItems = adapterItems?.toSet()?.toMutableList()*/


        if (adapterItems?.isNotEmpty() == true) {
            showContentUi()
        } else {
            if (firstLoading) {
                showLoadingUi()
            } else {
                showEmptyUi()
            }
        }
    }

    override fun showContentExtension() {
        itemAdapter?.adapterItems?.let {
            //FastAdapterDiffUtil.set(itemAdapter, adapterItems)
            itemAdapter?.setNewList(adapterItems)
        } ?: run {
            itemAdapter?.set(adapterItems)
        }
    }

    override fun showEmptyExtension() {
        txtProgress?.setText(R.string.txt_no_appointments)
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onEvent(event: AppointmentDownloadEvent) {
        if (isAttached && event.success) {
            reloadContentAsync()
        } else if (isAttached && !event.success) {
            if (adapterItems?.isEmpty() == true) {
                showEmptyUi()
            } else {
                hideLoadingUi()
            }
        }
    }
}