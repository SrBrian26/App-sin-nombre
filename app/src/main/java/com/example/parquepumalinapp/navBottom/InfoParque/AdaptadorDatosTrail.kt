package com.example.parquepumalinapp.navBottom.InfoParque

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.parquepumalinapp.R

class AdaptadorDatosTrail(private val trails: List<DatosTrail>) :
    RecyclerView.Adapter<AdaptadorDatosTrail.TrailViewHolder>() {

    inner class TrailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val trailName: TextView = view.findViewById(R.id.trailName)
        val trailDetails: View = view.findViewById(R.id.trailDetails)
        val description: TextView = view.findViewById(R.id.tvDescripcion)
        var img: ViewPager2 = view.findViewById(R.id.ImgInfo)
        val expandIndicator: ImageView = view.findViewById(R.id.expandIndicator)
        val tvdesliza: TextView = view.findViewById(R.id.tvDes)
    }

    private var expandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_view_content,
            parent, false)
        return TrailViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: TrailViewHolder, position: Int) {
        val trail = trails[position]
        holder.trailName.text = trail.nombre
        holder.description.text = trail.description
        holder.img.adapter = ImagePagerAdapter(trail.imagen)

        val isExpanded = position == expandedPosition
        holder.trailDetails.visibility = if (isExpanded) View.VISIBLE else View.GONE

        holder.trailName.setTextColor(
            if (isExpanded && holder.trailName.text != "Restricciones")
                ContextCompat.getColor(holder.itemView.context, R.color.colorSelected)
            else if (isExpanded && holder.trailName.text == "Restricciones")
                ContextCompat.getColor(holder.itemView.context, R.color.Amarillo)
            else Color.WHITE
        )
        holder.expandIndicator.rotation = if (isExpanded) 270f else 0f

        holder.itemView.setOnClickListener {
            expandedPosition = if (isExpanded) -1 else position
            notifyDataSetChanged()
        }
        holder.tvdesliza.text = if (isExpanded && holder.trailName.text == "Restricciones") ""
        else "Desliza para ver más imágenes"
    }
    override fun getItemCount(): Int = trails.size
}