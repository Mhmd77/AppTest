package com.example.testproject

import android.content.Context
import com.example.testproject.data.source.Repository
import com.example.testproject.data.source.mock.MockDataSource

object ServiceLocator {
    @Volatile
    private var repository: Repository? = null

    fun provideRepository(): Repository {
        synchronized(this) {
            return repository ?: createRepository()
        }
    }

    private fun createRepository(): Repository {
        val newRepo = Repository(createMockDataSource())
        repository = newRepo
        return newRepo
    }

    private fun createMockDataSource(): MockDataSource {
        return MockDataSource()
    }
}