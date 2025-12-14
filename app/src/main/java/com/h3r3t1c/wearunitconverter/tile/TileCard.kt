package com.h3r3t1c.wearunitconverter.tile

import android.content.Context
import androidx.wear.protolayout.ColorBuilders
import androidx.wear.protolayout.DimensionBuilders.dp
import androidx.wear.protolayout.DimensionBuilders.expand
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ModifiersBuilders
import androidx.wear.protolayout.material3.MaterialScope
import androidx.wear.protolayout.material3.Typography
import androidx.wear.protolayout.material3.icon
import androidx.wear.protolayout.material3.text
import androidx.wear.protolayout.types.layoutString
import com.h3r3t1c.wearunitconverter.database.data.FavoriteConversion
import com.h3r3t1c.wearunitconverter.tile.FavoritesTile.Companion.ARROW
import com.h3r3t1c.wearunitconverter.tile.FavoritesTile.Companion.ICON_ONE

fun MaterialScope.fullLengthCard(context: Context, fav: FavoriteConversion, height: Float, icon: String = ICON_ONE): LayoutElementBuilders.LayoutElement{
    val row = LayoutElementBuilders.Row.Builder()
        .setWidth(expand())
        .setHeight(dp(height))
        .setVerticalAlignment(LayoutElementBuilders.VERTICAL_ALIGN_CENTER)
        .setModifiers(
            ModifiersBuilders.Modifiers.Builder()
                .setBackground(
                    ModifiersBuilders.Background.Builder()
                        .setCorner(shapes.large)
                        .setColor(ColorBuilders.argb(colorScheme.primaryContainer.staticArgb))
                        .build()
                )
                .setPadding(
                    ModifiersBuilders.Padding.Builder()
                        .setStart(dp(6f))
                        .setEnd(dp(6f))
                        .build()
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
            typography = Typography.TITLE_MEDIUM
        )
    )
    return row.build()
}