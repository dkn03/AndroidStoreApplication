package db.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="users")
data class User(
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "name") val name: String)
{
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}