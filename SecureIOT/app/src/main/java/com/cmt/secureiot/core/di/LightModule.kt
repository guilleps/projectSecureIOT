package com.cmt.secureiot.core.di

import com.cmt.secureiot.light.data.repository.LightRepository
import com.cmt.secureiot.light.data.repository.LightRepositoryImpl
import com.cmt.secureiot.light.domain.usecase.LightUseCase
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LightModule {

    @Provides
    @Singleton
    fun provideLightRepository(firebaseDatabase: FirebaseDatabase): LightRepository {
        return LightRepositoryImpl(firebaseDatabase)
    }

    @Provides
    @Singleton
    fun provideLightUseCase(lightRepository: LightRepository): LightUseCase {
        return LightUseCase(lightRepository)
    }
}