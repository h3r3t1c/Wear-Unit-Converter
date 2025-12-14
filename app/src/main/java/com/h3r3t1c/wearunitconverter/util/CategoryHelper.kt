package com.h3r3t1c.wearunitconverter.util

import android.content.Context
import androidx.compose.runtime.Stable
import com.h3r3t1c.wearunitconverter.R
import eu.hansolo.unit.converter.Converter

object CategoryHelper {

    @Stable
    fun getDisplayName(context: Context, category: Converter.Category): String {
        return when (category) {
            Converter.Category.ACCELERATION -> context.getString(R.string.acceleration)
            Converter.Category.ANGLE -> context.getString(R.string.angle)
            Converter.Category.AREA -> context.getString(R.string.area)
            Converter.Category.CSS_UNITS -> context.getString(R.string.css_units)
            Converter.Category.CURRENT -> context.getString(R.string.current)
            Converter.Category.DATA -> context.getString(R.string.data)
            Converter.Category.ELECTRIC_CHARGE -> context.getString(R.string.electric_charge)
            Converter.Category.ENERGY -> context.getString(R.string.energy)
            Converter.Category.FORCE -> context.getString(R.string.force)
            Converter.Category.LENGTH -> context.getString(R.string.length)
            Converter.Category.LUMINANCE -> context.getString(R.string.luminance)
            Converter.Category.LUMINOUS_FLUX -> context.getString(R.string.luminous_flux)
            Converter.Category.MASS -> context.getString(R.string.mass)
            Converter.Category.PRESSURE -> context.getString(R.string.pressure)
            Converter.Category.SPEED -> context.getString(R.string.speed)
            Converter.Category.TEMPERATURE -> context.getString(R.string.temperature)
            Converter.Category.TEMPERATURE_GRADIENT -> context.getString(R.string.temperature_gradient)
            Converter.Category.TIME -> context.getString(R.string.time)
            Converter.Category.TORQUE -> context.getString(R.string.torque)
            Converter.Category.VOLUME -> context.getString(R.string.volume)
            Converter.Category.VOLTAGE -> context.getString(R.string.voltage)
            Converter.Category.WORK -> context.getString(R.string.work)
            Converter.Category.BLOOD_GLUCOSE -> context.getString(R.string.blood_glucose)
            else -> category.name
        }
    }
    @Stable
    fun getIconForCategory(category: Converter.Category): Int {
        return when (category) {
            Converter.Category.ACCELERATION -> R.drawable.ic_acceleration
            Converter.Category.ANGLE -> R.drawable.ic_angle
            Converter.Category.AREA -> R.drawable.ic_area
            Converter.Category.CSS_UNITS -> R.drawable.ic_css
            Converter.Category.CURRENT -> R.drawable.ic_current
            Converter.Category.DATA -> R.drawable.ic_data
            Converter.Category.ELECTRIC_CHARGE -> R.drawable.ic_electric_charge
            Converter.Category.ENERGY -> R.drawable.ic_energy
            Converter.Category.FORCE -> R.drawable.ic_force
            Converter.Category.LENGTH -> R.drawable.ic_length
            Converter.Category.LUMINANCE -> R.drawable.ic_luminance
            Converter.Category.LUMINOUS_FLUX -> R.drawable.ic_luminous_flux
            Converter.Category.MASS -> R.drawable.ic_mass
            Converter.Category.PRESSURE -> R.drawable.ic_pressure
            Converter.Category.SPEED -> R.drawable.ic_speed
            Converter.Category.TEMPERATURE -> R.drawable.ic_temperature
            Converter.Category.TEMPERATURE_GRADIENT -> R.drawable.ic_temperature_gradient
            Converter.Category.TIME -> R.drawable.ic_time
            Converter.Category.TORQUE -> R.drawable.ic_torque
            Converter.Category.VOLUME -> R.drawable.ic_volume
            Converter.Category.VOLTAGE -> R.drawable.ic_voltage
            Converter.Category.WORK -> R.drawable.ic_work
            Converter.Category.BLOOD_GLUCOSE -> R.drawable.ic_blood_glucose
            else -> R.drawable.app_icon
        }
    }
}