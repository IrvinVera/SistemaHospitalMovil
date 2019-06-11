package inventarios.uv.mx.apphospital.controllers.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.mikepenz.fastadapter.items.AbstractItem
import inventarios.uv.mx.apphospital.R
import inventarios.uv.mx.apphospital.model.entities.Appointment
import java.text.SimpleDateFormat
import java.util.*

class AppointmentItem : AbstractItem<AppointmentItem, AppointmentItem.ViewHolder>() {

    var item: Appointment?= null

    override fun getViewHolder(view: View?): ViewHolder = ViewHolder(view!!)

    override fun bindView(viewHolder: ViewHolder, payloads: List<Any>) {
        super.bindView(viewHolder, payloads)
        viewHolder.bind(item!!)
    }

    override fun unbindView(viewHolder: ViewHolder?) {
        super.unbindView(viewHolder)
        viewHolder?.reset()
    }

    override fun getType(): Int = R.id.appointments_item_adapter

    override fun getLayoutRes(): Int = R.layout.item_list_appointments

    override fun getIdentifier(): Long {
        return item?.id ?: 0L
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @JvmField
        @BindView(R.id.fechaCita)
        var txtDateAppointment : TextView? =  null

        @JvmField
        @BindView(R.id.noPacientes)
        var txtNoPatients: TextView? =  null

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(item: Appointment) {
            /*txtNameDependency?.text = item.name ?: itemView.context.getString(R.string.txt_unaviable)
            txtAddressDependency?.text = item.address ?: itemView.context.getString(R.string.txt_unaviable)*/
            txtNoPatients?.text = "Número de pacientes antes de tu turno: "+item.noPacientes
            val targetFormat = SimpleDateFormat("dd-MMMM-yyyy")
            txtDateAppointment?.text = "Fecha: "+targetFormat.format(Calendar.getInstance().time)

        }

        fun reset() {

        }

    }
}