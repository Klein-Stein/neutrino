package com.kleinstein.neutrino

import com.kleinstein.neutrino.contracts.IModule
import com.kleinstein.neutrino.exceptions.NeutrinoException
import com.kleinstein.neutrino.fabrics.IFabric
import kotlin.reflect.KClass
import kotlin.reflect.cast

class Module(override val name: String, private val body: IModule.() -> Unit) :
    IModule {
    private val fabrics = HashMap<String, IFabric<*>>()

    override fun <T : Any> addFabric(tag: String, fabric: IFabric<T>) {
        fabrics[tag] = fabric
    }

    override fun build(): IModule {
        body()
        return this
    }

    override fun contains(tag: String): Boolean = fabrics.containsKey(tag)

    override fun <T : Any> resolve(clazz: KClass<out T>): T = resolve(clazz.qualifiedName!!, clazz)

    override fun <T : Any> resolve(tag: String, clazz: KClass<out T>): T {
        val fabric = fabrics[tag] ?: throw NeutrinoException("The `$tag` not found")
        val obj = fabric.buildOrGet()
        if (obj::class != clazz) {
            val objType = obj::class.qualifiedName!!
            val tType = clazz.qualifiedName!!
            throw NeutrinoException(
                "Can't resolve `$tag` tag: `$objType` can't be casted to `$tType`"
            )
        }
        return clazz.cast(obj)
    }
}
