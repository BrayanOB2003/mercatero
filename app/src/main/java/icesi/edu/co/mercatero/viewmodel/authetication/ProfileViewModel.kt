package icesi.edu.co.mercatero.viewmodel.authetication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import icesi.edu.co.mercatero.model.user.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileViewModel: ViewModel() {

    private val _client = MutableLiveData(Client())
    val client: LiveData<Client> get() = _client



    fun getProfileData(id:String){

        viewModelScope.launch(Dispatchers.IO) {

            val result = Firebase.firestore.collection("cliente").document(id).get().await()
            Log.d("Test",result.toString())
            val currentClient = result.toObject(Client::class.java)
            Log.d("Test",currentClient.toString())
            _client.postValue(currentClient)

            }
        }

    }


