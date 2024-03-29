package db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async

class ProductViewModel(private val productRepository: ProductRepository): ViewModel() {

    fun getAllProducts() = viewModelScope.async {
        productRepository.getAllProducts().asLiveData()
    }

    fun getProductById(id: Long) = viewModelScope.async{
        productRepository.getProductById(id).asLiveData()
    }

}

class ProductViewModelFactory(private val productRepository: ProductRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(productRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}