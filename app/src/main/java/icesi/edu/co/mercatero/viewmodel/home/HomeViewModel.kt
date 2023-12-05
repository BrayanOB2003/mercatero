package icesi.edu.co.mercatero.viewmodel.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.mercatero.model.Order
import icesi.edu.co.mercatero.model.Product
import icesi.edu.co.mercatero.model.Shop
import icesi.edu.co.mercatero.model.user.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeViewModel:ViewModel() {

    private val _products = MutableLiveData(ArrayList<Product>())
    val products: LiveData<ArrayList<Product>> get() = _products

    private val products2 = ArrayList<Product>()

    private val _store = MutableLiveData(ArrayList<Shop>())
    val stores: LiveData<ArrayList<Shop>> get() = _store

    private val store2 = ArrayList<Shop>()

    private val _orders = MutableLiveData(ArrayList<Order>())
    val orders: LiveData<ArrayList<Order>> get() = _orders

    fun getProductList(){

        viewModelScope.launch(Dispatchers.IO) {

//            Log.d("Test","LLega al viewmodelScope")

            val result= Firebase.firestore.collection("producto").get().await()

            for(doc in result.documents) {

                //Log.d("Test ","Esta en el for " + doc.toString())
                val product = doc.toObject(Product::class.java)

                product?.let { product ->
                //     Log.d("Test", it.toString())

                    var shop = product.let { it ->
                        Firebase.firestore.collection("tienda")
                            .document(it.shop_id).get().await().toObject(Shop::class.java)
                    }
                    shop?.let { it ->
                        product.shopName = it.name
                    }

                    products2.add(product)
                //     Log.d("Test","Pasa del primer query")

                }

            }
            //Log.d("Test","Salio del for con esto " + products2.size)
            _products.postValue(products2)

        }
    }

    fun getStoreList(){

        viewModelScope.launch(Dispatchers.IO) {


            val result= Firebase.firestore.collection("tienda").get().await()

            Log.d("Test","Pasa del primer query")


            for(doc in result.documents) {

                Log.d("Test ","Esta en el for " + doc.toString())
                val store = doc.toObject(Shop::class.java)

                store.let {
                    Log.d("Test", "anadio algo "  + it.toString())
                    store2.add(it!!)

                }

            }
            Log.d("Test","Salio del for store con esto " + products2.size)
           if(store2.isNotEmpty()) {
               _store.postValue(store2)
           }
        }

    }

    fun getOrdersOfUser() {
        viewModelScope.launch(Dispatchers.IO) {
            //var userId = Firebase.auth.currentUser?.uid
            val result = Firebase.firestore.collection("pedido")
                .whereEqualTo("client_id", "p1lSI3Bs4nflEuW2Ip8Cgx0Kxhp1")
                .get()
                .await()

            val orders = result.documents.mapNotNull { document ->
                document.toObject(Order::class.java)
            }

            val sortedOrders = orders.sortedBy { it.status != "OnFinished" }

            _orders.postValue(sortedOrders as ArrayList<Order>?)
        }
    }
}