package com.h3r3t1c.wearunitconverter.tile

import android.content.Context
import androidx.wear.protolayout.DimensionBuilders.dp
import androidx.wear.protolayout.DimensionBuilders.expand
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.protolayout.material3.MaterialScope
import androidx.wear.protolayout.material3.materialScope
import androidx.wear.protolayout.material3.primaryLayout
import androidx.wear.protolayout.material3.text
import androidx.wear.protolayout.material3.textEdgeButton
import androidx.wear.protolayout.modifiers.clickable
import androidx.wear.protolayout.types.layoutString
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.tooling.preview.Preview
import androidx.wear.tiles.tooling.preview.TilePreviewData
import androidx.wear.tooling.preview.devices.WearDevices
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.tiles.SuspendingTileService
import com.h3r3t1c.wearunitconverter.R
import com.h3r3t1c.wearunitconverter.database.data.FavoriteConversion
import com.h3r3t1c.wearunitconverter.tile.FavoritesTile.Companion.ICON_TWO
import com.h3r3t1c.wearunitconverter.tile.FavoritesTile.Companion.icons
import com.h3r3t1c.wearunitconverter.ui.WearStyleHelper
import com.h3r3t1c.wearunitconverter.util.CategoryHelper
import eu.hansolo.unit.converter.Converter
import java.util.UUID

@OptIn(ExperimentalHorologistApi::class)
class FavoritesTile: SuspendingTileService() {

    companion object{
        const val ICON_ONE = "one"
        const val ICON_TWO = "two"
        const val ARROW = "➡"
        val icons = listOf(
            ICON_ONE,
            ICON_TWO
        )
    }

    override suspend fun tileRequest(requestParams: RequestBuilders.TileRequest): TileBuilders.Tile {
        return tile(this, requestParams, emptyList())
    }

    override suspend fun resourcesRequest(requestParams: RequestBuilders.ResourcesRequest): ResourceBuilders.Resources {
        return resources(requestParams, emptyList())
    }
}

private fun buildLayout(context: Context, requestParams: RequestBuilders.TileRequest): LayoutElementBuilders.LayoutElement{
    val box = LayoutElementBuilders.Box.Builder().setWidth(expand()).setHeight(expand()).setHorizontalAlignment(LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER).setVerticalAlignment(LayoutElementBuilders.VERTICAL_ALIGN_TOP)
    box.addContent(
        PlatformTime.buildTime(context)
    )
    box.addContent(
        materialScope(context, requestParams.deviceConfiguration){
            primaryLayout(
                mainSlot = {
                    if(favs.size == 1){
                        singleFavorite(context, favs[0])
                    }
                    else if(favs.size > 1){
                        twoFavorite(context, favs)
                    }
                    else{
                        LayoutElementBuilders.Box.Builder().build()
                    }
                },
                bottomSlot = {
                    textEdgeButton(
                        onClick = clickable(),
                    ){
                        text(
                            text = context.getString(R.string.more).layoutString
                        )
                    }
                }
            )
        }
    )
    return box.build()
}

private fun MaterialScope.twoFavorite(context: Context, favs: List<FavoriteConversion>): LayoutElementBuilders.LayoutElement{
    val isLarge = WearStyleHelper.isLargeScreen(context)
    val col = LayoutElementBuilders.Column.Builder().setWidth(expand())
    val height = if(isLarge) 52f else 48f
    col.addContent(
        fullLengthCard(context, favs[0], height)
    )
    col.addContent(
        LayoutElementBuilders.Spacer.Builder().setHeight(dp(4f)).build()
    )
    col.addContent(
        fullLengthCard(context, favs[1], height, ICON_TWO)
    )
    return col.build()
}

private fun MaterialScope.singleFavorite(context: Context, fav: FavoriteConversion): LayoutElementBuilders.LayoutElement{
    val isLarge = WearStyleHelper.isLargeScreen(context)
    return fullLengthCard(context, fav, if(isLarge) 62f else 56f)
}



private fun resources(requestParams: RequestBuilders.ResourcesRequest, favs: List<FavoriteConversion>): ResourceBuilders.Resources {
    val resources = ResourceBuilders.Resources.Builder()
    resources.setVersion(requestParams.version)
    favs.forEachIndexed { index, fav ->
        resources.addIdToImageMapping(
            icons[index],
            ResourceBuilders.ImageResource.Builder().setAndroidResourceByResId(
                ResourceBuilders.AndroidImageResourceByResId.Builder()
                    .setResourceId(CategoryHelper.getIconForCategory(fav.type))
                    .build()
            ).build()
        )
    }
    /*resources.addIdToImageMapping(
        ICON_EDGE_BUTTON,
        ResourceBuilders.ImageResource.Builder().setAndroidResourceByResId(
            ResourceBuilders.AndroidImageResourceByResId.Builder()
                .setResourceId(R.drawable.)
                .build()
        ).build()
    )*/
    return resources.build()
}
private fun tile(context: Context, requestParams: RequestBuilders.TileRequest, favs: List<FavoriteConversion> ): TileBuilders.Tile {
    return TileBuilders.Tile.Builder()
        .setResourcesVersion(UUID.randomUUID().toString())
        .setTileTimeline(TimelineBuilders.Timeline.fromLayoutElement(buildLayout(context, requestParams)))
        .build()
}


val favs = listOf(
    FavoriteConversion(0, Converter.Category.TEMPERATURE, Converter.UnitDefinition.FAHRENHEIT, Converter.UnitDefinition.CELSIUS),
    FavoriteConversion(0, Converter.Category.LENGTH, Converter.UnitDefinition.INCHES, Converter.UnitDefinition.FEET),

)

@Preview(device = WearDevices.SMALL_ROUND)
@Preview(device = WearDevices.LARGE_ROUND)
fun tilePreviewBattery(context: Context) = TilePreviewData({ resources(it, favs) }) {
    tile(context, it, favs)
}