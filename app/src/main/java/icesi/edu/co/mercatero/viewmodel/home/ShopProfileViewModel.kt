package icesi.edu.co.mercatero.viewmodel.home

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import icesi.edu.co.mercatero.model.Shop
import icesi.edu.co.mercatero.model.user.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ShopProfileViewModel: ViewModel() {

    private val _shop = MutableLiveData(Shop())
    val shop: LiveData<Shop> get() = _shop

    private val _authStateLV = MutableLiveData(Boolean)

    fun getProfileData(){

        viewModelScope.launch(Dispatchers.IO) {
            var id = Firebase.auth.currentUser?.uid
            id?.let {
                val result = Firebase.firestore.collection("tienda").document(id).get().await()
                Log.d("Test",result.toString())
                val currentShop = result.toObject(Shop::class.java)
                Log.d("Test",currentShop.toString())
                _shop.postValue(currentShop)
            }
        }
    }

    fun updateProfileImage(newURI: Uri){

        viewModelScope.launch (Dispatchers.IO){
            var id = Firebase.auth.currentUser?.uid

            val storageReference = Firebase.storage.reference
                .child("tienda")
                .child(UUID.randomUUID().toString())
            storageReference.putFile(newURI).await()
            val imageURL = storageReference.downloadUrl.await().toString()
            val updates = hashMapOf<String, Any>(
                "imageURL" to imageURL
            )
            id?.let {
                Firebase.firestore.collection("tienda").document(id).update(updates)
            }
        }

    }

    fun signOut(){
        viewModelScope.launch (Dispatchers.IO) {
            Firebase.auth.signOut()
        }
    }
}