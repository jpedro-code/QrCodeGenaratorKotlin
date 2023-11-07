package com.devandroid.qrcodekotlin

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import kotlin.math.absoluteValue


class MainActivity : AppCompatActivity() {
    var qrCodeImage: ImageView?= null
    var genaratorButton : Button? = null
    var qrCodeTextContent : EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
        genaratorButton!!.setOnClickListener {
            if(TextUtils.isEmpty(qrCodeTextContent?.text.toString())){
                qrCodeTextContent!!.setError("Empty")
                qrCodeTextContent!!.requestFocus()
            }else{
                genarateQrCode(qrCodeTextContent?.text.toString());
            }
            }
        }

    private fun initComponents() {
        qrCodeImage = findViewById(R.id.qr_code_image)
        genaratorButton = findViewById(R.id.button_genarator)
        qrCodeTextContent = findViewById(R.id.qr_code_content)
    }

    private fun genarateQrCode(textContent: String) {

        val qrCodeWriter = QRCodeWriter()

        try{
            val matrix = qrCodeWriter.encode(textContent,BarcodeFormat.QR_CODE,200,200)
            val width:Int = matrix.width
            val heigth:Int = matrix.height

            val bitmap = Bitmap.createBitmap(width,heigth,Bitmap.Config.RGB_565)

            for(x in 0 until width){
                for(y in 0 until  heigth){
                    bitmap.setPixel(x,y, if(matrix[x,y]) Color.BLACK else Color.WHITE)
                }
            }
            qrCodeImage!!.setImageBitmap(bitmap)
        }catch (e : WriterException){
            Toast.makeText(this, "Erro",Toast.LENGTH_LONG).show()
        }
    }
}