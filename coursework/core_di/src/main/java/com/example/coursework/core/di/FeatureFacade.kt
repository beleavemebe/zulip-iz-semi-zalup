package com.example.coursework.core.di

interface BaseDeps

interface BaseApi

interface DaggerComponent

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
        if (_component == null) {
            _deps = deps
            _component = createComponent(deps)
            _api = createApi(component, deps)
        }
        return this
    }

    fun clear() {
        _component = null
        _api = null
    }

    protected abstract fun createComponent(deps: Deps): Component

    protected abstract fun createApi(component: Component, deps: Deps): Api

    private fun <T> notNull(t: T?) = requireNotNull(t) {
        "Facade ${this.javaClass.simpleName} was not initialized or already cleared"
    }
}
