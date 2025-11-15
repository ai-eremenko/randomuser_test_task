package com.example.randomuser_test_task.feature.userslist

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val usersListModule = module {
        viewModel { UsersListViewModel(get()) }
}