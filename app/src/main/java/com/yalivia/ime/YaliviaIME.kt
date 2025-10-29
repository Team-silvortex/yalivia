package com.yalivia.ime

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import android.inputmethodservice.InputMethodService
import com.yalivia.core.model.*
import com.yalivia.core.engine.TokenGraphEngine
import com.yalivia.core.assembler.AsmTarget
import com.yalivia.core.assembler.SimpleCommandAssembler
import kotlinx.serialization.json.Json
import java.nio.charset.Charset

class YaliviaIME : InputMethodService() {

  private lateinit var engine: TokenGraphEngine
  private val path = SelectionPath()
  private val assembler = SimpleCommandAssembler()

  override fun onCreate() {
    super.onCreate()
    val graph = assets.open("graph.json").use { input ->
      val text = input.readBytes().toString(Charset.forName("UTF-8"))
      Json.decodeFromString(TokenGraph.serializer(), text)
    }
    engine = TokenGraphEngine(graph)
  }

  override fun onCreateInputView(): View {
    return ComposeView(this).apply {
      setContent {
        MaterialTheme {
          IMEPanel(
            engine = engine,
            path = path,
            onPick = { nodeId ->
              if (!engine.append(path, nodeId)) {
                // 简单非法提示可加上 Snackbar/震动，这里先略
              }
            },
            onCommit = {
              val text = assembler.compile(path, engine.graph, AsmTarget.PlainText).getOrNull() ?: ""
              currentInputConnection.commitText(text, 1)
              path.clear()
            },
            onClear = { path.clear() }
          )
        }
      }
    }
  }
}

@Composable
private fun IMEPanel(
  engine: TokenGraphEngine,
  path: SelectionPath,
  onPick: (String) -> Unit,
  onCommit: () -> Unit,
  onClear: () -> Unit
) {
  val candidates by remember(path.nodeIds) {
    mutableStateOf(engine.nextCandidates(path))
  }
  val preview = remember(path.nodeIds.toList()) { path.nodeIds.joinToString(" > ") }

  Column(
    Modifier
      .fillMaxWidth()
      .background(Color(0xFFF7F7F7))
      .padding(8.dp)
  ) {
    // 面包屑
    Text(text = "Path: $preview", style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)
    Spacer(Modifier.height(6.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      Button(onClick = onCommit, modifier = Modifier.weight(1f)) { Text("提交") }
      OutlinedButton(onClick = onClear, modifier = Modifier.weight(1f)) { Text("清空") }
    }
    Spacer(Modifier.height(8.dp))
    // 候选网格
    LazyVerticalGrid(columns = GridCells.Fixed(4), verticalArrangement = Arrangement.spacedBy(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.heightIn(max = 220.dp)) {
      items(candidates) { node ->
        Surface(
          tonalElevation = 2.dp,
          modifier = Modifier
            .height(44.dp)
            .clickable { onPick(node.id) }
        ) {
          Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            Text(node.label)
          }
        }
      }
    }
  }
}