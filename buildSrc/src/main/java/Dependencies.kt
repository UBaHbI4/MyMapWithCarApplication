import org.gradle.api.JavaVersion

object Config {
    const val APPLICATION_ID = "softing.ubah4ukdev.mymapapplication"
    const val COMPILE_SDK = 32
    const val MIN_SDK_VERSION = 27
    const val TARGET_SDK = 32
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"
    const val JVM_TARGET = "1.8"
    val java_version = JavaVersion.VERSION_1_8
}

//Версии библиотек
object LibVersion {
    const val CORE_KTX_VERSION = "1.7.0"
    const val APPCOMPAT_VERSION = "1.4.1"
    const val ANDROID_MATERIAL_VERSION = "1.5.0"
    const val CONSTRAINT_LAYOUT_VERSION = "2.1.3"
    const val LIFECYCLE_VERSION = "2.4.1"
    const val NAVIGATION_FRAGMENT_VERSION = "2.4.1"
    const val NAVIGATION_UI_KTX_VERSION = "2.4.1"

    // ViewBindingPropertyDelegate
    const val VIEW_BINDING_DELEGATE_VERSION = "1.5.6"

    // Yandex MapKit
    const val YANDEX_MAP_KIT_VERSION = "4.0.0-full"

    // Room
    const val ROOM_VERSION = "2.4.1"

    // Koin
    const val KOIN_VERSION = "3.1.5"

    // Tests
    const val JUNIT_VERSION = "4.13.2"
    const val TEST_EXT_JUNIT_VERSION = "1.1.3"
    const val TEST_ESPRESSO_VERSION = "3.4.0"
}

object Design {
    const val APPCOMPAT = "androidx.appcompat:appcompat:${LibVersion.APPCOMPAT_VERSION}"
    const val MATERIAL =
        "com.google.android.material:material:${LibVersion.ANDROID_MATERIAL_VERSION}"
    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${LibVersion.CONSTRAINT_LAYOUT_VERSION}"
    const val NAVIGATION_FRAGMENT =
        "androidx.navigation:navigation-fragment:${LibVersion.NAVIGATION_FRAGMENT_VERSION}"
    const val NAVIGATION_UI_KTX =
        "androidx.navigation:navigation-ui-ktx:${LibVersion.NAVIGATION_UI_KTX_VERSION}"
}

object Kotlin {
    const val CORE = "androidx.core:core-ktx:${LibVersion.CORE_KTX_VERSION}"
}

object LifeCycle {
    const val LIVEDATA_KTX =
        "androidx.lifecycle:lifecycle-livedata-ktx:${LibVersion.LIFECYCLE_VERSION}"
    const val VIEW_MODEL_KTX =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${LibVersion.LIFECYCLE_VERSION}"
}

object ViewBindingDelegate {
    const val DELEGATE =
        "com.github.kirich1409:viewbindingpropertydelegate:${LibVersion.VIEW_BINDING_DELEGATE_VERSION}"
}

object Map {
    const val YANDEX_MAP_KIT = "com.yandex.android:maps.mobile:${LibVersion.YANDEX_MAP_KIT_VERSION}"
}

object Room {
    const val RUN_TIME = "androidx.room:room-runtime:${LibVersion.ROOM_VERSION}"
    const val KTX = "androidx.room:room-ktx:${LibVersion.ROOM_VERSION}"
    const val RX_JAVA = "androidx.room:room-rxjava2:${LibVersion.ROOM_VERSION}"
    const val COMPILER = "androidx.room:room-compiler:${LibVersion.ROOM_VERSION}"
}

object Koin {
    const val CORE = "io.insert-koin:koin-core:${LibVersion.KOIN_VERSION}"
    const val ANDROID = "io.insert-koin:koin-android:${LibVersion.KOIN_VERSION}"
    const val ANDROID_COMPAT = "io.insert-koin:koin-android-compat:${LibVersion.KOIN_VERSION}"
    const val TEST = "io.insert-koin:koin-test:${LibVersion.KOIN_VERSION}"
    const val TEST_JUNIT4 = "io.insert-koin:koin-test-junit4:${LibVersion.KOIN_VERSION}"
}

object Tests {
    const val JUNIT = "junit:junit:${LibVersion.JUNIT_VERSION}"
    const val TEST_EXT_JUNIT = "androidx.test.ext:junit:${LibVersion.TEST_EXT_JUNIT_VERSION}"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:${LibVersion.TEST_ESPRESSO_VERSION}"
}