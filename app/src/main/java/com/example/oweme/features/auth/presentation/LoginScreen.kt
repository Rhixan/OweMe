package com.example.oweme.features.auth.presentation

import android.os.Build.VERSION.SDK_INT
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.oweme.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreen(
    onNavigateToSignUp: () -> Unit = {},
    onLoginSuccess: () -> Unit = {}
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }

    // --- Smooth Focus Animations ---
    val animDuration = 250

    // Email transitions
    val emailBgColor by animateColorAsState(
        targetValue = if (isEmailFocused) Color(0x26C5C5C5) else Color(0x1AC5C5C5),
        animationSpec = tween(animDuration),
        label = "emailBg"
    )
    val emailBorderColor by animateColorAsState(
        targetValue = if (isEmailFocused) Color.White.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.12f),
        animationSpec = tween(animDuration),
        label = "emailBorderColor"
    )
    val emailBorderWidth by animateDpAsState(
        targetValue = if (isEmailFocused) 1.dp else 1.dp,
        animationSpec = tween(animDuration),
        label = "emailBorderWidth"
    )

    // Password transitions
    val passBgColor by animateColorAsState(
        targetValue = if (isPasswordFocused) Color(0x26C5C5C5) else Color(0x1AC5C5C5),
        animationSpec = tween(animDuration),
        label = "passBg"
    )
    val passBorderColor by animateColorAsState(
        targetValue = if (isPasswordFocused) Color.White.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.12f),
        animationSpec = tween(animDuration),
        label = "passBorderColor"
    )
    val passBorderWidth by animateDpAsState(
        targetValue = if (isPasswordFocused) 1.dp else 1.dp,
        animationSpec = tween(animDuration),
        label = "passBorderWidth"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    }
                )
            }
    ) {
        // 1. Background GIF
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(R.raw.login_signup_background)
                .crossfade(true)
                .build(),
            imageLoader = imageLoader,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = 80.dp)
        )

        // 2. Dark Overlay Layer
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.20f))
        )

        // 3. UI Foreground (Shifts above keyboard automatically)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .imePadding() // Automatically pushes above the soft keyboard
                .navigationBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    // Allows smooth micro-scrolling on tiny screens when keyboard is expanded
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Log-In",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Email Input
                BasicTextField(
                    value = email,
                    onValueChange = { email = it },
                    textStyle = TextStyle(
                        color = Color(0xFFFCFAFA),
                        fontSize = 14.sp
                    ),
                    cursorBrush = SolidColor(Color(0xFFFCFAFA)),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .width(280.dp)
                        .height(48.dp)
                        .onFocusChanged { isEmailFocused = it.isFocused }
                        .background(color = emailBgColor, shape = RoundedCornerShape(10.dp))
                        .border(width = emailBorderWidth, color = emailBorderColor, shape = RoundedCornerShape(10.dp)),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (email.isEmpty()) {
                                Text(
                                    text = "Email",
                                    color = Color.White.copy(alpha = 0.6f),
                                    fontSize = 14.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Password Input
                BasicTextField(
                    value = password,
                    onValueChange = { password = it },
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp
                    ),
                    cursorBrush = SolidColor(Color.White),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .width(280.dp)
                        .height(48.dp)
                        .onFocusChanged { isPasswordFocused = it.isFocused }
                        .background(color = passBgColor, shape = RoundedCornerShape(10.dp))
                        .border(width = passBorderWidth, color = passBorderColor, shape = RoundedCornerShape(10.dp)),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (password.isEmpty()) {
                                Text(
                                    text = "Password",
                                    color = Color.White.copy(alpha = 0.6f),
                                    fontSize = 14.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Sign-up link text
                Text(
                    text = "Don’t have an account?\nSign-Up here!",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier
                        .clickable { onNavigateToSignUp() }
                        .padding(8.dp)
                )
            }
        }
    }
}