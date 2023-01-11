package com.example.aplicacionsophos.UI
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionsophos.data.model.*
import com.example.aplicacionsophos.service.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AplicationViewModel: ViewModel() {
private  val BASE_URL = "https://6w33tkx4f9.execute-api.us-east-1.amazonaws.com/"
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private val service: Api = retrofit.create(Api::class.java)

val documentList = MutableLiveData<List<Document>>()
val oficeList = MutableLiveData<List<Office>>()
val logininfo = MutableLiveData<User>()
val loginuser = MutableLiveData<String>()
val iduser = MutableLiveData<Int>()
fun getDocumentList(id:String,correo:String){
    val call = service.getdocuments(id,correo)
    call.enqueue(object : Callback<DocumentsResponse>{
        override fun onResponse(call: Call<DocumentsResponse>, response: Response<DocumentsResponse>) {
            response.body()?.Items?.let { list ->
                documentList.postValue(list)
            }
        }
        override fun onFailure(call: Call<DocumentsResponse>, t: Throwable) {
            call.cancel()
        }

    })
}

    fun getOficeList() {
//viewModelScope.launch {
    val call = service.getofices()
    call.enqueue(object : Callback<officeResponse> {
        override fun onResponse(call: Call<officeResponse>, response: Response<officeResponse>) {
            response.body()?.Items?.let { list -> oficeList.postValue(list) }

        }

        override fun onFailure(call: Call<officeResponse>, t: Throwable) {
            call.cancel()
        }

    })



}

fun getuserlogin(correo: String,password:String){
    val call = service.postlogin(correo,password)
    call.enqueue(object : Callback<User>{
        override fun onResponse(call: Call<User>, response: Response<User>) {
             if(response.isSuccessful) {
                val loginresponse = response.body()
                 response.body()?.let { list -> logininfo.postValue(list) }
                /* if (loginresponse == null) {
                    response.errorBody().toString()
                }else{
                    if(loginresponse.acceso.equals(true) ){
                        /*    Toast.makeText(applicationContext, "bienvenido", Toast.LENGTH_SHORT).show()
                        gotomenu()*/
                        response.body()?.nombre?.let { name -> loginuser.postValue(name) }
                        response.body()?.id?.let { id -> iduser.postValue(id) }
                        }else {
                        loginresponse.acceso
                    }
                }*/
            }
            else{
                 response.errorBody().toString()
                }
        }
        override fun onFailure(call: Call<User>, t: Throwable) {
            call.cancel()
        }

    })
}

    fun postdocument(document: Document){

        val call=service.postdocuments(document)
        call.enqueue(object : Callback<Document> {
            override fun onResponse(call: Call<Document>, response: Response<Document>) {
                if(response.isSuccessful) {
                    val postresponse = response.body()
                    if (postresponse == null) {
                        call.cancel()
                    } else{
                        call.execute()
                    }
                }
            }


            override fun onFailure(call: Call<Document>, t: Throwable) {
                call.cancel()
            }

        })
    }
}

