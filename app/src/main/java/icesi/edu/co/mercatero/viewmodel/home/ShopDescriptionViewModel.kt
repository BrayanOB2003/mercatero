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

    private val products2 = ArrayList<Product>()

    private val _store = MutableLiveData(Shop())
    val store: LiveData<Shop> get() = _store

    fun getStoreInfo(storeId: String){


        viewModelScope.launch(Dispatchers.IO){

            val result = Firebase.firestore.collection("tienda").document(storeId).get().await()

            val shop = result.toObject(Shop::class.java)


            _store.postValue(shop)

            val result2 = Firebase.firestore.collection("producto").whereEqualTo("shop_id",storeId).get().await()

            for(doc in result2.documents) {

                //Log.d("Test ","Esta en el for " + doc.toString())
                val product = doc.toObject(Product::class.java)

                product.let {
                    //      Log.d("Test", it.toString())
                    products2.add(it!!)

                }

            }
            //Log.d("Test","Salio del for con esto " + products2.size)
            _products.postValue(products2)

        }



        }

    }


