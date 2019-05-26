package inventarios.uv.mx.apphospital.model.managers

import com.google.gson.Gson
import com.vicpin.krealmextensions.createOrUpdate
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.queryFirst
import inventarios.uv.mx.apphospital.model.entities.User
import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalLogin
import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalLoginResquest
import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalRequest
import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalToken
import inventarios.uv.mx.apphospital.model.utils.Responses
import inventarios.uv.mx.apphospital.model.webclients.SessionClient
import java.util.*

class SessionManager {
    open val serviceClient = SessionClient()

    fun login(user: User): Responses {
        val hospitalLogin = HospitalLogin()
        hospitalLogin.user = user.username
        hospitalLogin.password = user.password

        val hospitalLoginRequest = HospitalLoginResquest()
        hospitalLoginRequest.hospitalLogin = hospitalLogin
        try{
            val response = serviceClient.login(hospitalLoginRequest)
            if (response?.success == true) {
                if( save(response.body)){
                    return Responses.SUCCESS
                }else{
                    return Responses.DATA_ERROR
                }
            } else{
                return Responses.WRONG_DATA
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return Responses.CONNECTION_ERROR
        }
    }

    fun refresh(user: User): Responses{
        val hospotalLogin = HospitalLogin()
        hospotalLogin.user = user.username
        hospotalLogin.password = user.password
        hospotalLogin.refreshToken = SessionManager().getToken()!!.refreshToken

        val hospitalLoginResquest = HospitalLoginResquest()
        hospitalLoginResquest.hospitalLogin = hospotalLogin
        try{
            val response = serviceClient.refresh(hospitalLoginResquest)
            if (response?.success == true) {
                if( save(response.body)){
                    return Responses.SUCCESS
                }else{
                    return Responses.DATA_ERROR
                }
            } else{
                return Responses.WRONG_DATA
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return Responses.CONNECTION_ERROR
        }
    }

    fun logout(){
        val request = HospitalRequest()
        request.token = SessionManager().getToken()
        try {
            serviceClient.logout(request)
            deleteToken()

        }catch(ex: Exception){
            ex.printStackTrace()
        }
    }

    fun save(json: String?): Boolean {

        json?.let {
            try {
                val typeToken = object : com.google.gson.reflect.TypeToken<HospitalToken>() {}.type
                val inventoryToken: HospitalToken = Gson().fromJson(json, typeToken)

                inventoryToken.id = 1
                inventoryToken.createOrUpdate()

                return true
            } catch (ex: Exception) {
                ex.printStackTrace()
                return false
            }
        } ?: return false
    }

    fun refreshToken(): Boolean {
        return false
    }

    fun getToken(): HospitalToken? {
        try {
            return HospitalToken().queryFirst()
        } catch (ex: Exception) {
            return null
        }
    }

    fun deleteToken() {
        HospitalToken().deleteAll()
        /*User().deleteAll()
        Verifier().deleteAll()
        Dependency().deleteAll()
        StockTaking().deleteAll()*/
    }

    fun isLoggedIn(): Boolean {
        return try {
            HospitalToken().queryFirst()?.let {
                val now = Calendar.getInstance().timeInMillis
                return (it.accessToken != null) && (now - (it.createdAt ?: 0L)) > it.expiresIn ?: 0L
            } ?: return false
        } catch (ex: Exception) {
            false
        }
    }
}