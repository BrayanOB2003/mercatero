package icesi.edu.co.mercatero.viewmodel.product

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.mercatero.model.shop.Product
import icesi.edu.co.mercatero.viewmodel.authetication.AuthState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProductViewModel: ViewModel() {

    private val currentProduct = MutableLiveData<Product?>()
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    fun addProductPrimaryData(name: String, description: String, price: Double) {
        viewModelScope.launch(Dispatchers.Main) {
            currentProduct.value = Product(
                shop_id = null,
                name = name,
                description = description,
                price = price
            )
        }
        Log.e(">>>", "data")
    }

    fun addProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            val product = currentProduct.value
            val shopId = auth.currentUser?.uid
            product?.shop_id = shopId
            Log.e(">>>", product.toString())
            Log.e(">>>", shopId.toString())
            if (shopId != null && product != null) {
                try {
                    val productCollection = db.collection("producto")
                    val shopDocument = db.collection("tienda").document(shopId)

                    val result = productCollection.add(product)/*
                    db.runTransaction { transaction ->
                        val shopSnapshot = transaction.get(shopDocument)
                        val currentProductsList = shopSnapshot.get("productos") as ArrayList<String>?: ArrayList()
                        currentProductsList.add(result.id)
                        transaction.update(shopDocument, "productos", currentProductsList)
                    }.await()*/
                    withContext(Dispatchers.Main) {
                        currentProduct.value = null
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        currentProduct.value = null
                    }
                }
            }
        }
    }
}