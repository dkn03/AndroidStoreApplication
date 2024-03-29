package db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import db.Entities.Cart
import db.Entities.Product
import db.Entities.User
import db.dao.CartDao
import db.dao.ProductDao
import db.dao.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    version = 1,
    entities = [
        User:: class,
        Product:: class,
        Cart:: class
    ]
)
public abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao


    private class UserDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val userDao = database.userDao()
                    val productDao = database.productDao()
                    val cartDao = database.cartDao()

                    val user = User("123@mail.ru", "+79641112233", "qwerty", "database.User")
                    userDao.insert(user)
                    val product1: Product = Product("Гитара Hello Kitty", 4, 1500.0,
                        "https://guitar.com/wp-content/uploads/2022/11/hello-kitty-strat-squier@2000x1500.jpg;" +
                                "https://guitarplayer.ru/attach/134490-224179.jpg;" +
                                "https://www.chicagomusicexchange.com/cdn/shop/files/squier-electric-guitars-solid-body-squier-hello-kitty-stratocaster-pink-2006-u5413917001-30601500196999_2000x.jpg?v=1693274155",
                        "qwe");
                    val product2: Product = Product("Чехол для гитары", 15, 100.0, "https://3tone.me/upload/iblock/a4b/a4bd9b78205339a3ae5b455df50f1205.jpeg", "vbg")
                    productDao.insert(product1)
                    productDao.insert(product2)

                    cartDao.insert(Cart(1, 1, 1))

                }
            }
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "musicStoreDatabase.db"
                ).addCallback(UserDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}
