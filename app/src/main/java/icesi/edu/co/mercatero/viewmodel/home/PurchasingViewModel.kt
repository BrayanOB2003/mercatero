package icesi.edu.co.mercatero.viewmodel.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.mercatero.model.Order
import icesi.edu.co.mercatero.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PurchasingViewModel: ViewModel() {

    private val _products = MutableLiveData(ArrayList<Product>())
    val products: LiveData<ArrayList<Product>> get() = _products

    private val products2 = ArrayList<Product>()

    fun getProductsBought(){

        viewModelScope.launch(Dispatchers.IO) {


            val result = Firebase.firestore.collection("pedido").whereEqualTo("client_id",Firebase.auth.currentUser!!.uid).get().await()

            for (doc in result.documents){

                var order = doc.toObject(Order::class.java)


               for(id in order!!.idProducts){

                Log.d("Test", "Id del producto " + id)

                   var result2 = Firebase.firestore.collection("producto").document(id).get().await()

                   var product = result2.toObject(Product::class.java)
                   if(products2.contains(product)){

                   }else{
                   products2.add(product!!)
                   }
               }

            }
            _products.postValue(products2)


        }

    }

}