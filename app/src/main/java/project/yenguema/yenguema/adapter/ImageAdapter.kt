package project.yenguema.yenguema.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import project.yenguema.yenguema.R

class ImageAdapter: RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    private var imgURLList = emptyList<String>()
    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val image: RoundedImageView = itemView.findViewById(R.id.prests_images_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewItem = inflater.inflate(R.layout.list_prests_images, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentImgURL = imgURLList[position]
        Picasso.get()
            .load(currentImgURL)
            .error(R.mipmap.sprest_foreground)
            .fit()
            .placeholder(R.mipmap.sprest_foreground)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return imgURLList.size
    }

    fun setImagesList(imgURL:List<String>){
        imgURLList = imgURL
    }
}