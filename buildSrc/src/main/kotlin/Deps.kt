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


    object Glide : Group("com.github.bumptech.glide") {
        val core = withArtifact("glide", Versions.glide)
        val compiler = withArtifact("compiler", Versions.glide)
    }

    val kotlin = dependency("org.jetbrains.kotlin:kotlin-stdlib-jdk7", Versions.kotlin)

    val material = dependency("com.google.android.material:material", Versions.material)
    val materialValues = dependency("blue.aodev:material-values", Versions.materialValues)

    object Navigation : Group("androidx.navigation") {
        val fragment = Navigation.withArtifact("navigation-fragment-ktx", Versions.navigation)
        val ui = Navigation.withArtifact("navigation-ui-ktx", Versions.navigation)
    }

    object Paging : Group("androidx.paging") {
        val core = Paging.withArtifact("paging-runtime", Versions.paging)
        val rx = Paging.withArtifact("paging-rxjava2", Versions.paging)
    }

    val remoteData = dependency("com.github.torresmi:remotedata", Versions.remoteData)

    object Retrofit : Group("com.squareup.retrofit2") {
        val core = withArtifact("retrofit", Versions.retrofit)
        val moshiConverter = withArtifact("converter-moshi", Versions.retrofit)
        val rxJavaAdapter = withArtifact("adapter-rxjava2", Versions.retrofit)
    }

    object RxJava : Group("io.reactivex.rxjava2") {
        val android = withArtifact("rxandroid", Versions.rxAndroid)
        val core = withArtifact("rxjava", Versions.rxJava)
        val kotlin = withArtifact("rxkotlin", Versions.rxKotlin)
    }

    val rxRelay = dependency("com.jakewharton.rxrelay2:rxrelay", Versions.relay)

    object Test {
        val junit = dependency("junit:junit", Versions.junit)
        val kotest = dependency("io.kotest:kotest-runner-junit5-jvm", Versions.kotest)
        val kotestAssertion = dependency("io.kotest:kotest-assertions-core-jvm", Versions.kotest)
        val mockk = dependency("io.mockk:mockk", Versions.mockk)
    }

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
    const val epoxy = "4.1.0"
    const val glide = "4.11.0"
    const val junit = "4.13"
    const val kotest = "4.3.2"
    const val kotlin = "1.4.10"
    const val lifecycle = "2.2.0"
    const val material = "1.3.0-alpha02"
    const val materialValues = "1.1.1"
    const val mockk = "1.10.4"
    const val navigation = "2.3.0"
    const val paging = "3.0.0-alpha12"
    const val relay = "2.1.1"
    const val remoteData = "1.1"
    const val retrofit = "2.9.0"
    const val rxAndroid = "2.1.1"
    const val rxJava = "2.2.20"
    const val rxKotlin = "2.4.0"
    const val viewpager2 = "1.0.0"

}
