import com.example.ecocap.Data.Dao.QuestDao
import com.example.ecocap.Data.Repository.UserStore
import com.example.ecocap.Data.Repository.PointStore
import com.example.ecocap.Data.Repository.QuestStore

class QuestRepository(private val questDao: QuestDao) {
    suspend fun insertQuests(quests: List<QuestStore>) {
        questDao.insertAll(quests)
    }

    suspend fun getAllQuests(): List<QuestStore> {
        return questDao.getAllQuests()
    }
}