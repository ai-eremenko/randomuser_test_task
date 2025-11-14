package com.example.randomuser_test_task.domain

import com.example.randomuser_test_task.domain.generateuser.GenerateUserInteractor
import com.example.randomuser_test_task.domain.generateuser.GenerateUserInteractorImpl
import com.example.randomuser_test_task.domain.userdetail.UserDetailsInteractor
import com.example.randomuser_test_task.domain.userdetail.UserDetailsInteractorImpl
import com.example.randomuser_test_task.domain.userslist.UsersListInteractor
import com.example.randomuser_test_task.domain.userslist.UsersListInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    factory<GenerateUserInteractor> {
        GenerateUserInteractorImpl(get())
    }
    factory<UserDetailsInteractor> {
        UserDetailsInteractorImpl(get())
    }
    factory<UsersListInteractor> {
        UsersListInteractorImpl(get())
    }
}