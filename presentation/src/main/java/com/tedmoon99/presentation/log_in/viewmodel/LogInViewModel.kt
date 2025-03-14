package com.tedmoon99.presentation.log_in.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tedmoon99.domain.kakao.usecase.KakaoUseCase
import com.tedmoon99.presentation.common.viewmodel.BaseViewModel
import com.tedmoon99.presentation.log_in.LogInContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val kakaoUseCase: KakaoUseCase,
) : BaseViewModel<LogInContract.Event, LogInContract.State, LogInContract.Effect>() {

    override fun createInitialState(): LogInContract.State {
        return LogInContract.State(isLoggedIn = false, isLoading = false)
    }

    override fun handleEvent(event: LogInContract.Event) {
        when (event) {
            is LogInContract.Event.KakaoLogInClicked -> {
                logInWithKakao()
            }

            is LogInContract.Event.AppleLogInClicked -> {
                Log.d(TAG, "애플 로그인 클릭!")
            }

            is LogInContract.Event.GoogleLogInClicked -> {
                Log.d(TAG, "구글 로그인 클릭!")
            }
        }
    }

    fun triggerKakaoLogIn() {
        if (currentState.isLoading) return // 중복 로그인 방지
        setEvent(LogInContract.Event.KakaoLogInClicked)
    }

    private fun logInWithKakao() {
        viewModelScope.launch {
            Log.d(TAG, "카카오 로그인 클릭!")
            setState(currentState.copy(isLoading = true))
            val result = kakaoUseCase.requestKakaoSignIn()
            setState(currentState.copy(isLoading = false))
            if (result.success) {
                if (result.isFirstLogin) {
                    // 프로필 작성 화면으로 이동
                    setEffect(LogInContract.Effect.NavigateToWriteProfile)
                } else {
                    // 홈으로 이동
                    setEffect(LogInContract.Effect.NavigateToHome)
                }
            } else {
                setEffect(LogInContract.Effect.ShowErrorMessage)
            }
        }
    }

    companion object {
        private const val TAG = "LogInViewModel"
    }
}