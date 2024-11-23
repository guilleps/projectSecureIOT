package com.cmt.secureiot.core.di

import com.cmt.secureiot.alarm.data.repository.AlarmRepository
import com.cmt.secureiot.alarm.data.repository.AlarmRepositoryImpl
import com.cmt.secureiot.alarm.domain.usecase.AlarmUseCase
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlarmModule {

    @Provides
    @Singleton
    fun provideAlarmRepository(firebaseDatabase: FirebaseDatabase): AlarmRepository {
        return AlarmRepositoryImpl(firebaseDatabase)
    }

    @Provides
    @Singleton
    fun provideAlarmUseCase(alarmRepository: AlarmRepository): AlarmUseCase {
        return AlarmUseCase(alarmRepository)
    }
}