package com.example.randomuser_test_task.feature.userdetail

import com.example.randomuser_test_task.feature.userslist.UsersListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userDetailModule = module{
    viewModel { UserDetailViewModel(get(), get()) }
}