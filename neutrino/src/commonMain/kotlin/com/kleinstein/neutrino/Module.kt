package com.kleinstein.neutrino

import com.kleinstein.neutrino.contracts.IModule
import com.kleinstein.neutrino.exceptions.NeutrinoException
import com.kleinstein.neutrino.contracts.IFabric
import com.kleinstein.neutrino.contracts.ILazyFabric
import kotlin.reflect.KClass
import kotlin.reflect.cast

class Module(override val name: String, private val body: IModule.() -> Unit) :
    IModule {
    private val fabrics = hashMapOf<String, IFabric<*>>()

    override val size: Int
        get() = fabrics.size

    override fun <T : Any> addFabric(tag: String, fabric: IFabric<T>) {
        fabrics[tag] = fabric
    }

    override fun build(): IModule {
        body()
        return this
    }

    override fun containsAll(elements: Collection<Map.Entry<String, IFabric<*>>>): Boolean =
        fabrics.entries.containsAll(elements)

    override fun isEmpty(): Boolean = fabrics.isEmpty()

    override fun iterator(): Iterator<Map.Entry<String, IFabric<*>>> = fabrics.iterator()

    override fun containsTag(tag: String): Boolean = fabrics.containsKey(tag)

    override fun contains(element: Map.Entry<String, IFabric<*>>): Boolean =
        fabrics.entries.contains(element)

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

    override fun <T : Any> resolveLazy(clazz: KClass<out T>): Lazy<T> =
        resolveLazy(clazz.qualifiedName!!, clazz)

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> resolveLazy(tag: String, clazz: KClass<out T>): Lazy<T> {
        val fabric = fabrics[tag] ?: throw NeutrinoException("The `$tag` not found")
        if (fabric !is ILazyFabric<*>) {
            throw NeutrinoException("Can't resolve `$tag` tag: the object is not lazy")
        }
        val obj = fabric.buildOrGet()
        return obj as Lazy<T>
    }
}
