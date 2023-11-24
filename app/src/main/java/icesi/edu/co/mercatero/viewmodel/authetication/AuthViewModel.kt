package icesi.edu.co.mercatero.viewmodel.authetication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.mercatero.model.enumeration.ErrorMessage
import icesi.edu.co.mercatero.model.user.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthViewModel: ViewModel() {
    val authClient = MutableLiveData<Client>()
    val authStateLV = MutableLiveData<AuthState>()


    fun signUpPrimaryData(names: String, lastNames: String, email: String, phoneNumber: String){
        authClient.value = Client(name = names,lastName = lastNames, email = email, number_phone = phoneNumber,
        CC = null, address = null, client_id = null)
    }

    fun signupClient(email: String, password: String, name: String, lastName: String, cc: Long,
                     address: String, number_phone: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = Firebase.auth.createUserWithEmailAndPassword(email, password).await()
                withContext(Dispatchers.Main){
                    authStateLV.value = AuthState(result.user?.uid, true)
                }
            } catch (e: Exception){
                withContext(Dispatchers.Main){
                    authStateLV.value = AuthState(null, false)
                }
            }
        }
    }

    fun signInClient(email: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = Firebase.auth.signInWithEmailAndPassword(email, pass).await()
                withContext(Dispatchers.Main){authStateLV.value = AuthState(result.user?.uid, true)}
            } catch (e: Exception) {
                withContext(Dispatchers.Main){authStateLV.value = AuthState(null, false)}
            }
        }
    }

    fun reloadState(){
        authStateLV.value?.isAuth = null
    }

    fun signInValidation(){
        viewModelScope.launch(Dispatchers.IO) {
            val result  = FirebaseAuth.getInstance().currentUser
            withContext(Dispatchers.Main){
                if(result != null) {
                    authStateLV.value = AuthState(result.uid, true)
                } else {
                    authStateLV.value = AuthState(null, false)
                    Log.e("CCCC","dsf")
                }
            }
        }
    }

}

data class AuthState(
    var userID: String? = null,
    var isAuth: Boolean?
)