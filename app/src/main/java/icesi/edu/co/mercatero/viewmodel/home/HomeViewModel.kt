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

    private val _products = MutableLiveData(ArrayList<Product>())
    val products: LiveData<ArrayList<Product>> get() = _products

    val products2 = ArrayList<Product>()

    fun getProductList(){

        viewModelScope.launch(Dispatchers.IO) {

            val result= Firebase.firestore.collection("producto").get().await()

            for(doc in result.documents){

                val product = doc.toObject(Product::class.java)

                product.let{
                    Log.d("Test",it.toString())
                   products2.add(it!!)

                }

            }
            Log.d("Test",products2.toString())
            _products.postValue(products2)

        }
    }


}