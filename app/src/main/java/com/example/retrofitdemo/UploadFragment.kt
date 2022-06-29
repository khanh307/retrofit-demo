package com.example.retrofitdemo

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_upload.*
import java.net.URI


class UploadFragment : Fragment() {

    val REQUEST_CODE_IMAGE = 123;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_upload, container, false)
        var imagepicked: ImageView = view.findViewById(R.id.imagepicked)
        var name: TextView = view.findViewById(R.id.name)
        var up: Button = view.findViewById(R.id.up)

        imagepicked.setOnClickListener {
            var intent: Intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            startActivityForResult(intent, REQUEST_CODE_IMAGE)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null){
            var uri: Uri? = data.data
            var inputStream = context?.contentResolver?.openInputStream(uri!!)
            var bitmap = BitmapFactory.decodeStream(inputStream)
            imagepicked.setImageBitmap(bitmap)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}