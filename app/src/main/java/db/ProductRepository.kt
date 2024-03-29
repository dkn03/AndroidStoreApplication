package db

import androidx.annotation.WorkerThread
import db.Entities.Product
import db.dao.ProductDao
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    @WorkerThread
    suspend fun getProductById(id: Long): Flow<Product> {
        return productDao.getProductById(id)
    }

    @WorkerThread
    suspend fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAll()
    }

    @WorkerThread
    suspend fun delete(product: Product){
        productDao.delete(product)
    }

}