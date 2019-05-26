package inventarios.uv.mx.apphospital.model.entities

import com.google.gson.annotations.SerializedName
import io.realm.annotations.PrimaryKey

open class User{
    @PrimaryKey
    @SerializedName("id")
    var id : Long? = null

    @SerializedName("username")
    var username : String? = null

    @SerializedName("email")
    var email : String? = null

    @SerializedName("password")
    var password : String? = null
}