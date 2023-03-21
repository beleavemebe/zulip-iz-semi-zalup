package com.example.coursework.feature.people.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.coursework.feature.people.R
import com.example.coursework.feature.people.databinding.FragmentPeopleBinding
import com.example.coursework.feature.people.domain.GetOtherUsers
import com.example.coursework.feature.people.ui.model.PeopleUi
import com.example.coursework.feature.people.ui.recycler.PeopleViewHolderFactory
import com.example.coursework.shared.profile.domain.User
import ru.tinkoff.mobile.tech.ti_recycler.adapters.SimpleTiAdapter
import ru.tinkoff.mobile.tech.ti_recycler_coroutines.TiRecyclerCoroutines
import ru.tinkoff.mobile.tech.ti_recycler_coroutines.base.CoroutinesHolderFactory

class PeopleFragment : Fragment(R.layout.fragment_people) {
    private val binding by viewBinding(FragmentPeopleBinding::bind)
    private val recycler by lazy {
        TiRecyclerCoroutines(
            binding.rvUsers,
            SimpleTiAdapter<PeopleUi, CoroutinesHolderFactory>(PeopleViewHolderFactory())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.adapter.items = GetOtherUsers().execute().map(::toPersonUi)
    }

    private fun toPersonUi(user: User): PeopleUi {
        return PeopleUi(
            id = user.id,
            name = user.name,
            imageUrl = user.imageUrl,
            isOnline = user.onlineStatus == User.OnlineStatus.ONLINE,
            email = user.email
        )
    }
}
