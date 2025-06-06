package com.aritra.medsync.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import com.aritra.medsync.R
import com.aritra.medsync.domain.model.Medication
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utils {

    fun Medication.getMedicationItem(): List<Pair<String, String>> {

        val medicationItems = mutableListOf<Pair<String, String>>()

        medicationItems.add(Pair("Medication Name", medicineName))
        medicationItems.add(Pair("Medication Dose", pillsAmount + " " + getMedicineUnit(medicineType)))

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

    fun greetingText(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        return when (hour) {
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

    @Composable
    fun getMedicineImage(medicineType: String): Painter {
        return when (medicineType) {
            "TABLET" -> painterResource(id = R.drawable.pill)
            "CAPSULE" -> painterResource(id = R.drawable.capsule)
            "SYRUP" -> painterResource(id = R.drawable.amp)
            "INHALER" -> painterResource(id = R.drawable.inahler)
            else -> painterResource(id = R.drawable.ic_launcher_foreground) // TODO: Need to change the image
        }
    }

    @Composable
    fun formatDateHeader(date: String): String {
        return try {
            // Parse the input date string "yyyy-MM-dd"
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val parsedDate = inputFormat.parse(date) ?: return date

            // Format the day with ordinal suffix (e.g., "4th")
            val dayFormat = SimpleDateFormat("d", Locale.getDefault())
            val day = dayFormat.format(parsedDate).toInt()
            val dayWithSuffix = day.toString() + getOrdinalSuffix(day)

            // Format the month and year
            val monthYearFormat = SimpleDateFormat("MMMM, yyyy", Locale.getDefault())
            val monthYear = monthYearFormat.format(parsedDate)

            // Combine to get "4th April, 2025"
            "$dayWithSuffix $monthYear"
        } catch (e: Exception) {
            date // Fallback to raw date string if parsing fails
        }
    }

    fun getOrdinalSuffix(day: Int): String {
        return when {
            day in 11..13 -> "th" // Special case for 11th, 12th, 13th
            day % 10 == 1 -> "st"
            day % 10 == 2 -> "nd"
            day % 10 == 3 -> "rd"
            else -> "th"
        }
    }
}