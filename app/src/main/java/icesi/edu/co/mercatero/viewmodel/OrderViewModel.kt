package icesi.edu.co.mercatero.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.mercatero.model.shop.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OrderViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    suspend fun addOrder(order: Order) {
        if (order.getIdProducts()?.isNotEmpty() == true) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val orderCollection = db.collection("pedido")
                    orderCollection.add(order).await()
                } catch (e : Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    suspend fun calculatePrice(order: Order): Double {
        var totalPrice = 0.0
        val productQuantities = order.products
        if (productQuantities != null) {
            val deferredPrices = productQuantities.map { (productId, quantity) ->
                viewModelScope.async(Dispatchers.IO) {
                    val productPrice = getProductPrice(productId)
                    productPrice?.times(quantity) ?: 0.0
                }
            }
            totalPrice = deferredPrices.sumOf { it.await() }
        }
        return totalPrice
    }


    private suspend fun getProductPrice(productId: String): Double? {
        return try {
            val productCollection = db.collection("producto")
            val document = productCollection.document(productId).get().await()
            if (document.exists()) {
                document.data?.get("price") as? Double
            } else {
                0.0
            }
        } catch (e : Exception) {
            e.printStackTrace()
            0.0
        }
    }
}