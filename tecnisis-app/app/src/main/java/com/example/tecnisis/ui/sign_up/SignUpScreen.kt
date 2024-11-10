package com.example.tecnisis.ui.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tecnisis.R
import com.example.tecnisis.ui.components.CustomBasicTextField
import com.example.tecnisis.ui.components.CustomEmailField
import com.example.tecnisis.ui.components.CustomNumberField
import com.example.tecnisis.ui.components.CustomPasswordField
import com.example.tecnisis.ui.components.CustomPhoneNumberField
import com.example.tecnisis.ui.components.InfoBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    onSignUp: MutableState<()->Unit>,
    navController: NavHostController
) {

    val name by viewModel.name.observeAsState("")
    val surnames by viewModel.surnames.observeAsState("")
    val email by viewModel.email.observeAsState("")
    val pass by viewModel.pass.observeAsState("")
    val repeatedPass by viewModel.repeatedPass.observeAsState("")
    val dni by viewModel.dni.observeAsState("")
    val phone by viewModel.phone.observeAsState("")
    val address by viewModel.address.observeAsState("")
    val message by viewModel.message.observeAsState("")

    onSignUp.value = {
        viewModel.registerUser()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        InfoBox(description = stringResource(R.string.sign_up_info))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)){
            CustomBasicTextField(stringResource(R.string.name), name, Modifier.weight(1f), onValueChange = { viewModel.updateName(it) })
            CustomBasicTextField(stringResource(R.string.surnames), surnames, Modifier.weight(1f), onValueChange = { viewModel.updateSurnames(it) })
        }
        CustomEmailField(stringResource(R.string.email), email , Modifier.fillMaxWidth(), onValueChange = { viewModel.updateEmail(it) })
        CustomPasswordField(stringResource(R.string.password), pass, Modifier.fillMaxWidth(), onValueChange = { viewModel.updatePass(it) })
        CustomPasswordField(stringResource(R.string.repeat_pass), repeatedPass, Modifier.fillMaxWidth(), onValueChange = { viewModel.updateRepeatedPass(it) })
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CustomNumberField(stringResource(R.string.dni), dni, Modifier.weight(1f), onValueChange = { viewModel.updateDNI(it) })
            CustomPhoneNumberField(stringResource(R.string.phone), phone, Modifier.weight(1f), onValueChange = { viewModel.updatePhone(it) })
        }
        CustomBasicTextField(stringResource(R.string.address), address, Modifier.fillMaxWidth(), onValueChange = { viewModel.updateAddress(it) })

    }
}