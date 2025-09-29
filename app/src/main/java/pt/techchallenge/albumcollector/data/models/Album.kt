package pt.techchallenge.albumcollector.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Entity(tableName = "albums")
@Serializable
data class Album(
    @SerializedName("albumId") val albumId: Int?,
    @PrimaryKey @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String?
)
