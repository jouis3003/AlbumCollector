package pt.techchallenge.albumcollector.ui.screens.listScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pt.techchallenge.albumcollector.data.models.Album
import pt.techchallenge.albumcollector.data.repository.AlbumRepository
import pt.techchallenge.albumcollector.data.wrapper.DataWrapper
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(private val albumRepository: AlbumRepository) :
    ViewModel() {

    private val _albums =
        MutableStateFlow(DataWrapper<List<Album>, Boolean, Exception>(isLoading = true))
    val albums: StateFlow<DataWrapper<List<Album>, Boolean, Exception>> = _albums

    init {
        loadAlbums()
    }

    private fun loadAlbums() {
        viewModelScope.launch {
            _albums.value = DataWrapper(data = null, isLoading = true, error = null)
            _albums.value = albumRepository.getAlbums()
        }
    }
}