package ars.example.hisab.View

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ars.example.hisab.Model.entitiy
import ars.example.hisab.databinding.PayableiItemCardBinding

class paymentAdaptor( val content:Context, val  mylist :List<entitiy>):RecyclerView.Adapter<paymentAdaptor.viewHolder>() {
    inner  class viewHolder(val binding :PayableiItemCardBinding):ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(PayableiItemCardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return mylist.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

       holder.binding.payableitemName.text =mylist[position].ItemName
       holder.binding.date.text = mylist[position].Date

       holder.binding?.amount?.text =mylist[position].Total.toString() +" \u20B9"
       holder.binding?.status?.setOnClickListener {

       }
    }

}