package icesi.edu.co.mercatero.viewmodel.authetication

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
    val authStateLV = MutableLiveData<AuthState>()
    val errorLV = MutableLiveData<String>()

    fun signupClient(email: String, password: String, name: String, lastName: String, CC: Long,
                     address: String, number_phone: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = Firebase.auth.createUserWithEmailAndPassword(email, password).await()
                withContext(Dispatchers.Main){
                    authStateLV.value = AuthState(result.user?.uid, true)
                }
            }catch (e: FirebaseAuthInvalidCredentialsException) {
                withContext(Dispatchers.Main){errorLV.value =
                    ErrorMessage.WRONG_FORMAT_PASSWORD.toString()
                }
            } catch (e: FirebaseAuthUserCollisionException) {
                withContext(Dispatchers.Main){errorLV.value =
                    ErrorMessage.DUPLICATED_EMAIL.toString()
                }
            } catch (e: FirebaseAuthWeakPasswordException) {
                withContext(Dispatchers.Main){errorLV.value =
                    ErrorMessage.WEAK_PASSWORD.toString()
                }
            }
        }
    }

    fun signInClient(email: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = Firebase.auth.signInWithEmailAndPassword(email, pass).await()
                withContext(Dispatchers.Main){
                    authStateLV.value = AuthState(result.user?.uid, true)
                }
            } catch (e: FirebaseAuthException) {
                withContext(Dispatchers.Main){errorLV.value =
                    ErrorMessage.WRONG_AUTHENTICATION.toString()
                }
            }
        }

    }

    fun signInValidation(){
        viewModelScope.launch(Dispatchers.IO) {
            val result  = FirebaseAuth.getInstance().currentUser
            withContext(Dispatchers.Main){
                result?.let {
                    authStateLV.value = AuthState(result.uid, true)
                }
            }
        }
    }


}

data class AuthState(
    var userID: String? = null,
    var isAuth: Boolean
)