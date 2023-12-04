package icesi.edu.co.mercatero.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.mercatero.model.shop.Order
import kotlinx.coroutines.Dispatchers
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
}