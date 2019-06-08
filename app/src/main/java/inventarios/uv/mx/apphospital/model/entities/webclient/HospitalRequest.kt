package inventarios.uv.mx.apphospital.model.entities.webclient

open class HospitalRequest {

    var authorization: String? = null

    var offset: Long? = null

    var pageNumber: Long? = null

    var size: Long? = null

    var paged: Boolean? = true

    var sortSorted: Boolean? = null

    var sortUnsorted: Boolean? = null

    var unpaged: Boolean? = null

    var token: HospitalToken? = null
}