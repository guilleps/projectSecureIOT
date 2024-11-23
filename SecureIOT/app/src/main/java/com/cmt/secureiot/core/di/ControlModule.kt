package com.cmt.secureiot.core.di

import com.cmt.secureiot.control.data.repository.ControlRepository
import com.cmt.secureiot.control.data.repository.ControlRepositoryImpl
import com.cmt.secureiot.control.domain.usecase.ControlUseCase
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ControlModule {

    @Provides
    @Singleton
    fun provideControlRepository(firebaseDatabase: FirebaseDatabase): ControlRepository {
        return ControlRepositoryImpl(firebaseDatabase)
    }

    @Provides
    @Singleton
    fun provideControlUseCase(controlRepository: ControlRepository): ControlUseCase {
        return ControlUseCase(controlRepository)
    }
}