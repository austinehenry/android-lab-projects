package com.example.calculator

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorUI() {
    var display by remember { mutableStateOf("Hi Henry :-)") }
    var operand1 by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("") }
    val context = LocalContext.current

    val buttonBaseModifier = Modifier
        .aspectRatio(1f)
        .padding(4.dp)

    fun onButtonClick(value: String) {
        when (value) {
            "Bye" -> {
                (context as? Activity)?.finish()
            }
            "C" -> {
                display = "0"
                operand1 = ""
                operator = ""
            }
            "+", "-", "*", "/" -> {
                operand1 = display
                operator = value
                display = "0"
            }
            "=" -> {
                val result = try {
                    val op1 = operand1.toDouble()
                    val op2 = display.toDouble()
                    when (operator) {
                        "+" -> op1 + op2
                        "-" -> op1 - op2
                        "*" -> op1 * op2
                        "/" -> if (op2 != 0.0) op1 / op2 else "Undefined :-("
                        else -> "Error"
                    }
                } catch (e: Exception) {
                    "Error"
                }
                display = result.toString()
                operand1 = ""
                operator = ""
            }
            else -> {
                display = if (display == "0") value else display + value
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = display,
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f),
            maxLines = 1
        )

        val buttons = listOf(
            listOf("C", "±", "%", "/"),
            listOf("7", "8", "9", "*"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("0", ".", "=", "Bye")
        )

        buttons.forEach { row ->
            Row(modifier = Modifier.fillMaxWidth()) {
                row.forEach { label ->
                    if (label.isNotEmpty()) {
                        CalcButton(
                            text = label,
                            modifier = Modifier
                                .weight(1f)
                                .then(buttonBaseModifier)
                        ) {
                            onButtonClick(label)
                        }
                    } else {
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .then(buttonBaseModifier)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalcButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val bgColor = when (text) {
        "C", "±", "%" -> Color(0xFFD4D4D2)
        "/", "*", "-", "+", "=" -> Color(0xFFFF9500)
        "Bye" ->Color.Red
        else -> Color(0xFF505050)
    }

    val textColor = when (text) {
        "C", "±", "%" -> Color.Black
        else -> Color.White
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(bgColor, RoundedCornerShape(40.dp))
            .clickable { onClick() }
    ) {
        Text(
            text = text,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )

    }
}
