import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.ColumnInfo

@Entity(
    tableName = "points",
    foreignKeys = [
        ForeignKey(
            entity = UserStore::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE //if user deleted, delete points too
        )
    ]
)
data class PointStore (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val image: String,
    val streakMultiplier: Double,
    val scoreGained: Int
)
