package com.amaizzzing.dictionary.di

import com.amaizzzing.dictionary.model.data.DataModel
import com.amaizzzing.dictionary.model.repository.Repository
import com.amaizzzing.dictionary.view.main.MainInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
            @Named(NAME_REMOTE) repositoryRemote: Repository<List<DataModel>>,
            @Named(NAME_LOCAL) repositoryLocal: Repository<List<DataModel>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}
