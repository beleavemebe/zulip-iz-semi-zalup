package com.example.coursework.feature.streams.impl.data

import com.example.coursework.feature.streams.impl.data.db.StreamEntity
import com.example.coursework.feature.streams.impl.data.db.TopicEntity
import com.example.coursework.feature.streams.impl.data.model.dto.StreamDto
import com.example.coursework.feature.streams.impl.data.model.dto.SubscribedStreamDto
import com.example.coursework.feature.streams.impl.data.model.dto.TopicDto
import com.example.coursework.feature.streams.impl.domain.model.Stream
import com.example.coursework.feature.streams.impl.domain.model.Topic

object StreamsMapper {
    fun toStream(dto: StreamDto): Stream {
        return Stream(dto.stream_id, dto.name)
    }

    fun toStream(dto: SubscribedStreamDto): Stream {
        return Stream(dto.stream_id, dto.name)
    }

    fun toTopic(dto: TopicDto): Topic {
        return Topic(dto.name)
    }

    fun toStream(entity: StreamEntity): Stream {
        return Stream(entity.id, entity.name)
    }

    fun toTopic(entity: TopicEntity): Topic {
        return Topic(entity.name)
    }
}
