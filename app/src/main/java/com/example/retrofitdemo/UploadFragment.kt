package com.example.retrofitdemo

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.retrofitdemo.Retrofit.APIUtils
import com.example.retrofitdemo.Retrofit.DataClient
import kotlinx.android.synthetic.main.fragment_upload.*

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

class UploadFragment : Fragment() {

    val REQUEST_CODE_IMAGE = 123;
    var realpath: String = ""

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

        up.setOnClickListener {
            var file: File = File(realpath)
            var filepath: String = file.absolutePath
            var mangtenFile: List<String> = filepath.split("\\.")
            //filepath = mangtenFile.get(0) + System.currentTimeMillis()+"."+ mangtenFile.get(1)
            if(name.text.length > 0 ){
                var requestBody: RequestBody= RequestBody.create(MediaType.parse("multipart/form-data"), file)
                var body: MultipartBody.Part = MultipartBody.Part.createFormData("upload", filepath, requestBody)
                var dataClient: DataClient = APIUtils.getData()
                var callBack: retrofit2.Call<String> = dataClient.uploadImage(body)
                callBack.enqueue(object: retrofit2.Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        Log.d("BBB", "Go")
                        if(response.isSuccessful){
                            var message: String = response.body().toString()
                            Log.d("BBB", message)
                            if(message.length > 0){
                               var insertdata: DataClient = APIUtils.getData()
                                var callback: retrofit2.Call<String> = insertdata.insertData(name.text.toString(), APIUtils.Base_Url+"images/"+message)
                                callback.enqueue(object: retrofit2.Callback<String>{
                                    override fun onResponse(
                                        call: Call<String>,
                                        response: Response<String>
                                    ) {
                                        if((response.body().toString()).equals("Success")){
                                            Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    override fun onFailure(call: Call<String>, t: Throwable) {

                                    }

                                })

                            }
                        } else {
                            Log.d("BBB", "Response null")
                            Toast.makeText(context, "Response null", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("CCC", t.message.toString())
                    }

                })
            }

        }

        return view
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null){
            var uri: Uri? = data.data
            realpath = getRealPathFromURI(uri)!!
            var inputStream = context?.contentResolver?.openInputStream(uri!!)
            var bitmap = BitmapFactory.decodeStream(inputStream)
            imagepicked.setImageBitmap(bitmap)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun getRealPathFromURI(contentUri: Uri?): String? {
        var path: String? = null
        val proj = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor: Cursor? = context?.contentResolver?.query(contentUri!!, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            path = cursor.getString(column_index)
        }
        cursor.close()
        return path
    }

}



