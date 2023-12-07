package icesi.edu.co.mercatero.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.mercatero.model.Product
import icesi.edu.co.mercatero.model.Shop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ShopDescriptionViewModel:ViewModel() {

    private val _products = MutableLiveData(ArrayList<Product>())
    val products: LiveData<ArrayList<Product>> get() = _products

    fun getStoreInfo(storeId: String){

        var products2 = ArrayList<Product>()

        viewModelScope.launch(Dispatchers.IO){

            val result2 = Firebase.firestore.collection("producto").whereEqualTo("shop_id",storeId).get().await()

            for(doc in result2.documents) {

                //Log.d("Test ","Esta en el for " + doc.toString())
                val product = doc.toObject(Product::class.java)

                product.let {
                    //      Log.d("Test", it.toString())
                    products2.add(it!!)

                }

            }
            _products.postValue(products2)

        }



        }

    }


