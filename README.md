# AlbumCollector

In this README file I will present the justifications for the choices made by me in the project

First Decision - Jetpack Compose over XML layout files

Jetpack Compose allows me to use:
    - Declarative UI Programming - this leads to less boilerplate as with XML you would often need to have multiple files/classes for a single view (for example - RecyclerView)
    - Type Safety - the whole code is done in Kotlin
    - Compile-time validation for UI - XML often provides almost no compile-time validation causing runtime crashes
    - Better Performance - Compose only updates parts of the UI that actually need to change while XML often requires the whole hierarchy to update
    - Very easy integration with Material3
    - Future-Proof - Google is investing heavily in Compose and it's bound to be the future of Android UI development



Second Decision - Architecture 

I've decided to use an architecture based on the Clean Architecture Pattern and the MVVM Pattern.

The project will have 3 main packages: data, di and ui


