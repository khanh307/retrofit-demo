package com.example.retrofitdemo.Retrofit

object APIUtils {
    public var Base_Url = "http://192.168.1.172:8012/Retrofit/"

    public fun getData(): DataClient{
        return RetrofitClient().getClient(Base_Url).create(DataClient::class.java)
    }
}