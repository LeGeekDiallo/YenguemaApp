package project.yenguema.yenguema.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import project.yenguema.yenguema.R

class DashboardAdapter:RecyclerView.Adapter<DashboardAdapter.ViewHolder>(), View.OnClickListener {

    interface NewActivityByService{
        fun newActivityByService(serviceName:String)
    }

    private var servicesTitle = emptyList<String>()
    private var img = emptyList<Int>()
    var newActivityListener: NewActivityByService? = null
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val service_card: CardView = itemView.findViewById(R.id.service_card)
        val service_title:TextView =service_card.findViewById(R.id.title)
        val img:ImageView = service_card.findViewById(R.id.img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewItem = inflater.inflate(R.layout.services_item, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentServiceTitle = servicesTitle[position]
        val currentSimg = img[position]
        holder.service_title.text = currentServiceTitle
        holder.img.setImageResource(currentSimg)
        holder.service_card.setOnClickListener {
            newActivityListener?.newActivityByService(currentServiceTitle)
        }
    }

    override fun getItemCount(): Int {
        return servicesTitle.size
    }
    fun setData(serviceTitle: List<String>, imgs:List<Int>){
        servicesTitle = serviceTitle
        img = imgs
        notifyDataSetChanged()
    }
    override fun onClick(p0: View?) {

    }
}