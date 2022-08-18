package com.luceno.firebasechat.feature_auth.module

import com.luceno.firebasechat.feature_auth.services.AccountService
import com.luceno.firebasechat.feature_auth.services.impl.AccountServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthModule {

    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService


}