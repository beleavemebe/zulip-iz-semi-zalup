package com.example.coursework.feature.people.domain

import com.example.core.ui.throwRandomly
import com.example.coursework.shared.profile.domain.User
import kotlinx.coroutines.delay
import java.util.*

class GetOtherUsers {
    suspend fun execute(): List<User> {
        delay(100)
        throwRandomly()
        return listOf(
            User(UUID.randomUUID().toString(), "Подрыв Устоев", "idk@example.com", "", User.OnlineStatus.OFFLINE, "https://upload.wikimedia.org/wikipedia/en/thumb/9/96/Meme_Man_on_transparent_background.webp/316px-Meme_Man_on_transparent_background.webp.png"),
            User(UUID.randomUUID().toString(), "Отряд Ковбоев", "idk@example.com", "", User.OnlineStatus.OFFLINE, "https://upload.wikimedia.org/wikipedia/en/thumb/9/96/Meme_Man_on_transparent_background.webp/316px-Meme_Man_on_transparent_background.webp.png"),
            User(UUID.randomUUID().toString(), "Улов Налимов", "idk@example.com", "", User.OnlineStatus.ONLINE, "https://upload.wikimedia.org/wikipedia/en/thumb/9/96/Meme_Man_on_transparent_background.webp/316px-Meme_Man_on_transparent_background.webp.png"),
            User(UUID.randomUUID().toString(), "Улов Кальмаров", "idk@example.com", "", User.OnlineStatus.ONLINE, "https://upload.wikimedia.org/wikipedia/en/thumb/9/96/Meme_Man_on_transparent_background.webp/316px-Meme_Man_on_transparent_background.webp.png"),
            User(UUID.randomUUID().toString(), "Рулон Обоев", "idk@example.com", "", User.OnlineStatus.OFFLINE, "https://upload.wikimedia.org/wikipedia/en/thumb/9/96/Meme_Man_on_transparent_background.webp/316px-Meme_Man_on_transparent_background.webp.png"),
            User(UUID.randomUUID().toString(), "Учет Расходов", "idk@example.com", "", User.OnlineStatus.ONLINE, "https://upload.wikimedia.org/wikipedia/en/thumb/9/96/Meme_Man_on_transparent_background.webp/316px-Meme_Man_on_transparent_background.webp.png"),
            User(UUID.randomUUID().toString(), "Обед Лемуров", "idk@example.com", "", User.OnlineStatus.ONLINE, "https://upload.wikimedia.org/wikipedia/en/thumb/9/96/Meme_Man_on_transparent_background.webp/316px-Meme_Man_on_transparent_background.webp.png"),
            User(UUID.randomUUID().toString(), "Удар Морозов", "idk@example.com", "", User.OnlineStatus.OFFLINE, "https://upload.wikimedia.org/wikipedia/en/thumb/9/96/Meme_Man_on_transparent_background.webp/316px-Meme_Man_on_transparent_background.webp.png"),
            User(UUID.randomUUID().toString(), "Заплыв Матросов", "idk@example.com", "", User.OnlineStatus.OFFLINE, "https://upload.wikimedia.org/wikipedia/en/thumb/9/96/Meme_Man_on_transparent_background.webp/316px-Meme_Man_on_transparent_background.webp.png"),
            User(UUID.randomUUID().toString(), "Творец Кошмаров", "idk@example.com", "", User.OnlineStatus.OFFLINE, "https://upload.wikimedia.org/wikipedia/en/thumb/9/96/Meme_Man_on_transparent_background.webp/316px-Meme_Man_on_transparent_background.webp.png"),
            User(UUID.randomUUID().toString(), "Иллидан Яростьбуряев", "idk@example.com", "", User.OnlineStatus.OFFLINE, "https://upload.wikimedia.org/wikipedia/en/thumb/9/96/Meme_Man_on_transparent_background.webp/316px-Meme_Man_on_transparent_background.webp.png"),
            User(UUID.randomUUID().toString(), "Алишер Усманов", "idk@example.com", "", User.OnlineStatus.OFFLINE, "https://upload.wikimedia.org/wikipedia/en/thumb/9/96/Meme_Man_on_transparent_background.webp/316px-Meme_Man_on_transparent_background.webp.png"),
            User(UUID.randomUUID().toString(), "Тархун Черноголовов", "idk@example.com", "", User.OnlineStatus.ONLINE, "https://upload.wikimedia.org/wikipedia/en/thumb/9/96/Meme_Man_on_transparent_background.webp/316px-Meme_Man_on_transparent_background.webp.png"),
            User(UUID.randomUUID().toString(), "Бегун Безногов", "idk@example.com", "", User.OnlineStatus.OFFLINE, "https://upload.wikimedia.org/wikipedia/en/thumb/9/96/Meme_Man_on_transparent_background.webp/316px-Meme_Man_on_transparent_background.webp.png"),
            User(UUID.randomUUID().toString(), "Моргун Безглазов", "idk@example.com", "", User.OnlineStatus.ONLINE, "https://upload.wikimedia.org/wikipedia/en/thumb/9/96/Meme_Man_on_transparent_background.webp/316px-Meme_Man_on_transparent_background.webp.png"),
        )
    }
}
