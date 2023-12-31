package icesi.edu.co.mercatero.viewmodel.home

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import icesi.edu.co.mercatero.model.Order
import icesi.edu.co.mercatero.model.Product
import icesi.edu.co.mercatero.model.Shop
import icesi.edu.co.mercatero.model.enumeration.OrderStatus
import icesi.edu.co.mercatero.model.user.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

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

    private val db = Firebase.firestore

    private val _myOrders = MutableLiveData<ArrayList<Order>>()
    val myOrders: LiveData<ArrayList<Order>> get() = _myOrders

    fun getProductList(){

        val products2 = ArrayList<Product>()

        viewModelScope.launch(Dispatchers.IO) {
            observerShopDocuments()

            if(!productsLoaded){
                val result= Firebase.firestore.collection("producto").get().await()

                for(doc in result.documents) {

                    val product = doc.toObject(Product::class.java)

                    product?.let { productP ->

                        val shop = productP.let {
                            Firebase.firestore.collection("tienda")
                                .document(it.shop_id).get().await().toObject(Shop::class.java)
                        }
                        shop?.let {
                            productP.shopName = it.name
                        }

                        products2.add(productP)
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
            val userId = Firebase.auth.currentUser?.uid
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

            val client = result?.toObject(Client::class.java)

            withContext(Dispatchers.Main) {
                _clientAuth.value = client
                clientLoaded = true
            }
        }
    }

    fun updateProfileImage(newURI: Uri){

        viewModelScope.launch (Dispatchers.IO){
            val id = Firebase.auth.currentUser?.uid

            val storageReference = Firebase.storage.reference
                .child("cliente")
                .child(UUID.randomUUID().toString())
            storageReference.putFile(newURI).await()
            val imageURL = storageReference.downloadUrl.await().toString()
            val updates = hashMapOf<String, Any>(
                "imageURL" to imageURL
            )
            id?.let {
                Firebase.firestore.collection("cliente").document(id).update(updates)
            }
        }

    }

    fun signOut(){
        viewModelScope.launch (Dispatchers.IO) {
            Firebase.auth.signOut()
        }
    }

    fun addProductToOrder(product: Product, quantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                    val result = Firebase.firestore.collection("pedido")
                        .whereEqualTo("shop_id", product.shop_id)
                        .whereEqualTo("client_id", clientAuth.value?.client_id)
                        .whereEqualTo("status", OrderStatus.TO_ORDER.name)
                        .get().await()

                    val currentOrders = result.documents.mapNotNull { document ->
                        document.toObject(Order::class.java)
                    }.toMutableList()

                    if(currentOrders.size == 1) {
                        val shopOrder = currentOrders[0]

                        val existingProduct = shopOrder.idProducts.indexOf(product.product_id)
                        if (existingProduct != -1) {
                            val currentQuantity = shopOrder.quantities[existingProduct].toInt()
                            shopOrder.quantities[existingProduct] =
                                (currentQuantity + quantity).toString()
                        } else {
                            shopOrder.idProducts.add(product.product_id)
                            shopOrder.quantities.add(quantity.toString())
                        }
                        val oldPrice = shopOrder.price.toInt()
                        shopOrder.price = (oldPrice + product.price.toInt().times(quantity)).toString()

                        currentOrders[0] = shopOrder
                        db.collection("pedido").document(shopOrder.order_id).set(shopOrder).await()
                    } else {
                        val newOrder = clientAuth.value?.client_id?.let { client_id ->
                            clientAuth.value?.address?.let { address ->
                                Order(
                                    order_id = UUID.randomUUID().toString(),
                                    client_id = client_id,
                                    shop_id = product.shop_id,
                                    address = address,
                                    price = product.price.toInt().times(quantity).toString(),
                                    idProducts = arrayListOf(product.product_id),
                                    quantities = arrayListOf(quantity.toString()),
                                    status = OrderStatus.TO_ORDER.name
                                )
                            }
                        }
                        newOrder?.let { db.collection("pedido").document(it.order_id).set(newOrder).await() }
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }

    }

    suspend fun addOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            _myOrders.value?.forEach{
                    order -> addOrder(order)
            }
            _myOrders.postValue(arrayListOf())
        }
    }

    private suspend fun addOrder(order: Order) {
        if (order.idProducts.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    db.collection("pedido").add(order).await()
                } catch (e : Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}