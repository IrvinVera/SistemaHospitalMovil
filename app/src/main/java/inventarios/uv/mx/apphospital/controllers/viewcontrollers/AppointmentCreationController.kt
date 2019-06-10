package inventarios.uv.mx.apphospital.controllers.viewcontrollers

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import inventarios.uv.mx.apphospital.R
import inventarios.uv.mx.apphospital.controllers.events.AppointmentDownloadEvent
import inventarios.uv.mx.apphospital.controllers.events.AppointmentUploadEvent
import inventarios.uv.mx.apphospital.controllers.viewcontrollers.generics.GenericController
import inventarios.uv.mx.apphospital.model.entities.Appointment
import inventarios.uv.mx.apphospital.model.entities.Persona
import inventarios.uv.mx.apphospital.model.managers.AppointmentManager
import inventarios.uv.mx.apphospital.model.managers.PersonaManager
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*


class AppointmentCreationController: GenericController() {

    override var debugTag = this.javaClass.simpleName
    override var controllerId = R.layout.controller_create_appointment

    val manager = AppointmentManager()

    private var persona: Persona? = null

    @BindView(R.id.viewLoadingCreateStock)
    lateinit var  viewLoadingCreateStock : LinearLayout

    @BindView(R.id.buttonAppointment)
    lateinit var buttonCreateAppointment: Button

    @BindView(R.id.labelNoPacientes)
    lateinit var labelNoPacientes: TextView

    @BindView(R.id.labelFechaCreacion)
    lateinit var labelFechaCreacion: TextView

    var noPacientes: String = ""

    var content: MutableList<Appointment>? = null

    /*fun setDependency(dependency: Dependency){
        this.dependency = dependency
    }*/

    override fun setupListeners() {
        super.setupListeners()
    }

    override fun setupUI() {
        val targetFormat = SimpleDateFormat("dd-MMMM-yyyy")
        labelFechaCreacion.text = "Fecha: "+targetFormat.format(Calendar.getInstance().time)
    }

    override fun loadContentExtension(firstLoading: Boolean) {
        /*content = manager.loadNoPacientes() as MutableList<Appointment> Aqui va el de loadNoPacientes
        content?.forEach{ item ->
            noPacientes = item.noPacientes.toString()
        }*/
        if (noPacientes != "") {
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
            //spinnerReasons.setItems(itemReasons!!)
        //labelNoPacientes.text = "Número de pacientes en espera: "+noPacientes

    }

    override fun fetchContentExtension() {
       /*val existsContent= manager.loadNoPacientes() as ArrayList<Appointment>
        if (existsContent.isNullOrEmpty()) {
            manager.fetchAppointmentNumberPatients()
        }else{
            loadContentExtension(false)
        }*/
    }

    override fun forceFetchContentExtension() {
    }

    @OnClick(R.id.buttonAppointment)
    fun buttonCrateAppointmentOnClick() {
        createAppointment()
    }

    private fun createAppointment(){
        persona = PersonaManager().getUser()
        val myAlertDialog = AlertDialog.Builder(this.activity)
        myAlertDialog.setTitle("Reservar consulta")
        myAlertDialog.setMessage("¿Seguro que deseas reservar una consulta para el dia de hoy?")
        myAlertDialog.setPositiveButton("OK", DialogInterface.OnClickListener { arg0, arg1 ->
            this.view?.let { Snackbar.make(it, R.string.txt_appointment_creado, Snackbar.LENGTH_SHORT).show() }
            navigator.navigateToAppointment()
        })
        myAlertDialog.setNegativeButton("Cancelar", DialogInterface.OnClickListener { arg0, arg1 ->
        })
        myAlertDialog.show()
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


    /*private fun validateFields(): Boolean {

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
    }*/


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

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onEvent(event: AppointmentDownloadEvent) {
        if (isAttached && event.success) {
            reloadContentAsync()
        } else if (isAttached && !event.success) {
            if (noPacientes == "") {
                //desactivar spiner
            } else {
                hideLoadingUi()
            }
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: AppointmentUploadEvent) {
        if (isAttached && event.success) {
            this.view?.let { Snackbar.make(it, R.string.txt_appointment_creado, Snackbar.LENGTH_SHORT).show() }
            navigator.navigateToAppointment()
        } else if (isAttached && !event.success) {
            this.view?.let { Snackbar.make(it, R.string.txt_connection_error, Snackbar.LENGTH_SHORT).show() }
            if (noPacientes == "") {

            } else {
                hideLoadingUi()
            }
        }
    }

}