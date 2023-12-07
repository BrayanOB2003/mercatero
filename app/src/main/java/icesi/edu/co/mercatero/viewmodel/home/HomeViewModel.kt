package icesi.edu.co.mercatero.viewmodel.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.mercatero.model.Order
import icesi.edu.co.mercatero.model.Product
import icesi.edu.co.mercatero.model.Shop
import icesi.edu.co.mercatero.model.user.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeViewModel:ViewModel() {

    private val _products = MutableLiveData(ArrayList<Product>())
    val products: LiveData<ArrayList<Product>> get() = _products
    var productsLoaded = false

    private val _store = MutableLiveData(ArrayList<Shop>())
    val stores: LiveData<ArrayList<Shop>> get() = _store
    var storesLoaded = false
    private val store2 = ArrayList<Shop>()

    private val _orders = MutableLiveData(ArrayList<Order>())
    val orders: LiveData<ArrayList<Order>> get() = _orders

    private val _clientAuth = MutableLiveData(Client())
    val clientAuth: LiveData<Client> get() = _clientAuth
    var clientLoaded = false

    fun getProductList(){

        val products2 = ArrayList<Product>()

        viewModelScope.launch(Dispatchers.IO) {
            observerShopDocuments()

            if(!productsLoaded){
                val result= Firebase.firestore.collection("producto").get().await()

                for(doc in result.documents) {

                    val product = doc.toObject(Product::class.java)

                    product?.let { product ->

                        var shop = product.let { it ->
                            Firebase.firestore.collection("tienda")
                                .document(it.shop_id).get().await().toObject(Shop::class.java)
                        }
                        shop?.let { it ->
                            product.shopName = it.name
                        }

                        products2.add(product)
                    }
                }
                _products.postValue(products2)
                productsLoaded = true
            }
        }
    }

    fun getStoreList(){
        viewModelScope.launch(Dispatchers.IO) {
            if(!storesLoaded){
                val result= Firebase.firestore.collection("tienda").get().await()

                for(doc in result.documents) {

                    val store = doc.toObject(Shop::class.java)

                    store?.let {
                        store2.add(it)

                    }
                }
                if(store2.isNotEmpty()) {
                    _store.postValue(store2)
                    storesLoaded = true
                }
            }
        }

    }

    private fun observerShopDocuments(){
        val collectionRef = FirebaseFirestore.getInstance().collection("producto")

        collectionRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                return@addSnapshotListener
            }
            for (documentChange in snapshot?.documentChanges!!) {
                val newData = documentChange.document.toObject(Product::class.java)
                _products.value?.add(newData)
            }
        }
    }

    fun getOrdersOfUser() {
        viewModelScope.launch(Dispatchers.IO) {
            var userId = Firebase.auth.currentUser?.uid
            val result = Firebase.firestore.collection("pedido")
                .whereEqualTo("client_id", userId)
                .get()
                .await()

            val orders = result.documents.mapNotNull { document ->
                document.toObject(Order::class.java)
            }

            val sortedOrders = orders.sortedBy { it.status != "TO_DO" }

            _orders.postValue(ArrayList(sortedOrders))
        }
    }

    fun getAuthUser(){
        viewModelScope.launch (Dispatchers.IO){
            val userId = Firebase.auth.currentUser?.uid
            val result = userId?.let { Firebase.firestore.collection("cliente").document(it).get().await() }

            var client = result?.toObject(Client::class.java)

            withContext(Dispatchers.Main) {
                _clientAuth.value = client
                clientLoaded = true
            }
        }
    }
}