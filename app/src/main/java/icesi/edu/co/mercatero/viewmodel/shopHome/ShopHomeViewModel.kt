package icesi.edu.co.mercatero.viewmodel.shopHome

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import icesi.edu.co.mercatero.model.Order
import icesi.edu.co.mercatero.model.OrderListInfo
import icesi.edu.co.mercatero.model.user.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ShopHomeViewModel: ViewModel() {


    private val _orders = MutableLiveData(OrderListInfo())
    val orders: LiveData<OrderListInfo> get() = _orders

    private var orders2 = ArrayList<OrderListInfo>()

    private var orderTmp = ArrayList<Order>()

    private var listNames = ArrayList<String>()

    fun updateOrderStatus(order_id: String){
        //Logica de actualización de status en la base de datos

        viewModelScope.launch(Dispatchers.IO) {

            val result = Firebase.firestore.collection("pedido").document(order_id).get().await()

            var order = result.toObject(Order::class.java)

            if (order != null) {
                when(order.status){

                "TO_DO" -> order.status = "IN_PROGRESS"
                "IN_PROGRESS" -> order.status = "TO_DELIVER"
                "TO_DELIVER"   -> order.status =  "DELIVERED"

                }
            }
            Firebase.firestore.collection("pedido").document(order_id).update("status",order!!.status).addOnSuccessListener {



            }

        }
    }

    fun getOrdersToAcept(store_id: String){


        viewModelScope.launch(Dispatchers.IO) {

            val result = Firebase.firestore.collection("pedido").whereEqualTo("shop_id",store_id).get().await()

            for(doc in result){
                var order = doc.toObject(Order::class.java)

                    Log.d("Test","Orders Antes del If " + order.order_id)

                if(order.status == "TO_DO"){

                    Log.d("Test",  "Order en el for "+ order.order_id)
                    orderTmp.add(order)

                    val result2 = Firebase.firestore.collection("cliente").document(order.client_id).get().await()

                    val client = result2.toObject(Client::class.java)

                    if (client != null) {
                            listNames.add(client!!.name + " " + client!!.lastName)
                    }


                }

            }
            val orderInfo = OrderListInfo(orderTmp, listNames)
            Log.d("Test",orderInfo.orderInfo.joinToString("Order "))
            Log.d("Test",orderInfo.listName.joinToString("Nombres "))

            _orders.postValue(orderInfo)

        }

    }
    fun getOrdersInPreparation(store_id: String){

        viewModelScope.launch(Dispatchers.IO) {

            val result = Firebase.firestore.collection("pedido").whereEqualTo("shop_id",store_id).get().await()

            for(doc in result){

                var order = doc.toObject(Order::class.java)


                if(order.status == "IN_PROGRESS"){

                    orderTmp.add(order)

                    val result2 = Firebase.firestore.collection("cliente").document(order.client_id).get().await()

                    val client = result2.toObject(Client::class.java)

                    if (client != null) {
                        listNames.add(client!!.name + " " + client!!.lastName)
                    }


                }

            }
            val orderInfo = OrderListInfo(orderTmp, listNames)
            _orders.postValue(orderInfo)

        }

        }





    fun getOrdersInDelivery(store_id: String){

        viewModelScope.launch(Dispatchers.IO) {

            val result = Firebase.firestore.collection("pedido").whereEqualTo("shop_id",store_id).get().await()

            for(doc in result){

                var order = doc.toObject(Order::class.java)


                if(order.status == "TO_DELIVER"){

                    orderTmp.add(order)

                    val result2 = Firebase.firestore.collection("cliente").document(order.client_id).get().await()

                    val client = result2.toObject(Client::class.java)

                    if (client != null) {
                        listNames.add(client!!.name + " " + client!!.lastName)
                    }


                }

            }
            val orderInfo = OrderListInfo(orderTmp, listNames)
            _orders.postValue(orderInfo)

        }

        }
    }
