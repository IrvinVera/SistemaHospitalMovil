package inventarios.uv.mx.apphospital.model.managers

import com.google.gson.Gson
import com.vicpin.krealmextensions.createOrUpdate
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.queryFirst
import inventarios.uv.mx.apphospital.model.entities.Persona
import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalLogin
import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalLoginResquest
import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalRequest
import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalToken
import inventarios.uv.mx.apphospital.model.utils.Responses
import inventarios.uv.mx.apphospital.model.webclients.SessionClient

class SessionManager {
    open val serviceClient = SessionClient()

    fun login(username: String, password: String): Responses {
        val hospitalLogin = HospitalLogin()
        hospitalLogin.NombreUsuario = username
        hospitalLogin.Contrasena = password

        val hospitalLoginRequest = HospitalLoginResquest()
        hospitalLoginRequest.hospitalLogin = hospitalLogin
        try{
            val response = serviceClient.login(hospitalLoginRequest)
            if (response?.success == true) {
                if( save(response.body)){
                    if(getUserData(username)) {
                        return Responses.SUCCESS
                    }else{
                        return Responses.CONNECTION_ERROR
                    }
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
            //serviceClient.logout(request)
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
        Persona().deleteAll()
        /*Verifier().deleteAll()
        Dependency().deleteAll()
        StockTaking().deleteAll()*/
    }

    fun isLoggedIn(): Boolean {
        return try {
            HospitalToken().queryFirst()?.let {
                return it.token != null
            } ?: return false
        } catch (ex: Exception) {
            false
        }
    }

    fun getUserData(username: String): Boolean{
        val userManager = PersonaManager()
        return userManager.fetchByUsername(username)
    }
}