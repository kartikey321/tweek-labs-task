package com.example.tweeks_lab_task.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tweeks_lab_task.R
import com.example.tweeks_lab_task.databinding.AthleteItemBinding
import com.example.tweeks_lab_task.models.Athlete

class AthleteAdapter(
    public var context:Context,
    public var athletes:ArrayList<Athlete>,

):RecyclerView.Adapter<AthleteAdapter.MyViewHolder>() {
    class MyViewHolder(val binding:AthleteItemBinding):RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AthleteAdapter.MyViewHolder {

        return  MyViewHolder(AthleteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: AthleteAdapter.MyViewHolder, i: Int) {

        holder.binding.cardView.setOnClickListener {
            athletes.forEach{
                it.selected=false
            }
          athletes[i].selected=true

            notifyDataSetChanged()
        }

        if(athletes[i].selected==true){
            holder.binding.cardView.setBackgroundResource(R.drawable.select_item)
            print(athletes[i].name)
            var id = Integer.parseInt(athletes[i].id)
            if(i==0){
                holder.binding.rankIm!!.visibility = View.VISIBLE
                holder.binding.rankIm!!.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.first))
                holder.binding.rankTxt.text=""
            }
            else if(i==1){
                holder.binding.rankIm!!.visibility = View.VISIBLE
                holder.binding.rankIm!!.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.second))
                holder.binding.rankTxt.text=""
            }
            else if(i==2){
                holder.binding.rankIm!!.visibility = View.VISIBLE
                holder.binding.rankIm.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.third))
                holder.binding.rankTxt.text=""
            }
            else{
                holder.binding.rankTxt!!.visibility = View.VISIBLE
                holder.binding.rankIm!!.visibility = View.GONE
                holder.binding.rankTxt?.text=i.toString()
            }
            holder.binding.name?.text=athletes[i].name
            holder.binding.score?.text= athletes[i].score.toString()
        }
        else{
            holder.binding.cardView.setBackgroundResource(R.drawable.un_select_item)
            print(athletes[i].name)
            var id = Integer.parseInt(athletes[i].id)
            if(i==0){
                holder.binding.rankIm!!.visibility = View.VISIBLE
                holder.binding.rankIm!!.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.first))
                holder.binding.rankTxt.text=""
            }
            else if(i==1){
                holder.binding.rankIm!!.visibility = View.VISIBLE
                holder.binding.rankIm!!.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.second))
                holder.binding.rankTxt.text=""
            }
            else if(i==2){
                holder.binding.rankIm!!.visibility = View.VISIBLE
                holder.binding.rankIm.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.third))
                holder.binding.rankTxt.text=""
            }
            else{
                holder.binding.rankTxt!!.visibility = View.VISIBLE
                holder.binding.rankIm!!.visibility = View.GONE
                holder.binding.rankTxt?.text=i.toString()
            }
            holder.binding.name?.text=athletes[i].name
            holder.binding.score?.text= athletes[i].score.toString()
        }


    }

    override fun getItemCount(): Int {
        return athletes.size
    }

}