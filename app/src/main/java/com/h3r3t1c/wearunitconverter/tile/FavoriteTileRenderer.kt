package com.h3r3t1c.wearunitconverter.tile

import android.content.ComponentName
import android.content.Context
import androidx.wear.protolayout.ActionBuilders
import androidx.wear.protolayout.ColorBuilders
import androidx.wear.protolayout.DimensionBuilders.dp
import androidx.wear.protolayout.DimensionBuilders.expand
import androidx.wear.protolayout.DimensionBuilders.wrap
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ModifiersBuilders
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.protolayout.material3.ButtonDefaults.filledTonalButtonColors
import androidx.wear.protolayout.material3.MaterialScope
import androidx.wear.protolayout.material3.Typography
import androidx.wear.protolayout.material3.buttonGroup
import androidx.wear.protolayout.material3.icon
import androidx.wear.protolayout.material3.materialScope
import androidx.wear.protolayout.material3.primaryLayout
import androidx.wear.protolayout.material3.text
import androidx.wear.protolayout.material3.textEdgeButton
import androidx.wear.protolayout.modifiers.clickable
import androidx.wear.protolayout.types.LayoutColor
import androidx.wear.protolayout.types.layoutString
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.tooling.preview.Preview
import androidx.wear.tiles.tooling.preview.TilePreviewData
import androidx.wear.tooling.preview.devices.WearDevices
import com.h3r3t1c.wearunitconverter.R
import com.h3r3t1c.wearunitconverter.activities.ConvertActivity
import com.h3r3t1c.wearunitconverter.activities.MainActivity
import com.h3r3t1c.wearunitconverter.database.data.FavoriteConversion
import com.h3r3t1c.wearunitconverter.ui.WearStyleHelper
import com.h3r3t1c.wearunitconverter.util.CategoryHelper
import eu.hansolo.unit.converter.Converter


private const val ICON_ONE = "ic_one"
private const val ICON_TWO = "ic_two"
private const val ICON_THREE = "ic_three"
private const val ICON_FOUR = "ic_four"
private const val ARROW = "➡"
private val icons = listOf(
    ICON_ONE,
    ICON_TWO,
    ICON_THREE,
    ICON_FOUR
)

fun resources(requestParams: RequestBuilders.ResourcesRequest, favs: List<FavoriteConversion>): ResourceBuilders.Resources {
    val resources = ResourceBuilders.Resources.Builder()
    resources.setVersion(requestParams.version)
    if(favs.isEmpty()){
        resources.addIdToImageMapping(
            ICON_ONE,
            ResourceBuilders.ImageResource.Builder().setAndroidResourceByResId(
                ResourceBuilders.AndroidImageResourceByResId.Builder()
                    .setResourceId(R.drawable.app_icon)
                    .build()
            ).build()
        )
    }else {
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
    }

    return resources.build()
}
fun tile(context: Context, requestParams: RequestBuilders.TileRequest, favs: List<FavoriteConversion> ): TileBuilders.Tile {
    return TileBuilders.Tile.Builder()
        .setResourcesVersion(if(favs.isEmpty()) "none" else favs.joinToString { it.type.name})
        .setTileTimeline(TimelineBuilders.Timeline.fromLayoutElement(buildLayout(context, requestParams, favs)))
        .build()
}

