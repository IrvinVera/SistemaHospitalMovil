package inventarios.uv.mx.apphospital.model.managers

import com.google.gson.Gson
import com.vicpin.krealmextensions.createOrUpdate
import com.vicpin.krealmextensions.queryFirst
import inventarios.uv.mx.apphospital.controllers.events.UsersDeleteEvent
import inventarios.uv.mx.apphospital.controllers.events.UsersDownloadEvent
import inventarios.uv.mx.apphospital.controllers.events.UsersEditEvent
import inventarios.uv.mx.apphospital.controllers.events.UsersUploadEvent
import inventarios.uv.mx.apphospital.model.entities.User
import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalRequest
import inventarios.uv.mx.apphospital.model.utils.EventEnums
import inventarios.uv.mx.apphospital.model.webclients.UserClient
import org.greenrobot.eventbus.EventBus

class UserManager: GenericManager() {
    override val serviceClient = UserClient()

    override fun sendEvent(success: Boolean?, httpStatus: Int?, action: EventEnums) {
        when (action){
            EventEnums.GET -> EventBus.getDefault().post(
                UsersDownloadEvent(
                    success  ?: false, httpStatus?: 403)
            )
            EventEnums.POST -> EventBus.getDefault().post(
                UsersUploadEvent(
                    success  ?: false, httpStatus?: 403)
            )
            EventEnums.PUT -> EventBus.getDefault().post(
                UsersEditEvent(
                    success  ?: false, httpStatus?: 403)
            )
            EventEnums.DELETE -> EventBus.getDefault().post(
                UsersDeleteEvent(
                    success  ?: false, httpStatus?: 403)
            )
            else -> EventBus.getDefault().post(UsersDownloadEvent(
                    success  ?: false, httpStatus?: 403))
        }
    }

    override fun beforeSave() {

    }

    override fun afterSave() {

    }

    override fun save(json: String?): Boolean {
        json?.let {
            try {
                val typeToken = object : com.google.gson.reflect.TypeToken<User>() {}.type
                val user: User = Gson().fromJson(json, typeToken)
                user.createOrUpdate()
                return true
            } catch (ex: Exception) {
                ex.printStackTrace()
                return false
            }
        } ?: return false
    }

    fun getUser(): User? {
        try {
            return User().queryFirst()
        } catch (ex: Exception) {
            return null
        }
    }


    fun fetchByUsername( username : String): Boolean {
        val request = HospitalRequest()
        request.token = SessionManager().getToken()
        var success = false
        var status: Int? = 0

        try {
            val response = serviceClient.fetchByUsername(username, request)
            val json = response?.body()?.string()
            status = response?.code()
            println(status.toString()+"....................................................................")
            println(json+".................................................................................")
            success = response?.isSuccessful ?: false
            if (success) {
                success = save(json)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        //sendEvent(success, status)
        /*if (success) {

        }*/
        return success
    }
}