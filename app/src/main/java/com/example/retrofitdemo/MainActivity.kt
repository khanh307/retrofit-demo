package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var inHome = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(HomeFragment())


        upload.setOnClickListener {
            if(inHome){
                replaceFragment(UploadFragment())
                inHome = false
            }
        }

        back.setOnClickListener {
            if(!inHome){
                replaceFragment(HomeFragment())
            }
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        var transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame, fragment)
        transaction.commit()
    }


}