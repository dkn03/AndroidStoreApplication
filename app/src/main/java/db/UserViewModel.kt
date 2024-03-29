package db
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import db.Entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository,
                    private val productRepository: ProductRepository): ViewModel() {

    val allUsers: LiveData<List<User>> = userRepository.allUsers.asLiveData()

    fun insert(user: User) = viewModelScope.launch {
        userRepository.insert(user)
    }

    fun updateUserEmail(id: Long, newEmail: String) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.updateUserEmail(id, newEmail)
    }

    fun getUserById(id: Long) = viewModelScope.async {
        return@async userRepository.getUserById(id).asLiveData()
    }

    fun getUserByEmail(email: String) = viewModelScope.async {
        userRepository.getUserByEmail(email).asLiveData()
    }

    fun getAllUsers() = viewModelScope.async {
        userRepository.getAllUsers().asLiveData()
    }

    fun getAllProducts() = viewModelScope.async {

        productRepository.getAllProducts().asLiveData()
    }

}

class UserViewModelFactory(private val userRepository: UserRepository, private val productRepository: ProductRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userRepository, productRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}