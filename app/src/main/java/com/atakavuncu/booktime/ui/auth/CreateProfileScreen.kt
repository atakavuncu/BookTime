package com.atakavuncu.booktime.ui.auth

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.ui.components.BTButton
import com.atakavuncu.booktime.ui.components.BTTextField
import com.atakavuncu.booktime.ui.navigation.Route
import com.atakavuncu.booktime.ui.theme.BackgroundWhite
import com.atakavuncu.booktime.ui.theme.Black
import com.atakavuncu.booktime.ui.theme.DarkGrey
import com.atakavuncu.booktime.ui.theme.Grey
import com.atakavuncu.booktime.ui.theme.TextBlack
import com.atakavuncu.booktime.ui.theme.White
import com.atakavuncu.booktime.utils.SessionManager

@Composable
fun CreateProfileScreen(
    navController: NavHostController,
    sessionManager: SessionManager,
    viewModel: UserViewModel = hiltViewModel()
) {
    var username by remember { mutableStateOf("") }
    val profileImageUri = remember { mutableStateOf<Uri?>(null) }
    val coverImageUri = remember { mutableStateOf<Uri?>(null) }
    val profileImageUrl = remember { mutableStateOf("") }
    val coverImageUrl = remember { mutableStateOf("") }
    var isCreated = remember { mutableStateOf(false) }
    val registerResult by viewModel.registerResult.collectAsState()
    val userId by viewModel.userId.collectAsState()
    val profilePhotoUrl by viewModel.profilePhotoUrl.collectAsState()
    val coverPhotoUrl by viewModel.coverPhotoUrl.collectAsState()

    viewModel.getUserId()
    Log.d("CreateProfileScreen", "userId: $userId")

    if (isCreated.value) {
        navController.navigate(Route.MAIN)
    }

    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundWhite)
                    .padding(vertical = paddingValues.calculateTopPadding())
                    .padding(top = 70.dp, start = 40.dp, end = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.booktime_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                BTTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = "Kullanıcı Adı"
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Profil Fotoğrafı", color = Black, fontWeight = FontWeight.Medium, textAlign = TextAlign.Start)
                Spacer(modifier = Modifier.height(8.dp))
                ImageSelectorButton(
                    imageUri = profileImageUri.value,
                    onImageSelected = { profileImageUri.value = it },
                    buttonText = "Görsel Seç"
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Kapak Fotoğrafı", color = Black, fontWeight = FontWeight.Medium, textAlign = TextAlign.Start)
                Spacer(modifier = Modifier.height(8.dp))
                ImageSelectorButton(
                    imageUri = coverImageUri.value,
                    onImageSelected = { coverImageUri.value = it },
                    buttonText = "Görsel Seç"
                )
                Spacer(modifier = Modifier.height(24.dp))
                BTButton(
                    text = "OLUŞTUR",
                    onClick = {
                        userId?.let { userId ->
                            profileImageUri.value?.let { profileUri ->
                                // Profil fotoğrafı yükleniyor
                                viewModel.uploadImageToStorage(
                                    imageUri = profileUri,
                                    userId = userId,
                                    fileName = "profile_photo",
                                    onSuccess = { profileImageUrl.value = it },
                                    onFailure = { /* Hata yönetimi yapılabilir */ }
                                )
                            }
                            coverImageUri.value?.let { coverUri ->
                                // Kapak fotoğrafı yükleniyor
                                viewModel.uploadImageToStorage(
                                    imageUri = coverUri,
                                    userId = userId,
                                    fileName = "cover_photo",
                                    onSuccess = { coverImageUrl.value = it },
                                    onFailure = { /* Hata yönetimi yapılabilir */ }
                                )
                            }

                            // Fotoğraf yükleme işlemleri tamamlandığında profil oluşturma işlemi yapılacak
                            viewModel.getUserImageUrl(userId, true)
                            viewModel.getUserImageUrl(userId, false)

                            // Kayıt işlemi için her iki URL'in de null olmadığını kontrol ediyoruz
                            if (!profilePhotoUrl.isNullOrEmpty() && !coverPhotoUrl.isNullOrEmpty()) {
                                Log.d("CreateProfileScreen", "profileImageUrl: $profilePhotoUrl")
                                Log.d("CreateProfileScreen", "coverImageUrl: $coverPhotoUrl")

                                viewModel.createUserProfile(
                                    username = username,
                                    profilePhotoUrl = profilePhotoUrl,
                                    coverPhotoUrl = coverPhotoUrl
                                )

                                isCreated.value = true
                            } else {
                                Log.d("CreateProfileScreen", "Profile veya kapak fotoğrafı henüz yüklenmedi.")
                            }
                        }
                    }
                )
            }
        }
    )
}

@Composable
fun ImageSelectorButton(imageUri: Uri?, onImageSelected: (Uri) -> Unit, buttonText: String) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                onImageSelected(uri)
            }
        }
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(8.dp)
    ) {
        Button(
            onClick = { launcher.launch("image/*") },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Grey)
        ) {
            Text(text = buttonText, color = TextBlack, fontSize = 10.sp)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = imageUri?.lastPathSegment ?: "Henüz görsel seçilmedi",
            fontSize = 10.sp,
            color = DarkGrey,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
