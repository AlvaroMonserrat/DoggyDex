package com.rrat.doggydex.di

import com.rrat.doggydex.doglist.DogRepository
import com.rrat.doggydex.doglist.DogTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class DogTasksModule {

    @Binds
    abstract fun bindDogTasks(dogRepository: DogRepository): DogTasks

}