package inventarios.uv.mx.apphospital.model.managers

import com.vicpin.krealmextensions.query
import inventarios.uv.mx.apphospital.controllers.events.AppointmentDeleteEvent
import inventarios.uv.mx.apphospital.controllers.events.AppointmentDownloadEvent
import inventarios.uv.mx.apphospital.controllers.events.AppointmentEditEvent
import inventarios.uv.mx.apphospital.controllers.events.AppointmentUploadEvent
import inventarios.uv.mx.apphospital.model.entities.Appointment
import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalRequest
import inventarios.uv.mx.apphospital.model.utils.EventEnums
import inventarios.uv.mx.apphospital.model.webclients.AppointmentClient
import org.greenrobot.eventbus.EventBus

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
                /*val response = serviceClient.postStocktaking(personaId)
                val json = response?.body()?.string()
                status = response?.code()
                println(status.toString()+"....................................................................")
                println(json+".................................................................................")
                success = response?.isSuccessful ?: false
                if (success) {
                    stocktaking.createOrUpdate()
                }*/
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        sendEvent(success, status, EventEnums.POST)
    }

    fun fetchAppointmentById(personaId: Long) {
        val request = HospitalRequest()
        request.token = SessionManager().getToken()
        var success = false
        var status: Int? = 0

        try {
            /*val response = serviceClient.fetchByUsername(username, request)
            val json = response?.body()?.string()
            status = response?.code()
            println(status.toString()+"....................................................................")
            println(json+".................................................................................")
            success = response?.isSuccessful ?: false
            if (success) {
                success = save(json)
            }*/
        } catch (ex: Exception) {
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
            /*val response = serviceClient.fetchByUsername(username, request)
            val json = response?.body()?.string()
            status = response?.code()
            println(status.toString()+"....................................................................")
            println(json+".................................................................................")
            success = response?.isSuccessful ?: false
            if (success) {
                success = save(json)
            }*/
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        sendEvent(success, status, EventEnums.GET)
    }

    fun loadById(id: Long): List<Appointment> {
        try {
            return Appointment().query { equalTo("id", id) }
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