package ars.example.hisab.controller

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ars.example.hisab.Model.Dao
import ars.example.hisab.R
import ars.example.hisab.RoomApp
import ars.example.hisab.View.adaptor
import ars.example.hisab.databinding.ActivityMainBinding
import ars.example.hisab.databinding.AddItemBinding
import ars.example.hisab.Model.entitiy
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding?= null

    private var daao : Dao?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        daao = (application as RoomApp).db.getDao()
        lifecycleScope.launch {
            daao!!.getUnpaid(false).collect{
                binding?.itemList?.adapter = adaptor(it,this@MainActivity,{id,unit->setUpdate(id,unit)},{name->mInsert(name)},{id->mdelete(id)})
                binding?.itemList?.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            }
            }

        binding?.itemList?.adapter?.notifyDataSetChanged()


            binding?.AddNew?.setOnClickListener {

                showAddDialog()

            }

            setSupportActionBar(binding?.toolbarMain)



        }

    private fun mdelete(id:Int){
        lifecycleScope.launch{
         val xc=daao!!.getById(id)
            daao!!.delete(xc)
        }
    }
    private fun mInsert(name: entitiy){
        if(name.Total>0) {
            lifecycleScope.launch {
                daao?.update(
                    entitiy(
                        id = name.id,
                        ItemName = name.ItemName,
                        Units = name.Units,
                        Amount_PerUnit = name.Amount_PerUnit,
                        Status = true,
                        Date = getDate(),
                        Total = name.Total
                    )
                )
                daao?.insert(
                    entitiy(
                        ItemName = name.ItemName,
                        Units = name.Units,
                        Amount_PerUnit = name.Amount_PerUnit,
                        Total = 0.0,
                        Status = false
                    )
                )
            }
        }
    }

     private fun setUpdate(id:Int,unit:Double?){
       var x : Double =0.0
        var y: Double =0.0
        var total=0.0
        var name=""
        var  status =false
        var date =""
        var newTotal = 0.0

        lifecycleScope.launch{
              val it= daao!!.getById(id)
              if(unit==null){  x =   it.Units}
              else{  x =  unit }
                y = it.Amount_PerUnit
                total = it.Total
                date = it.Date
                name = it.ItemName
                status = it.Status
                newTotal =  it.Total+ (x * y)
              Log.i("tag",newTotal.toString())



                Log.i("tagOut",newTotal.toString())
                daao?.update(
                    entitiy(id=id,Total =newTotal,
                    Status = status, ItemName = name, Units = it.Units, Amount_PerUnit = y, Date = date)
                )
                 }


    }

 private fun showAddDialog(){
     val bind  = AddItemBinding.inflate(LayoutInflater.from(this))
     val dialog = Dialog(this)
     dialog.setContentView(bind.root)
     dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
     bind?.button?.setOnClickListener {
         val name  = bind?.etItemName?.text.toString()
         val prize  = bind?.etRupee?.text.toString()
         val units = bind?.etUnits?.text.toString().toDouble()
         lifecycleScope.launch {
            daao?.insert (entitiy(ItemName  = name , Units = units, Amount_PerUnit = prize.toDouble(), Total = 0.0, Status = false))
         }
         dialog.dismiss()

      }
      dialog.show()

     }









    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.history ->{
                val intent = Intent(this, PaymentActivity::class.java)
                startActivity(intent)

            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun getDate():String{

        val timeIntMilis = System.currentTimeMillis()
        val date  = Date(timeIntMilis)
        val sdf = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())
        val  v = sdf.format(date)
        return v
    }


    }