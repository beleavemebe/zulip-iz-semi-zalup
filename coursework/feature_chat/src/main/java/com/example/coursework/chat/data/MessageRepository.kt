package com.example.coursework.chat.data

import com.example.coursework.chat.data.MessageRepository.currentUserId
import com.example.coursework.chat.model.Message
import com.example.coursework.chat.model.Reaction
import com.example.coursework.chat.util.emojis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.*
import kotlin.random.Random

object MessageRepository {
    const val currentUserId = "0"

    private val _messages = MutableStateFlow(fakeMessages)
    val messages: Flow<List<Message>> = _messages.asStateFlow()

    suspend fun sendMessage(
        text: String
    ) = withContext(Dispatchers.Default) {
        val oldMessages = _messages.value
        _messages.value = oldMessages + message(text, posted = LocalDateTime.now())
    }

    suspend fun sendOrRevokeReaction(
        messageId: String,
        emoji: String
    ) = withContext(Dispatchers.Default) {
        val oldMessages = _messages.value
        val target = oldMessages.indexOfFirst { it.id == messageId }
        val message = oldMessages[target]
        val reaction = Reaction(emoji, currentUserId)
        val newMessage = if (reaction in message.reactions) {
            message.copy(reactions = message.reactions - reaction)
        } else {
            message.copy(reactions = message.reactions + reaction)
        }

        _messages.value = buildList {
            addAll(oldMessages)
            set(target, newMessage)
        }
    }
}


