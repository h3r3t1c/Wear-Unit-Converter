package com.h3r3t1c.wearunitconverter.tile

import android.content.Context
import android.text.format.DateFormat
import androidx.wear.protolayout.ColorBuilders
import androidx.wear.protolayout.DimensionBuilders
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ModifiersBuilders
import androidx.wear.protolayout.TypeBuilders
import androidx.wear.protolayout.TypeBuilders.StringLayoutConstraint
import androidx.wear.protolayout.expression.DynamicBuilders.DynamicInstant
import androidx.wear.protolayout.expression.DynamicBuilders.DynamicInt32.IntFormatter
import androidx.wear.protolayout.expression.DynamicBuilders.DynamicString
import androidx.wear.protolayout.material.Text
import androidx.wear.protolayout.material.Typography
import java.time.ZoneId
object PlatformTime {
    fun buildTime(context: Context): LayoutElementBuilders.LayoutElement {

        val dt = if(DateFormat.is24HourFormat(context)){
            DynamicInstant.platformTimeWithSecondsPrecision().getHour(ZoneId.systemDefault()).format(IntFormatter.Builder().setMinIntegerDigits(2).build()).concat(
                DynamicString.constant(":")).concat(
                DynamicInstant.platformTimeWithSecondsPrecision().getMinute(
                    ZoneId.systemDefault()).format(IntFormatter.Builder().setMinIntegerDigits(2).build()))
        }else{
            DynamicString.onCondition(
                DynamicInstant.platformTimeWithSecondsPrecision().getHour(
                    ZoneId.systemDefault()).rem(12).eq(0))
                .use(
                    DynamicString.constant("12").concat(DynamicString.constant(":")).concat(
                        DynamicInstant.platformTimeWithSecondsPrecision().getMinute(ZoneId.systemDefault()).format(
                            IntFormatter.Builder().setMinIntegerDigits(2).build())))
                .elseUse(
                    DynamicInstant.platformTimeWithSecondsPrecision().getHour(ZoneId.systemDefault()).rem(12).format().concat(
                        DynamicString.constant(":")).concat(
                        DynamicInstant.platformTimeWithSecondsPrecision().getMinute(
                            ZoneId.systemDefault()).format(IntFormatter.Builder().setMinIntegerDigits(2).build())))
        }

        return Text.Builder(
            context,
            TypeBuilders.StringProp.Builder("   ").setDynamicValue(dt).build(),
            StringLayoutConstraint.Builder(" 00:00 ").build()
        )

            .setOverflow(LayoutElementBuilders.TEXT_OVERFLOW_UNDEFINED)
            .setModifiers(
                ModifiersBuilders.Modifiers.Builder()
                    .setPadding(
                        ModifiersBuilders.Padding.Builder().setTop(
                            DimensionBuilders.dp(4f)).build()).build())
            .setTypography(Typography.TYPOGRAPHY_CAPTION1)
            .setColor(ColorBuilders.argb(0xffffffff.toInt())).build()
    }
}