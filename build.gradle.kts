import kotlin.script.experimental.jvm.util.classpathFromClass

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.application) apply false
    alias(libs.plugins.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
//    alias(libs.plugins.arouter) apply false
}

true // Needed to make the Suppress annotation work for the plugins block
