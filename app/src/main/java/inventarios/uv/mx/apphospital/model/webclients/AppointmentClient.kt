package inventarios.uv.mx.apphospital.model.webclients

import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalRequest
import inventarios.uv.mx.apphospital.model.utils.Constants
import okhttp3.*

class AppointmentClient: WebServiceClient() {
    override var entitiesPath:String? = Constants.WS_URL.listEsperaPath

    @Throws(Exception::class)
    fun fetchNoPacientesEspera(hospitalRequest: HospitalRequest): Response? {
        val client = OkHttpClient()
        val httpUrlBuilder = HttpUrl.Builder()
            .scheme(Constants.WS_URL.scheme)
            .host(Constants.WS_URL.baseUrl)
            .port(Constants.WS_URL.port)
            .addPathSegment(Constants.WS_URL.api)
            .addPathSegment(entitiesPath)
            .addPathSegment(Constants.WS_URL.obtenerTodosLosPacientes)


        val url = httpUrlBuilder.toString()

        val request = Request.Builder().addHeader("Authorization","bearer "+hospitalRequest.token!!.token).url(url).build()

        val response = client.newCall(request).execute()


        return response
    }

    @Throws(Exception::class)
    fun fetchNoPacientesPrevios(idPaciente: String, hospitalRequest: HospitalRequest): Response? {
        val client = OkHttpClient()
        val httpUrlBuilder = HttpUrl.Builder()
            .scheme(Constants.WS_URL.scheme)
            .host(Constants.WS_URL.baseUrl)
            .port(Constants.WS_URL.port)
            .addPathSegment(Constants.WS_URL.api)
            .addPathSegment(entitiesPath)
            .addPathSegment(Constants.WS_URL.obtenerPacientesPrevios).addQueryParameter("idPaciente", idPaciente)


        val url = httpUrlBuilder.toString()

        val request = Request.Builder().addHeader("Authorization","bearer "+hospitalRequest.token!!.token).url(url).build()

        val response = client.newCall(request).execute()


        return response
    }

    fun postCita(idPaciente: String, hospitalRequest: HospitalRequest): Response? {
        val client = OkHttpClient()
        val httpUrlBuilder = HttpUrl.Builder()
            .scheme(Constants.WS_URL.scheme)
            .host(Constants.WS_URL.baseUrl)
            .port(Constants.WS_URL.port)
            .addPathSegment(Constants.WS_URL.api)
            .addPathSegment(entitiesPath)
            .addPathSegment(Constants.WS_URL.agregar).addQueryParameter("idPaciente", idPaciente)


        val url = httpUrlBuilder.toString()

        val request = Request.Builder().addHeader("Authorization","bearer "+hospitalRequest.token!!.token).url(url).post(
            RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                "{}")).build()

        val response = client.newCall(request).execute()
        return response
    }
}