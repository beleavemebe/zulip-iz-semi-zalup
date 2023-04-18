package com.example.coursework.core.di

//interface FeatureFacade<Deps : BaseDeps, Api : BaseApi> {
//    fun init(deps: Deps)
//    fun getApi(): Api
//    fun clear()
//}

interface BaseDeps

interface BaseApi

interface DaggerComponent

abstract class FeatureFacade<Deps : BaseDeps, Api : BaseApi, Component : DaggerComponent> {
    protected abstract fun createApi(deps: Deps): Api
    protected abstract fun createComponent(deps: Deps): Component

    private var deps: Deps? = null
    private var api: Api? = null
    private var component: Component? = null

    fun init(deps: Deps) {
        if (component == null) {
            this.deps = deps
            component = createComponent(deps)
            api = createApi(deps)
        }
    }

    fun getDeps(): Deps = requireNotNull(deps)

    fun getApi(): Api = requireNotNull(api)

    fun getComponent(): Component = requireNotNull(component)

    fun clear() {
        component = null
    }
}
