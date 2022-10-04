package com.example.tweeks_lab_task.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tweeks_lab_task.R
import com.example.tweeks_lab_task.databinding.AthleteItemBinding
import com.example.tweeks_lab_task.models.Athlete

class SearchAdapter(
    public var context:Context,
    public var athletes:ArrayList<Athlete>,

    ):RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {
    var selectIndex:Int=0
    class MyViewHolder(val binding:AthleteItemBinding):RecyclerView.ViewHolder(binding.root){

    }
    fun setSelectedIndex(index:Int){
        selectIndex=index
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.MyViewHolder {

        return  MyViewHolder(AthleteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: SearchAdapter.MyViewHolder, i: Int) {

       holder.binding.rankIm!!.visibility = View.GONE
        holder.binding.name!!.text=athletes[i].name
        holder.binding.score!!.text= athletes[i].score.toString()
    }

    override fun getItemCount(): Int {
        if (athletes.size>=3){
            return 3
        }
        else{
            return athletes.size
        }
    }

}