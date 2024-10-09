package com.valeriaukat.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valeriaukat.lemonade.ui.theme.LemonadeTheme

// Kelas utama untuk aplikasi Lemonade
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Mengaktifkan mode edge-to-edge untuk tampilan penuh
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        // Menetapkan konten untuk komposisi dengan tema Lemonade
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp() {
    // State untuk melacak langkah saat ini dan jumlah squeeze
    var currentStep by remember { mutableIntStateOf(1) }
    var squeezeCount by remember { mutableIntStateOf(0) }

    // Scaffold untuk menyediakan struktur dasar aplikasi dengan AppBar
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    // Judul AppBar
                    Text(
                        text = "Lemonade",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        // Surface untuk menempatkan konten dengan latar belakang
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            color = MaterialTheme.colorScheme.background
        ) {
            // Menampilkan konten berdasarkan langkah saat ini
            when (currentStep) {
                1 -> LemonTextAndImage(
                    textLabelResourceId = R.string.lemon_select,
                    drawableResourceId = R.drawable.lemon_tree,
                    contentDescriptionResourceId = R.string.lemon_tree_description,
                    onImageClick = {
                        // Jika langkah 1, beralih ke langkah 2 dan atur jumlah squeeze
                        currentStep = 2
                        squeezeCount = (2..4).random()
                    }
                )

                2 -> LemonTextAndImage(
                    textLabelResourceId = R.string.lemon_squeeze,
                    drawableResourceId = R.drawable.lemon_squeeze,
                    contentDescriptionResourceId = R.string.lemon_content_description,
                    onImageClick = {
                        // Mengurangi jumlah squeeze
                        squeezeCount--
                        // Jika squeezeCount mencapai 0, beralih ke langkah 3
                        if (squeezeCount <= 0) {
                            currentStep = 3
                        }
                    }
                )

                3 -> LemonTextAndImage(
                    textLabelResourceId = R.string.lemonade_drink,
                    drawableResourceId = R.drawable.lemon_drink,
                    contentDescriptionResourceId = R.string.glass_lemonade_description,
                    onImageClick = {
                        // Beralih ke langkah 4
                        currentStep = 4
                    }
                )

                4 -> LemonTextAndImage(
                    textLabelResourceId = R.string.empty_glass,
                    drawableResourceId = R.drawable.lemon_restart,
                    contentDescriptionResourceId = R.string.empty_glass,
                    onImageClick = {
                        // Kembali ke langkah 1
                        currentStep = 1
                    }
                )
            }
        }
    }
}

// Composable untuk menampilkan gambar dan teks dengan interaksi
@Composable
fun LemonTextAndImage(
    textLabelResourceId: Int,
    drawableResourceId: Int,
    contentDescriptionResourceId: Int,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Menampilkan gambar dengan interaksi klik
            Image(
                painter = painterResource(drawableResourceId),
                contentDescription = stringResource(contentDescriptionResourceId),
                modifier = Modifier
                    .size(200.dp)
                    .clickable { onImageClick() } // Memanggil fungsi saat gambar diklik
            )
            Spacer(modifier = Modifier.height(16.dp)) // Spasi di bawah gambar
            // Menampilkan teks yang sesuai dengan langkah saat ini
            Text(
                text = stringResource(textLabelResourceId),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

// Preview untuk melihat tampilan aplikasi di editor
@Preview
@Composable
fun LemonPreview() {
    LemonadeTheme {
        LemonadeApp() // Menampilkan LemonadeApp dalam preview
    }
}
