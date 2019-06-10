package inventarios.uv.mx.apphospital.model.webclients

import com.google.gson.Gson
import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalLoginResponse
import inventarios.uv.mx.apphospital.model.entities.webclient.HospitalLoginResquest
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
                .addPathSegment(Constants.WS_URL.api)
                .addPathSegment(Constants.WS_URL.cuentaPath)
                .addPathSegment(Constants.WS_URL.loginPath)


        val url = httpUrlBuilder.toString()

        val jsonObject = Gson().toJson(inventoryLoginResquest.hospitalLogin)
        val request = Request.Builder().addHeader("Content-Type","application/json").url(url).post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString())).build()

        val response = client.newCall(request).execute()
        println(response.toString()+"..............................................................................................")
        if (response.isSuccessful) {
            return HospitalLoginResponse(response.isSuccessful, response.body()?.string()
                    ?: "", response.code())
        }
        return HospitalLoginResponse.returnBardRequestResponse()
    }

    /*fun trustAllSslClient(client: OkHttpClient): OkHttpClient {
        val trustAllSslContext = SSLContext.getInstance("SSL")
        trustAllSslContext.init(null, trustAllCerts, java.security.SecureRandom())
        val trustAllSslSocketFactory = trustAllSslContext.getSocketFactory();
        val builder = client.newBuilder()
        builder.sslSocketFactory(trustAllSslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier(object : HostnameVerifier {
            override fun verify(hostname: String, session: SSLSession): Boolean {
                return true
            }
        })
        return builder.build()
    }

    private val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
        }

        override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
            return arrayOf()
        }
    })*/




    /*@Throws(Exception::class)
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
    }*/
}