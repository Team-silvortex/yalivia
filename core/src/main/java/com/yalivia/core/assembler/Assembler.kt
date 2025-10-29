package com.yalivia.core.assembler

import com.yalivia.core.model.*

sealed interface AsmTarget {
    data object PlainText: AsmTarget
    data object Command: AsmTarget
    data class Json(val pretty: Boolean = true): AsmTarget
}

interface Assembler {
    fun compile(path: SelectionPath, graph: TokenGraph, target: AsmTarget): Result<String>
}

class SimpleCommandAssembler : Assembler {
    override fun compile(path: SelectionPath, graph: TokenGraph, target: AsmTarget): Result<String> = runCatching {
        val parts = path.nodeIds.map { id ->
            val n = graph.nodes[id] ?: error("node $id not found")
            n.payload["cmd"] ?: n.label.lowercase()
        }
        when (target) {
            AsmTarget.Command, AsmTarget.PlainText -> parts.joinToString(" ")
            is AsmTarget.Json -> {
                val json = kotlinx.serialization.json.Json { prettyPrint = target.pretty }
                json.encodeToString(ListSerializer(String.serializer()), parts)
            }
        }
    }
}