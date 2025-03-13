package com.tedmoon99.domain.member.entity

data class DuplicatedCheckResultEntity(
    val success: Boolean,
    val isDuplicated: Boolean,
    val isBadWorld: Boolean,
)
