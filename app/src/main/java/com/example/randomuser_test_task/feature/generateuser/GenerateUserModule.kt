package com.example.randomuser_test_task.feature.generateuser

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val generateUserModule = module {
    viewModel { (cameFromUserList: Boolean) ->
        GenerateUserViewModel(usersRepository = get(), cameFromUserList = cameFromUserList)
    }
}