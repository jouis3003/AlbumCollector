# AlbumCollector

This project was developed using Android Studio Narwhal 3 Feature Drop | 2025.1.3
When testing use the same or a newer version in order to avoid any compatibility issues with AGP.

In this README file I will present the justifications for the choices made by me in the project

Jetpack Compose over XML layout files

Jetpack Compose allows me to use:
    - Declarative UI Programming - this leads to less boilerplate as with XML you would often need to have multiple files/classes for a single view (for example - RecyclerView)
    - Type Safety - the whole code is done in Kotlin
    - Compile-time validation for UI - XML often provides almost no compile-time validation causing runtime crashes
    - Better Performance - Compose only updates parts of the UI that actually need to change while XML often requires the whole hierarchy to update
    - Very easy integration with Material3
    - Future-Proof - Google is investing heavily in Compose and it's bound to be the future of Android UI development


Architecture 

I've decided to use an architecture based on the Clean Architecture Pattern and the MVVM Pattern.

The project will have 3 main packages: data, di and ui


Added libraries

- MaterialIcons will allow me to have access to a broader range of material icons. (https://developer.android.com/reference/kotlin/androidx/compose/material/icons/package-summary)
- Hilt for dependency injection - Hilt is Google's recommended DI library for Jetpack Compose. (https://developer.android.com/training/dependency-injection#hilt)
- Coroutines for asynchronous programming - Kotlin's Coroutines provide a clean and efficient way to handle asynchronous operations. I will mainly use them for network requests and database operations. (https://developer.android.com/kotlin/coroutines)
- Navigation for navigation between screens - Jetpack Navigation provides type-safe navigation between screens and compile-time route validation. (https://developer.android.com/develop/ui/compose/navigation)
- Coil for image loading - Coil allows me to load images during the app's runtime. (https://coil-kt.github.io/coil/)
- Retrofit for networking - Retrofit is a type-safe HTTP client for Android that simplifies API calls with automatic JSON parsing. (https://github.com/square/retrofit)
- Mockk for testing - Mockk is a mocking library with Native Kotlin support. (https://github.com/mockk/mockk)
