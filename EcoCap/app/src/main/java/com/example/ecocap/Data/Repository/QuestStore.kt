import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quests")
data class QuestStore (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)