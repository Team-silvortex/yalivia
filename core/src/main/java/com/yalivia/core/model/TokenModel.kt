package com.yalivia.core.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenNode(
    val id: String,
    val label: String,
    val tag: String,
    val payload: Map<String, String> = emptyMap(),
    val next: List<String> = emptyList()
)

@Serializable
data class TokenGraph(
    val nodes: Map<String, TokenNode>,
    val roots: List<String>
)

data class SelectionPath(val nodeIds: MutableList<String> = mutableListOf()) {
    fun lastOrNull(): String? = nodeIds.lastOrNull()
    fun clear() = nodeIds.clear()
}