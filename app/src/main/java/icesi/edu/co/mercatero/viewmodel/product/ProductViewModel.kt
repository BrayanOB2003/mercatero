package icesi.edu.co.mercatero.viewmodel.product

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import icesi.edu.co.mercatero.model.shop.Product
import icesi.edu.co.mercatero.viewmodel.authetication.AuthState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

class ProductViewModel: ViewModel() {

    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    private val _myProducts = MutableLiveData<Array<Product>>()
    val myProducts: LiveData<Array<Product>> get() = _myProducts

    fun loadUser(): String? {
        return auth.currentUser?.uid
    }

    suspend fun addProduct(name: String, description: String, price: Double, uri: Uri) {
        val shopId = auth.currentUser?.uid
        if (shopId != null) {
            val filename = UUID.randomUUID().toString()
            Firebase.storage.getReference().child("product").child(filename).putFile(uri)
            val newProduct = Product(shopId, name, description, price, filename)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val productCollection = db.collection("producto")
                    val shopDocument = db.collection("tienda").document(shopId)
                    productCollection.add(newProduct).await()/*
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

    suspend fun loadMyProducts() {
        val shopId = auth.currentUser?.uid
        if (shopId != null) {
            try {
                val result = db.collection("producto")
                    .whereEqualTo("shop_id", shopId)
                    .get()
                    .await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}