package com.h3r3t1c.wearunitconverter.ui.compose.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.util.lerp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumnDefaults
import androidx.wear.compose.foundation.lazy.ScalingParams
import androidx.wear.compose.material.ChipDefaults
import kotlin.math.ceil
import kotlin.math.sqrt

@Composable
public fun rememberResponsiveColumnPadding(
    first: ColumnItemType = ItemType.Unspecified,
    last: ColumnItemType = ItemType.Unspecified,
    horizontalPercent: Float = 0.052f,
): PaddingValues {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp

    val horizontalPadding = (screenWidthDp * horizontalPercent).ceilPx()

    return PaddingValues(
        top = first.topPadding(horizontalPercent).ceilPx(),
        bottom = last.bottomPadding(horizontalPercent).ceilPx(),
        start = horizontalPadding,
        end = horizontalPadding,
    )
}

internal val Padding12Pct = 0.1248f
internal val Padding14Pct = 0.1456f
internal val Padding16Pct = 0.1664f
internal val Padding20Pct = 0.2083f
internal val Padding21Pct = 0.2188f
internal val Padding31Pct = 0.3646f

internal fun calculateVerticalOffsetForChip(
    viewportDiameter: Float,
    horizontalPaddingPercent: Float,
): Dp {
    val childViewHeight: Float = ChipDefaults.Height.value
    val childViewWidth: Float = viewportDiameter * (1.0f - (2f * horizontalPaddingPercent))
    val radius = viewportDiameter / 2f
    return (
            radius -
                    sqrt(
                        (radius - childViewHeight + childViewWidth * 0.5f) * (radius - childViewWidth * 0.5f),
                    ) -
                    childViewHeight * 0.5f
            ).dp
}

fun responsiveScalingParams(screenWidthDp: Float): ScalingParams {
    val sizeRatio =
        ((screenWidthDp - 192) / (233 - 192).toFloat()).coerceIn(0f, 1.5f)
    val presetRatio = 0f

    val minElementHeight = lerp(0.2f, 0.157f, sizeRatio)
    val maxElementHeight =
        lerp(0.6f, 0.472f, sizeRatio).coerceAtLeast(minElementHeight)
    val minTransitionArea = lerp(0.35f, lerp(0.35f, 0.393f, presetRatio), sizeRatio)
    val maxTransitionArea = lerp(0.55f, lerp(0.55f, 0.593f, presetRatio), sizeRatio)

    val scalingParams = ScalingLazyColumnDefaults.scalingParams(
        minElementHeight = minElementHeight,
        maxElementHeight = maxElementHeight,
        minTransitionArea = minTransitionArea,
        maxTransitionArea = maxTransitionArea,
    )
    return scalingParams
}
enum class ItemType(
    val topPaddingPct: Float,
    val bottomPaddingPct: Float,
    val paddingCorrection: Dp = 0.dp,
) : ColumnItemType {
    Card(Padding21Pct, Padding31Pct),
    Chip(Padding21Pct, Padding31Pct),
    CompactChip(
        topPaddingPct = Padding12Pct,
        bottomPaddingPct = Padding20Pct,
        paddingCorrection = (-8).dp,
    ),
    Icon(Padding12Pct, Padding21Pct),
    MultiButton(Padding21Pct, Padding20Pct),
    SingleButton(Padding12Pct, Padding20Pct),
    Text(Padding16Pct, Padding31Pct),
    BodyText(Padding21Pct, Padding31Pct),
    Dialog(Padding14Pct, Padding20Pct),
    Unspecified(0f, 0f),
    ;

    @Composable
    override fun topPadding(horizontalPercent: Float): Dp {
        val configuration = LocalConfiguration.current
        val screenWidthDp = configuration.screenWidthDp.dp
        val screenHeightDp = configuration.screenHeightDp.dp

        return (
                if (this != Unspecified) {
                    topPaddingPct * screenHeightDp + paddingCorrection
                } else {
                    if (configuration.isScreenRound) {
                        calculateVerticalOffsetForChip(screenWidthDp.value, horizontalPercent)
                    } else {
                        32.dp
                    }
                }
                ).ceilPx()
    }

    @Composable
    override fun bottomPadding(horizontalPercent: Float): Dp {
        val configuration = LocalConfiguration.current
        val screenWidthDp = configuration.screenWidthDp.dp
        val screenHeightDp = configuration.screenHeightDp.dp
        return (
                if (this != Unspecified) {
                    bottomPaddingPct * screenHeightDp + paddingCorrection
                } else {
                    if (configuration.isScreenRound) {
                        calculateVerticalOffsetForChip(
                            screenWidthDp.value,
                            horizontalPercent,
                        ) + 10.dp
                    } else {
                        0.dp
                    }
                }
                ).ceilPx()
    }
}

@Composable
public fun padding(
    first: ItemType = ItemType.Unspecified,
    last: ItemType = ItemType.Unspecified,
    horizontalPercent: Float = 0.052f,
): @Composable () -> PaddingValues {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.toFloat()
    val screenHeightDp = configuration.screenHeightDp.toFloat()

    return {
        val height = screenHeightDp.dp
        val horizontalPadding = screenWidthDp.dp * horizontalPercent

        val topPadding = if (first != ItemType.Unspecified) {
            first.topPaddingPct * height + first.paddingCorrection
        } else {
            if (configuration.isScreenRound) {
                calculateVerticalOffsetForChip(screenWidthDp, horizontalPercent)
            } else {
                32.dp
            }
        }

        val bottomPadding = if (last != ItemType.Unspecified) {
            last.bottomPaddingPct * height + first.paddingCorrection
        } else {
            if (configuration.isScreenRound) {
                calculateVerticalOffsetForChip(
                    screenWidthDp,
                    horizontalPercent,
                ) + 10.dp
            } else {
                0.dp
            }
        }

        PaddingValues(
            top = topPadding,
            bottom = bottomPadding,
            start = horizontalPadding,
            end = horizontalPadding,
        )
    }
}
/**
 * Represents the types of items that can be placed in a Wear column and how to calculate an
 * optimal or safe padding.
 */
public interface ColumnItemType {
    /**
     * Calculates the padding for the top of the Column based on the provided horizontal padding.
     */
    @Composable
    public fun topPadding(horizontalPercent: Float): Dp

    /**
     * Calculates the padding for the bottom of the Column based on the provided horizontal padding.
     */
    @Composable
    public fun bottomPadding(horizontalPercent: Float): Dp

    companion object {
        val Button: ColumnItemType
            get() = ItemType.Chip

        val ListHeader: ColumnItemType
            get() = ItemType.Text

        val BodyText: ColumnItemType
            get() = ItemType.BodyText

        val Card: ColumnItemType
            get() = ItemType.Card

        val IconButton: ColumnItemType
            get() = ItemType.SingleButton

        val ButtonRow: ColumnItemType
            get() = ItemType.MultiButton
    }
}

@Composable
internal fun Dp.ceilPx(): Dp {
    val density = LocalDensity.current

    return with(density) {
        ceil(this@ceilPx.toPx()).toDp()
    }
}