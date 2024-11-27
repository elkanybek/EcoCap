import com.example.ecocap.Data.Dao.QuestDao
import com.example.ecocap.Data.Database.QuestStore

class QuestRepository(private val questDao: QuestDao) {
    suspend fun insertQuests(quests: List<QuestStore>) {
        questDao.insertAll(quests)
    }

    suspend fun getAllQuests(): List<QuestStore> {
        return questDao.getAllQuests()
    }

    suspend fun getRandomQuest(): QuestStore{
        return questDao.getRandomQuest()
    }
}
