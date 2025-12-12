package com.example.itcourses.presentation

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.itcourses.R
import com.example.utils.EmailValidator

@Composable
fun LoginScreen(onNavigateToMain: () -> Unit)
{
    var email by rememberSaveable{mutableStateOf("")}
    var password by rememberSaveable{mutableStateOf("")}
    var isErrorEmail by rememberSaveable { mutableStateOf(false) }
    var isErrorPassword by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
        .fillMaxSize().
        verticalScroll(scrollState) ) {

        Column(
            modifier = Modifier.padding(top=140.dp).fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(stringResource(id = R.string.login),
                modifier = Modifier.padding(bottom = 20.dp),
                style = MaterialTheme.typography.headlineLarge)

            Text(stringResource(id = R.string.email),
                modifier = Modifier.padding(bottom = 5.dp),
                style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                email,
                {
                    email = it
                    isErrorEmail = EmailValidator.isValidEmail(it.trim())
                },
                textStyle = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .height(50.dp)
                    .fillMaxWidth(),

                singleLine = true,
                shape = MaterialTheme.shapes.extraLarge,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = colorResource(R.color.dark_gray),
                    focusedContainerColor = colorResource(R.color.dark_gray),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.write_login),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                },
            )

           Text(stringResource(id = R.string.password),
               modifier = Modifier.padding(bottom = 5.dp),
               style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                password,
                {
                    password = it
                    isErrorPassword = it.isNotEmpty()
                },
                textStyle = TextStyle(fontSize = 16.sp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .height(50.dp)
                    .fillMaxWidth(),

                singleLine = true,
                shape = MaterialTheme.shapes.extraLarge,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = colorResource(R.color.dark_gray),
                    focusedContainerColor = colorResource(R.color.dark_gray),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.write_password),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                },
            )
            Button(onClick = { onNavigateToMain()},
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .height(50.dp)
                        .fillMaxWidth(),
                enabled = isErrorEmail && isErrorPassword,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.green),
                    contentColor = Color.White
                )
                )
            {
                Text(stringResource(id = R.string.login))
            }

            Column(
                modifier= Modifier
                    .padding(bottom = 20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row {
                    Text(stringResource(id = R.string.not_account),
                        modifier = Modifier.padding(end = 8.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.5.sp
                        ))
                    Text(stringResource(id = R.string.registration),
                        color = colorResource(R.color.green),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.5.sp
                        ))
                }

                Text(stringResource(id = R.string.forgot_password),
                    color = colorResource(R.color.green),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.5.sp
                    ))
            }

            HorizontalDivider(
                thickness = 2.dp,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = {
                        val url = "https://vk.com/"
                        val uri = url.toUri()
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .height(48.dp)
                        .weight(0.5f)
                        .clip(MaterialTheme.shapes.extraLarge),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.blue),
                        contentColor = Color.White
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.vk),
                        contentDescription = "",
                        modifier = Modifier,
                        contentScale = ContentScale.Crop
                    )
                }

                Box( modifier = Modifier
                    .height(48.dp)
                    .weight(0.5f)) {
                    GradientButton(context)
                }
            }
        }
    }
}

@Composable
private fun GradientButton(context: Context) {
    val colorStart = colorResource(R.color.start_gradient)
    val colorEnd = colorResource(R.color.stop_gradient)

    val gradientColors = listOf(colorStart, colorEnd)
    val shape = MaterialTheme.shapes.extraLarge
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(
                brush = Brush.verticalGradient(colors = gradientColors),
                shape = shape
            )
            .clickable(
                onClick = {
                    val url = "https://ok.ru/"
                    val uri = url.toUri()
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)
                } ,
                interactionSource = interactionSource,
                indication = null
            )
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .height(48.dp),
         contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ok),
            contentDescription = "",
            modifier = Modifier,
            contentScale = ContentScale.Crop
        )
    }
}