package com.example.randomuser_test_task.feature.generateuser

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.randomuser_test_task.domain.model.Gender
import com.example.randomuser_test_task.domain.model.Nationality
import com.example.randomuser_test_task.feature.generateuser.view.GenderSelection
import com.example.randomuser_test_task.feature.generateuser.view.NationalitySelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateUserScreen(navController: NavController) {
    var selectedGender by remember { mutableStateOf<Gender?>(null) }
    var selectedNationalities by remember { mutableStateOf(setOf<Nationality>()) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Generate User",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Button(
                    onClick = {
                        generateUser(selectedGender, selectedNationalities)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    enabled = selectedNationalities.isNotEmpty()
                ) {
                    Text("Generate")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Select Gender",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            GenderSelection(
                selectedGender = selectedGender,
                onGenderSelected = { gender ->
                    selectedGender = gender
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Select Nationality",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            NationalitySelection(
                selectedNationalities = selectedNationalities,
                onNationalitiesChanged = { nationalities ->
                    selectedNationalities = nationalities
                }
            )
        }
    }
}

