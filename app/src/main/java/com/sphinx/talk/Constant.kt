package com.sphinx.talk

object Constant {
    private const val HOST = "192.168.1.6:3000"
    const val SERVER_URL = "http://$HOST"
    const val WS_URL = "ws://$HOST"


}


val levels = listOf(
    "any level",
    "beginner",
    "upper beginner",
    "intermediate",
    "upper intermediate",
    "advanced",
    "upper advanced"
)