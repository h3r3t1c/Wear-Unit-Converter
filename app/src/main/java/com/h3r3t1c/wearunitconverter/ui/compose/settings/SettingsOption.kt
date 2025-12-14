package com.h3r3t1c.wearunitconverter.ui.compose.settings

sealed class SettingsOption(val key: String) {
    class Header(val stringResource: Int) : SettingsOption(stringResource.toString())
    class Info(val stringResource: Int, val value: String, val icon :Int) : SettingsOption(stringResource.toString())
    class ClickOption(val stringResource: Int, val icon :Int, val click: () -> Unit) : SettingsOption(stringResource.toString())
    data class BoolOption(val stringResource: Int, val bool: Boolean, val mKey: String) : SettingsOption(mKey)
}
