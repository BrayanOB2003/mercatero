package icesi.edu.co.mercatero.viewmodel.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.mercatero.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeViewModel:ViewModel() {

    private val _products = MutableLiveData(Product())
    val products: LiveData<Product> get() = _products

    fun getProductList(){

        viewModelScope.launch(Dispatchers.IO) {

            val result= Firebase.firestore.collection("producto").get().await()

            for(doc in result.documents){

                val product = doc.toObject(Product::class.java)

                product.let{
                    Log.d("Test",it.toString())
                    _products.postValue(product)

                }

            }

        }
    }


}