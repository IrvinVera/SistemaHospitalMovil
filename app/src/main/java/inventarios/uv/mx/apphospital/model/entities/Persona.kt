package inventarios.uv.mx.apphospital.model.entities

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Persona: RealmObject(){
    @PrimaryKey
    @SerializedName("idPersona")
    var idPersona : Long? = null

    @SerializedName("nombre")
    var nombre : String? = null

    @SerializedName("apellidos")
    var apellidos : String? = null

    @SerializedName("Correo")
    var correo : String? = null

    @SerializedName("telefono")
    var telefono : String? = null

    @SerializedName("genero")
    var genero : String? = null

    @SerializedName("rol")
    var rol : String? = null

    @SerializedName("fechaNacimiento")
    var fechaNacimiento : String? = null
}