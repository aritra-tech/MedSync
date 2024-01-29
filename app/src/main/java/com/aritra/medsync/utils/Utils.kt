package com.aritra.medsync.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.toLowerCase
import androidx.core.content.ContextCompat
import com.aritra.medsync.R
import com.aritra.medsync.domain.model.Medication
import java.time.LocalTime
import java.time.ZoneId
import java.util.Locale

object Utils {

    fun Medication.getMedicationItem(): List<Pair<String, String>> {

        val medicationItems = mutableListOf<Pair<String, String>>()

        medicationItems.add(Pair("Medication Name", medicineName))
        medicationItems.add(Pair("Medication Dose", pillsAmount + " " + getMedicineUnit(medicineType)))
        medicationItems.add(Pair("Medication Frequency", pillsFrequency))

        return medicationItems
    }

    fun getMedicineUnit(medicineType: String): String {
        return when (medicineType) {
            "TABLET" -> "Tablet"
            "CAPSULE" -> "Capsule"
            "SYRUP" -> "mL"
            "INHALER" -> "Puffs"
            else -> "None"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun greetingText(): String {
        return when (LocalTime.now(ZoneId.systemDefault()).hour) {
            in 5..11 -> "Good morning,"
            in 12..16 -> "Good afternoon,"
            in 17..20 -> "Good evening,"
            else -> "Good night,"
        }
    }

    fun mailTo(context: Context) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(context.resources.getString(R.string.mailTo))
        context.startActivity(openURL)
    }

    fun inviteFriends(context: Context) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            ContextCompat.getString(context, R.string.referral_message)
        )
        sendIntent.type = "text/plain"
        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }
}