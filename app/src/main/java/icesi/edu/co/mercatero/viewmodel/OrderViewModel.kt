package icesi.edu.co.mercatero.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.mercatero.model.Order
import icesi.edu.co.mercatero.model.Product
import icesi.edu.co.mercatero.model.enumeration.OrderStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class OrderViewModel: ViewModel() {

    private val db = Firebase.firestore

    private val _myOrders = MutableLiveData<ArrayList<Order>>()
    val myOrders: LiveData<ArrayList<Order>> get() = _myOrders

    fun addProductToOrder(product: Product, quantity: Int) {
        val currentOrders = _myOrders.value ?: arrayListOf()
        val shopOrder = currentOrders.find { it.shop_id == product.shop_id }
        if (shopOrder != null) {
            val existingProduct = shopOrder.idProducts.indexOf(product.product_id)
            if (existingProduct != -1) {
                val currentQuantity = shopOrder.quantities[existingProduct].toInt()
                shopOrder.quantities[existingProduct] = (currentQuantity + quantity).toString()
            } else {
                shopOrder.idProducts.add(product.product_id)
                shopOrder.quantities.add(quantity.toString())
            }
            val oldPrice = shopOrder.price.toInt()
            shopOrder.price = (oldPrice+product.price.toInt().times(quantity)).toString()
            val shopOrderIndex = currentOrders.indexOfFirst { it.shop_id == product.shop_id }
            if (shopOrderIndex != -1) {
                currentOrders[shopOrderIndex] = shopOrder
            }
        } else {
            val userId = Firebase.auth.currentUser?.uid
            if (userId != null) {
                val newOrder = Order(
                    order_id = UUID.randomUUID().toString(),
                    client_id = userId,
                    shop_id = product.shop_id,
                    address = "",
                    price = product.price.toInt().times(quantity).toString(),
                    idProducts = arrayListOf(product.product_id),
                    quantities = arrayListOf(""+quantity),
                    status = OrderStatus.TO_DO.toString()
                )
                currentOrders.add(newOrder)
            }
        }
        _myOrders.postValue(currentOrders)
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