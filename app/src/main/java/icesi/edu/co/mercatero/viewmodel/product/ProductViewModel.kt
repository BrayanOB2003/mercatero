package icesi.edu.co.mercatero.viewmodel.product

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import icesi.edu.co.mercatero.model.Product
import icesi.edu.co.mercatero.model.Shop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID
import kotlin.collections.ArrayList

class ProductViewModel: ViewModel() {


    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val _myProducts = MutableLiveData<ArrayList<Product>>()
    val myProducts: LiveData<ArrayList<Product>> get() = _myProducts

    private val products = ArrayList<Product>()

    private val _shopAuth = MutableLiveData(Shop())
    val shopAuth: LiveData<Shop> get() = _shopAuth

    suspend fun addProduct(name: String, description: String, price: String, uri: Uri) {
        val shopId = Firebase.auth.currentUser!!.uid
        if (shopId != null) {
            val filename = UUID.randomUUID().toString()
            val productId = UUID.randomUUID().toString()
            val storageReference =Firebase.storage.reference
                .child("product")
                .child(filename)
            storageReference.putFile(uri).await()
            val imageUrl = storageReference.downloadUrl.await().toString()
            val newProduct = Product(productId, name, description, price, imageUrl, shopId, "")
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val shopDocument = db.collection("tienda")
                        .document(shopId).get().await()
                    newProduct.shopName = shopDocument.getString("name") ?: ""
                    db.collection("producto").document(productId).set(newProduct).await()
                    /*
                    db.runTransaction { transaction ->
                        val shopSnapshot = transaction.get(shopDocument)
                        val currentProductsList = shopSnapshot.get("productos") as ArrayList<String>?: ArrayList()
                        currentProductsList.add(result.id)
                        transaction.update(shopDocument, "productos", currentProductsList)
                    }.await()*/
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

     fun loadMyProducts() {
         viewModelScope.launch(Dispatchers.IO) {

            val result = Firebase.firestore.collection("producto").whereEqualTo("shop_id",Firebase.auth.currentUser!!.uid).get().await()

             for(doc in result.documents){

                 var product = doc.toObject(Product::class.java)

                 Log.d("Test",product!!.product_id)
                 products.add(product!!)

             }
             _myProducts.postValue(products)
         }
     }

    fun getAuthUser(){
        viewModelScope.launch (Dispatchers.IO){
            val userId = Firebase.auth.currentUser?.uid
            val result = userId?.let { Firebase.firestore.collection("tienda").document(it).get().await() }

            var shop = result?.toObject(Shop::class.java)
            _shopAuth.postValue(shop)
        }
    }
}

  /*  private fun getUserShop(): String? {
        return auth.currentUser?.uid
    }

   */
