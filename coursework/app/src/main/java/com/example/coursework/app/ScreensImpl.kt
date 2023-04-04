package com.example.coursework.app

import com.example.core.ui.Screens
import com.example.coursework.chat.ui.TopicFragment
import com.example.coursework.feature.channels.ui.ChannelsFragment
import com.example.coursework.feature.people.ui.PeopleFragment
import com.example.coursework.feature.profile.ui.ProfileFragment
import com.example.coursework.main.MainFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

class ScreensImpl : Screens {
    override fun main() = FragmentScreen { MainFragment() }
    override fun channels() = FragmentScreen { ChannelsFragment() }
    override fun people() = FragmentScreen { PeopleFragment() }
    override fun profile() = FragmentScreen { ProfileFragment() }
    override fun topic(
        stream: Int,
        topic: String,
    ) = FragmentScreen {
        TopicFragment.newInstance(stream, topic)
    }
}
