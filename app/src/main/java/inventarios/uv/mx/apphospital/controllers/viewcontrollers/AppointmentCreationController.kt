package inventarios.uv.mx.apphospital.controllers.viewcontrollers

import android.app.Activity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.jaredrummler.materialspinner.MaterialSpinner
import inventarios.uv.mx.apphospital.R
import inventarios.uv.mx.apphospital.controllers.events.AppointmentUploadEvent
import inventarios.uv.mx.apphospital.controllers.viewcontrollers.generics.GenericController
import inventarios.uv.mx.apphospital.model.utils.LogUtils
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.security.cert.CertPathValidatorException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AppointmentCreationController: GenericController() {

    override var debugTag = this.javaClass.simpleName
    override var controllerId = R.layout.controller_create_appointment

    //...................override val manager: ReasonManager by inject()

    //...................val stocktakingManager: StocktakingManager by inject()

    //...................private var dependency: Dependency? = null

    @BindView(R.id.viewLoadingCreateStock)
    lateinit var  viewLoadingCreateStock : LinearLayout

    @BindView(R.id.textFieldOfficingNumber)
    lateinit var textFieldOfficingNumber: TextInputLayout

    @BindView(R.id.textFieldDependencyManager)
    lateinit var textFieldDependencyManager: TextInputLayout

    @BindView(R.id.spinnerReasons)
    lateinit var spinnerReasons: MaterialSpinner

    @BindView(R.id.buttonCreateStock)
    lateinit var buttonCreateStock: Button

    @BindView(R.id.labelDate)
    lateinit var labelDate: TextView

    @BindView(R.id.labelOfficingNumber)
    lateinit var labelOfficingNumber: TextView

    @BindView(R.id.labelReason)
    lateinit var labelReason: TextView

    @BindView(R.id.labelDependencyManager)
    lateinit var labelDependencyManager: TextView

    var itemReasons: MutableList<String>? = null

    var content: MutableList<CertPathValidatorException.Reason>? = null

    /*fun setDependency(dependency: Dependency){
        this.dependency = dependency
    }*/

    override fun setupListeners() {
        super.setupListeners()
        textFieldDependencyManager.editText?.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                hideKeyboard()
                //login()
            }
            false
        }
    }


    override fun setupUI() {
        itemReasons = ArrayList()
        val targetFormat = SimpleDateFormat("dd-MMMM-yyyy")
        labelDate.text = labelDate.text.toString()+" "+targetFormat.format(Calendar.getInstance().time)
    }

    override fun loadContentExtension(firstLoading: Boolean) {
        /*content = manager.load() as MutableList<CertPathValidatorException.Reason>
        content?.forEach{ item ->
            itemReasons?.add(item.description.toString())
        }*/
        if (itemReasons?.isNotEmpty() == true) {
            showContentUi()
        } else {
            if (firstLoading) {
                showLoadingUi()
            } else {
                //desactivar spinner
            }
        }
    }


    override fun showContentExtension() {
            spinnerReasons.setItems(itemReasons!!)

    }

    override fun fetchContentExtension() {
       /* val existsContent= manager.load() as ArrayList<CertPathValidatorException.Reason>
        if (existsContent.isNullOrEmpty()) {
            manager.fetchAll()
        }else{
            loadContentExtension(false)
        }*/
        loadContentExtension()
    }

    override fun forceFetchContentExtension() {
    }

    @OnClick(R.id.buttonCreateStock)
    fun buttonCrateStockOnClick() {
        createStock()
    }

    private fun createStock(){
        /*ui.launch {
            if(validateFields()) {
                val key = VerifierManager().getVerifier()?.key
                val reasonId = content?.get(spinnerReasons.selectedIndex)?.id
                val dependencyId = dependency?.id
                textFieldOfficingNumber.visibility = View.GONE
                textFieldDependencyManager.visibility = View.GONE
                spinnerReasons.visibility = View.GONE
                buttonCreateStock.visibility = View.GONE
                labelOfficingNumber.visibility = View.GONE
                labelReason.visibility = View.GONE
                labelDependencyManager.visibility = View.GONE
                labelDate.visibility = View.GONE
                viewLoadingCreateStock.visibility = View.VISIBLE
                val stocktaking = StockTaking()
                stocktaking.officingNumber = textFieldOfficingNumber.editText?.text.toString().toLong()
                stocktaking.managerName = textFieldDependencyManager.editText?.text.toString()
                stocktaking.createdAt = Calendar.getInstance().time
                stocktaking.dependencyId = dependencyId
                stocktaking.reasonDescription = content?.get(spinnerReasons.selectedIndex)?.description
                bg.async {
                    stocktakingManager.postStocktaking(dependencyId.toString(), key.toString(), reasonId.toString(), stocktaking)
                }.await()
            }

            if (isAttached) {
                textFieldOfficingNumber.visibility = View.VISIBLE
                textFieldDependencyManager.visibility = View.VISIBLE
                spinnerReasons.visibility = View.VISIBLE
                buttonCreateStock.visibility = View.VISIBLE
                labelOfficingNumber.visibility = View.VISIBLE
                labelReason.visibility = View.VISIBLE
                labelDependencyManager.visibility = View.VISIBLE
                labelDate.visibility = View.VISIBLE
                viewLoadingCreateStock.visibility = View.GONE
            }
        }*/
    }


    private fun validateFields(): Boolean {

        if (!validateField(textFieldOfficingNumber)) {
            try {
                this.view?.let { Snackbar.make(it, "Se necisita numero de oficio", Snackbar.LENGTH_SHORT).show() }
            } catch (ex: Exception) {
                LogUtils.Error(debugTag, ex)
            }
            return false
        }

        if (!validateField(textFieldDependencyManager)) {
            try {
                this.view?.let { Snackbar.make(it, "Se necesita encargado", Snackbar.LENGTH_SHORT).show() }
            } catch (ex: Exception) {
                LogUtils.Error(debugTag, ex)
            }
            return false
        }

        if (spinnerReasons.selectedIndex == null) {
            try {
                this.view?.let { Snackbar.make(it, "Seleccione un motivo", Snackbar.LENGTH_SHORT).show() }
            } catch (ex: Exception) {
                LogUtils.Error(debugTag, ex)
            }
            return false
        }

        return true
    }


    private fun validateField(textInputLayout: TextInputLayout?): Boolean =
            !textInputLayout?.editText?.text.toString().isEmpty()

    private fun hideKeyboard() {
        activity?.let {
            val inputMethodManager: InputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = activity?.currentFocus
            if (view == null) {
                view = View(activity)
            }
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /*@Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onEvent(event: ReasonsDownloadEvent) {
        if (isAttached && event.success) {
            reloadContentAsync()
        } else if (isAttached && !event.success) {
            if (itemReasons?.isEmpty() == true) {
                //desactivar spiner
            } else {
                hideLoadingUi()
            }
        }
    }*/

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: AppointmentUploadEvent) {
        if (isAttached && event.success) {
            navigator.navigateToLogin()
        } else if (isAttached && !event.success) {
            if (itemReasons?.isEmpty() == true) {
                //mostrar que necesita conección
            } else {
                hideLoadingUi()
            }
        }
    }

}