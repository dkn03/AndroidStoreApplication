package db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import db.Entities.User
import kotlinx.coroutines.flow.Flow

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<User>>

    @Query("Select * FROM users WHERE id = :id")
    fun getUserById(id: Long): Flow<User>

    @Query("Select * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): Flow<User>

    @Query("UPDATE users SET email=:newEmail WHERE id = :id")
    fun updateUserEmail(id: Long, newEmail: String)

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}