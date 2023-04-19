package com.example.coursework.app.di

import com.example.coursework.core.di.GlobalCicerone
import com.example.coursework.feature.people.impl.PeopleFacade
import com.example.coursework.feature.profile.ui.di.ProfileFacade
import com.example.coursework.feature.streams.impl.StreamsFacade
import com.example.coursework.main.di.MainFacade
import com.example.coursework.shared.profile.impl.data.SharedProfileFacade
import com.example.coursework.topic.impl.TopicFacade
import com.example.feature.main.api.MainApi
import com.example.feature.main.api.MainDeps
import com.example.feature.people.api.PeopleDeps
import com.example.feature.profile.api.ProfileDeps
import com.example.feature.streams.api.StreamsDeps
import com.example.feature.topic.api.TopicDeps
import com.example.shared.profile.api.SharedProfileApi
import com.example.shared.profile.api.SharedProfileDeps
import com.example.shared.profile.api.domain.UsersRepository
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object FeatureGluingModule {
    @Provides
    fun provideTopicDeps(
        appComponent: AppComponent
    ): TopicDeps {
        return appComponent
    }
    
    @Provides
    fun provideSharedProfileDeps(): SharedProfileDeps {
        return SharedProfileDeps.Stub
    }

    @Provides
    fun provideSharedProfileApi(
        deps: SharedProfileDeps
    ): SharedProfileApi {
        return SharedProfileFacade.init(deps).api
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        api: SharedProfileApi
    ): UsersRepository {
        return api.usersRepository
    }

    @Provides
    fun providePeopleDeps(
        appComponent: AppComponent
    ): PeopleDeps {
        return appComponent
    }

    @Provides
    fun provideProfileDeps(appComponent: AppComponent): ProfileDeps {
        return appComponent
    }

    @Provides
    fun provideStreamsDeps(
        @GlobalCicerone cicerone: Cicerone<Router>,
        topicDeps: TopicDeps
    ): StreamsDeps {
        return object : StreamsDeps {
            override val globalCicerone = cicerone

            override fun getTopicScreen(stream: Int, topic: String): FragmentScreen =
                TopicFacade.init(topicDeps).api.getTopicScreen(stream, topic)
        }
    }

    @Provides
    fun provideMainDeps(
        streamsDeps: StreamsDeps,
        peopleDeps: PeopleDeps,
        profileDeps: ProfileDeps
    ): MainDeps {
        return object : MainDeps {
            override fun getStreamsScreen(): FragmentScreen =
                StreamsFacade.init(streamsDeps).api.getStreamsScreen()

            override fun getPeopleScreen(): FragmentScreen =
                PeopleFacade.init(peopleDeps).api.getPeopleScreen()

            override fun getProfileScreen(): FragmentScreen =
                ProfileFacade.init(profileDeps).api.getProfileScreen(null)
        }
    }

    @Provides
    fun provideMainApi(
        deps: MainDeps
    ): MainApi {
        return MainFacade.init(deps).api
    }
}
