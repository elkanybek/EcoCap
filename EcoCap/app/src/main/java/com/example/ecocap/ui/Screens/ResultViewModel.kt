package com.example.ecocap.ui.Screens

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ecocap.Data.Database.PointStore
import com.example.ecocap.Data.Database.QuestStore
import com.example.ecocap.Data.Database.UserStore
import com.example.ecocap.Data.Repository.PointRepository
import com.example.ecocap.Data.Repository.UserRepository
import com.google.mlkit.vision.label.ImageLabel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.ByteArrayOutputStream
import java.io.InputStream

class ResultViewModel(private val pointRepository: PointRepository, private val userRepository: UserRepository): ViewModel() {
    var sessionId: Int = 1
    var result by mutableStateOf(false)
    var pointsGained by mutableStateOf(0)
    var imageBytes by mutableStateOf<ByteArray>(ByteArray(0))
    var totalPoints by mutableStateOf(0)

    val animals = listOf(
        "Frog",
        "Tiger",
        "Elephant",
        "Penguin",
        "Panda",
        "Koala",
        "Giraffe",
        "Lion",
        "Zebra",
        "Kangaroo",
        "Polar Bear"
    )


    suspend fun checkResult(quests: List<QuestStore>, labels: List<ImageLabel>, image: Uri?, context: Context): Boolean{
        pointsGained = 0
        result = false
        for(quest in quests){
            for(label in labels){
                if(quest.name == label.text && label.confidence > 0.2){
//                if(true){ // for testing
                    result = true
                    pointsGained = 200
                    imageBytes = uriToBytes(uri = image, context)

                    val pointStore: PointStore = PointStore(
                        userId = sessionId,
                        questName = quest.name,
                        image = imageBytes,
                        streakMultiplier = 1.0,
                        scoreGained = pointsGained
                    )

                    val points: Int = userRepository.getUserPoints(sessionId)
                    totalPoints = points + pointsGained
                    userRepository.updateTotalPoints(sessionId, points + totalPoints)

                    pointRepository.insertPoints(pointStore)
                    return true
                }
            }
        }
        return false
    }

    private fun uriToBytes(uri: Uri?, context: Context): ByteArray{
        if (uri == null) {
            return ByteArray(0)
        }
        val inputStream: InputStream = context.contentResolver.openInputStream(uri)!!
        val byteArrayOutputStream = ByteArrayOutputStream()

        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } != -1) {
            byteArrayOutputStream.write(buffer, 0, length)
        }

        return byteArrayOutputStream.toByteArray()
    }
}