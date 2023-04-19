package com.example.coursework.topic.impl.data.model

@kotlinx.serialization.Serializable
data class NarrowElement<out T>(
    val operator: String,
    val operand: T,
)

object Narrow {
    operator fun <T : Any> invoke(
        vararg args: Pair<String, T>,
    ): List<NarrowElement<Any>> = args.map {
        NarrowElement(it.first, it.second)
    }
}
