package com.example.assingment5

import android.Manifest.permission.CALL_PHONE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var phoneNumber = "01849655585"
    private val PHONE_CALL_PERMISSION_REQUEST_CODE = 12598


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





       //using Glide
        glide()

     //using listView
        listView()




        //Call button
        btn_call.setOnClickListener {
            callThePerson()
        }


        //Email button
        btn_email.setOnClickListener {
            val addresses = "md.t.kamal539@gmail.com"
            val subject = "Test"
            val message = "Test"
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL,addresses)
            intent.putExtra(Intent.EXTRA_SUBJECT,subject)
            intent.putExtra(Intent.EXTRA_TEXT,message)
            intent.type="message/rfc822"
            startActivity(Intent.createChooser(intent,"Send Email using:"))
        }
        //Share button
        btn_share.setOnClickListener {
            val intent=Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this app:")
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Share To:"))
        }

        //camera button
        btn_camera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intent)
        }



    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PHONE_CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callThePerson()
            }
        }

    }


    private fun callThePerson() {
        if (isPermissionGranted()) {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$phoneNumber")
            startActivity(callIntent)
        } else {
            requestPermission()
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(applicationContext, CALL_PHONE) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(CALL_PHONE),
            PHONE_CALL_PERMISSION_REQUEST_CODE
        )
    }

    private fun getListData(): Array<String> {
        return arrayOf(
            "Argentina",
            "Australia",
            "Brazil",
            "Canada",
            "China",
            "France",
            "Germany",
            "India",
            "Indonesia",
            "Italy",
            "Bangladesh",
            "Japan",
            "Mexico",
            "Russia",
            "Saudi Arabia",
            "South Africa",
            "South Korea",
            "Turkey",
            "United Kingdom",
            "United States"
        )

    }


     private fun glide(){
        //using glide
        Glide.with(this@MainActivity)
            .load("https://i.imgur.com/qpr5LR2.jpg")
            //.load("https://i.imgur.com/Vth6CBz.gif")
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(iv_glide)

    }

    private fun listView(){

        val listData = getListData()

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            listData
        )
        listView.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
           val value=position+1
            val pos = (value% 2==0)

            if(pos){

                Toast.makeText(this,listData[position]+value+": Even",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,listData[position]+value+": Odd",Toast.LENGTH_SHORT).show()
            }


        }
    }
}
