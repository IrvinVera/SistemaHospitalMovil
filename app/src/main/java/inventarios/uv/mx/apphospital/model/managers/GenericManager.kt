package inventarios.uv.mx.apphospital.model.managers

import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalRequest
import inventarios.uv.mx.apphospital.model.utils.EventEnums
import inventarios.uv.mx.apphospital.model.webclients.WebServiceClient
import org.json.JSONObject

abstract class GenericManager {
    protected val maxNumberOfIntents = 2
    protected var intents = 0
    open val serviceClient = WebServiceClient()
    abstract fun sendEvent(success: Boolean?, httpStatus: Int?, action: EventEnums)
    abstract fun beforeSave()
    abstract fun afterSave()
    abstract fun save(json: String?): Boolean

    fun fetchAll() {
        val request = HospitalRequest()
        request.token = SessionManager().getToken()
        var success = false
        var status: Int? = 0

            try {
                    val response = serviceClient.fetchAll(request)
                    val json = response?.body()?.string()
                    status = response?.code()
                    println(status.toString() + "....................................................................")
                    println(json + ".................................................................................")
                    success = response?.isSuccessful ?: false
                    if (success) {
                        success = save(json)
                    }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        sendEvent(success, status, EventEnums.GET)
    }

    fun fetchById( id : String) {
        val request = HospitalRequest()
        request.token = SessionManager().getToken()
        var success = false
        var status: Int? = 0

        //try {
        val response = serviceClient.fetchById(id, request)
        val json = response?.body()?.string()

        status = response?.code()
        println(status.toString()+"....................................................................")
        println(json+".................................................................................")
        //success = save(json)

        /*} catch (ex: Exception) {
            println("petooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo")
        }*/
        sendEvent(success, status,EventEnums.TEMP)
        if (success) {

        }
    }
    fun post(jsonObject: JSONObject){
        val request = HospitalRequest()
        request.token = SessionManager().getToken()
        var success = false
        var status: Int? = 0

        //try {
        val response = serviceClient.post(jsonObject, request)
        val json = response?.body()?.string()

        status = response?.code()
        println(status.toString()+"....................................................................")
        println(json+".................................................................................")
        //success = save(json)

        /*} catch (ex: Exception) {
            println("petooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo")
        }*/
        sendEvent(success, status, EventEnums.POST)
        if (success) {

        }
    }

    fun put(jsonObject: JSONObject){
        val request = HospitalRequest()
        request.token = SessionManager().getToken()
        var success = false
        var status: Int? = 0

        //try {
        val response = serviceClient.put(jsonObject, request)
        val json = response?.body()?.string()

        status = response?.code()
        println(status.toString()+"....................................................................")
        println(json+".................................................................................")
        //success = save(json)

        /*} catch (ex: Exception) {
            println("petooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo")
        }*/
        sendEvent(success, status, EventEnums.PUT)
        if (success) {

        }
    }

    fun delete(id: String){
        val request = HospitalRequest()
        request.token = SessionManager().getToken()
        var success = false
        var status: Int? = 0

        //try {
        val response = serviceClient.delete(id,request)
        val json = response?.body()?.string()

        status = response?.code()
        println(status.toString()+"....................................................................")
        println(json+".................................................................................")
        //success = save(json)

        /*} catch (ex: Exception) {
            println("petooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo")
        }*/
        sendEvent(success, status, EventEnums.DELETE)
        if (success) {

        }
    }

    fun canUpdate():Boolean =true


}