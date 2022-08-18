package com.luceno.firebasechat.feature_chat.module

import com.luceno.firebasechat.feature_chat.data.datasources.MessagesRemoteDataSourceImpl
import com.luceno.firebasechat.feature_chat.data.repositories.MessagesRepositoryImpl
import com.luceno.firebasechat.feature_chat.data.repositories.StorageRepositoryImpl
import com.luceno.firebasechat.feature_chat.domain.repositories.MessagesRepository
import com.luceno.firebasechat.feature_chat.domain.repositories.StorageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeatureChatModule {

    @Provides
    @Singleton
    fun provideMessagesRepository(): MessagesRepository {
        return MessagesRepositoryImpl(MessagesRemoteDataSourceImpl())
    }

    @Provides
    @Singleton
    fun provideStorageRepository(): StorageRepository {
        return StorageRepositoryImpl()
    }

}