package com.tedmoon99.presentation.log_in.utils

import com.tedmoon99.presentation.R

enum class Scope(val title: String, val message: String, val icon: Int) {
    PUBLIC_SCOPE("전체 공개", "모든 사용자가 볼 수 있어요", R.drawable.icon_world),
    ONLY_FOLLOW_SCOPE("팔로우 공개", "나를 팔로우한 사용자만 볼 수 있어요.", R.drawable.icon_person),
    PRIVATE_SCOPE("나만 보기", "나만 볼 수 있어요.", R.drawable.icon_lock),
}