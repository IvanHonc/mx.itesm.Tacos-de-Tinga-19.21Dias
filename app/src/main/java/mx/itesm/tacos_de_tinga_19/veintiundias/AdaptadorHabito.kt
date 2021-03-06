package mx.itesm.tacos_de_tinga_19.veintiundias

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.renglon_habito.view.*
import org.w3c.dom.Text
import java.util.*
import java.util.concurrent.TimeUnit

class AdaptadorHabito (private val habits : List<Habito>, val _Auth: FirebaseAuth, val _controller: habitsController) : RecyclerView.Adapter<AdaptadorHabito.ViewLine>() {
    var listener: ClickListener? = null
    private val Auth : FirebaseAuth = _Auth
    private val controller : habitsController = _controller
    private var days: Long = 0

    inner class ViewLine(val viewLineCard: View) :
        RecyclerView.ViewHolder(viewLineCard) {
        fun setCard(habito: Habito) {

            if(TimeUnit.DAYS.convert(habito.startDate.time - Calendar.getInstance().time.time, TimeUnit.MILLISECONDS)>21)
                days = 21
            else
                days = TimeUnit.DAYS.convert( Calendar.getInstance().time.time - habito.startDate.time, TimeUnit.MILLISECONDS)
            viewLineCard.tvHabitoTitle.text = habito.name
            viewLineCard.tvHabitoTiempo.text = days.toString()
            viewLineCard.pbDiasHabito.setProgress(21-days.toInt())
            viewLineCard.delete.setOnClickListener {
                controller.deleteHabits(Auth.currentUser.uid, habito.name);
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewLine {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.renglon_habito, parent, false)

        return ViewLine(view)
    }

    override fun onBindViewHolder(holder: ViewLine, position: Int) {
        val card = habits[position]
        holder.setCard(card)

        holder.viewLineCard.setOnClickListener {
            listener?.clicked(position)
            println("Hizo click sobre $position")
        }

    }

    override fun getItemCount(): Int {
        return habits.size
    }



}