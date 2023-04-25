package com.example.coursework.app.di

import android.content.Context
import com.example.coursework.app.database.di.AppDbApi
import com.example.coursework.app.database.di.AppDbDeps
import com.example.coursework.app.database.di.AppDbFacade
import com.example.coursework.core.database.di.CoreDbApi
import com.example.coursework.core.database.di.CoreDbDeps
import com.example.coursework.core.database.di.CoreDbFacade
import com.example.coursework.core.di.GlobalCicerone
import com.example.coursework.feature.people.impl.PeopleFacade
import com.example.coursework.feature.profile.ui.di.ProfileFacade
import com.example.coursework.feature.streams.impl.StreamsFacade
import com.example.coursework.main.di.MainFacade
import com.example.coursework.shared.profile.impl.di.SharedProfileFacade
import com.example.coursework.topic.impl.TopicFacade
import com.example.feature.main.api.MainApi
import com.example.feature.main.api.MainDeps
import com.example.feature.people.api.PeopleApi
import com.example.feature.people.api.PeopleDeps
import com.example.feature.profile.api.ProfileApi
import com.example.feature.profile.api.ProfileDeps
import com.example.feature.streams.api.StreamsApi
import com.example.feature.streams.api.StreamsDeps
import com.example.feature.topic.api.TopicApi
import com.example.feature.topic.api.TopicDeps
import com.example.shared.profile.api.SharedProfileApi
import com.example.shared.profile.api.SharedProfileDeps
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.Module
import dagger.Provides

@Module
object FeatureGluingModule {
    @Provides
    fun provideTopicDeps(
        appComponent: AppComponent
    ): TopicDeps {
        return appComponent
    }

    @Provides
    fun provideTopicApi(
        deps: TopicDeps
    ): TopicApi {
        return TopicFacade.init(deps).api
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
    fun providePeopleDeps(
        appComponent: AppComponent
    ): PeopleDeps {
        return appComponent
    }

    @Provides
    fun providePeopleApi(
        peopleDeps: PeopleDeps
    ): PeopleApi {
        return PeopleFacade.init(peopleDeps).api
    }

    @Provides
    fun provideProfileDeps(
        appComponent: AppComponent
    ): ProfileDeps {
        return appComponent
    }

    @Provides
    fun provideProfileApi(
        profileDeps: ProfileDeps
    ): ProfileApi {
        return ProfileFacade.init(profileDeps).api
    }

    @Provides
    fun provideStreamsDeps(
        @GlobalCicerone cicerone: Cicerone<Router>,
        topicDeps: TopicDeps,
        coreDbApi: CoreDbApi
    ): StreamsDeps {
        return object : StreamsDeps {
            override val globalCicerone = cicerone

            override val daoProvider = coreDbApi.daoProvider

            override fun getTopicScreen(stream: Int, topic: String): FragmentScreen =
                provideTopicApi(topicDeps).getTopicScreen(stream, topic)
        }
    }

    @Provides
    fun provideStreamsApi(
        streamsDeps: StreamsDeps
    ): StreamsApi {
        return StreamsFacade.init(streamsDeps).api
    }

    @Provides
    fun provideMainDeps(
        streamsApi: StreamsApi,
        peopleApi: PeopleApi,
        profileApi: ProfileApi
    ): MainDeps {
        return object : MainDeps {
            override fun getStreamsScreen() = streamsApi.getStreamsScreen()
            override fun getPeopleScreen() = peopleApi.getPeopleScreen()
            override fun getProfileScreen() = profileApi.getProfileScreen(null)
        }
    }

    @Provides
    fun provideMainApi(
        deps: MainDeps
    ): MainApi {
        return MainFacade.init(deps).api
    }

    @Provides
    fun provideAppDbDeps(
        context: Context
    ): AppDbDeps {
        return object : AppDbDeps {
            override val context = context
        }
    }

    @Provides
    fun provideAppDbApi(
        deps: AppDbDeps
    ): AppDbApi {
        return AppDbFacade.init(deps).api
    }

    @Provides
    fun provideCoreDbDeps(
        appDbApi: AppDbApi
    ): CoreDbDeps {
        return CoreDbDeps(
            daoSuppliers = appDbApi.daoSuppliers.toList()
        )
    }

    @Provides
    fun provideCoreDbApi(
        deps: CoreDbDeps
    ): CoreDbApi {
        return CoreDbFacade.init(deps).api
    }
}
