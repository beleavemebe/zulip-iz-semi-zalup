package com.example.coursework.app.di

import android.content.Context
import com.example.coursework.app.database.di.AppDbApi
import com.example.coursework.app.database.di.AppDbDeps
import com.example.coursework.app.database.di.AppDbFacade
import com.example.coursework.core.database.di.CoreDbApi
import com.example.coursework.core.database.di.CoreDbDeps
import com.example.coursework.core.database.di.CoreDbFacade
import com.example.coursework.core.di.GlobalCicerone
import com.example.coursework.core.di.LocalCicerone
import com.example.coursework.core.network.di.CoreNetworkApi
import com.example.coursework.core.network.di.CoreNetworkDeps
import com.example.coursework.core.network.di.CoreNetworkFacade
import com.example.coursework.feature.create_stream.api.CreateStreamApi
import com.example.coursework.feature.create_stream.api.CreateStreamDeps
import com.example.coursework.feature.create_topic.impl.CreateTopicFacade
import com.example.coursework.feature.people.impl.PeopleFacade
import com.example.coursework.feature.profile.ui.di.ProfileFacade
import com.example.coursework.feature.streams.impl.StreamsFacade
import com.example.coursework.main.di.MainFacade
import com.example.coursework.shared.profile.impl.di.SharedProfileFacade
import com.example.coursework.shared_messages.api.SharedMessagesApi
import com.example.coursework.shared_messages.api.SharedMessagesDeps
import com.example.coursework.shared_messages.impl.SharedMessagesFacade
import com.example.coursework.shared_streams.api.SharedStreamsApi
import com.example.coursework.shared_streams.api.SharedStreamsDeps
import com.example.coursework.topic.impl.TopicFacade
import com.example.feature.create_stream.impl.CreateStreamFacade
import com.example.feature.create_topic.api.CreateTopicApi
import com.example.feature.create_topic.api.CreateTopicDeps
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
import com.example.shared.profile.api.domain.usecase.GetCurrentUser
import com.example.shared_streams.impl.coursework.SharedStreamsFacade
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
object FeatureGluingModule {
    @Provides
    fun provideTopicDeps(
        sharedMessagesApi: SharedMessagesApi,
        getCurrentUser: GetCurrentUser,
        @GlobalCicerone globalCicerone: Cicerone<Router>
    ): TopicDeps {
        return TopicDeps(
            sharedMessagesApi.messageRepository,
            getCurrentUser,
            globalCicerone
        )
    }

    @Provides
    fun provideTopicApi(
        deps: TopicDeps
    ): TopicApi {
        return TopicFacade.init(deps).api
    }

    @Provides
    fun provideSharedProfileDeps(
        coreNetworkApi: CoreNetworkApi
    ): SharedProfileDeps {
        return object : SharedProfileDeps {
            override val retrofit = coreNetworkApi.retrofit
        }
    }

    @Provides
    fun provideSharedProfileApi(
        deps: SharedProfileDeps
    ): SharedProfileApi {
        return SharedProfileFacade.init(deps).api
    }

    @Provides
    fun provideSharedMessagesDeps(
        coreNetworkApi: CoreNetworkApi,
        coreDbApi: CoreDbApi
    ): SharedMessagesDeps {
        return SharedMessagesDeps(coreNetworkApi.retrofit, coreDbApi.daoProvider)
    }

    @Provides
    fun provideSharedMessagesApi(
        deps: SharedMessagesDeps
    ): SharedMessagesApi {
        return SharedMessagesFacade.init(deps).api
    }

    @Provides
    fun provideSharedStreamsDeps(
        coreNetworkApi: CoreNetworkApi,
        coreDbApi: CoreDbApi
    ): SharedStreamsDeps {
        return object : SharedStreamsDeps {
            override val retrofit = coreNetworkApi.retrofit
            override val daoProvider = coreDbApi.daoProvider
        }
    }