fun buildLayout(context: Context, requestParams: RequestBuilders.TileRequest, favs: List<FavoriteConversion>): LayoutElementBuilders.LayoutElement{
    val isLarge = WearStyleHelper.isLargeScreen(context)
    val box = LayoutElementBuilders.Box.Builder().setWidth(expand()).setHeight(expand()).setHorizontalAlignment(LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER).setVerticalAlignment(LayoutElementBuilders.VERTICAL_ALIGN_TOP)
    box.addContent(
        PlatformTime.buildTime(context)
    )
    box.addContent(
        materialScope(context, requestParams.deviceConfiguration){
            primaryLayout(
                titleSlot = if(favs.isEmpty() || favs.size == 1 || favs.size == 3 || (favs.size == 2 && isLarge)) ({
                    text(context.getString(R.string.favorites).layoutString, scalable = false)
                })else null,
                mainSlot = {
                    when (favs.size) {
                        1 -> {
                            singleFavorite(context, favs[0])
                        }
                        2 -> {
                            twoFavorite(context, favs)
                        }
                        3 -> {
                            threeFavorites(context, favs)
                        }
                        4 -> {
                            fourFavorites(context, favs)
                        }
                        else -> {
                            noFavorites(context)
                        }
                    }
                },
                bottomSlot = if(favs.isNotEmpty()) ({
                        textEdgeButton(
                            onClick = clickable(
                                action = ActionBuilders.launchAction(ComponentName(context, MainActivity::class.java))
                            ),
                            colors = filledTonalButtonColors(),
                        ) {
                            text(
                                text = context.getString(R.string.more).layoutString
                            )
                        }
                    })else null

            )
        }
    )
    return box.build()
}

private fun MaterialScope.noFavorites(context: Context): LayoutElementBuilders.LayoutElement{
    val isLarge = WearStyleHelper.isLargeScreen(context)
    val iconSize = if(isLarge) 40f else 32f
    val box = LayoutElementBuilders.Box.Builder()
        .setWidth(expand())
        .setHeight(expand())
        .setVerticalAlignment(LayoutElementBuilders.VERTICAL_ALIGN_CENTER)
        .setHorizontalAlignment(LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER)

        .setModifiers(
            ModifiersBuilders.Modifiers.Builder()
                .setBackground(
                    ModifiersBuilders.Background.Builder()
                        .setCorner(shapes.large)
                        .setColor(ColorBuilders.argb(context.getColor(R.color.ic_launcher_background)))
                        .build()
                )
                .setPadding(
                    ModifiersBuilders.Padding.Builder()
                        .setAll(dp(4f))
                        .build()
                )
                .setClickable(
                    clickable(action =  ActionBuilders.launchAction(ComponentName(context, MainActivity::class.java)))
                )
                .build()
        )
    val col = LayoutElementBuilders.Column.Builder().setWidth(expand()).setHeight(wrap())
        .setHorizontalAlignment(LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER)


    col.addContent(
        icon(
            ICON_ONE,
            width = dp(iconSize),
            height = dp(iconSize),
            tintColor = LayoutColor(0xffffffff.toInt())

        )
    )
    col.addContent(
        LayoutElementBuilders.Spacer.Builder().setWidth(dp(6f)).setHeight(dp(6f)).build()
    )
    col.addContent(
        text(context.getString(R.string.no_favorites).layoutString, scalable = false, color = LayoutColor(0xffffffff.toInt()), typography = Typography.TITLE_MEDIUM)
    )
    col.addContent(
        text(context.getString(R.string.tap_to_add_favorites).layoutString, scalable = false, color = LayoutColor(0xffffffff.toInt()))
    )
    box.addContent(col.build())
    return box.build()
}
private fun MaterialScope.fourFavorites(context: Context, favs: List<FavoriteConversion>): LayoutElementBuilders.LayoutElement {
    val isLarge = WearStyleHelper.isLargeScreen(context)
    val col = LayoutElementBuilders.Column.Builder().setWidth(expand()).setHeight(expand())
    col.addContent(
        buttonGroup {
            buttonGroupItem {
                fourFavoritesCard(context, favs[0], isLarge)
            }
            buttonGroupItem {
                fourFavoritesCard(context, favs[1], isLarge)
            }
        }
    )
    col.addContent(
        LayoutElementBuilders.Spacer.Builder().setHeight(dp(4f)).build()
    )
    col.addContent(
        buttonGroup {
            buttonGroupItem {
                fourFavoritesCard(context, favs[2], isLarge)
            }
            buttonGroupItem {
                fourFavoritesCard(context, favs[3], isLarge)
            }
        }
    )
    return col.build()
}

