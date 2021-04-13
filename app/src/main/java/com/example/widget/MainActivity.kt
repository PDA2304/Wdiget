package com.example.widget

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // условие на проверку есть ли разрешение на камеру
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Нет разрешения на камеру", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 111)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        when(requestCode){
            111->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "YES!", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "NO!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}