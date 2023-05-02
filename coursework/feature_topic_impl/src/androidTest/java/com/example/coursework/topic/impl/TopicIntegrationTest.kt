package com.example.coursework.topic.impl

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.platform.app.InstrumentationRegistry
import com.example.coursework.core.database.DaoProvider
import com.example.coursework.core.network.di.CoreNetworkDeps
import com.example.coursework.core.network.di.CoreNetworkFacade
import com.example.coursework.topic.impl.data.db.TopicDao
import com.example.coursework.topic.impl.ui.TopicFragment
import com.example.feature.topic.api.TopicDeps
import com.example.shared.profile.api.domain.User
import com.example.shared.profile.api.domain.UserPresence
import com.example.shared.profile.api.domain.usecase.GetCurrentUser
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit

class TopicUiTest : TestCase() {

    @get:Rule
    val mockWebServer = MockWebServer()

    @Before
    fun before() {
        TopicFacade.init(
            object : TopicDeps {
                override val retrofit = CoreNetworkFacade.init(
                    deps = CoreNetworkDeps(mockWebServer.url("/").toString())
                ).api.retrofit

                override val getCurrentUser = mockk<GetCurrentUser> {
                    coEvery { execute() } returns User(
                        id = 604397,
                        name = "Roman Shemanovskii",
                        email = "не важно",
                        presence = UserPresence.ACTIVE,
                        imageUrl = "https://zulip-avatars.s3.amazonaws.com/54137/8701cd432f4a3a44a9439f1e5213f2206115887e?version=2"
                    )
                }

                override val daoProvider = mockk<DaoProvider> {
                    every { get(TopicDao::class) } returns mockk(relaxUnitFun = true) {
                        coEvery { getCachedMessages(any()) } returns emptyList()
                    }
                }
            }
        )
    }

    @After
    fun after() {
        TopicFacade.clear()
    }

    private fun loadFromAssets(fileName: String): String {
        return InstrumentationRegistry.getInstrumentation()
            .context
            .assets
            .open(fileName)
            .bufferedReader()
            .readText()
    }

    @Test
    fun verify_recycler_displays_messages_response() = run {
        mockWebServer.dispatcher = MockRequestDispatcher().apply {
            returnsForPath("https://tinkoff-android-spring-2023.zulipchat.com/api/v1/messages") {
                setBody(loadFromAssets("messages.json"))
            }
        }

        launchFragmentInContainer<TopicFragment>(
            fragmentArgs = TopicFragment.createArguments(379888, "general"),
            themeResId = com.google.android.material.R.style.Theme_MaterialComponents_DayNight_NoActionBar
        )

        val topicScreen = TopicScreen()
        step("verify_recycler_displays_messages_response") {
            topicScreen.recycler.isDisplayed()
            topicScreen.recycler.hasSize(6)
        }
    }

    @Test
    fun verify_recycler_displays_no_messages() = run {
        mockWebServer.dispatcher = MockRequestDispatcher().apply {
            returnsForPath("/api/v1/messages") {
                setBody("{\n\"result\":\"success\",\n\"msg\":\"\",\n \"messages\":[]}")
            }
        }

        launchFragmentInContainer<TopicFragment>(
            fragmentArgs = TopicFragment.createArguments(379888, "general"),
            themeResId = com.google.android.material.R.style.Theme_MaterialComponents_DayNight_NoActionBar
        )

        val topicScreen = TopicScreen()
        step("verify_recycler_displays_no_messages") {
            topicScreen.recycler.isDisplayed()
            topicScreen.recycler.hasSize(0)
        }
    }

    @Test
    fun verify_error_is_displayed() = run {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                throw RuntimeException("Oopsy!")
            }
        }

        launchFragmentInContainer<TopicFragment>(
            fragmentArgs = TopicFragment.createArguments(379888, "general"),
            themeResId = com.google.android.material.R.style.Theme_MaterialComponents_DayNight_NoActionBar
        )

        val topicScreen = TopicScreen()
        step("verify_error_is_displayed") {
            flakySafely {
                topicScreen.tvError.isDisplayed()
            }
        }
    }
}
