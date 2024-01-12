package com.aritra.medsync.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.floor

@Preview
@Composable
fun TicketView(
    content: @Composable () -> Unit = {
        Text("Ticket View")
    }
) {
    Surface(
        color = Color.Transparent
    ){
        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(Color.White, shape = TicketShape(16f, 4f))
        ) {

            Box(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}

class TicketShape(
    private val teethWidthDp: Float,
    private val teethHeightDp: Float
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(Path().apply {

        moveTo(
            size.width * 0.99f,
            size.height * 0.01f
        )

        val teethHeightPx = teethHeightDp * density.density
        var fullTeethWidthPx = teethWidthDp * density.density
        var halfTeethWidthPx = fullTeethWidthPx / 2
        var currentDrawPositionX = size.width * 0.99f
        var teethBasePositionY = size.height * 0.01f + teethHeightPx
        val shapeWidthPx = size.width * 0.99f - size.width * 0.01f

        val teethCount = shapeWidthPx / fullTeethWidthPx
        val minTeethCount = floor(teethCount)

        // logic to find optimized count of teethes to fit the available width without overflowing
        // or underflowing teethes, by modifying the teeth width
        if (teethCount != minTeethCount) { // check to allow drawing if shape width is a multiple of teeth count
            val newTeethWidthPx = shapeWidthPx / minTeethCount
            fullTeethWidthPx = newTeethWidthPx
            halfTeethWidthPx = fullTeethWidthPx / 2
        }

        var drawnTeethCount = 1 // considering we will draw half of first and last teeth
        // statically we start with one teeth

        // draw half of first teeth
        lineTo(
            currentDrawPositionX - halfTeethWidthPx,
            teethBasePositionY + teethHeightPx
        )

        // draw remaining teethes
        while (drawnTeethCount < minTeethCount) {

            currentDrawPositionX -= halfTeethWidthPx

            // draw right half of teeth
            lineTo(
                currentDrawPositionX - halfTeethWidthPx,
                teethBasePositionY - teethHeightPx
            )

            currentDrawPositionX -= halfTeethWidthPx

            // draw left half of teeth
            lineTo(
                currentDrawPositionX - halfTeethWidthPx,
                teethBasePositionY + teethHeightPx
            )

            drawnTeethCount++
        }

        currentDrawPositionX -= halfTeethWidthPx

        // draw half of last teeth
        lineTo(
            currentDrawPositionX - halfTeethWidthPx,
            teethBasePositionY - teethHeightPx
        )

        // draw left edge
        lineTo(
            size.width * 0.01f,
            size.height * 0.99f
        )

        drawnTeethCount = 1
        teethBasePositionY = size.height * 0.99f - teethHeightPx
        currentDrawPositionX = size.width * 0.01f

        // draw half of first teeth
        lineTo(
            currentDrawPositionX,
            teethBasePositionY + teethHeightPx
        )

        lineTo(
            currentDrawPositionX + halfTeethWidthPx,
            teethBasePositionY - teethHeightPx
        )

        // draw remaining teethes
        while (drawnTeethCount < minTeethCount) {

            currentDrawPositionX += halfTeethWidthPx

            // draw left half of teeth
            lineTo(
                currentDrawPositionX + halfTeethWidthPx,
                teethBasePositionY + teethHeightPx
            )

            currentDrawPositionX += halfTeethWidthPx

            // draw right half of teeth
            lineTo(
                currentDrawPositionX + halfTeethWidthPx,
                teethBasePositionY - teethHeightPx
            )

            drawnTeethCount++
        }

        currentDrawPositionX += halfTeethWidthPx

        // draw half of last teeth
        lineTo(
            currentDrawPositionX + halfTeethWidthPx,
            teethBasePositionY + teethHeightPx
        )

        // left edge will automatically be drawn to close the path with the top-left arc
        close()
    })

}
