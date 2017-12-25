package com.example.noguchi2.jmaillibrary

import java.util.*
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

/**
 * Created by D.Noguchi on 2017/10/02.
 * JavaMail API for Android class
 */
class AndroidJavaMail(
  //      private val email:String,
        private val user:String,
        private val password:String,
        server:String,
        port:String,
        private val protocol:String,
        private val secure:Boolean
) {

//    private lateinit var mSubject:String
    private val multipart = MimeMultipart()
    private val messagePart = MimeBodyPart()                   // メール本文用Content

    private val props: Properties = Properties()                // メール送信サーバープロパティ
    private val session = Session.getInstance(props, object : javax.mail.Authenticator() {
        override fun getPasswordAuthentication(): javax.mail.PasswordAuthentication {
            return PasswordAuthentication(user, password)
        }
    })
    private val msg = MimeMessage(session)                     // メールデータ用変数
    private lateinit var mTransport:Transport                 // メール送信用Transport
    private val  charCode = "utf-8"

    // props初期化
    init {
//        props.put("mail.smtp.host", server)
//        props.put("mail.host", server)
//        props.put("mail.smtp.port", port)
//        props.put("mail.smtp.auth", "true")

        // procol別初期化処理
//        if (protocol == "smtp") {
//            // SSL設定
//            if (port == "465") {
//                props.put("mail.smtp.socketFactory.port", port)
//                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
//                props.put("mail.smtp.socketFactory.fallback", "false")
//            }
//
//            // TSL設定
//            if (port == "587") {
//                props.put("mail.smtp.starttls.enable", "true")
//            }
//        }

        // プロトコル別初期化処理
        when(protocol){
            "smtp"->{
                initPropsSmtp(server,port)
            }
            "pop"->{
                initPropsPop(server,port)
            }
            "imap"->{
                initPropsImap(server,port)
            }
        }
        msg.setFrom(InternetAddress(user))
    }

    private fun initPropsSmtp(server:String,port:String){

        props.put("mail.smtp.host", server)
        props.put("mail.host", server)
        props.put("mail.smtp.port", port)
        props.put("mail.smtp.auth", "true")

        when(secure) {
            false -> {
                // SSL設定
                props.put("mail.smtp.socketFactory.port", port)
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
                props.put("mail.smtp.socketFactory.fallback", "false")
            }
        }
    }

    private fun initPropsPop(server: String,port: String) {
        props.put("mail.pop3.host", server)
        props.put("mail.host", server)
        props.put("mail.pop3.port", port)
        //props.put("mail.pop3.auth", "true")

        when(secure){
            true->{
                // TSL設定

            }false->{
            //SSL設定
            props.put("mail.pop3.smtp.socketFactory.port",port)
            props.put("mail,pop3.socketFactory.class","jacax.net.ssl.SSLSocketFactory")
            props.put("mail.pop3.socketFactory.fallback","false")

        }
        }

    }

    private fun initPropsImap(server: String,port:String){
        props.put("mail.imap.host", server)
        props.put("mail.host", server)
        props.put("mail.imap.port", port)
        //props.put("mail.imap.auth", "true")

        when(secure){
            false->{
                //SSL設定
                props.put("mail.imap.socketFactory.port",port)
                props.put("mail.imap.socketFactory.class","javax.net.ssl.SSLSocketFactory")
                props.put("mail.imap.socketFactory.fallback","false")
            }
        }

    }


    fun isSetToAdress(email:String):Boolean {
        return try{
            msg.setRecipient(javax.mail.Message.RecipientType.TO, InternetAddress(email))
            true
        }catch (e:Exception){
            false
        }
    }
    fun isSetSubject(subject:String):Boolean {
        return try {
            msg.setSubject(subject, charCode)
            true

        }catch (e:Exception){
            false
        }
    }

    fun isSetMessage(message:String):Boolean{
        return try {
            messagePart.setText(message)
            multipart.addBodyPart(messagePart)
            msg.setContent(multipart, charCode)

            true
        }catch(e:Exception){
            false
        }
    }

    fun isSendMail():Boolean{

        try {

//            val props = Properties()
//            props.put("mail.smtp.host", server)
//            props.put("mail.host", server)
//            props.put("mail.smtp.port", port)
//            props.put("mail.smtp.auth","true")
//
//            // SSL設定
//            if (port == "465") {
//                props.put("mail.smtp.socketFactory.port", port)
//                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
//                props.put("mail.smtp.socketFactory.fallback","false")
//            }
//
//            // TSL設定
//            if(port == "587"){
//                props.put("mail.smtp.starttls.enable", "true")
//            }
//
//            val session = Session.getInstance(props, object : javax.mail.Authenticator() {
//                override fun getPasswordAuthentication(): javax.mail.PasswordAuthentication {
//                    return PasswordAuthentication(user, password)
//                }
//            })

//            val msg = MimeMessage(session)
//            msg.setSubject("", "utf-8")
//            val multiPart=MimeMultipart()
//            val messagePart=MimeBodyPart()
//            messagePart.setText("Mail Send Test")
//            multiPart.addBodyPart(messagePart)
//            msg.setContent(multiPart,"utf-8")
//            msg.setFrom(InternetAddress(user))
//            msg.setRecipient(javax.mail.Message.RecipientType.TO, InternetAddress(email))

//            val transport = session.getTransport("smtp")
//            transport.connect(user, password)
//            transport.sendMessage(msg, msg.allRecipients)
//            transport.close()

            mTransport = session.getTransport(protocol)
            mTransport.connect(user,password)
            mTransport.sendMessage(msg,msg.allRecipients)
            mTransport.close()

            return true
        } catch (e: Exception) {
            return false
        }
    }
}