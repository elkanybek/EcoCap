import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.ecocap.Data.Dao.PointDao
import com.example.ecocap.Data.Dao.QuestDao
import com.example.ecocap.Data.Dao.UserDao

@Database(entities = [UserStore::class, PointStore::class, QuestStore::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PointDao
    abstract fun commentDao(): QuestDao
}
