package inventarios.uv.mx.apphospital.model.utils

class Constants {
    object WS_URL {
        @JvmField val scheme: String = "http"
        @JvmField val baseUrl: String = "148.226.26.147"
        @JvmField val port: Int = 8080
        @JvmField val version: String = "v01"
        @JvmField val private: String = "private"
        @JvmField val public: String = "public"
        @JvmField val username: String = "username"
        @JvmField val email: String = "email"

        @JvmField val itemsPath: String = "items"
        @JvmField val externalItemsPath: String = "externalItems"
        @JvmField val departmentsPath: String = "departments"
        @JvmField val dependenciesPath: String = "dependencies"
        @JvmField val reasonsPath: String = "reasons"
        @JvmField val regionsPath: String = "regions"
        @JvmField val siiulocationsPath: String = "siiulocations"
        @JvmField val stocktakingPath: String = "stocktakings"
        @JvmField val usersPath: String = "users"
        @JvmField val verifiersPath: String = "verifiers"
        @JvmField val loginPath: String = "login"
        @JvmField val logoutPath: String = "logout"
        @JvmField val refreshPath: String = "refresh"

    }
}