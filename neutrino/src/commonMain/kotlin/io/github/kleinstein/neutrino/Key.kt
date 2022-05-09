package io.github.kleinstein.neutrino

import kotlin.reflect.KType
import io.github.kleinstein.neutrino.contracts.IModule

/**
 * This class is used as a unique key to resole dependencies in [modules][IModule]
 *
 * @property type Dependency type
 * @property tag Dependency tag (optional)
 */
data class Key(
    val type: KType,
    val tag: String? = null
)
