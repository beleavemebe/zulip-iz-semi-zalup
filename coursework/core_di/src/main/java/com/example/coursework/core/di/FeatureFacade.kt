package com.example.coursework.core.di

interface BaseDeps {
    companion object : BaseDeps
}

interface BaseApi {
    companion object : BaseApi
}

interface DaggerComponent {
    companion object : DaggerComponent
}

abstract class FeatureFacade<Deps : BaseDeps, Api : BaseApi, Component : DaggerComponent> {
    private var _deps: Deps? = null
    private var _api: Api? = null
    private var _component: Component? = null

    val deps: Deps
        get() = notNull(_deps)

    val api: Api
        get() = notNull(_api)

    val component: Component
        get() = notNull(_component)

    fun init(deps: Deps): FeatureFacade<Deps, Api, Component> {
        val component = createComponent(deps)
        val api = createApi(component, deps)
        return init(deps, api, component)
    }

    fun init(
        deps: Deps,
        api: Api,
        component: Component,
    ): FeatureFacade<Deps, Api, Component> {
        if (_deps == null) _deps = deps
        if (_api == null) _api = api
        if (_component == null) _component = component
        return this
    }

    fun clear() {
        _deps = null
        _api = null
        _component = null
    }

    protected abstract fun createComponent(deps: Deps): Component

    protected abstract fun createApi(component: Component, deps: Deps): Api

    private fun <T> notNull(t: T?) = requireNotNull(t) {
        "Facade ${this.javaClass.simpleName} was not initialized or already cleared"
    }
}
