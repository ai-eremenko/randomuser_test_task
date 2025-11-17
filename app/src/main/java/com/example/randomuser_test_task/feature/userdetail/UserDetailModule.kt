package com.example.randomuser_test_task.feature.userdetail

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userDetailModule = module{
    viewModel { (userId: String) ->
        UserDetailViewModel(
            userId = userId,
            usersRepository = get()
        )
    }
}