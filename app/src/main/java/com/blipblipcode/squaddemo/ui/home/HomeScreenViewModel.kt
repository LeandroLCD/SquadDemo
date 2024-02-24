package com.blipblipcode.squaddemo.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blipblipcode.squaddemo.ui.home.interfaces.IAddMeasureUseCase
import com.blipblipcode.squaddemo.ui.home.interfaces.IGetMeasureListUseCase
import com.blipblipcode.squaddemo.ui.home.models.MeasureModel
import com.blipblipcode.squaddemo.ui.utilities.toInches
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val addMeasureUseCase: IAddMeasureUseCase,
    private val getMeasureListUseCase: IGetMeasureListUseCase
) : ViewModel() {
    private val TAG = this.javaClass.name

    var measure by mutableFloatStateOf(0f)
        private set

    private var _data = MutableLiveData<Float>()
    val data = _data.value

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getMeasureListUseCase().collect { list ->
                list.forEach {
                    Log.d(TAG, "viewModel: MeasureModel : $it")
                }
            }

        }
    }

    fun onChangedValue(measure1: Float, measure2: Float) {

        measure = abs(measure2.minus(measure1).toInches())
    }

    fun onSaveMeasure(name: String = "", measure: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            addMeasureUseCase(MeasureModel(name = name, measure = measure, udm = Udm.Inches))
        }
    }

}