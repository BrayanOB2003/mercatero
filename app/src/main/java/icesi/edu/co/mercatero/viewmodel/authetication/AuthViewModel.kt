package icesi.edu.co.mercatero.viewmodel.authetication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.mercatero.model.Shop
import icesi.edu.co.mercatero.model.enumeration.UserType
import icesi.edu.co.mercatero.model.user.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.Serializable

class AuthViewModel: ViewModel() {
    private val authClient = MutableLiveData<Client>()
    private val _authStateLV = MutableLiveData(AuthState())
    val authStateLV: LiveData<AuthState> get() = _authStateLV
    val emailValidState = MutableLiveData<Boolean>()
    private val authShop = MutableLiveData<Shop>()
    private val db = Firebase.firestore

    fun signUpPrimaryData(names: String, lastNames: String, email: String, phoneNumber: String) {
        viewModelScope.launch(Dispatchers.Main) {
            authClient.value = Client(
                name = names,
                lastName = lastNames,
                email = email,
                phone = phoneNumber,
                CC = null,
                address = null,
                client_id = null,
                imageURL = null
            )
        }
    }

    fun signUpPrimaryDataShop(name: String, address: String, email: String, phone: String) {
        viewModelScope.launch(Dispatchers.Main) {
            authShop.value = Shop(
                shop_id = null,
                name = name,
                address = address,
                email = email,
                phone = phone,
                imageUrl = null
            )
        }
    }

    fun signupShop(password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val shop = authShop.value
            if (shop != null) {
                try {
                    val result = Firebase.auth.createUserWithEmailAndPassword(
                        authShop.value?.email.toString(),
                        password
                    ).await()
                    withContext(Dispatchers.Main) {
                        _authStateLV.value = AuthState(result.user?.uid, true, UserType.SHOP)
                        authShop.value?.shop_id = result.user?.uid

                        authShop.value?.shop_id?.let {
                            authShop.value?.let { it1 ->
                                db.collection("tienda").document(it).set(
                                    it1
                                )
                            }
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _authStateLV.value = AuthState(null, false)
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    _authStateLV.value = AuthState(null, null)
                }
            }
        }
    }

    fun signupClient(password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val client = authClient.value
            if (client != null) {
                try {
                    val result = Firebase.auth.createUserWithEmailAndPassword(
                        authClient.value?.email.toString(),
                        password
                    ).await()
                    withContext(Dispatchers.Main) {

                        _authStateLV.value = AuthState(result.user?.uid, true, UserType.CLIENT)
                        authClient.value?.client_id = result.user?.uid

                        client.client_id?.let {
                            authClient.value?.let { it1 ->
                                db.collection("cliente").document(it).set(
                                    it1
                                )
                            }
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _authStateLV.value = AuthState(null, false)
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    _authStateLV.value = AuthState(null, null)
                }
            }
        }
    }

    fun signInClient(email: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = Firebase.auth.signInWithEmailAndPassword(email, pass).await()
                withContext(Dispatchers.Main){
                    checkUserType(result.user?.uid.toString())
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){_authStateLV.value = AuthState(null, false)}
            }
        }
    }

    fun reloadState() {
        authStateLV.value?.isAuth = null
    }

    fun signInValidation() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = Firebase.auth.currentUser?.uid
            userId?.let { checkUserType(it) }
        }
    }

    private fun checkUserType(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val clientDoc = db.collection("cliente").document(userId).get().await()
                val shopDoc = db.collection("tienda").document(userId).get().await()

                withContext(Dispatchers.Main) {
                    if (clientDoc.exists()) {
                        _authStateLV.value = AuthState(userId, true, UserType.CLIENT)
                    } else if (shopDoc.exists()) {
                        _authStateLV.value = AuthState(userId, true, UserType.SHOP)
                    } else {
                        _authStateLV.value = AuthState(null, false, null)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _authStateLV.value = AuthState(null, false, null)
                }
            }
        }
    }
    class AuthState : Serializable {
        var userID: String? = null
        var isAuth: Boolean? = null
        var userType: UserType? = null
        constructor()
        constructor(
            userID: String?,
            isAuth: Boolean?,
            userType: UserType?
        ) {
            this.userID = userID
            this.isAuth = isAuth
            this.userType = userType
        }
        constructor(
            userID: String?,
            isAuth: Boolean?,
        ) {
            this.userID = userID
            this.isAuth = isAuth
        }
    }

}
