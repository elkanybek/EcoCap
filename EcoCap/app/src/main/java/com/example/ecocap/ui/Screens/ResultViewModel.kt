package com.example.ecocap.ui.Screens

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecocap.Data.Database.PointStore
import com.example.ecocap.Data.Database.QuestStore
import com.example.ecocap.Data.Database.UserStore
import com.example.ecocap.Data.Repository.PointRepository
import com.example.ecocap.Data.Repository.UserRepository
import com.example.ecocap.ui.Camera.CaptureImageViewModel
import com.google.mlkit.vision.label.ImageLabel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.InputStream

class ResultViewModel(private val pointRepository: PointRepository, private val userRepository: UserRepository, private val homeViewModel: HomeViewModel): ViewModel() {
    var sessionId: Int = 1
    var result by mutableStateOf(false)
    var pointsGained by mutableStateOf(0)
    var imageBytes by mutableStateOf<ByteArray>(ByteArray(0))
    var totalPoints by mutableStateOf(0)

    init {
        viewModelScope.launch{
            totalPoints = userRepository.getUserPoints(sessionId)
        }
    }

    suspend fun checkResult(quests: MutableList<QuestStore>, labels: List<ImageLabel>, image: Uri?, context: Context): Boolean{
        pointsGained = 0
        result = false
        homeViewModel.checkStreak()
        val questsToRemove = mutableListOf<QuestStore>()
        var isMatchFound = false
        for (quest in quests) {
            for (label in labels) {
                if (quest.name == label.text && label.confidence > 0.2) {
                    result = true
                    isMatchFound = true
                    val streakMultiplier = homeViewModel.dailyStreak.toDouble()
                    pointsGained = (200 * streakMultiplier).toInt()

                    imageBytes = uriToBytes(uri = image, context)

                    val pointStore = PointStore(
                        userId = sessionId,
                        questName = quest.name,
                        image = imageBytes,
                        streakMultiplier = streakMultiplier,
                        scoreGained = pointsGained
                    )
                    pointRepository.insertPoints(pointStore)

                    val points: List<PointStore> = pointRepository.getAllPoints(sessionId)
                    totalPoints = 0
                    for(point in points){
                        totalPoints += point.scoreGained
                    }
                    userRepository.updateTotalPoints(sessionId, totalPoints)

                    quests.remove(quest)
                    break
                }
            }
            if (isMatchFound) break
        }
        return isMatchFound
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