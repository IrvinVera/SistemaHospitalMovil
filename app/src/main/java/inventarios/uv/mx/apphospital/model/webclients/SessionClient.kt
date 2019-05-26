package inventarios.uv.mx.apphospital.model.webclients

import com.google.gson.Gson
import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalLoginResponse
import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalLoginResquest
import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalRequest
import inventarios.uv.mx.apphospital.model.utils.Constants
import okhttp3.*

class SessionClient {

    @Throws(Exception::class)
    fun login(inventoryLoginResquest: HospitalLoginResquest): HospitalLoginResponse? {
        val client = OkHttpClient()
        val httpUrlBuilder = HttpUrl.Builder()
                .scheme(Constants.WS_URL.scheme)
                .host(Constants.WS_URL.baseUrl)
                .port(Constants.WS_URL.port)
                .addPathSegment(Constants.WS_URL.version)
                .addPathSegment(Constants.WS_URL.public)
                .addPathSegment(Constants.WS_URL.loginPath)


        val url = httpUrlBuilder.toString()

        val jsonObject = Gson().toJson(inventoryLoginResquest.hospitalLogin)
        val request = Request.Builder().addHeader("Authorization","Basic NVYwTXN1a3drZHFlMjBvODowS0x1aE40aHlBQ3JrbFdR").url(url).post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString())).build()

        val response = client.newCall(request).execute()

        if (response.isSuccessful) {
            return HospitalLoginResponse(response.isSuccessful, response.body()?.string()
                    ?: "", response.code())
        }
        return HospitalLoginResponse.returnBardRequestResponse()
    }

    @Throws(Exception::class)
    fun refresh(inventoryLoginResquest: HospitalLoginResquest): HospitalLoginResponse? {
        val client = OkHttpClient()
        val httpUrlBuilder = HttpUrl.Builder()
                .scheme(Constants.WS_URL.scheme)
                .host(Constants.WS_URL.baseUrl)
                .port(Constants.WS_URL.port)
                .addPathSegment(Constants.WS_URL.version)
                .addPathSegment(Constants.WS_URL.public)
                .addPathSegment(Constants.WS_URL.refreshPath)


        val url = httpUrlBuilder.toString()

        val jsonObject = Gson().toJson(inventoryLoginResquest.hospitalLogin)
        val request = Request.Builder().addHeader("Authorization","Basic NVYwTXN1a3drZHFlMjBvODowS0x1aE40aHlBQ3JrbFdR").url(url).post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString())).build()

        val response = client.newCall(request).execute()

        if (response.isSuccessful) {
            return HospitalLoginResponse(response.isSuccessful, response.body()?.string()
                    ?: "", response.code())
        }
        return HospitalLoginResponse.returnBardRequestResponse()
    }

    @Throws(Exception::class)
    fun logout(inventoryRequest: HospitalRequest): Boolean {
        var success = false
        val client = OkHttpClient()
        val httpUrlBuilder = HttpUrl.Builder()
                .scheme(Constants.WS_URL.scheme)
                .host(Constants.WS_URL.baseUrl)
                .port(Constants.WS_URL.port)
                .addPathSegment(Constants.WS_URL.version)
                .addPathSegment(Constants.WS_URL.public)
                .addPathSegment(Constants.WS_URL.logoutPath)



        val url = httpUrlBuilder.toString()

        val request = Request.Builder().addHeader("Authorization",inventoryRequest.token!!.tokenType+" "+inventoryRequest.token!!.accessToken).url(url).delete().build()

    val response = client.newCall(request).execute()

        if (response.isSuccessful) {
            success = true
        }
        return success
    }
}