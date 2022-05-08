package io.github.kleinstein.neutrino

import io.github.kleinstein.neutrino.contracts.IModule
import io.github.kleinstein.neutrino.exceptions.NeutrinoException
import io.github.kleinstein.neutrino.contracts.IFabric
import kotlin.reflect.KClass
import kotlin.reflect.cast

/**
 * [Injector]'s module
 *
 * Modules allow to split dependencies into separate independent groups that can be convenient for
 * decomposition of their initialization
 *
 * @property name Module name
 * @param body Initialization block of dependencies
 * @property size The number of added injections
 * @constructor Creates a new module
 */
class Module(override val name: String, private val body: IModule.() -> Unit) : IModule {
    private val fabrics = hashMapOf<String, IFabric<*>>()

    override val size: Int
        get() = fabrics.size

    override fun addFabric(tag: String, fabric: IFabric<*>) {
        fabrics[tag] = fabric
    }

    override fun removeFabric(tag: String): Boolean = fabrics.remove(tag) != null

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

    override fun <T : Any> resolve(clazz: KClass<out T>): T = resolve(clazz.simpleName!!, clazz)

    override fun <T : Any> resolve(tag: String, clazz: KClass<out T>): T {
        val fabric = fabrics[tag] ?: throw NeutrinoException("The `$tag` not found")
        val obj = fabric.buildOrGet()
        if (obj::class != clazz) {
            val objType = obj::class.simpleName!!
            val tType = clazz.simpleName!!
            throw NeutrinoException(
                "Can't resolve `$tag` tag: `$objType` can't be casted to `$tType`"
            )
        }
        return clazz.cast(obj)
    }
}
