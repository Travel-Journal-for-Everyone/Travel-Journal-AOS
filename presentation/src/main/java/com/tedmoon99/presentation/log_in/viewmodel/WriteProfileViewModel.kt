package com.tedmoon99.presentation.log_in.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tedmoon99.presentation.common.viewmodel.BaseViewModel
import com.tedmoon99.presentation.log_in.WriteProfileContract
import com.tedmoon99.presentation.log_in.utils.Scope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class WriteProfileViewModel @Inject constructor(

) : BaseViewModel<WriteProfileContract.Event, WriteProfileContract.State, WriteProfileContract.Effect>() {

    override fun createInitialState(): WriteProfileContract.State {
        return WriteProfileContract.State(
            scope = Scope.PUBLIC_SCOPE,
            name = "",
        )
    }

    override fun handleEvent(event: WriteProfileContract.Event) {
        when (event) {
            is WriteProfileContract.Event.ProfileImageClicked -> {
                // 앨범 or Camera 처리

            }

            is WriteProfileContract.Event.DoubleCheckClicked -> {
                // 서버에 중복 확인 요청
                isNotDuplicatedName()
            }
            is WriteProfileContract.Event.NameEntered -> {
                // 유효성 검사 수행
                isValidateNickName()
            }

            is WriteProfileContract.Event.ScopeClicked -> {
                val isScopeOpened = currentState.isScopeOpened
                setState(currentState.copy(
                    isScopeOpened = !isScopeOpened,
                    scope = event.scope
                ))
            }
        }
    }

    fun triggerNameCheck(name: String) {
        setState(currentState.copy(name = name))
        setEvent(WriteProfileContract.Event.NameEntered)
    }

    fun triggerNameDoubleCheck() {
        if (currentState.isLoading) return // 중복 확인 방지
        setEvent(WriteProfileContract.Event.DoubleCheckClicked)
    }

    private fun isNotDuplicatedName() {
        viewModelScope.launch {
            setState(currentState.copy(isLoading = true))
            // 서버에 중복 확인 요청
            val isNotDuplicate = true
            setState(currentState.copy(isLoading = false))
            // 상태 update
            setState(currentState.copy(isNotDuplicatedName = isNotDuplicate))
            if (isNotDuplicate) setEffect(WriteProfileContract.Effect.ShowSuccessMessage) // 성공 메시지 전달
        }
    }

    private fun isValidateNickName() {
        /** 정규 표현식 내용
         * 국문으로 2자 - 12자
         */
        val namePattern = "^[가-힣]{2,12}$"
        val isValidate = Pattern.matches(namePattern, currentState.name.trim())
        Log.d(TAG, "유효성 검사 대상 이름: ${currentState.name}")
        Log.d(TAG, "이름 유효성 검사 결과: $isValidate")
        // 상태 update
        setState(currentState.copy(isValidateName = isValidate, isNotDuplicatedName = false))
    }


    companion object {
        private const val TAG = "WriteProfileViewModel"

    }
}