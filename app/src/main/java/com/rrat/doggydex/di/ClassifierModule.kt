package com.rrat.doggydex.di

import com.rrat.doggydex.machinelearning.ClassifierRepository
import com.rrat.doggydex.machinelearning.ClassifierTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class ClassifierModule {
    @Binds
    abstract fun bindClassifierTasks( classifierRepository: ClassifierRepository): ClassifierTasks
}