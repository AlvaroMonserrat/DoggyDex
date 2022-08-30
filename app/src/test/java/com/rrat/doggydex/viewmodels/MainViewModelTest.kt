package com.rrat.doggydex.viewmodels

import com.rrat.doggydex.base.BaseUnitTest
import com.rrat.doggydex.fake.FakeClassifierRepository
import com.rrat.doggydex.fake.FakeDogRepository
import com.rrat.doggydex.fake.FakeImageProxy
import com.rrat.doggydex.main.MainViewModel
import com.rrat.doggydex.utils.getValueForTest
import junit.framework.Assert.assertEquals
import org.junit.Test

class MainViewModelTest : BaseUnitTest() {

    @Test
    fun recognizeImageSuccessfully(){


        val viewModel = MainViewModel(
            FakeDogRepository(),
            FakeClassifierRepository()
        )
        viewModel.recognizeImage(FakeImageProxy())

        assertEquals("1", viewModel.dogRecognition.getValueForTest()?.id)
    }
}