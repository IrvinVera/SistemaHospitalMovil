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

    @SerializedName("access_token")
    var accessToken: String? = null

    @SerializedName("token_type")
    var tokenType: String? = null

    @SerializedName("refresh_token")
    var refreshToken: String? = null

    @SerializedName("expires_in")
    var expiresIn: Long? = null

    @SerializedName("scope")
    var scope: String? = null

    @SerializedName("created_at")
    var createdAt: Long? = null

    @SerializedName("update_at")
    var updatedAt: Long? = null
}