import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "points")
data class PointStore (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val image: String,
    val streakMultiplier: Double,
    val scoreGained: Int,
    val dateTime: Date,
)
