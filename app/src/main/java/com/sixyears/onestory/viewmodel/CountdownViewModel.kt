package com.sixyears.onestory.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sixyears.onestory.util.DateUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountdownViewModel : ViewModel() {

    private val _countdown = MutableLiveData<DateUtils.Countdown>()
    val countdown: LiveData<DateUtils.Countdown> = _countdown

    private val _lovePercent = MutableLiveData<Int>()
    val lovePercent: LiveData<Int> = _lovePercent

    private val _isBirthdayToday = MutableLiveData<Boolean>()
    val isBirthdayToday: LiveData<Boolean> = _isBirthdayToday

    init {
        _lovePercent.value = DateUtils.loveGrowingPercent()
        _isBirthdayToday.value = DateUtils.isTodayBirthday()
        startTicking()
    }

    private fun startTicking() {
        viewModelScope.launch {
            while (true) {
                _countdown.value = DateUtils.countdownToBirthday()
                _isBirthdayToday.value = DateUtils.isTodayBirthday()
                delay(1000)
            }
        }
    }
}
