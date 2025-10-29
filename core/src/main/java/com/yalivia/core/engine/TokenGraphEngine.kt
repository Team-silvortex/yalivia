package com.yalivia.core.engine

import com.yalivia.core.model.*

class TokenGraphEngine(val graph: TokenGraph) {

    fun nextCandidates(path: SelectionPath): List<TokenNode> {
        val current = path.lastOrNull()
        val nextIds = if (current == null) graph.roots else (graph.nodes[current]?.next ?: emptyList())
        return nextIds.mapNotNull { graph.nodes[it] }
    }

    fun isValidAppend(path: SelectionPath, nodeId: String): Boolean {
        val current = path.lastOrNull()
        return if (current == null) graph.roots.contains(nodeId)
        else graph.nodes[current]?.next?.contains(nodeId) == true
    }

    fun append(path: SelectionPath, nodeId: String): Boolean {
        if (!isValidAppend(path, nodeId)) return false
        path.nodeIds += nodeId
        return true
    }

    fun explain(nodeId: String): String {
        val n = graph.nodes[nodeId] ?: return nodeId
        val payloadStr = if (n.payload.isNotEmpty()) " • ${n.payload.entries.joinToString()}" else ""
        return "${n.label}$payloadStr"
    }
}