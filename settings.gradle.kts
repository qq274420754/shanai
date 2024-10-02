pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = java.net.URI.create("https://maven.aliyun.com/repository/google") }
        maven { url = java.net.URI.create("https://maven.aliyun.com/repository/gradle-plugin") }
        maven { url = java.net.URI.create("https://maven.aliyun.com/repository/jcenter") }
        maven { url = java.net.URI.create("https://maven.aliyun.com/repository/public") }
        maven { url = java.net.URI.create("https://maven.aliyun.com/repository/central") }
        maven { url = java.net.URI.create("https://plugins.gradle.org/m2/com/gradle") }
        maven { url = java.net.URI.create("https://maven.google.com") }
        maven { url = java.net.URI.create("https://jitpack.io") }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = java.net.URI.create("https://maven.aliyun.com/repository/google") }
        maven { url = java.net.URI.create("https://maven.aliyun.com/repository/gradle-plugin") }
        maven { url = java.net.URI.create("https://maven.aliyun.com/repository/jcenter") }
        maven { url = java.net.URI.create("https://maven.aliyun.com/repository/public") }
        maven { url = java.net.URI.create("https://maven.aliyun.com/repository/central") }
        maven { url = java.net.URI.create("https://plugins.gradle.org/m2/com/gradle") }
        maven { url = java.net.URI.create("https://maven.google.com") }
        maven { url = java.net.URI.create("https://jitpack.io") }
    }
}

rootProject.name = "shanai"

include(
    ":app",
    ":lib_base",
    ":lib_common",
    ":module_home"
)
include(":module-main")
include(":module_me")
