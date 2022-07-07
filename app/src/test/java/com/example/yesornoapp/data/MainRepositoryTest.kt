package com.example.yesornoapp.data

import com.example.yesornoapp.core.Resource
import com.example.yesornoapp.core.db.AppDatabase
import com.example.yesornoapp.core.retrofit.ApiService
import com.example.yesornoapp.data.model.Answer
import com.example.yesornoapp.data.model.AnswerBD
import com.example.yesornoapp.data.repository.MainRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainRepositoryTest {

    private var mockApi: ApiService = mock()
    private val answerDBSpy: AppDatabase = spy()
    var repository = MainRepository(mockApi, answerDBSpy)
    private val successAnswer =
        Answer("yes", false, "https://yesno.wtf/assets/yes/12-e4f57c8f172c51fdd983c2837349f853.gif")
    private val dbResult =
        AnswerBD(1, "no", "https://yesno.wtf/assets/no/14-cb78bf7104f848794808d61b9cd83eba.gif")
    private val dbResultLikeAnswer =
        Answer( "no", false,"https://yesno.wtf/assets/no/14-cb78bf7104f848794808d61b9cd83eba.gif")

    private val errorText = "oh no tests testing!"



    @Test
    fun DBTestGetLast() {
        CoroutineScope(Dispatchers.IO).launch {
            whenever(answerDBSpy.answerDao().getLastAnswer()).thenReturn(listOf(dbResult))
            assertEquals(dbResult, repository.getLastAnswerFromDatabase())
        }
    }

    @Test
    fun DBTestGetSize() {
        CoroutineScope(Dispatchers.IO).launch {
            whenever(answerDBSpy.answerDao().getSize()).thenReturn(1)
            assertEquals(1, repository.getDatabaseSize())
        }
    }

    @Test
    fun DBTestInsert() {
        CoroutineScope(Dispatchers.IO).launch {

            whenever(answerDBSpy.answerDao().getMaxIdFromTable()).thenReturn(1)

            repository.saveAnswerToDatabase(dbResultLikeAnswer)

            //Todo проверка по количеству
            verify(answerDBSpy.answerDao()).insertNewAnswer(AnswerBD(2, "no", "https://yesno.wtf/assets/no/14-cb78bf7104f848794808d61b9cd83eba.gif"))
            verify(answerDBSpy.answerDao()).getMaxIdFromTable()
        }
    }

    @Test
    fun apiSuccessTesting() {
        CoroutineScope(Dispatchers.IO).launch {
            whenever(mockApi.getAnswer()).thenReturn(successAnswer)

            repository.getAnswer().collect {
                assertEquals(Resource.success(successAnswer), it)
            }
        }
    }

    @Test
    fun apiFailureWithDBTest() {
        CoroutineScope(Dispatchers.IO).launch {

            whenever(mockApi.getAnswer()).thenThrow(Exception())
            whenever(answerDBSpy.answerDao().getSize()).thenReturn(1)
            whenever(answerDBSpy.answerDao().getLastAnswer()).thenReturn(listOf(dbResult))

            repository.getAnswer().collect {
                assertEquals(Resource.error(dbResult, errorText), it)
            }
        }
    }

    @Test
    fun apiFailureWithoutDBTest() {
        CoroutineScope(Dispatchers.IO).launch {
            whenever(mockApi.getAnswer()).thenThrow(Exception())
            whenever(answerDBSpy.answerDao().getSize()).thenReturn(0)

            repository.getAnswer().collect {
                assertEquals(Resource.error(null, errorText), it)
            }
        }
    }

}