private val fakeMessages = listOf(
    message("Помогите, что делать? Ребенок проглотил три арбуза, живот болит, температура 40, как ему помочь? Кричит, плачет, говорит что у него живот болит и глотнул три арбуза."),
    message("Я\uD83D\uDD7Aсворую\uD83C\uDFC3\u200D♀бабки\uD83D\uDCB5 прямо\uD83D\uDCBAиз\uD83D\uDCE2комода\uD83D\uDCED своей\uD83D\uDCC7 бабки\uD83D\uDC75батя\uD83D\uDC6E\u200D♀даст\uD83D\uDD27по\uD83D\uDE4Aморде\uD83D\uDC3Dпап\uD83D\uDE2Dне\uD83D\uDC4Aнадо\uD83D\uDC94я\uD83D\uDE46\u200D♂больше\uD83C\uDFB2не\uD83C\uDFB9буду\uD83D\uDCA5но\uD83D\uDE40это\uD83C\uDFC1не\uD83D\uDE45\u200D♂правда"),
    message("Зайдя в браузер, вы натыкаетесь на очередную рекламу сайта знакомств, «Лиза в 250 метров от вас». Игнорируя рекламу, вы продолжаете листать, но возвращаясь наверх страницы замечаете, что счетчик уже на 100 метров, и медленно тикает вниз…\n\n75 метров.\n\n50 метров.\n\n30, 20..\n\nВаш пульс учащается, вы бежите со всех ног но чувствуете, как дистанция между вами и «Лизой» сужается…\n\nСужается как тот коридор, из серии Симпсонов. Там Неду Фландерсу дом снесло ураганом, ему соседи новый построили, а он наперекосяк был, там коридор еще сужался. Смешной момент был."),
    message("Привет, милый. Хорошего тебе вечера Ты самый красивый на твиче! Иногда, когда я вижу твой курсор на экране, я навожусь на него и представляю что мы с тобой держимся за руки"),
    message("Ты сидишь и думаешь что это паста ,но как это может быть пастой, если это даже съесть нельзя, а вот это уже паста: Ингредиенты 450г спагетти; соль - по вкусу; 200г бекона; 2 столовые ложки оливкового масла; 3 яичных желтка; 100г мелко натёртого пармезана; молотый чёрный перец - по вкусу."),
    message("Мне папа запрещает долго сидеть за компьютером, если я долго сижу за компом он начинает водить мое лицом по клавивлцшцзжбытслузуцхфхва"),
    message("\uD83C\uDF3C \uD83C\uDF3A Т Е П Е Р Ь \uD83C\uDF3C \uD83C\uDF3A Э Т О \uD83C\uDF3C \uD83C\uDF3A MercyWing1 PinkMercy MercyWing2 Д Е В Ч А Ч И Й \uD83C\uDF3C \uD83C\uDF3A Ч А Т \uD83C\uDF3C \uD83C\uDF3A\uD83C\uDF3C \uD83C\uDF3A Т Е П Е Р Ь \uD83C\uDF3C \uD83C\uDF3A Э Т О \uD83C\uDF3C \uD83C\uDF3A MercyWing1 PinkMercy MercyWing2 Д Е В Ч А Ч И Й \uD83C\uDF3C \uD83C\uDF3A Ч А Т \uD83C\uDF3C \uD83C\uDF3A"),
    message("⣿⣿⠛⣩⣭⢻⡿⢋⡍⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿ ⣿⠁⢰⣿⣷⣾⠁⣼⡇⣿⡟⢻⡿⠛⣿⡟⢻⡿⠛⣿⠟⢻⣿⣿⣿⠿⢛⠛⢻⣿ ⡏⠀⣾⣿⣿⡇⢀⣏⠈⣯⠀⣸⡃⢀⣿⠀⠘⠇⢰⣿⠀⣘⠙⢻⣇⠰⠿⠀⣾⣿ ⣇⠀⠻⠿⠿⡃⠰⠿⢢⡏⠀⠿⠀⠸⠏⢀⣿⠀⠸⠏⠀⠿⠁⡜⣹⢇⡇⠀⠿⣿ ⣿⣶⣶⣶⣾⣿⣶⣶⣿⣿⣶⣾⣷⣶⣧⣼⣿⣷⣶⣿⣶⣶⣾⣿⣶⣾⣿⣶⣾⣿ ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠋⠉⠁⠀⠈⠙⠁⠙⢿⣿⣿⣿⣿⣿⣿⣿⣿ ⣿⣿⠿⠛⠛⠻⣿⣿⣿⡇⠀⠂⠁⠀⠀⠀⠀⠀⠀⠀⠀⠈⢿⡿⢿⣿⣿⣿⣿⣿ ⡿⠁⢀⣤⣄⠀⠈⣿⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠹⣿⣿⣿⣿ ⠁⠀⠸⠛⢻⣧⡀⠀⢀⣸⠆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠄⠀⠀⢹⣿⣿⣿ ⣤⣤⣤⣤⣤⣽⣿⣿⣶⣾⡖⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠰⣿⡀⠁⠀⠀⢹⣿⣿ ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣆⠀⠀⢸⣿⣿ ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⣿⣿⣷⡀⠈⢻⣿ ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡏⠀⠀⡀⠀⠀⢰⣶⡄⠀⠀⠀⠀⣿⣿⣿⣿⣆⡀⢹ ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣇⣀⣀⣤⣦⣀⠀⣸⣿⡟⡂⠀⠀⢠⣿⣿⣿⣿⣿⣿⣿ ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣶⡀⠀⠀⣿⣿⣿⣿⣿⣿⣿"),
    message("⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠠⠄⠄⠒⠂⢀⠄⠄⠄⠄⠄⠄ ⢀⠄⠄⠄⠄⠄⠄⠄⠄⢀⡀⠄⣰⣶⣦⡈⠄⠄⠄⠄⠄⠄ ⠄⠄⠂⠄⠄⠄⠄⠄⠄⠄⣿⣖⣿⣷⣴⡄⠄⠄⠄⠄⠄⠄ ⠄⠄⠄⠄⠁⠄⠄⠄⠄⣸⣿⣿⣿⠛⠩⠁⠄⠄⠄⠄⠄⠄ ⠄⠄⠄⠄⠄⠄⣀⣤⣾⣿⣿⡏⠉⠄⠁⠄⠄⠄⠄⠄⠄⠄ ⠄⠄⢀⣴⣶⣿⣿⣿⣿⣿⡟⠺⡇⠄⢵⣤⣀⠄⠄⠄⠄⠄ ⠄⢠⣿⣿⣿⣿⣿⣿⣿⡏⠁⠄⣷⣀⠈⠙⠛⠑⠄⠄⠄⠄ ⠄⣼⣿⣿⣿⡇⠹⣿⣿⣿⡦⠄⠹⢿⡇⠄⠄⠄⠄⠄⠄⠄ ⠄⣿⣿⣿⣿⠁⢰⣤⣀⣀⣠⣔⢰⠄⠄⠄⠄⢀⡈⠄⠄⠄ ⢠⣿⣿⠟⠄⠄⢸⣿⣿⣿⣿⠏⢸⡆⠄⠐⠄⢸⣿⣌⠄⠄ ⢸⣿⣿⡆⠄⠄⢸⣿⡿⢿⡤⠄⠈⠄⠄⢀⠄⢰⣿⣿⡄⠄ ⠈⢿⣿⣷⠄⠄⠄⣿⣷⣦⠄⠐⠄⠄⠄⠘⠄⠘⢿⣿⡇⠄ ⠄⠈⠻⣿⣇⠠⠄⢀⡉⠄⠄⠄⠄⠄⢀⡆⠄⠄⠘⣿⡇⠄ ⠄⠄⠄⠘⣿⣧⢀⣿⣿⢷⣶⣶⣶⣾⢟⣾⣄⠄⡴⣿⡀⠄ ⠄⠄⠄⠄⠘⣿⣧⣝⣿⣷⣝⢿⣿⠇⢸⣿⣿⡎⡡⠋⠄⠄ ⠄⠄⠄⠄⠄⣝⠛⠋⠁⣿⣿⡎⢠⣴⣾⣿⣿⣷⠄⠄⠄⠄ ⠄⠄⠄⠄⢠⣿⣷⣾⣿⣿⣿⠁⠘⢿⣿⣿⣿⣿⡄⠄⠄⠄ ⠄⠄⠄⠄⢸⣿⣿⣿⣿⣿⠃⠄⠄⠈⣿⣿⣿⣿⡇⠄⠄⠄"),
    message("forsen Joel forsen Joel forsen Joel forsen Joel forsen Joel forsen Joel forsen Joel forsen Joel forsen Joel"),
    message("че он там бубнит кто-нибудь понимает? =)))) а вообще норм парень похож на мишу галустяна \uD83D\uDC4D\uD83D\uDC4D\uD83D\uDC4Dхорошая трансляция"),
    message("❗Внимание. По достоверным источникам всем мобилизованным будут выданы сет алмазной брони с зачарованием \"защита 3\" и \"защита от огня 3\". Лук с зачарованием на огненные стрелы и бесконечность. 2 стака стрел. один стак жаренной свинины и 2 стака хлеба. Алмазный меч, алмазная кирка. Так же для отслуживших в воздушно десантных войсках будут выданы элитры."),
    message("Я определяю свой пол как боевой вертолёт. Люди говорят, что человеку быть вертолётом невозможно и я умственно отсталый, но меня это не волнует, я прекрасен. С этого момента я хочу, чтобы вы уважали мои права на обстрел с высоты и бессмысленные вращения. Если вы меня не принимаете, вы вертофоб и обязаны проверить вашу возможность быть транспортным средством. Спасибо за понимание."),
    message("Здравствуйте, пишет Людмила Ивановна. Петька на моём уроке от телефона не может отлипнуть, смотрит Вас, и про каких-то \"фа пахов\" говорит и \"арофлит\"!!!!"),
    message("Чел, ты что только и можешь общаться пастами? У тебя своих мыслей и словарного запаса нет? Слабо самому выразить свои эмоции не прибегая к тоннам ненужного текста. Признайся, ты прячешь свою сущность за абзацами, ты напуган... У тебя ещё небось файлик с пастами на все случаи жизни есть, про вентилятор, кондиционер, велосипед, гавно и тд. Будь проще."),
)

private fun message(
    pasta: String,
    posted: LocalDateTime = LocalDateTime.now().minusSeconds(Random.nextLong(1_000_000L))
): Message {
    return Message(
        id = UUID.randomUUID().toString(),
        author = "Roman Shemanovskii",
        authorImageUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/9/96/Meme_Man_on_transparent_background.webp/316px-Meme_Man_on_transparent_background.webp.png",
        message = pasta,
        posted = posted,
        reactions = if (Random.nextBoolean()) {
            emptyList()
        } else {
            listOf(
                Reaction(emojis.random().getCodeString(), "1"),
                Reaction(emojis.random().getCodeString(), "1"),
                Reaction("\uD83E\uDD21", "2"),
                Reaction("\uD83E\uDD21", if (Random.nextBoolean()) currentUserId else "3"),
            )
        }
    )
}
