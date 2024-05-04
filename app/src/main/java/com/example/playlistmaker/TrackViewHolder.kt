package com.example.playlistmaker

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val trackImage: ImageView = itemView.findViewById(R.id.artworkUrl100)
    private val trackName: TextView = itemView.findViewById(R.id.trackName)
    private val trackArtist: TextView = itemView.findViewById(R.id.artistName)
    private val trackTime: TextView = itemView.findViewById(R.id.trackTime)


    @SuppressLint("SetTextI18n")
    fun bind(model: Track) {
        Glide.with(itemView)
            .load(model.artworkUrl100)
            .centerCrop()
            .transform(RoundedCorners(6))
            .placeholder(R.drawable.placeholder)
            .into(trackImage)
        if (model.trackName.length > 30) trackName.text = "${model.trackName.slice(0..35)}..."
        else trackName.text = model.artistName
        if (model.artistName.length > 40) trackArtist.text = "${model.artistName.slice(0..40)}..."
        else trackArtist.text = model.artistName
        trackTime.text = model.trackTime
    }


}
