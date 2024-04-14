package com.project.skoolio.activities

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.project.skoolio.ui.theme.SkoolioTheme
import com.razorpay.Checkout
import com.razorpay.ExternalWalletListener
import com.razorpay.PaymentData
import org.json.JSONObject
import com.razorpay.PaymentResultWithDataListener;

class PaymentActivity: ComponentActivity(),PaymentResultWithDataListener, ExternalWalletListener, DialogInterface.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkoolioTheme {
//                 A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PaymentPage()
                }
            }
        }
    }
    @Composable
    fun PaymentPage() {
        val context = LocalContext.current
        Checkout.preload(context)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(text = "Payment")
            Button(onClick = { startPayment(context)}) {
                Text(text = "Pay")
            }
        }
    }

    fun startPayment(context: Context) {

        val activity: Activity = this
        val co = Checkout()
        val apiKey = "rzp_test_qLWmTUUyS6hCj0"
        co.setKeyID(apiKey)
        try {
            var options = JSONObject()
            options.put("name", "Skoolio")
            options.put("description", "Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#3399cc");
            options.put("order_id", "order_NyBQgLhFohfexq");
            options.put("currency", "INR")
            options.put("amount", "100")
            options.put("send_sms_hash", true);
            val prefill = JSONObject()
            prefill.put("email", "abhinandannarang3@gmail.com")
            prefill.put("contact", "9646388606")

            options.put("prefill", prefill)
            co.open(activity, options)
        }
        catch (e:Exception){
            Toast.makeText(context,"Some error occured while opening payment page.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?, PaymentData: PaymentData?) {
        TODO("Not yet implemented")
    }

    override fun onPaymentError(errorCode: Int, response: String?, p2: PaymentData?) {
        TODO("Not yet implemented")
    }

    override fun onExternalWalletSelected(p0: String?, p1: PaymentData?) {
        TODO("Not yet implemented")
    }

    override fun onClick(p0: DialogInterface?, p1: Int) {
        TODO("Not yet implemented")
    }

}

