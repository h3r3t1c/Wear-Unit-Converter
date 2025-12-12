package com.h3r3t1c.wearunitconverter.ui.compose.settings

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.wear.remote.interactions.RemoteActivityHelper
import com.h3r3t1c.wearunitconverter.BuildConfig
import com.h3r3t1c.wearunitconverter.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class SettingsViewModel(context: Context): ViewModel() {

    val options = mutableStateListOf<SettingsOption>()
    var showContinueOnPhoneDialog by mutableStateOf(false)

    init {
        options.add(SettingsOption.Header(R.string.settings))
        options.add(SettingsOption.ClickOption(R.string.max_decimal_places, R.drawable.ic_decimal_places){

        })
        options.add(SettingsOption.Header(R.string.support))
        options.add(SettingsOption.ClickOption(R.string.support_email, R.drawable.ic_email){
            openRemoteUrl(context, "mailto:th3h3r3t1c@gmail.com")
        })
        options.add(SettingsOption.ClickOption(R.string.play_store_page, R.drawable.ic_google_play){
            openLocalUrl(context,"https://play.google.com/store/apps/details?id=${context.packageName}")
        })
        options.add(SettingsOption.ClickOption(R.string.more_apps, R.drawable.ic_google_play){
            openRemoteUrl(context,"https://play.google.com/store/apps/developer?id=Thomas+Otero")
        })
        options.add(SettingsOption.ClickOption(R.string.app_github, R.drawable.ic_github){
            openRemoteUrl(context, "https://github.com/h3r3t1c/Wear-Unit-Converter")
        })
        options.add(SettingsOption.Info(R.string.version, "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})", R.drawable.ic_info))
    }

    private fun openLocalUrl(context: Context, url: String){
        try{
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        }catch (_: Exception){
            Toast.makeText(context, R.string.cannot_open, Toast.LENGTH_SHORT).show()
        }
    }
    private fun openRemoteUrl(context: Context, url: String){
        showContinueOnPhoneDialog = true
        viewModelScope.launch(Dispatchers.Default) {
            try{
                val remoteActivityHelper = RemoteActivityHelper(context, Executors.newSingleThreadExecutor())
                remoteActivityHelper.startRemoteActivity(
                    Intent(Intent.ACTION_VIEW)
                        .addCategory(Intent.CATEGORY_BROWSABLE)
                        .setData(
                            url.toUri()
                        ), null
                )
            }catch (_: Exception){}
        }
    }

    companion object{
        fun getFactory(context: Context,): ViewModelProvider.Factory{
            val factory : ViewModelProvider.Factory = viewModelFactory {
                initializer {
                    SettingsViewModel(context)
                }
            }
            return factory
        }
    }
}