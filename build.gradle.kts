// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.6.1" apply false
    id("org.jetbrains.kotlin.android") version "2.1.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0"
    id("com.google.devtools.ksp") version "2.1.0-1.0.29" apply false
    kotlin("plugin.serialization") version "2.0.21"
//    alias(libs.plugins.compose.compiler) apply false
}
