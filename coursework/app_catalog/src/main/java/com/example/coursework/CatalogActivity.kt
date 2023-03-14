package com.example.coursework

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.core.ui.FlexBoxLayout
import com.example.coursework.chat.ui.model.ReactionUi
import com.example.coursework.chat.ui.view.EmoteReactionView
import com.example.coursework.chat.ui.view.MessageView

class CatalogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)
        with(findViewById<LinearLayout>(R.id.linearLayout)) {
            val flexBoxLayout = findViewById<FlexBoxLayout>(R.id.flexBoxLayout)
            repeat(30) {
                flexBoxLayout.addView(
                    EmoteReactionView(this@CatalogActivity).apply {
                        setData(pressed = false, "\uD83E\uDD2E", "")
                    })
            }

            addView(EmoteReactionView(this@CatalogActivity).apply {
                setData(pressed = false, "\uD83E\uDD21", "6969")
            })
            addView(EmoteReactionView(this@CatalogActivity).apply {
                setData(pressed = true, "\uD83E\uDD2E", "9")
            })
            addView(EmoteReactionView(this@CatalogActivity).apply {
                setData(pressed = true, "\uD83D\uDE02", "100")
            })

            val messageView = MessageView(context)
            addView(messageView)
            messageView.author = "Roman Shemanovskii"
            messageView.message = "Сейчас ехал в автобусе, со мной ещё два пацана ехали, и ещё женщина в такой смешной шапке, пожилой мужчина, ещё узбек, старушка с дедом, ещё две женщины, одна с ребёнком. Потом зашёл мужик, а женщина в шапке вышла. Потом я сам уже вышел, не знаю, чем все закончилось"
            messageView.messageReactions = listOf(
                ReactionUi(emote = "\uD83D\uDC14", reactionCount = 80),
                ReactionUi(emote = "\uD83E\uDD21", reactionCount = 69)
            )
        }
    }
}
