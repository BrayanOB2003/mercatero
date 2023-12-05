package icesi.edu.co.mercatero.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.mercatero.model.Order
import icesi.edu.co.mercatero.model.Product

class OrderViewModel: ViewModel() {

    private val db = Firebase.firestore

    private val _myOrders = MutableLiveData<ArrayList<Order>>()
    val myOrders: LiveData<ArrayList<Order>> get() = _myOrders

    fun addProductToOrder(product: Product, quantity: Int) {
        val currentOrders = _myOrders.value ?: arrayListOf()
        val shopOrder = currentOrders.find { it.shop_id == product.shop_id }
        if (shopOrder != null) {

        }
    }
}