package com.project.skoolio.activities

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.project.skoolio.model.singletonObject.studentDetails
import com.project.skoolio.ui.theme.SkoolioTheme
import com.project.skoolio.utils.currentPayment
import com.project.skoolio.utils.epochToMonthYearString
import com.project.skoolio.utils.paymentPageData
import com.razorpay.Checkout
import com.razorpay.ExternalWalletListener
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject

class PaymentActivity: ComponentActivity(),PaymentResultWithDataListener, ExternalWalletListener, DialogInterface.OnClickListener{
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContent {
//            SkoolioTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                }
//            }
//        }
        PaymentPage()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun PaymentPage() {
//        val navController = rememberNavController()
//        val context = LocalContext.current

        Checkout.preload(this)
        startPayment()
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center) {
//            Text(text = "Payment")
//            Button(onClick = { startPayment(context)}) {
//                Text(text = "Pay")
//            }
//        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun startPayment(
//        context: Context
    ) {
        val activity: Activity = this
        val co = Checkout()
        val apiKey = co.setKeyID(paymentPageData.get("apiKey"))
        try {
            var options = JSONObject()
            options.put("name", "Skoolio")
            options.put("description", "Fee Payment for " + epochToMonthYearString(currentPayment.feeMonthEpoch) )
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#3399cc");
            options.put("order_id", currentPayment.orderId);
            options.put("currency", "INR")
            options.put("amount", currentPayment.feeAmount*100)
            options.put("send_sms_hash", true);
            val prefill = JSONObject()
            prefill.put("email", studentDetails.email)
            prefill.put("contact", studentDetails.primaryContact.value)

            options.put("prefill", prefill)
            co.open(activity, options)
        }
        catch (e:Exception){
            Toast.makeText(this,"Some error occured while opening payment page.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?, PaymentData: PaymentData?) {
//        Toast.makeText(this,"Payment success. Response is -$razorpayPaymentId", //TODO:Change this message later
//            Toast.LENGTH_LONG).show()
        val resultCode = Activity.RESULT_OK // or any custom result code you want to pass back
        val resultIntent = Intent()
        resultIntent.putExtra("razorpayPaymentId", razorpayPaymentId) // You can also pass data back via Intent extras
        setResult(resultCode, resultIntent)
        finish()
    }

    override fun onPaymentError(errorCode: Int, response: String?, p2: PaymentData?) {
//        Toast.makeText(this,"Payment failure. Response is -$response", Toast.LENGTH_LONG).show()
        val resultCode = Activity.RESULT_CANCELED // or any custom result code you want to pass back
        val resultIntent = Intent()
        resultIntent.putExtra("response", response) // You can also pass data back via Intent extras
        setResult(resultCode, resultIntent)
        finish()
    }

    override fun onExternalWalletSelected(p0: String?, p1: PaymentData?) {
        TODO("Not yet implemented")
    }

    override fun onClick(p0: DialogInterface?, p1: Int) {
        TODO("Not yet implemented")
    }

}

