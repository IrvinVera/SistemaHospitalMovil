package inventarios.uv.mx.apphospital.model.webclients

import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalRequest
import inventarios.uv.mx.apphospital.model.utils.Constants
import okhttp3.*
import org.json.JSONObject

open class WebServiceClient {
    open var entitiesPath: String? = null

    /**
     * Gets all records of a specified entity
     *
     * @throws Exception This exception can occur at the moment to build
     * or execute the url
     * @return A Response object that contains all the entity's records
     */
    fun fetchAll(hospitalRequest: HospitalRequest): Response? {
        val client = OkHttpClient()
        val httpUrlBuilder = HttpUrl.Builder()
                .scheme(Constants.WS_URL.scheme)
                .host(Constants.WS_URL.baseUrl)
                .port(Constants.WS_URL.port)
                .addPathSegment(entitiesPath)


        val url = httpUrlBuilder.toString()

        val request = Request.Builder().addHeader("Authorization","bearer "+hospitalRequest.token!!.token).url(url).build()

        val response = client.newCall(request).execute()

        return response
    }

    /**
     * Gets a record of an specific entity through its ID
     *
     * @param[id] No matter the type of id always a string is needed.
     * @throws Exception This exception can occur at the moment to build
     * or execute the url
     * @return A Response object that contains an specific record
     */
    @Throws(Exception::class)
    fun fetchById(id : String, hospitalRequest: HospitalRequest): Response? {
        val client = OkHttpClient()
        val httpUrlBuilder = HttpUrl.Builder()
                .scheme(Constants.WS_URL.scheme)
                .host(Constants.WS_URL.baseUrl)
                .port(Constants.WS_URL.port)
                .addPathSegment(entitiesPath)
                .addPathSegment(id)


        val url = httpUrlBuilder.toString()

        val request = Request.Builder().addHeader("Authorization","bearer "+hospitalRequest.token!!.token).url(url).build()

        val response = client.newCall(request).execute()

        if (response.isSuccessful) {
            return response
        }
        throw IllegalArgumentException("Response unsuccessful")
    }

    /**
     * Creates a record in the server
     *
     * @param[jsonObject] it is the entity that is going to be saved at the server
     * @throws Exception This exception can occur at the moment to build
     * or execute the url
     * @return A Response object that contains the id od the record in case of success
     */
    @Throws(Exception::class)
    fun post(jsonObject: JSONObject, hospitalRequest: HospitalRequest): Response? {
        val client = OkHttpClient()
        val httpUrlBuilder = HttpUrl.Builder()
                .scheme(Constants.WS_URL.scheme)
                .host(Constants.WS_URL.baseUrl)
                .port(Constants.WS_URL.port)
                .addPathSegment(entitiesPath)


        val url = httpUrlBuilder.toString()

        val request = Request.Builder().addHeader("Authorization","bearer "+hospitalRequest.token!!.token).url(url).post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString())).build()

        val response = client.newCall(request).execute()

        if (response.isSuccessful) {
            return response
        }
        throw IllegalArgumentException("Response unsuccessful")
    }

    /**
     * Updates an record in the server
     *
     * @param[jsonObject] it is the entity that is going to be updated at the server
     * @throws Exception This exception can occur at the moment to build
     * or execute the url
     * @return A Response object that contains the id od the record in case of success
     */
    @Throws(Exception::class)
    fun put(jsonObject: JSONObject, hospitalRequest: HospitalRequest): Response? {
        val client = OkHttpClient()
        val httpUrlBuilder = HttpUrl.Builder()
                .scheme(Constants.WS_URL.scheme)
                .host(Constants.WS_URL.baseUrl)
                .port(Constants.WS_URL.port)
                .addPathSegment(entitiesPath)


        val url = httpUrlBuilder.toString()

        val request = Request.Builder().addHeader("Authorization","bearer "+hospitalRequest.token!!.token).url(url).put(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString())).build()

        val response = client.newCall(request).execute()

        if (response.isSuccessful) {
            return response
        }
        throw IllegalArgumentException("Response unsuccessful")
    }

    /**
     * Changes the state "eliminado" of a record to true
     *
     * @param[id] No matter the type of id always a string is needed.
     * @throws Exception This exception can occur at the moment to build
     * or execute the url
     * @return A Response object that contains just the code of the response
     * in case of success
     */
    @Throws(Exception::class)
    fun delete(id: String, hospitalRequest: HospitalRequest): Response? {
        val client = OkHttpClient()
        val httpUrlBuilder = HttpUrl.Builder()
                .scheme(Constants.WS_URL.scheme)
                .host(Constants.WS_URL.baseUrl)
                .port(Constants.WS_URL.port)
                .addPathSegment(entitiesPath)
                .addPathSegment(id)


        val url = httpUrlBuilder.toString()

        val request = Request.Builder().addHeader("Authorization","bearer "+hospitalRequest.token!!.token).url(url).delete().build()

        val response = client.newCall(request).execute()

        if (response.isSuccessful) {
            return response
        }
        throw IllegalArgumentException("Response unsuccessful")
    }

}