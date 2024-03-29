package db

import androidx.annotation.WorkerThread
import db.Entities.User
import db.dao.UserDao
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    val allUsers: Flow<List<User>> = userDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    @WorkerThread
    suspend fun updateUserEmail(id: Long, newEmail: String){
        userDao.updateUserEmail(id, newEmail)
    }

    @WorkerThread
    suspend fun getUserById(id: Long): Flow<User> {
        return userDao.getUserById(id)
    }

    @WorkerThread
    suspend fun getUserByEmail(email: String): Flow<User>{
        return userDao.getUserByEmail(email)
    }

    @WorkerThread
    suspend fun getAllUsers(): Flow<List<User>> {
        return userDao.getAll()
    }

}