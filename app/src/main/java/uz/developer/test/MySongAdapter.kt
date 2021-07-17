package uz.developer.test

import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.bottom_sheet.*
import java.util.*


class MySongAdapter: RecyclerView.Adapter<MySongAdapter.MyViewHolder>() {

    private var playlist = ArrayList<Int>()
    var item_number: Int?=null

    fun renewItems(playslist: ArrayList<Int>) {
        playlist = playslist
        notifyDataSetChanged()
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtsong: TextView = itemView.findViewById(R.id.txt_song)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySongAdapter.MyViewHolder {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.single_item_song, null)
        return MyViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: MySongAdapter.MyViewHolder, position: Int) {
        val resID = arrayOf<String>("Music", "Musicc", "musics")
       holder.txtsong.text=resID[position]
            val resIDItem = intArrayOf(R.raw.music,R.raw.musics,R.raw.musicc)
        holder.itemView.setOnClickListener(View.OnClickListener {
            if (holder.txtsong.text=="Music"){
                (holder.itemView.context as MainActivity).mediaPlayer = MediaPlayer.create(holder.itemView.context, resIDItem[0])
                (holder.itemView.context as MainActivity).mediaPlayer!!.start()
                (holder.itemView.context as MainActivity).linerID.visibility = View.VISIBLE
                (holder.itemView.context as MainActivity).linerbottom.visibility = View.GONE
                (holder.itemView.context as MainActivity).linerlist.visibility = View.GONE
            }
            if (holder.txtsong.text=="Musicc"){
                (holder.itemView.context as MainActivity).mediaPlayer = MediaPlayer.create(holder.itemView.context, resIDItem.get(1))
                (holder.itemView.context as MainActivity).mediaPlayer!!.start()
                (holder.itemView.context as MainActivity).linerID.visibility = View.VISIBLE
                (holder.itemView.context as MainActivity).linerbottom.visibility = View.GONE
                (holder.itemView.context as MainActivity).linerlist.visibility = View.GONE
            }
            if (holder.txtsong.text=="musics"){
                (holder.itemView.context as MainActivity).mediaPlayer = MediaPlayer.create(holder.itemView.context, resIDItem.get(2))
                (holder.itemView.context as MainActivity).mediaPlayer!!.start()
                (holder.itemView.context as MainActivity).linerID.visibility = View.VISIBLE
                (holder.itemView.context as MainActivity).linerbottom.visibility = View.GONE
                (holder.itemView.context as MainActivity).linerlist.visibility = View.GONE
            }
        })
    }

    override fun getItemCount(): Int {
     return playlist.size
    }
}