package pt.techchallenge.albumcollector.ui.screens.listScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pt.techchallenge.albumcollector.data.models.Album
import pt.techchallenge.albumcollector.data.repositories.AlbumRepository
import pt.techchallenge.albumcollector.data.utils.NetworkUtils
import pt.techchallenge.albumcollector.data.wrapper.DataWrapper
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val albumRepository: AlbumRepository
) :
    ViewModel() {

    private val _albums =
        MutableStateFlow(DataWrapper<List<Album>, Boolean, Exception>(isLoading = true))
    val albums: StateFlow<DataWrapper<List<Album>, Boolean, Exception>> = _albums

    init {
        loadAlbums()
    }

    private fun loadAlbums() {
        viewModelScope.launch {
            if (NetworkUtils.isNetworkAvailable(context)) {
                val result = albumRepository.refreshAlbumsFromNetwork()
                if (result.isFailure) {
                    _albums.value = DataWrapper(
                        data = null,
                        isLoading = false,
                        error = result.exceptionOrNull() as? Exception
                    )
                    return@launch
                }
            }
            albumRepository.getAlbumsFromDatabase().collect { albumList ->
                _albums.value = DataWrapper(data = albumList, isLoading = false, error = null)
            }
        }
    }
}