package icesi.edu.co.mercatero.viewmodel.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.mercatero.model.Shop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ShopProfileViewModel: ViewModel() {

    private val _shop = MutableLiveData(Shop())
    val shop: LiveData<Shop> get() = _shop

    fun getShopData() {
        viewModelScope.launch(Dispatchers.IO) {
            var shopId = Firebase.auth.currentUser?.uid
            shopId?.let {
                val result = Firebase.firestore.collection("tienda").document(shopId).get().await()
                Log.d("Test",result.toString())
                val currentShop = result.toObject(Shop::class.java)
                Log.d("Test",currentShop.toString())
                _shop.postValue(currentShop)
            }
        }
    }
}