package db

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import db.Entities.Cart
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CartViewModel(private val cartRepository: CartRepository): ViewModel() {

    val allCarts: LiveData<List<Cart>> = cartRepository.allCarts.asLiveData()

    fun getUserProducts(id: Long) = viewModelScope.async{
        cartRepository.getUserProducts(id).asLiveData()
    }

    suspend fun insert(cart: Cart) = viewModelScope.launch{
        cartRepository.insert(cart)
    }


    suspend fun deleteCart(cart: Cart) = viewModelScope.launch{
        cartRepository.delete(cart)
    }
    fun getUserProductsAndAmount(id: Long) =  viewModelScope.async {
        cartRepository.getUserProductsAndAmount(id).asLiveData()
    }

    fun getItem(userId: Long, productId: Long) = viewModelScope.async{
        cartRepository.getItem(userId, productId).asLiveData()
    }

    fun getAll() = viewModelScope.async{
        cartRepository.getAll().asLiveData()
    }
}

class CartViewModelFactory(private val cartRepository: CartRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(cartRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}