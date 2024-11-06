package ars.example.hisab.View

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ars.example.hisab.Model.entitiy
import ars.example.hisab.databinding.AreYouSureBinding
import ars.example.hisab.databinding.ItemLayoutBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class adaptor(val itemList:List<entitiy>, val context:Context,
              val update :(id:Int, unit:Double?)->Unit,
              val minsert:(ItemName: entitiy)->Unit,
              val delete :(id:Int)->Unit):RecyclerView.Adapter<adaptor.viewHolder>() {
    inner class viewHolder(val binding:ItemLayoutBinding) : ViewHolder(binding.root){}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder( ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.binding.itemName.text= itemList[position].ItemName
        holder.binding.doneButton.setOnClickListener {
            showpopUp(position)
        }
        holder.binding.Total.text = itemList[position].Total.toString() +" ₹"
        holder.binding.Pay.setOnClickListener {
            showPaymentDialog(itemList[position])
        }
        holder.binding.date.text = get_date()
        holder.binding.delete.setOnClickListener {
            showDeleteDialog(itemList[position].id)
        }

    }
    private fun showDeleteDialog(id:Int ){
        val bind  = AreYouSureBinding.inflate(LayoutInflater.from(context))
        val dialog = Dialog(context)
        dialog.setContentView(bind.root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bind.textView2.text ="Are you sure ?"
        bind.textView2.setBackgroundColor(Color.parseColor("#FF0000"))
        bind.textView2.setTextColor(Color.WHITE)
        bind.positive.setTextColor(Color.WHITE)
        bind.unit.isVisible = false
        bind.negative.setTextColor((Color.WHITE))
        bind.positive.setBackgroundColor(Color.parseColor("#FF0000"))
        bind.negative.setBackgroundColor(Color.GREEN)
        bind.negative.setOnClickListener {
            dialog.dismiss()

        }
        bind.positive.setOnClickListener {
            delete.invoke(id)
            dialog.dismiss()
        }
        dialog.show()

    }
private fun showPaymentDialog(entitiy: entitiy)
{
    AlertDialog.Builder(context).setTitle("Confirm Payment").setMessage("Note : Do you want to proceed ?")
        .setNegativeButton("No"){
           dialogInterface,_-> dialogInterface.dismiss()
        }.setPositiveButton("Proceed"){
           dialogInterface,_->
            minsert.invoke(entitiy)
           dialogInterface.dismiss()
        }.create().show()
}


    private fun showpopUp(s: Int)
    {
       val bind  = AreYouSureBinding.inflate(LayoutInflater.from(context))
       val dialog = Dialog(context)
        dialog.setContentView(bind.root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bind.unit.isVisible =true
        bind.textView2.text =bind.textView2.text.toString()+" "+itemList[s].ItemName+"?"
        bind.negative.setOnClickListener {
            dialog.dismiss()

        }

        bind.positive.setOnClickListener {
            val v = bind.unit.text.toString()
            if(v.isNullOrEmpty() || v.isNullOrBlank()){
                update.invoke(itemList[s].id,null)
            }
            else{
                update.invoke(itemList[s].id,v.toDouble())
            }



            dialog.dismiss()
        }
        dialog.show()
    }

    private fun get_date():String{

        val timeIntMilis = System.currentTimeMillis()
        val date  = Date(timeIntMilis)
        val sdf = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())
        val  v = sdf.format(date)
        return v
    }


}