# AlbumCollector

This project was developed using Android Studio Narwhal 3 Feature Drop | 2025.1.3
When testing use the same or a newer version in order to avoid any compatibility issues with AGP.

In this README file I will present the justifications for the choices made by me in the project
## Project Overview

This project was developed using modern Android development practices, using a clean architecture approach with Jetpack Compose UI. 
This README will present the technical decisions and justifications for the choices made throughout the project.

## Architecture Decisions

### Clean Architecture + MVVM Pattern

I've implemented a hybrid architecture combining Clean Architecture principles with the MVVM pattern:

**Package Structure:**
- `data` - Data layer handling repositories, network calls, and database operations
- `di` - Dependency injection modules using Hilt
- `ui` - UI layer with ViewModels, Composables and navigation

**Benefits:**
- **Separation of Concerns**: Each layer has a single responsibility
- **Testability**: Business logic is isolated and easily testable
- **Maintainability**: Changes in one layer don't affect others
- **Scalability**: Easy to add new features without breaking existing code

### UI Technology Choice: Jetpack Compose over XML

**Justifications:**
- **Declarative UI Programming**: Reduces boilerplate compared to XML
- **Type Safety**: Everything is written in Kotlin, eliminating XML-related runtime errors
- **Compile-time Validation**: Catches UI errors at compile time rather than runtime
- **Performance**: Compose's recomposition system only updates changed UI parts
- **Material3 Integration**: Seamless theming and component integration
- **Future-Proof**: Google's strategic direction for Android UI development

## Library Choices & Justifications

### Dependency Injection
**Hilt**
- Google's recommended DI solution for Android
- Simplified Compose integration with `hiltViewModel()`
- Simplified testing with `@HiltAndroidTest`
- Reduces boilerplate compared to manual DI or Dagger

### Asynchronous Programming
**Kotlin Coroutines**
- Native Kotlin asynchronous programming
- Perfect integration with Compose's `collectAsState()`
- Structured concurrency prevents memory leaks
- Clean error handling with try-catch blocks
- Essential for network calls and database operations
- Superior to RxJava for Kotlin projects

### Navigation
**Navigation Compose**
- Type-safe navigation between screens
- Compile-time route validation
- Deep linking support (it was not used in this project)
- Seamless integration with Compose lifecycle

**Hilt Navigation Compose**
- Automatic ViewModel injection with `hiltViewModel()`
- Scoped ViewModels to navigation destinations
- Eliminates manual ViewModel factory creation

**Lifecycle ViewModel Compose**
- Automatic ViewModel lifecycle management
- Survives configuration changes
- Integration with Compose state management
- Reactive UI updates with `collectAsState()`

### Networking
**Retrofit**
- Industry standard for Android networking
- Type-safe API definitions with interfaces
- Automatic JSON parsing integration
- Excellent error handling and testing support
- Superior to manual HttpURLConnection

**Retrofit Gson Converter (3.0.0)**
- Automatic JSON serialization/deserialization
- Seamless integration with Retrofit
- Handles complex nested objects

**OkHttp**
- HTTP client underlying Retrofit
- Interceptor support for headers (User-Agent in `NetworkModule`)

### Database
**Room**
- Google's recommended persistence solution
- Compile-time SQL validation prevents runtime crashes
- Coroutines support with `Flow` for reactive data
- Migration support for schema changes
- Type-safe database operations

### Image Loading
**Coil**
- Modern Kotlin-first image loading library
- Native Compose integration with `AsyncImage`
- Automatic memory and disk caching
- Lightweight compared to Glide or Picasso
- Coroutines-based for better async handling

### UI Components
**Material Icons**
- Access to comprehensive Material Design icon set
- Vector-based icons able to render at any size
- Consistent with Material Design guidelines
- Reduces need for custom icon assets

### Testing Libraries
**MockK**
- Native Kotlin mocking library
- Better syntax than Mockito for Kotlin
- Support for coroutines and suspend functions
- Relaxed mocks reduce boilerplate

**MockWebServer**
- OkHttp's official testing server
- Mock HTTP responses for network testing
- Integration testing for API calls
- Better than mocking Retrofit directly

**Coroutines Test**
- Testing utilities for coroutines
- `runTest` for testing suspend functions
- `TestDispatcher` for controlled execution
- Essential for testing async operations

**Navigation Testing Android**
- Testing utilities for Navigation Compose
- Verify navigation behavior in isolation
- Test deep links and navigation arguments (not used in this project)

**Room Testing**
- In-memory database for testing
- Fast test execution without file I/O
- Real database behavior without persistence

**Hilt Android Testing**
- Testing support for Hilt dependency injection
- Integration with Android instrumentation tests

## Design System & Theming

**Material Theme Builder Integration**
- Generated theme using [Material Theme Builder](https://material-foundation.github.io/material-theme-builder/)
- Creates consistent `Color.kt`, `Theme.kt`, and `Type.kt` files
- Ensures Material Design 3 compliance
- Provides both light and dark theme variants
- Maintains accessibility standards with proper contrast ratios

## Testing Strategy

### Unit Tests
**Scope**: All classes in the `data` package (except DAOs) + ViewModels from `ui` package
- **Data Layer**: Repository implementations, network mappers, utility classes
- **ViewModels**: Business logic, state management, user interactions
- **Benefits**: Fast execution, no Android dependencies, isolated testing

### Android Instrumentation Tests
**Scope**: Database classes + UI Composables
- **Database**: Room DAOs, database migrations, actual SQLite operations
- **UI Components**: Compose screens, navigation behavior, user interactions
- **Benefits**: Real Android environment, actual database operations, UI rendering validation

**Testing Philosophy**: Test components in their natural environment - ViewModels don't need Android context (unit tests), while databases and UI require Android runtime (instrumentation tests).