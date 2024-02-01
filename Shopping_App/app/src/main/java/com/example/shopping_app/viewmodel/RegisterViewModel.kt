package com.example.shopping_app.viewmodel
import androidx.lifecycle.ViewModel
import com.example.shopping_app.data_classes.users
import com.example.shopping_app.static_classes.resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginRegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
):ViewModel(){
    private val _register = MutableStateFlow<resource<FirebaseUser>>(resource.Loading())
    val register: Flow<resource<FirebaseUser>> = _register

    fun createAccwithEmialandPassword(user: users,password: String){
        firebaseAuth.createUserWithEmailAndPassword(user.emailAdd,password).addOnSuccessListener {
            it.user?.let {
                _register.value = resource.Success(it)
            }
        }.addOnFailureListener{
            _register.value = resource.Error(it.message.toString())
        }
    }
}