package com.cmt.secureiot.core.di

import com.cmt.secureiot.door.data.repository.DoorRepository
import com.cmt.secureiot.door.data.repository.DoorRepositoryImpl
import com.cmt.secureiot.door.domain.usecase.DoorUseCase
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DoorModule {

    @Provides
    @Singleton
    fun provideDoorRepository(firebaseDatabase: FirebaseDatabase): DoorRepository {
        return DoorRepositoryImpl(firebaseDatabase)
    }

    @Provides
    @Singleton
    fun provideDoorUseCase(doorRepository: DoorRepository): DoorUseCase {
        return DoorUseCase(doorRepository)
    }
}