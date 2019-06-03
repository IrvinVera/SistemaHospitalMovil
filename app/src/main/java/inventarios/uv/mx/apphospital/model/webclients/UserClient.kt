package inventarios.uv.mx.apphospital.model.webclients

import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalRequest
import inventarios.uv.mx.apphospital.model.utils.Constants
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class UserClient: WebServiceClient() {

    override var entitiesPath:String? = Constants.WS_URL.usersPath


    @Throws(Exception::class)
    fun fetchByUsername(username : String, hospitalRequest: HospitalRequest): Response? {
        val client = OkHttpClient()
        val httpUrlBuilder = HttpUrl.Builder()
                .scheme(Constants.WS_URL.scheme)
                .host(Constants.WS_URL.baseUrl)
                .port(Constants.WS_URL.port)
                .addPathSegment(Constants.WS_URL.version)
                .addPathSegment(Constants.WS_URL.private)
                .addPathSegment(entitiesPath)
                .addPathSegment(Constants.WS_URL.username)
                .addPathSegment(username)


        val url = httpUrlBuilder.toString()

        val request = Request.Builder().addHeader("Authorization",hospitalRequest.token!!.tokenType+" "+hospitalRequest.token!!.accessToken).url(url).build()

        val response = client.newCall(request).execute()

        if (response.isSuccessful) {
            return response
        }
        throw IllegalArgumentException("Response unsuccessful")
    }

}