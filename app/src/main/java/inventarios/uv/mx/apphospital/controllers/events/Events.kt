package inventarios.uv.mx.apphospital.controllers.events

class Events ()

open class GenericDownloadEvent(val success: Boolean, val status: Int)

class UsersDownloadEvent(success: Boolean, status: Int) : GenericDownloadEvent(success, status)
class UsersUploadEvent(success: Boolean, status: Int) : GenericDownloadEvent(success, status)
class UsersEditEvent(success: Boolean, status: Int) : GenericDownloadEvent(success, status)
class UsersDeleteEvent(success: Boolean, status: Int) : GenericDownloadEvent(success, status)

class AppointmentDownloadEvent(success: Boolean, status: Int) : GenericDownloadEvent(success, status)
class AppointmentUploadEvent(success: Boolean, status: Int) : GenericDownloadEvent(success, status)
class AppointmentEditEvent(success: Boolean, status: Int) : GenericDownloadEvent(success, status)
class AppointmentDeleteEvent(success: Boolean, status: Int) : GenericDownloadEvent(success, status)

class AppointmentConnectionError()