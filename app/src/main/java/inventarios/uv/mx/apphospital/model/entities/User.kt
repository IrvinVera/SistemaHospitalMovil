package inventarios.uv.mx.apphospital.model.entities

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class User: RealmObject(){
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