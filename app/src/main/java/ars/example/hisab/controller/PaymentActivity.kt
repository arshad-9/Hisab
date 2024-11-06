package ars.example.hisab.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ars.example.hisab.Model.Dao
import ars.example.hisab.RoomApp
import ars.example.hisab.databinding.ActivityPaymentBinding
import ars.example.hisab.View.paymentAdaptor
import kotlinx.coroutines.launch

class PaymentActivity : AppCompatActivity() {
    private  var binding :ActivityPaymentBinding?= null
    private var daao : Dao?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding?.root)
         daao  = (application as RoomApp).db.getDao()
      setSupportActionBar(binding?.toolbarPayment)
        if(supportActionBar!= null)
        {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarPayment?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        lifecycleScope.launch {
            daao!!.getUnpaid(true).collect{
                binding?.payableitems?.adapter = paymentAdaptor(this@PaymentActivity,it)
                binding?.payableitems?.layoutManager=LinearLayoutManager(this@PaymentActivity,LinearLayoutManager.VERTICAL,false)

            }}
            binding?.payableitems?.adapter?.notifyDataSetChanged()
        
    }
}