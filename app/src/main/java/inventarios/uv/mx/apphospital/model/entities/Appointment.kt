package inventarios.uv.mx.apphospital.model.entities

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Appointment: RealmObject() {
    @PrimaryKey
    @SerializedName("id")
    var id : Long? = null
    @SerializedName("noPacientes")
    var noPacientes : Long? = null
}