package com.example.coursework.topic.impl

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.platform.app.InstrumentationRegistry
import com.example.core.ui.FlexBoxLayout
import com.example.coursework.core.database.DaoProvider
import com.example.coursework.core.network.di.CoreNetworkDeps
import com.example.coursework.core.network.di.CoreNetworkFacade
import com.example.coursework.topic.impl.data.db.TopicDao
import com.example.coursework.topic.impl.ui.TopicFragment
import com.example.coursework.topic.impl.ui.view.EmoteReactionView
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
import org.junit.*
import java.io.BufferedReader

// com.example.coursework.core.testcase
class TopicIntegrationTest : TestCase() {

    @get:Rule
    val mockWebServer = MockWebServer()

//    @JvmField
//    @RegisterExtension
//    val executorExtension = MockBackgroundExecutorExtension()

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
                        email = "user604397@tinkoff-android-spring-2023.zulipchat.com",
                        presence = UserPresence.ACTIVE,
                        imageUrl = "https://zulip-avatars.s3.amazonaws.com/54137/8701cd432f4a3a44a9439f1e5213f2206115887e?version=2"
                    )
                }

                override val daoProvider = mockk<DaoProvider> {
                    val topicDaoMock = mockk<TopicDao>(relaxUnitFun = true) {
                        coEvery { getCachedMessages(any()) } returns emptyList()
                    }

                    every { get(TopicDao::class) } returns topicDaoMock
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
            .use(BufferedReader::readText)
    }

    @Test
    fun verify_recycler_displays_correct_messages_response() = run {
        mockWebServer.dispatcher = MockRequestDispatcher().apply {
            returnsForPath("/api/v1/messages") {
                setBody(loadFromAssets("messages.json"))
            }
        }

        val scenario = launchFragmentInContainer<TopicFragment>(
            fragmentArgs = TopicFragment.createArguments(379888, "general"),
            themeResId = com.google.android.material.R.style.Theme_MaterialComponents_DayNight_NoActionBar
        )

        val reactionsScreen = ReactionsScreen()
        TopicScreen {
            step("recycler displays 5 messages and 2 date headers") {
                recycler {
                    isDisplayed()
                    hasSize(7)
                }
            }

            step("date headers are placed correctly") {
                recycler {
                    childAt<TopicScreen.DateHeaderItem>(0) { isDisplayed() }
                    childAt<TopicScreen.DateHeaderItem>(2) { isDisplayed() }
                }
            }

            step("first message has no reactions") {
                recycler {
                    childAt<TopicScreen.MessageItem>(1) {
                        fbReactions.isNotDisplayed()
                    }
                }
            }

            step("third message has a reaction") {
                recycler {
                    childAt<TopicScreen.MessageItem>(4) {
                        fbReactions.hasDescendant { isInstanceOf(EmoteReactionView::class.java) }
                    }
                }
            }

            step("fourth message has a reaction from other user and not the current one") {
                recycler {
                    childAt<TopicScreen.MessageItem>(5) {
                        fbReactions.view.check { view, _ ->
                            view as FlexBoxLayout
                            val reaction = view.getChildAt(0) as EmoteReactionView
                            Assert.assertFalse(reaction.pressed)
                        }
                    }
                }
            }

            step("fifth message has a reaction from current user") {
                recycler {
                    childAt<TopicScreen.MessageItem>(6) {
                        fbReactions.view.check { view, _ ->
                            view as FlexBoxLayout
                            val reaction = view.getChildAt(0) as EmoteReactionView
                            Assert.assertTrue(reaction.pressed)
                        }
                    }
                }
            }

            step("Добавление реакции лонг кликом на сообщение") {
                recycler {
                    childAt<TopicScreen.MessageItem>(6) {
                        fbReactions.view.check { view, _ ->
                            view as FlexBoxLayout
                            Assert.assertEquals(2, view.childCount)
                        }

                        longClick()
                        reactionsScreen {
                            fbEmojis.view.check { view, _ ->
                                view as FlexBoxLayout
                                view.getChildAt(0).performClick()
                            }
                        }

                        flakySafely(allowedExceptions = setOf(NullPointerException::class.java)) {
                            fbReactions.view.check { view, _ ->
                                view as FlexBoxLayout
                                Assert.assertEquals(3, view.childCount)
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    fun verify_recycler_displays_no_messages() = run {
        mockWebServer.dispatcher = MockRequestDispatcher().apply {
            returnsForPath("/api/v1/messages") {
                setBody(
                    """{
                          "found_newest": true,
                          "found_oldest": true,
                          "messages":[]
                       }
                    """.trimIndent()
                )
            }
        }

        launchFragmentInContainer<TopicFragment>(
            fragmentArgs = TopicFragment.createArguments(379888, "general"),
            themeResId = com.google.android.material.R.style.Theme_MaterialComponents_DayNight_NoActionBar
        )

        TopicScreen {
            step("verify_recycler_displays_no_messages") {
                recycler.isDisplayed()
                recycler.hasSize(0)
            }
        }
    }

    @Test
    fun verify_error_is_displayed() = run {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(400)
            }
        }

        launchFragmentInContainer<TopicFragment>(
            fragmentArgs = TopicFragment.createArguments(379888, "general"),
            themeResId = com.google.android.material.R.style.Theme_MaterialComponents_DayNight_NoActionBar
        )

        TopicScreen {
            step("verify_error_is_displayed") {
                tvError.isDisplayed()
            }
        }
    }
}
