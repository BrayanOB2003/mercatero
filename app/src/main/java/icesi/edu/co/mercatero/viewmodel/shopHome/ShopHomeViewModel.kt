package icesi.edu.co.mercatero.viewmodel.shopHome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.mercatero.model.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ShopHomeViewModel: ViewModel() {


    private val _orders = MutableLiveData(ArrayList<Order>())
    val orders: LiveData<ArrayList<Order>> get() = _orders

    private var orders2 = ArrayList<Order>()

    fun updateOrderStatus(order_id: String){
        //Logica de actualización de status en la base de datos

        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    fun getOrdersToAcept(store_id: String){


        viewModelScope.launch(Dispatchers.IO) {

            val result = Firebase.firestore.collection("pedido").whereEqualTo("shop_id",store_id).get().await()

            for(doc in result){

                var order = doc.toObject(Order::class.java)


                if(order.status == "TO_DO"){

                    orders2.add(order)


                }

            }
            _orders.postValue(orders2)

        }

    }
}