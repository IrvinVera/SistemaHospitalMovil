package inventarios.uv.mx.apphospital.model.utils

class Constants {
    object WS_URL {
        @JvmField val scheme: String = "http"
        @JvmField val baseUrl: String = "172.72.34.223"
        @JvmField val port: Int = 58853
        @JvmField val version: String = "v01"
        @JvmField val private: String = "private"
        @JvmField val public: String = "public"
        @JvmField val username: String = "username"
        @JvmField val email: String = "email"
        @JvmField val api: String = "api"

        @JvmField val itemsPath: String = "items"
        @JvmField val externalItemsPath: String = "externalItems"
        @JvmField val departmentsPath: String = "departments"
        @JvmField val dependenciesPath: String = "dependencies"
        @JvmField val reasonsPath: String = "reasons"
        @JvmField val regionsPath: String = "regions"
        @JvmField val siiulocationsPath: String = "siiulocations"
        @JvmField val stocktakingPath: String = "stocktakings"
        @JvmField val personaPath: String = "Persona"
        @JvmField val nombreUsuarioPath: String = "buscarPersonaNombreUsuario"
        @JvmField val verifiersPath: String = "verifiers"
        @JvmField val cuentaPath: String = "Cuenta"
        @JvmField val loginPath: String = "login"
        @JvmField val logoutPath: String = "logout"
        @JvmField val refreshPath: String = "refresh"
        @JvmField val listEsperaPath: String = "ListaEspera"
        @JvmField val obtenerTodosLosPacientes: String = "obtenerTotalPacientes"
        @JvmField val obtenerPacientesPrevios: String = "obtenerPosicionPaciente"
        @JvmField val agregar: String = "agregar"

    }
}