private fun MaterialScope.fourFavoritesCard(context: Context, fav: FavoriteConversion, isLarge: Boolean) : LayoutElementBuilders.LayoutElement{
    val bkgColor = ColorBuilders.ColorProp.Builder(colorScheme.primaryContainer.staticArgb)
    if(colorScheme.primaryContainer.dynamicArgb!= null)
        bkgColor.setDynamicValue(colorScheme.primaryContainer.dynamicArgb!!)
    val col = LayoutElementBuilders.Column.Builder()

        .setHorizontalAlignment(LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER)

        .addContent(
            text(fav.from.UNIT.unitShort.layoutString, scalable = false, maxLines = 1, color = colorScheme.onPrimaryContainer)
        )
    if(isLarge)
        col.addContent(
            text("⬇".layoutString, scalable = false, settings = listOf(LayoutElementBuilders.FontSetting.weight(900)), color = colorScheme.onPrimaryContainer)
        )
    col.addContent(
            text(fav.to.UNIT.unitShort.layoutString, scalable = false, maxLines = 1, color = colorScheme.onPrimaryContainer)
    )


    return LayoutElementBuilders.Box.Builder()
        .setHeight(expand())
        .setWidth(expand())
        .setHorizontalAlignment(LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER)
        .setVerticalAlignment(LayoutElementBuilders.VERTICAL_ALIGN_CENTER)
        .setModifiers(
            ModifiersBuilders.Modifiers.Builder()
                .setBackground(
                    ModifiersBuilders.Background.Builder()
                        .setCorner(shapes.medium)
                        .setColor(bkgColor.build())
                        .build()
                )
                .setPadding(
                    ModifiersBuilders.Padding.Builder()
                        .setStart(dp(4f))
                        .setEnd(dp(4f))
                        .build()
                )
                .setClickable(
                    convertClick(fav, context)
                )
                .build()
        )
        .addContent(
            col.build()
        )
        .build()

}

