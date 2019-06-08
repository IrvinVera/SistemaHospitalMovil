package inventarios.uv.mx.apphospital.model.entities.webclient

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class HospitalToken: RealmObject() {
    @PrimaryKey
    @SerializedName("id")
    var id: Long? = null

    @SerializedName("token")
    var token: String? = null

    @SerializedName("rol")
    var rol: String? = null

}