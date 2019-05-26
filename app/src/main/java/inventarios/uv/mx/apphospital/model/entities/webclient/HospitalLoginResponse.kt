package inventarios.uv.mx.apphospital.model.entities.webclient

class HospitalLoginResponse(val success: Boolean, val body: String, val status: Int){
    companion object {
        fun returnBardRequestResponse(): HospitalLoginResponse {
            return HospitalLoginResponse(false, "", 403)
        }
    }
}