package com.example.shared_streams.impl.coursework.data

import com.example.coursework.shared_streams.api.domain.model.Stream
import com.example.coursework.shared_streams.api.domain.model.Topic
import com.example.shared_streams.impl.coursework.data.db.StreamEntity
import com.example.shared_streams.impl.coursework.data.db.TopicEntity
import com.example.shared_streams.impl.coursework.data.model.dto.StreamDto
import com.example.shared_streams.impl.coursework.data.model.dto.TopicDto

object StreamsMapper {
    fun toStream(dto: StreamDto): Stream {
        return Stream(
            dto.stream_id,
            dto.name
        )
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

    fun toEntity(stream: Stream, subscribed: Boolean): StreamEntity {
        return StreamEntity(
            id = stream.id,
            name = stream.name,
            subscribed = subscribed
        )
    }

    fun toEntity(topic: Topic, streamId: Int): TopicEntity {
        return TopicEntity(
            name = topic.name,
            streamId = streamId
        )
    }
}
