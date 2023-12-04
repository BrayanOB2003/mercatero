package icesi.edu.co.mercatero.viewmodel

import android.util.Log
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
import kotlinx.coroutines.withContext

class OrderViewModel : ViewModel() {

    private val db = Firebase.firestore

    suspend fun addOrders(orders: List<Order>) {
        viewModelScope.launch(Dispatchers.IO) {
            orders.forEach{
                order -> addOrder(order)
            }
        }
    }

    private suspend fun addOrder(order: Order) {
        if (order.getIdProducts()?.isNotEmpty() == true) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    db.collection("pedido").add(order).await()
                } catch (e : Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    suspend fun calculatePriceOrders(orders: List<Order>): Int {
        var totalPrice = 0
        val deferredPrices = orders.map { order ->
            viewModelScope.async(Dispatchers.IO) {
                calculatePrice(order)
            }
        }
        deferredPrices.forEach {
            totalPrice += it.await()
        }
        return totalPrice
    }

    private suspend fun calculatePrice(order: Order): Int {
        var totalPrice = 0
        val productQuantities = order.products
        if (productQuantities != null) {
            totalPrice = productQuantities.map { (productId, quantity) ->
                    val productPrice = getProductPrice(productId)
                    productPrice?.times(quantity) ?: 0
            }.sum()
        }
        Log.e(">>> price pedido", totalPrice.toString())
        return totalPrice
    }

    private suspend fun getProductPrice(productId: String): Int? {
        return try {
            val document = db.collection("producto").document(productId).get().await()
            if (document.exists()) {
                document.data?.get("price").toString().toInt()
            } else {
                0
            }
        } catch (e : Exception) {
            e.printStackTrace()
            0
        }
    }
}