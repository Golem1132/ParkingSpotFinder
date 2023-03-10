package com.example.parkingspotfinder.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.parkingspotfinder.data.ParkingSpotMarker
import com.example.parkingspotfinder.data.ParkingSpotType
import com.google.android.gms.maps.model.LatLng
import java.time.Instant
import java.util.Date
import kotlin.math.roundToInt


@Composable
fun <T>SwipeableRow(
    item: T,
    buttonsRow: @Composable (RowScope, T) -> Unit,
    itemView: @Composable (T) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val density = LocalDensity.current.density
    val constraints = -(screenWidth * density) * 0.75f .. (screenWidth * density) * 0.75f
    var offsetX by remember {
        mutableStateOf(0f)
    }
    val dragState = rememberDraggableState() {
        if(offsetX in constraints)
            offsetX += it
        println(offsetX)
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .shadow(1.dp)
) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
            contentAlignment = Alignment.CenterEnd) {
            Surface(modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
                color = Color.White) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End) {
                    buttonsRow(this, item)
                }

            }

        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .offset {
                IntOffset(offsetX.roundToInt(), 0)
            }
            .draggable(
                state = dragState,
                orientation = Orientation.Horizontal,
                onDragStopped = {
                    offsetX = when {
                        offsetX > constraints.endInclusive -> {
                            constraints.endInclusive
                        }
                        offsetX < constraints.start -> {
                            constraints.start
                        }
                        else -> {
                            pullToClosetConstraint(offsetX, constraints, 0f)
                        }
                    }
                }
            ),
            contentAlignment = Alignment.CenterStart) {
            Surface(modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
                color = Color.White) {
                itemView(item)
            }

        }



    }
}


@Composable
fun <T> DraggableDrawer(
    list: List<T>,
    buttonsRow: @Composable (T) -> Unit,
    itemView: @Composable (T) -> Unit
) {
    val topScreen = LocalConfiguration.current.screenHeightDp
    val density = LocalDensity.current.density
    println(topScreen * density)
    var offsetY by remember {
        mutableStateOf((topScreen * density)/2)
    }
    val constraints = 0f..(topScreen * density)/2
    println(constraints)

    val dragState = rememberDraggableState() {
        if (offsetY in constraints) {
            offsetY += it
        }
        println(offsetY)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            modifier = Modifier.offset {
                IntOffset(0,offsetY.roundToInt())
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .height(20.dp)
                    .draggable(
                        state = dragState,
                        orientation = Orientation.Vertical,
                        onDragStopped = {
                            offsetY = when {
                                offsetY > constraints.endInclusive -> {
                                    constraints.endInclusive
                                }
                                offsetY < constraints.start -> {
                                    constraints.start
                                }
                                else -> pullToClosetConstraint(offsetY, constraints)

                            }

                        }
                    )
                    .background(
                        color = Color.LightGray, shape = RoundedCornerShape(
                            topStartPercent = 50, topEndPercent = 50
                        )
                    ),
                contentAlignment = Alignment.Center
            ){
                Image(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "")
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((topScreen / 2).dp)
                    .background(color = Color.White)
            ) {
                items(list) { item ->
                    SwipeableRow(item = item, buttonsRow = { scope, itemInfo ->
                        buttonsRow(itemInfo)
                    }) {
                        itemView(it)
                    }
                }
            }
        }
    }
}

private fun pullToClosetConstraint(check: Float, range: ClosedFloatingPointRange<Float>): Float {
    val middlePoint = range.endInclusive / 2
    if(check > middlePoint)
        return range.endInclusive
    else return range.start

}

private fun pullToClosetConstraint(check: Float, range: ClosedFloatingPointRange<Float>, middlePoint: Float): Float {
    when {
        check > middlePoint &&  check < (range.endInclusive / 2) -> {
            return middlePoint
        }
        check < middlePoint &&  check > (range.start / 2) -> {
            return middlePoint
        }
        check > middlePoint &&  check > (range.endInclusive / 2) -> {
            return range.endInclusive
        }
        check < middlePoint &&  check < (range.start / 2) -> {
            return range.start
        }
        else -> return  middlePoint

    }

}