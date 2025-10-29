package com.yalivia.app

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MaterialTheme {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
          Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
            Text(
              text = "Yalivia Android (Alpha)",
              style = MaterialTheme.typography.headlineMedium,
              textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
              startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
            }) {
              Text(text = getString(R.string.open_input_settings))
            }
            Spacer(Modifier.height(8.dp))
            Text("请在系统里启用并切换到 \"Yalivia\" 输入法。", style = MaterialTheme.typography.bodyMedium)
          }
        }
      }
    }
  }
}