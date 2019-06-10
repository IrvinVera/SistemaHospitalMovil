package inventarios.uv.mx.apphospital.model.webclients

import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalRequest
import inventarios.uv.mx.apphospital.model.utils.Constants
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class PersonaClient: WebServiceClient() {

    override var entitiesPath:String? = Constants.WS_URL.personaPath


    @Throws(Exception::class)
    fun fetchByUsername(username : String, hospitalRequest: HospitalRequest): Response? {
        val client = OkHttpClient()
        val httpUrlBuilder = HttpUrl.Builder()
                .scheme(Constants.WS_URL.scheme)
                .host(Constants.WS_URL.baseUrl)
                .port(Constants.WS_URL.port)
                .addPathSegment(Constants.WS_URL.api)
                .addPathSegment(entitiesPath)
                .addPathSegment(Constants.WS_URL.nombreUsuarioPath).addQueryParameter("nombreUsuarioPersona", username)


        val url = httpUrlBuilder.toString()

        val request = Request.Builder().addHeader("Authorization","bearer "+hospitalRequest.token!!.token).url(url).build()

        val response = client.newCall(request).execute()


        return response
    }

}