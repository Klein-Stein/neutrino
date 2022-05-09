package io.github.kleinstein.neutrino

import io.github.kleinstein.neutrino.contracts.IResolvable
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
fun <T: Any> IResolvable.resolve(tag: String, objCClass: ObjCClass): T =
    resolve(getOriginalKotlinClass(objCClass) as KClass<out T>, tag)

@Suppress("UNCHECKED_CAST")
fun <T: Any> IResolvable.resolve(objCClass: ObjCClass): T =
    resolve(getOriginalKotlinClass(objCClass) as KClass<out T>)
