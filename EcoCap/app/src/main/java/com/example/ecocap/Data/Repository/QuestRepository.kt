import com.example.ecocap.Data.Dao.QuestDao

class QuestRepository(private val questDao: QuestDao) {
    suspend fun insertQuests(quests: List<QuestStore>) {
        questDao.insertAll(quests)
    }

    suspend fun getAllQuests(): List<QuestStore> {
        return questDao.getAllQuests()
    }
}