    @Provides
    fun provideSharedStreamsApi(
        deps: SharedStreamsDeps
    ): SharedStreamsApi {
        return SharedStreamsFacade.init(deps).api
    }

    @Provides
    fun providePeopleDeps(
        appComponent: AppComponent
    ): PeopleDeps {
        return appComponent
    }

    @Provides
    fun providePeopleApi(
        deps: PeopleDeps
    ): PeopleApi {
        return PeopleFacade.init(deps).api
    }

    @Provides
    fun provideCreateTopicDeps(
        @GlobalCicerone globalCicerone: Cicerone<Router>,
        sharedStreamsApi: SharedStreamsApi,
        sharedMessagesApi: SharedMessagesApi,
        topicApiProvider: Provider<TopicApi>
    ): CreateTopicDeps {
        return object : CreateTopicDeps {
            override val globalCicerone = globalCicerone
            override val streamsRepository = sharedStreamsApi.streamsRepository
            override val messageRepository = sharedMessagesApi.messageRepository

            override fun onTopicCreated(streamId: Int, stream: String, topic: String) {
                globalCicerone.router.replaceScreen(
                    screen = topicApiProvider.get().getTopicScreen(streamId, stream, topic)
                )
            }
        }
    }

    @Provides
    fun provideCreateTopicApi(
        deps: CreateTopicDeps
    ): CreateTopicApi {
        return CreateTopicFacade.init(deps).api
    }

    @Provides
    fun provideProfileDeps(
        appComponent: AppComponent
    ): ProfileDeps {
        return appComponent
    }

    @Provides
    fun provideProfileApi(
        deps: ProfileDeps
    ): ProfileApi {
        return ProfileFacade.init(deps).api
    }

    @Provides
    fun provideCreateStreamDeps(
        @GlobalCicerone globalCicerone: Cicerone<Router>,
        sharedStreamsApi: SharedStreamsApi,
    ): CreateStreamDeps {
        return object : CreateStreamDeps {
            override val globalCicerone = globalCicerone
            override val streamsRepository = sharedStreamsApi.streamsRepository

            override fun onStreamCreated(name: String) {
                globalCicerone.router.backTo(null)
                StreamsFacade.onStreamCreated?.get()?.invoke(name)
            }
        }
    }

    @Provides
    fun provideCreateStreamApi(
        deps: CreateStreamDeps
    ): CreateStreamApi {
        return CreateStreamFacade.init(deps).api
    }

    @Provides
    fun provideStreamsDeps(
        @GlobalCicerone cicerone: Cicerone<Router>,
        sharedStreamsApi: SharedStreamsApi,
        topicApiProvider: Provider<TopicApi>,
        createStreamApiProvider: Provider<CreateStreamApi>,
        createTopicApiProvider: Provider<CreateTopicApi>
    ): StreamsDeps {
        return object : StreamsDeps {
            override val globalCicerone = cicerone

            override val streamsRepository = sharedStreamsApi.streamsRepository

            override fun getTopicScreen(streamId: Int, stream: String, topic: String) = 
                topicApiProvider.get().getTopicScreen(streamId, stream, topic)

            override fun getCreateStreamScreen() = 
                createStreamApiProvider.get().getCreateStreamScreen()

            override fun getCreateTopicScreen(streamId: Int, stream: String) =
                createTopicApiProvider.get().getCreateTopicScreen(streamId, stream)
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
        @LocalCicerone localCicerone: Cicerone<Router>,
        streamsApi: StreamsApi,
        peopleApi: PeopleApi,
        profileApi: ProfileApi
    ): MainDeps {
        return object : MainDeps {
            override val localCicerone = localCicerone
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

    @Provides
    fun provideCoreNetworkDeps(): CoreNetworkDeps {
        return CoreNetworkDeps()
    }

    @Provides
    fun provideCoreNetworkApi(
        coreNetworkDeps: CoreNetworkDeps
    ): CoreNetworkApi {
        return CoreNetworkFacade.init(coreNetworkDeps).api
    }
}
