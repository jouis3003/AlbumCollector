package pt.techchallenge.albumcollector.data.wrapper

data class DataWrapper<T, Boolean, E: Exception>(
    val data: T? = null,
    val isLoading: Boolean? = null,
    val error: E? = null
)