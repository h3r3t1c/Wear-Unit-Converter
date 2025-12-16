package com.h3r3t1c.wearunitconverter.tile

import android.content.Context
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.tiles.SuspendingTileService
import com.h3r3t1c.wearunitconverter.ext.favsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalHorologistApi::class)
class FavoritesTile: SuspendingTileService() {

    companion object{
        fun refresh(context: Context){
            getUpdater(context).requestUpdate(FavoritesTile::class.java)
        }
    }

    override suspend fun tileRequest(requestParams: RequestBuilders.TileRequest): TileBuilders.Tile {
        val favs = withContext(Dispatchers.IO){
            favsDatabase.getFavoritesForTile()
        }
        return tile(this, requestParams, favs)
    }

    override suspend fun resourcesRequest(requestParams: RequestBuilders.ResourcesRequest): ResourceBuilders.Resources {
        val favs = withContext(Dispatchers.IO){
            favsDatabase.getFavoritesForTile()
        }
        return resources(requestParams, favs)
    }
}