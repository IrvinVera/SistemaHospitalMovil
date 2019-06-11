package inventarios.uv.mx.apphospital.model.managers

import com.vicpin.krealmextensions.createOrUpdate
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.query
import inventarios.uv.mx.apphospital.controllers.events.*
import inventarios.uv.mx.apphospital.model.entities.Appointment
import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalRequest
import inventarios.uv.mx.apphospital.model.utils.EventEnums
import inventarios.uv.mx.apphospital.model.webclients.AppointmentClient
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

class AppointmentManager: GenericManager() {
    override val serviceClient = AppointmentClient()
    override fun beforeSave() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun afterSave() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun save(json: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun saveAppointment(json: String?, citaReservada: Boolean): Boolean {
        json?.let {
            try {
                val jsonObject = JSONObject(json)
                val appointment = Appointment()
                if(citaReservada) {
                    appointment.id = 1
                    appointment.noPacientes = jsonObject.getString("noPacientesPrevios")
                }else{
                    appointment.id = -1
                    appointment.noPacientes = jsonObject.getString("noPacientesEspera")
                }
                appointment.createOrUpdate()
                return true
            } catch (ex: Exception) {
                ex.printStackTrace()
                return false
            }
        } ?: return false
    }

    override fun sendEvent(success: Boolean?, httpStatus: Int?, action: EventEnums) {
        when (action){
            EventEnums.GET -> EventBus.getDefault().post(
                AppointmentDownloadEvent(
                    success  ?: false, httpStatus?: 403)
            )
            EventEnums.POST -> EventBus.getDefault().post(
                AppointmentUploadEvent(
                    success  ?: false, httpStatus?: 403)
            )
            EventEnums.PUT -> EventBus.getDefault().post(
                AppointmentEditEvent(
                    success  ?: false, httpStatus?: 403)
            )
            EventEnums.DELETE -> EventBus.getDefault().post(
                AppointmentDeleteEvent(
                    success  ?: false, httpStatus?: 403)
            )
            else -> EventBus.getDefault().post(
                AppointmentDownloadEvent(
                    success  ?: false, httpStatus?: 403)
            )
        }
    }

    fun postAppointment(personaId: Long){
        val request = HospitalRequest()
        //val jsonObject = JSONObject()
        request.token = SessionManager().getToken()
        var success = false
        var status: Int? = 0

        try {
            /*jsonObject.put("officingNumber", stocktaking.officingNumber)
            jsonObject.put("managerName", stocktaking.managerName)*/
                val response = serviceClient.postCita(personaId.toString(), request)
                val json = response?.body()?.string()
                status = response?.code()
                println(status.toString()+"....................................................................")
                println(json+".................................................................................")
                success = response?.isSuccessful ?: false
                /*if (success) {

                    stocktaking.createOrUpdate()
                }*/
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        sendEvent(success, status, EventEnums.POST)
    }

    fun fetchAppointmentPositionById(personaId: Long) {
        val request = HospitalRequest()
        request.token = SessionManager().getToken()
        var success = false
        var status: Int? = 0

        try {
            val response = serviceClient.fetchNoPacientesPrevios(personaId.toString(), request)
            val json = response?.body()?.string()
            status = response?.code()
            println(status.toString()+"....................................................................")
            println(json+".................................................................................")
            success = response?.isSuccessful ?: false
            if (success) {
                success = saveAppointment(json, true)
            }else{
                Appointment().deleteAll()
            }
        } catch (ex: Exception) {
            EventBus.getDefault().post(
                AppointmentConnectionError())
            ex.printStackTrace()
        }
        sendEvent(success, status, EventEnums.GET)
    }

    fun fetchAppointmentNumberPatients() {
        val request = HospitalRequest()
        request.token = SessionManager().getToken()
        var success = false
        var status: Int? = 0

        try {
            val response = serviceClient.fetchNoPacientesEspera(request)
            val json = response?.body()?.string()
            status = response?.code()
            println(status.toString()+"....................................................................")
            println(json+".................................................................................")
            success = response?.isSuccessful ?: false
            if (success) {
                success = saveAppointment(json, false)
            }
        } catch (ex: Exception) {
            EventBus.getDefault().post(
                AppointmentConnectionError())
            ex.printStackTrace()
        }
        sendEvent(success, status, EventEnums.GET)
    }

    fun loadCita(): List<Appointment> {
        try {
            val long : Long = 1
            return Appointment().query { equalTo("id", long) }
        }catch (ex: Exception){
            ex.printStackTrace()
            return ArrayList()
        }
    }

    fun loadNoPacientes(): List<Appointment> {
        try {
            val long : Long = -1
            return Appointment().query { equalTo("id", long) }
        }catch (ex: Exception){
            ex.printStackTrace()
            return ArrayList()
        }
    }
}