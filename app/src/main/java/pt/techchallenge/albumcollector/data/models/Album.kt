package pt.techchallenge.albumcollector.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Entity(tableName = "albums")
@Serializable
data class Album(
    val albumId: Int,
    @PrimaryKey val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)
