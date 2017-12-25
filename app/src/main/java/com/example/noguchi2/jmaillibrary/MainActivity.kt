package com.example.noguchi2.jmaillibrary

import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife

class MainActivity : AppCompatActivity() {

    @BindView(R.id.send)  lateinit var sendButton:Button
    @BindView(R.id.email)  lateinit var email:EditText
    @BindView(R.id.password) lateinit var password:EditText
    @BindView(R.id.server) lateinit var server:EditText
    @BindView(R.id.port)    lateinit var port:EditText
    @BindView(R.id.user) lateinit var user:EditText
    @BindView(R.id.subject) lateinit var subject:EditText
    @BindView(R.id.message) lateinit var message:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        ButterKnife.bind(this)

        sendButton.setOnClickListener {
            //val mailer = javaMailForAndroidl(email.text.toString(),user.text.toString(),password.text.toString(),server.text.toString(),port.text.toString())
            val mailer = AndroidJavaMail(user.text.toString(),password.text.toString(),server.text.toString(),port.text.toString(),"smtp",true)

            if(mailer.isSetToAdress(email.text.toString())){
//                val str = "setToAdress Succes"
                //Toast.makeText(this,"setAddess Succes",Toast.LENGTH_SHORT).show()
            }else{
                //Toast.makeText(this,"setAddess Faile",Toast.LENGTH_SHORT).show()
            }

            if (mailer.isSetSubject(subject.text.toString())){
                //Toast.makeText(this,"setSubject Succes",Toast.LENGTH_SHORT).show()
            }else{
                //Toast.makeText(this,"setSubject Faile",Toast.LENGTH_SHORT).show()
            }

            if(mailer.isSetMessage(message.text.toString())){
                //Toast.makeText(this,"setMessage Succes",Toast.LENGTH_SHORT).show()
            }else{
                //Toast.makeText(this,"setMessage Faile",Toast.LENGTH_SHORT).show()
            }

            if(mailer.isSendMail()){
                Toast.makeText(this,"Succes",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Faile",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
