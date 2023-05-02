package com.example.coursework.topic.impl

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class MockRequestDispatcher : Dispatcher() {

    private val responses: MutableMap<String, MockResponse> = mutableMapOf()

    override fun dispatch(request: RecordedRequest): MockResponse {
        println("dispatch $request")
        val path = request.path!!.removeQueryParams()
        val response = responses.entries.firstOrNull { it.key.endsWith(path) }
        return response?.value ?: MockResponse().setResponseCode(404)
    }

    fun returnsForPath(path: String, response: MockResponse.() -> MockResponse) {
        responses[path.removeQueryParams()] = response(MockResponse())
    }

    private fun String.removeQueryParams(): String {
        return substringBefore("?")
    }
}