private fun MaterialScope.threeFavorites(context: Context, favs: List<FavoriteConversion>): LayoutElementBuilders.LayoutElement{
    val isLarge = WearStyleHelper.isLargeScreen(context)
    val iconSize = if(isLarge) 26f else 24f
    val bkgColor = ColorBuilders.ColorProp.Builder(colorScheme.primaryContainer.staticArgb)
    if(colorScheme.primaryContainer.dynamicArgb!= null)
        bkgColor.setDynamicValue(colorScheme.primaryContainer.dynamicArgb!!)
    return buttonGroup {
        favs.forEachIndexed { index, fav ->
            buttonGroupItem {
                val col = LayoutElementBuilders.Column.Builder()

                    .setHorizontalAlignment(LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER)
                    .addContent(
                        icon(
                            icons[index],
                            width = dp(iconSize),
                            height = dp(iconSize),
                            tintColor = colorScheme.onPrimaryContainer
                        )
                    )
                    .addContent(
                        text(fav.from.UNIT.unitShort.layoutString, scalable = false, maxLines = 1, color = colorScheme.onPrimaryContainer)
                    )
                    .addContent(
                        text("⬇".layoutString, scalable = false, settings = listOf(LayoutElementBuilders.FontSetting.weight(900)), color = colorScheme.onPrimaryContainer)
                    )
                    .addContent(
                        text(fav.to.UNIT.unitShort.layoutString, scalable = false, maxLines = 1, color = colorScheme.onPrimaryContainer)
                    )
                    .build()
                LayoutElementBuilders.Box.Builder()
                    .setHeight(expand())
                    .setWidth(expand())
                    .setHorizontalAlignment(LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER)
                    .setVerticalAlignment(LayoutElementBuilders.VERTICAL_ALIGN_CENTER)
                    .setModifiers(
                        ModifiersBuilders.Modifiers.Builder()
                            .setBackground(
                                ModifiersBuilders.Background.Builder()
                                    .setCorner(shapes.large)
                                    .setColor(bkgColor.build())
                                    .build()
                            )
                            .setPadding(
                                ModifiersBuilders.Padding.Builder()
                                    .setStart(dp(4f))
                                    .setEnd(dp(4f))
                                    .build()
                            )
                            .setClickable(convertClick(fav, context))
                            .build()
                    )
                    .addContent(
                        col
                    )
                    .build()
            }
        }
    }
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

private fun MaterialScope.fullLengthCard(context: Context, fav: FavoriteConversion, height: Float, icon: String = ICON_ONE): LayoutElementBuilders.LayoutElement{
    val bkgColor = ColorBuilders.ColorProp.Builder(colorScheme.primaryContainer.staticArgb)
    if(colorScheme.primaryContainer.dynamicArgb!= null)
        bkgColor.setDynamicValue(colorScheme.primaryContainer.dynamicArgb!!)
    val row = LayoutElementBuilders.Row.Builder()
        .setWidth(expand())
        .setHeight(dp(height))
        .setVerticalAlignment(LayoutElementBuilders.VERTICAL_ALIGN_CENTER)
        .setModifiers(
            ModifiersBuilders.Modifiers.Builder()
                .setBackground(
                    ModifiersBuilders.Background.Builder()
                        .setCorner(shapes.large)
                        .setColor(bkgColor.build())
                        .build()
                )
                .setPadding(
                    ModifiersBuilders.Padding.Builder()
                        .setStart(dp(6f))
                        .setEnd(dp(6f))
                        .build()
                )
                .setClickable(
                    convertClick(fav, context)
                )
                .build()
        )
    row.addContent(
        icon(
            icon,
            width = dp(26f),
            height = dp(26f),
            tintColor = colorScheme.onPrimaryContainer

        )
    )
    row.addContent(
        LayoutElementBuilders.Spacer.Builder().setWidth(dp(6f)).build()
    )
    row.addContent(
        text(
            "${fav.from.UNIT.unitShort} $ARROW ${fav.to.UNIT.unitShort}".layoutString,
            color = colorScheme.onPrimaryContainer,
            typography = Typography.TITLE_MEDIUM,
            maxLines = 1,
            scalable = false
        )
    )
    return row.build()
}

private fun convertClick(fav: FavoriteConversion, context: Context):ModifiersBuilders.Clickable{
    val extras = mapOf(
        ConvertActivity.EXTRA_TO to ActionBuilders.stringExtra(fav.to.name),
        ConvertActivity.EXTRA_FROM to ActionBuilders.stringExtra(fav.from.name),
        ConvertActivity.EXTRA_TYPE to ActionBuilders.stringExtra(fav.type.name)
    )
    return clickable(
        action = ActionBuilders.launchAction(ComponentName(context, ConvertActivity::class.java), extras)
    )
}

val favs = listOf(
    FavoriteConversion(0, Converter.Category.TEMPERATURE, Converter.UnitDefinition.FAHRENHEIT, Converter.UnitDefinition.CELSIUS, 0L),
    FavoriteConversion(0, Converter.Category.LENGTH, Converter.UnitDefinition.INCHES, Converter.UnitDefinition.FEET, 0L),
    //FavoriteConversion(0, Converter.Category.SPEED, Converter.UnitDefinition.MILES_PER_HOUR, Converter.UnitDefinition.KILOMETER_PER_HOUR, 0L),
    //FavoriteConversion(0, Converter.Category.MASS, Converter.UnitDefinition.POUND, Converter.UnitDefinition.KILOGRAM, 0L)
)

@Preview(device = WearDevices.SMALL_ROUND)
@Preview(device = WearDevices.LARGE_ROUND)
fun tilePreviewBattery(context: Context) = TilePreviewData({ resources(it, favs) }) {
    tile(context, it, favs)
}