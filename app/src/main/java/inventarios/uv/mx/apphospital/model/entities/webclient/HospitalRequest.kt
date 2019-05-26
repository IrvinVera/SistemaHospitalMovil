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

    fun getHeaders(): HashMap<String, String> {
        token?.let {
            authorization = it.tokenType + " " + it.accessToken
        }

        val map = HashMap<String, String>()

        authorization?.let { map["Authorization"] = it }

        return map
    }

    fun getParams(): HashMap<String, String> {
        val map = HashMap<String, String>()

        offset?.let { map["offset"] = it.toString() }
        pageNumber?.let { map["pageNumber"] = it.toString() }
        size?.let { map["size"] = it.toString() }
        paged?.let { map["paged"] = it.toString() }
        sortSorted?.let { map["sort.sorted"] = it.toString() }
        sortUnsorted?.let { map["sort.unsorted"] = it.toString() }
        unpaged?.let { map["unpaged"] = it.toString() }

        return map
    }
}