package com.rrat.doggydex.di

import com.rrat.doggydex.auth.AuthRepository
import com.rrat.doggydex.auth.AuthTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthTasksModule {

    @Binds
    abstract fun bindAuthTasks(authRepository: AuthRepository): AuthTasks

}