package dependencies

object Deps {
    object Apk {
        const val compileSdkVersion = 30
        const val minSdkVersion = 21
        const val targetSdkVersion = 30
        const val versionCode = 1
        const val versionName = "0.1.0"
    }

    val appcompat = dependency("androidx.appcompat:appcompat", Versions.appcomat)

    val androidCore = dependency("androidx.core:core-ktx", Versions.androidCore)

    val constraintLayout =
        dependency("androidx.constraintlayout:constraintlayout", Versions.constraintLayout)

    object Epoxy : Group("com.airbnb.android") {
        val base = withArtifact("epoxy", Versions.epoxy)
        val paging = withArtifact("epoxy-paging", Versions.epoxy)
    }

    val kotlin = dependency("org.jetbrains.kotlin:kotlin-stdlib-jdk7", Versions.kotlin)

    val material = dependency("com.google.android.material:material", Versions.material)

    object Navigation : Group("androidx.navigation") {
        val fragment = withArtifact("navigation-fragment-ktx", Versions.navigation)
        val ui = withArtifact("navigation-ui-ktx", Versions.navigation)
    }

    object RxJava : Group("io.reactivex.rxjava2") {
        val android = withArtifact("rxandroid", Versions.rxAndroid)
        val core = withArtifact("rxjava", Versions.rxJava)
        val kotlin = withArtifact("rxkotlin", Versions.rxKotlin)
    }

    val rxRelay = dependency("com.jakewharton.rxrelay3:rxrelay", Versions.relay)

    val viewpager2 = dependency("androidx.viewpager2:viewpager2", Versions.viewpager2)

}

abstract class Group(val group: String) {
    fun withArtifact(artifact: String, version: String): String = "$group:$artifact:$version"
}

private fun dependency(path: String, version: String, extension: String? = null): String =
    extension?.let {
        "$path:$version@$extension"
    } ?: "$path:$version"

object Versions {
    const val androidCore = "1.3.2"
    const val appcomat = "1.2.0"
    const val constraintLayout = "2.0.4"
    const val epoxy = "4.3.1"
    const val kotlin = "1.3.70"
    const val lifecycle = "2.2.0"
    const val material = "1.3.0-alpha02"
    const val navigation = "2.3.0"
    const val relay = "3.0.0"
    const val rxAndroid = "3.0.0"
    const val rxJava = "3.0.9"
    const val rxKotlin = "3.0.1"
    const val viewpager2 = "1.0.0"

}
