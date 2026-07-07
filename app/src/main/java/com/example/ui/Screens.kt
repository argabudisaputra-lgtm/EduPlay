package com.example.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.QuizHistory
import com.example.data.UserProgress
import com.example.ui.theme.*
import kotlinx.coroutines.launch
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.random.Random
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getCurrentDateString(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date())
}

@Composable
fun MovingShapesBackground(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "shapes_anim")
    
    val animationPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28318f,
        animationSpec = infiniteRepeatable(
            animation = tween(18000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )

    // Vibrant soft/pastel background colors
    val primaryColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
    val secondaryColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.08f)
    val tertiaryColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.08f)

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        
        if (width > 0 && height > 0) {
            // Shape 1: A beautiful floating/drifting circle
            val cx1 = width * 0.25f + (width * 0.12f * kotlin.math.sin(animationPhase.toDouble())).toFloat()
            val cy1 = height * 0.22f + (height * 0.12f * kotlin.math.cos(animationPhase.toDouble())).toFloat()
            val r1 = width * 0.18f + (width * 0.03f * kotlin.math.sin(animationPhase.toDouble() * 2)).toFloat()
            drawCircle(color = primaryColor, radius = r1, center = Offset(cx1, cy1))

            // Shape 2: Floating larger circle at the bottom right area
            val cx2 = width * 0.75f + (width * 0.15f * kotlin.math.cos(animationPhase.toDouble() * 1.3)).toFloat()
            val cy2 = height * 0.75f + (height * 0.1f * kotlin.math.sin(animationPhase.toDouble() * 1.3)).toFloat()
            val r2 = width * 0.25f + (width * 0.04f * kotlin.math.cos(animationPhase.toDouble())).toFloat()
            drawCircle(color = secondaryColor, radius = r2, center = Offset(cx2, cy2))

            // Shape 3: Floating square rotating in the middle left area
            val cx3 = width * 0.15f + (width * 0.08f * kotlin.math.cos(animationPhase.toDouble() * 0.8)).toFloat()
            val cy3 = height * 0.62f + (height * 0.1f * kotlin.math.sin(animationPhase.toDouble() * 0.8)).toFloat()
            val size3 = width * 0.16f
            val rotation3 = (animationPhase * 180f / 3.14159f) * 0.4f
            rotate(degrees = rotation3, pivot = Offset(cx3, cy3)) {
                drawRect(
                    color = tertiaryColor,
                    topLeft = Offset(cx3 - size3 / 2, cy3 - size3 / 2),
                    size = androidx.compose.ui.geometry.Size(size3, size3)
                )
            }

            // Shape 4: Floating triangle/star-like shape top right
            val cx4 = width * 0.82f + (width * 0.08f * kotlin.math.sin(animationPhase.toDouble() * 1.1)).toFloat()
            val cy4 = height * 0.28f + (height * 0.1f * kotlin.math.cos(animationPhase.toDouble() * 1.1)).toFloat()
            val size4 = width * 0.15f
            val rotation4 = -(animationPhase * 180f / 3.14159f) * 0.3f
            rotate(degrees = rotation4, pivot = Offset(cx4, cy4)) {
                val path = Path().apply {
                    moveTo(cx4, cy4 - size4 / 2)
                    lineTo(cx4 + size4 / 2, cy4 + size4 / 2)
                    lineTo(cx4 - size4 / 2, cy4 + size4 / 2)
                    close()
                }
                drawPath(path = path, color = secondaryColor)
            }

            // Shape 5: A soft central ring or decorative stroke
            val cx5 = width * 0.5f + (width * 0.06f * kotlin.math.sin(animationPhase.toDouble() * 0.5)).toFloat()
            val cy5 = height * 0.48f + (height * 0.08f * kotlin.math.cos(animationPhase.toDouble() * 0.5)).toFloat()
            val r5 = width * 0.14f
            drawCircle(
                color = primaryColor.copy(alpha = 0.06f),
                radius = r5,
                center = Offset(cx5, cy5),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 8f)
            )
        }
    }
}

// --- Shared Components ---

@Composable
fun EduHeader(
    progress: UserProgress?,
    onBack: (() -> Unit)? = null,
    title: String,
    onSettingsClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    viewModel: EduViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsStateWithLifecycle()

    Card(
        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (onBack != null) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                            .testTag("header_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
                    }
                } else {
                    // Small mascot icon (Cuter EP Knowledge Land logo)
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                    ) {
                        Image(
                            painter = painterResource(id = com.example.R.drawable.img_app_icon),
                            contentDescription = "EP Knowledge Land Logo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp)
                ) {
                    val isEduPlayTitle = title.contains("EduPlay", ignoreCase = true) || title.contains("EP Knowledge Land", ignoreCase = true)
                    Text(
                        text = if (isEduPlayTitle) "EP Knowledge Land" else title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            fontSize = 22.sp
                        )
                    )
                    if (isEduPlayTitle) {
                        Text(
                            text = "by .Maju",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Stars display bubble
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White.copy(alpha = 0.25f))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Bintang",
                            tint = Color(0xFFFACC15),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${progress?.starsCount ?: 0}",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 15.sp
                        )
                    }

                    // TEMA KAPSU (Theme switch capsule)
                    Spacer(modifier = Modifier.width(6.dp))
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White.copy(alpha = 0.2f))
                            .padding(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Cerah (Light)
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(if (!isDarkTheme) Color.White else Color.Transparent)
                                .clickable { viewModel.setTheme(false) }
                                .testTag("theme_cerah_button")
                        ) {
                            Text("☀️", fontSize = 12.sp)
                        }

                        // Petang (Dark)
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(if (isDarkTheme) Color.Black.copy(alpha = 0.4f) else Color.Transparent)
                                .clickable { viewModel.setTheme(true) }
                                .testTag("theme_petang_button")
                        ) {
                            Text("🌙", fontSize = 12.sp)
                        }
                    }

                    if (onSettingsClick != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        IconButton(
                            onClick = onSettingsClick,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f))
                                .testTag("header_settings_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Pengaturan",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress Level Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEAB308))
                ) {
                    Text(
                        text = "Lvl ${progress?.level ?: 1}",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                val xpRatio = (progress?.xp ?: 0) / 100f
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${progress?.name ?: "Sobat Edu"} 🚀",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${progress?.xp ?: 0}/100 XP",
                            color = Color.White.copy(alpha = 0.8f),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { xpRatio },
                        color = Color(0xFF34D399),
                        trackColor = Color.White.copy(alpha = 0.3f),
                        strokeCap = StrokeCap.Round,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                }
            }
        }
    }
}

// --- Confetti particle structures and falling animations ---

data class ConfettiParticle(
    var x: Float,
    var y: Float,
    val color: Color,
    val size: Float,
    val speedY: Float,
    val speedX: Float,
    val rotationSpeed: Float,
    var rotation: Float = 0f,
    val shapeType: Int // 0: Rectangle, 1: Circle, 2: Triangle
)

@Composable
fun ConfettiRain(
    modifier: Modifier = Modifier,
    durationMillis: Long = 4500
) {
    var isRunning by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(durationMillis)
        isRunning = false
    }

    if (!isRunning) return

    val colors = listOf(
        Color(0xFFEAB308), // Gold/Yellow
        Color(0xFFEF4444), // Red
        Color(0xFF3B82F6), // Blue
        Color(0xFF10B981), // Green
        Color(0xFF8B5CF6), // Purple
        Color(0xFFEC4899), // Pink
        Color(0xFFF97316)  // Orange
    )

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()

        if (width > 0f && height > 0f) {
            val particles = remember {
                List(75) {
                    ConfettiParticle(
                        x = Random.nextFloat() * width,
                        y = -Random.nextFloat() * (height * 0.7f) - 50f, // staggered starting heights above screen
                        color = colors.random(),
                        size = (12..28).random().toFloat(),
                        speedY = (6..14).random().toFloat(),
                        speedX = (-3..3).random().toFloat(),
                        rotationSpeed = (-8..8).random().toFloat(),
                        shapeType = (0..2).random()
                    )
                }
            }

            // Infinite transition drives frame ticks for custom composition updates
            val infiniteTransition = rememberInfiniteTransition(label = "ConfettiTick")
            val frameTrigger = infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "FrameAnimator"
            )

            Canvas(modifier = Modifier.fillMaxSize()) {
                val unused = frameTrigger.value // Read state to force recomposition per frame tick

                particles.forEach { particle ->
                    // Particle kinematics
                    particle.y += particle.speedY
                    particle.x += particle.speedX
                    particle.rotation += particle.rotationSpeed

                    // Edge wrap logic
                    if (particle.y > height) {
                        particle.y = -particle.size * 2
                        particle.x = Random.nextFloat() * width
                    }
                    if (particle.x < -particle.size) {
                        particle.x = width + particle.size
                    } else if (particle.x > width + particle.size) {
                        particle.x = -particle.size
                    }

                    // Render rotated shape
                    rotate(degrees = particle.rotation, pivot = Offset(particle.x, particle.y)) {
                        when (particle.shapeType) {
                            0 -> { // Elegant Ribbon
                                drawRect(
                                    color = particle.color,
                                    topLeft = Offset(particle.x, particle.y),
                                    size = Size(particle.size, particle.size / 2.5f)
                                )
                            }
                            1 -> { // Shiny Circle
                                drawCircle(
                                    color = particle.color,
                                    radius = particle.size / 2.5f,
                                    center = Offset(particle.x, particle.y)
                                )
                            }
                            2 -> { // Majestic Triangle
                                val path = Path().apply {
                                    moveTo(particle.x, particle.y)
                                    lineTo(particle.x - particle.size / 2, particle.y + particle.size)
                                    lineTo(particle.x + particle.size / 2, particle.y + particle.size)
                                    close()
                                }
                                drawPath(path = path, color = particle.color)
                            }
                        }
                    }
                }
            }
        }
    }
}

// Custom animated action card
@Composable
fun EduActionCard(
    title: String,
    subtitle: String,
    emoji: String,
    colorScheme: Brush,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "click_scale"
    )

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(124.dp)
            .scale(scale)
            .shadow(4.dp, RoundedCornerShape(20.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
                onClick = onClick
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme)
                .padding(14.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.75f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 18.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            fontSize = 12.sp
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Mainkan",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            // Big background emoji
            Text(
                text = emoji,
                fontSize = 54.sp,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 4.dp, y = 4.dp)
                    .rotate(10f)
            )
        }
    }
}

// --- 1. Dashboard Screen ---

@Composable
fun DashboardScreen(
    viewModel: EduViewModel,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    var showEditNameDialog by remember { mutableStateOf(false) }
    var currentNameInput by remember { mutableStateOf(progress?.name ?: "") }
    var currentAgeInput by remember { mutableStateOf(progress?.age?.toString() ?: "7") }
    var localLanguageInput by remember { mutableStateOf(progress?.language ?: "id") }
    var showInfoDialog by remember { mutableStateOf(false) }
    var showInstallDialog by remember { mutableStateOf(false) }

    val lang = progress?.language ?: "id"

    LaunchedEffect(progress) {
        if (progress != null) {
            currentNameInput = progress.name
            currentAgeInput = progress.age.toString()
            localLanguageInput = progress.language
        }
    }

    Scaffold(
        bottomBar = {
            // Elegant bottom status triggers
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .navigationBarsPadding()
                    .padding(vertical = 8.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = { showInfoDialog = true },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.testTag("about_app_button")
                ) {
                    Icon(Icons.Default.Info, contentDescription = Localization.get(lang, "about_btn"), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(Localization.get(lang, "about_btn"), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }

                VerticalDivider(modifier = Modifier.height(20.dp))

                TextButton(
                    onClick = { showInstallDialog = true },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.tertiary),
                    modifier = Modifier.testTag("settings_install_button")
                ) {
                    Icon(Icons.Default.Share, contentDescription = Localization.get(lang, "install_btn"), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(Localization.get(lang, "install_btn"), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }

                VerticalDivider(modifier = Modifier.height(20.dp))

                TextButton(
                    onClick = { viewModel.navigateTo(Screen.Achievements) },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.testTag("achievements_nav_button")
                ) {
                    Icon(Icons.Default.Star, contentDescription = Localization.get(lang, "piala"), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(Localization.get(lang, "pencapaian"), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            MovingShapesBackground()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
            // Profile & Progress section with settings trigger enabled in header
            EduHeader(
                progress = progress,
                title = Localization.get(lang, "app_title"),
                onSettingsClick = { showEditNameDialog = true }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Intro Greeting & Edit Name action
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = Localization.get(lang, "welcome"),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "${progress?.name ?: "Sobat Edu"}! 👋",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    }

                    IconButton(
                        onClick = { showEditNameDialog = true },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                            .testTag("edit_name_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Ubah Nama",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // --- Premium Featured Daily Challenge Hero Card (Daily Quest) ---
                val currentDate = remember { getCurrentDateString() }
                val isCompletedToday = progress?.lastDailyChallengeDate == currentDate

                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = EduSecondary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(2.dp, RoundedCornerShape(24.dp))
                        .testTag("card_daily_quest")
                        .clickable {
                            viewModel.startDailyChallenge(currentDate)
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column {
                            // Badge with Earthy Green Palette
                            Surface(
                                color = EduPrimary,
                                shape = RoundedCornerShape(100.dp),
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Text(
                                    text = if (lang == "id") "MISI HARIAN 🌟" else "DAILY QUEST 🌟",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    ),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }

                            Text(
                                text = Localization.get(lang, "daily_title"),
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF1A1C16),
                                    fontSize = 20.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = if (isCompletedToday) {
                                    if (lang == "id") "Luar biasa! Kamu telah melengkapi misi hari ini. Kembali besok untuk tantangan berikutnya! 🎉" else "Awesome! You have completed today's quest. Come back tomorrow for the next challenge! 🎉"
                                } else {
                                    if (lang == "id") "Kuis 1 pertanyaan pilihan ganda unik kiriman Gemini AI. Dapatkan +5 Stars ⭐ & +30 XP ⚡!" else "1 unique multiple choice question quiz powered by Gemini AI. Get +5 Stars ⭐ & +30 XP ⚡!"
                                },
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = EduPrimary,
                                    fontWeight = FontWeight.Medium
                                )
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (isCompletedToday) Icons.Default.CheckCircle else Icons.Default.PlayArrow,
                                        contentDescription = if (isCompletedToday) "Selesai" else "Mulai",
                                        tint = EduPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (isCompletedToday) {
                                            if (lang == "id") "Misi Selesai" else "Quest Completed"
                                        } else {
                                            if (lang == "id") "Sentuh Untuk Mulai" else "Tap To Start"
                                        },
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = EduPrimary
                                    )
                                }

                                Surface(
                                    shape = CircleShape,
                                    color = EduPrimary,
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                            contentDescription = "Buka",
                                            tint = Color.White,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Module Action Title
                Text(
                    text = Localization.get(lang, "choose_adventure"),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Core Feature 1: EduBot Chat Room
                EduActionCard(
                    title = Localization.get(lang, "edubot_title"),
                    subtitle = Localization.get(lang, "edubot_desc"),
                    emoji = "🤖",
                    colorScheme = Brush.linearGradient(
                        colors = if (androidx.compose.foundation.isSystemInDarkTheme()) {
                            listOf(Color(0xFF2E2A4A), Color(0xFF443D6F))
                        } else {
                            listOf(Color(0xFFE5E2FF), Color(0xFFC4BFFF))
                        }
                    ),
                    onClick = { viewModel.navigateTo(Screen.EduBotChat) },
                    modifier = Modifier.testTag("card_edubot")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Core Feature 2: Quiz Arena
                EduActionCard(
                    title = Localization.get(lang, "quiz_title"),
                    subtitle = Localization.get(lang, "quiz_desc"),
                    emoji = "🏆",
                    colorScheme = Brush.linearGradient(
                        colors = if (androidx.compose.foundation.isSystemInDarkTheme()) {
                            listOf(Color(0xFF4C2A31), Color(0xFF723D4D))
                        } else {
                            listOf(Color(0xFFFFE5EC), Color(0xFFFFC2D1))
                        }
                    ),
                    onClick = { viewModel.navigateTo(Screen.QuizSettings) },
                    modifier = Modifier.testTag("card_quiz")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Core Feature 2.5: Petualangan Matematika
                EduActionCard(
                    title = Localization.get(lang, "math_title"),
                    subtitle = Localization.get(lang, "math_desc"),
                    emoji = "🦁",
                    colorScheme = Brush.linearGradient(
                        colors = if (androidx.compose.foundation.isSystemInDarkTheme()) {
                            listOf(Color(0xFF1B3D2F), Color(0xFF2C5E4A))
                        } else {
                            listOf(Color(0xFFECFDF5), Color(0xFFA7F3D0))
                        }
                    ),
                    onClick = { viewModel.navigateTo(Screen.MathSettings) },
                    modifier = Modifier.testTag("card_math")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Core Feature 2.8: Tantangan Bahasa Inggris
                EduActionCard(
                    title = Localization.get(lang, "english_title"),
                    subtitle = Localization.get(lang, "english_desc"),
                    emoji = "🐥",
                    colorScheme = Brush.linearGradient(
                        colors = if (androidx.compose.foundation.isSystemInDarkTheme()) {
                            listOf(Color(0xFF1A384A), Color(0xFF255776))
                        } else {
                            listOf(Color(0xFFEDF8FF), Color(0xFF8AD1FC))
                        }
                    ),
                    onClick = { viewModel.navigateTo(Screen.EnglishSettings) },
                    modifier = Modifier.testTag("card_english")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Core Feature 2.9: Tantangan Agama Islam & Al-Qur'an
                EduActionCard(
                    title = Localization.get(lang, "islamic_title"),
                    subtitle = Localization.get(lang, "islamic_desc"),
                    emoji = "🕊️",
                    colorScheme = Brush.linearGradient(
                        colors = if (androidx.compose.foundation.isSystemInDarkTheme()) {
                            listOf(Color(0xFF163E36), Color(0xFF21584C))
                        } else {
                            listOf(Color(0xFFF0FDFA), Color(0xFF99F6E4))
                        }
                    ),
                    onClick = { viewModel.navigateTo(Screen.IslamicSettings) },
                    modifier = Modifier.testTag("card_islamic")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Core Feature 2.95: Math Land RPG Petualangan
                EduActionCard(
                    title = Localization.get(lang, "math_land_title"),
                    subtitle = Localization.get(lang, "math_land_desc"),
                    emoji = "🏰",
                    colorScheme = Brush.linearGradient(
                        colors = if (androidx.compose.foundation.isSystemInDarkTheme()) {
                            listOf(Color(0xFF4C391B), Color(0xFF725C26))
                        } else {
                            listOf(Color(0xFFFEF3C7), Color(0xFFFDE68A))
                        }
                    ),
                    onClick = { viewModel.navigateTo(Screen.MathLandMap) },
                    modifier = Modifier.testTag("card_math_land")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Core Feature 3: Tebak Kata
                EduActionCard(
                    title = Localization.get(lang, "puzzle_title"),
                    subtitle = Localization.get(lang, "puzzle_desc"),
                    emoji = "🐶",
                    colorScheme = Brush.linearGradient(
                        colors = if (androidx.compose.foundation.isSystemInDarkTheme()) {
                            listOf(Color(0xFF4A2B45), Color(0xFF6F3F66))
                        } else {
                            listOf(Color(0xFFFFF1F2), Color(0xFFFECDD3))
                        }
                    ),
                    onClick = { 
                        viewModel.navigateTo(Screen.WordPuzzle) 
                        viewModel.startNewPuzzle("Sains Cilik 🧪🐢")
                    },
                    modifier = Modifier.testTag("card_puzzle")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Core Feature 4: Detektif Kosakata AI (Hangman)
                EduActionCard(
                    title = Localization.get(lang, "hangman_title"),
                    subtitle = Localization.get(lang, "hangman_desc"),
                    emoji = "🦊",
                    colorScheme = Brush.linearGradient(
                        colors = if (androidx.compose.foundation.isSystemInDarkTheme()) {
                            listOf(Color(0xFF4D2A43), Color(0xFF6E395F))
                        } else {
                            listOf(Color(0xFFFFF1F2), Color(0xFFFBCFE8))
                        }
                    ),
                    onClick = { 
                        viewModel.navigateTo(Screen.Hangman) 
                        viewModel.startNewHangman("Sains Cilik 🧪🐢")
                    },
                    modifier = Modifier.testTag("card_hangman")
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Quick Reset database trigger (offline testing / mock clean up)
                OutlinedButton(
                    onClick = { viewModel.resetData() },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
                    border = BorderStroke(1.dp, Color.LightGray),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("button_reset_database")
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Reset Progress Petualangan", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
    }

    // Edit Profile (Settings) Dialog
    if (showEditNameDialog) {
        AlertDialog(
            onDismissRequest = { showEditNameDialog = false },
            icon = { Text("👶⚙️", fontSize = 44.sp) },
            title = { Text(Localization.get(localLanguageInput, "settings_title"), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center) },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = Localization.get(localLanguageInput, "settings_subtitle"),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    OutlinedTextField(
                        value = currentNameInput,
                        onValueChange = { currentNameInput = it },
                        label = { Text(Localization.get(localLanguageInput, "name_label")) },
                        placeholder = { Text(Localization.get(localLanguageInput, "name_placeholder")) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_edit_name")
                    )

                    OutlinedTextField(
                        value = currentAgeInput,
                        onValueChange = { input ->
                            // Only allow numbers, maximum 2 digits
                            if (input.isEmpty() || (input.all { it.isDigit() } && input.length <= 2)) {
                                currentAgeInput = input
                            }
                        },
                        label = { Text(Localization.get(localLanguageInput, "age_label")) },
                        placeholder = { Text(Localization.get(localLanguageInput, "age_placeholder")) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_edit_age")
                    )

                    Text(
                        text = Localization.get(localLanguageInput, "lang_section_title"),
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("id", "en").forEach { langOption ->
                            val isSelected = localLanguageInput == langOption
                            val label = if (langOption == "id") "Bahasa Indonesia 🇮🇩" else "English 🇬🇧"
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(
                                    width = 2.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                                ),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { localLanguageInput = langOption }
                                    .testTag("lang_option_$langOption")
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    val parsedAge = currentAgeInput.toIntOrNull() ?: 7
                    val categoryText = when {
                        parsedAge in 3..6 -> Localization.get(localLanguageInput, "level_1_3")
                        parsedAge in 7..9 -> Localization.get(localLanguageInput, "level_4_6")
                        parsedAge in 10..12 -> Localization.get(localLanguageInput, "level_7_9")
                        parsedAge >= 13 -> Localization.get(localLanguageInput, "level_10_plus")
                        else -> Localization.get(localLanguageInput, "level_4_6")
                    }
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("👉", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${Localization.get(localLanguageInput, "level_auto_adjust")}\n$categoryText",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                    TextButton(
                        onClick = {
                            showEditNameDialog = false
                            showInstallDialog = true
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.tertiary),
                        modifier = Modifier.fillMaxWidth().testTag("profile_install_shortcut")
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Instal")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(Localization.get(localLanguageInput, "install_prompt"), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val finalName = currentNameInput.ifBlank { progress?.name ?: "Sobat Edu" }
                        val finalAge = currentAgeInput.toIntOrNull() ?: progress?.age ?: 7
                        viewModel.updateUserProfile(finalName, finalAge)
                        viewModel.updateLanguage(localLanguageInput)
                        showEditNameDialog = false
                    },
                    modifier = Modifier.testTag("confirm_edit_profile")
                ) {
                    Text(Localization.get(localLanguageInput, "save_btn"))
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditNameDialog = false }) {
                    Text(Localization.get(localLanguageInput, "cancel_btn"))
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }

    // Info Dialog
    if (showInfoDialog) {
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            icon = {
                Image(
                    painter = painterResource(id = com.example.R.drawable.img_app_icon),
                    contentDescription = "EP Knowledge Land Logo",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            },
            title = { Text("Tentang EP Knowledge Land by .Maju", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center) },
            text = {
                Text(
                    text = "EP Knowledge Land by .Maju adalah platform bermain sambil belajar yang digerakkan oleh kecerdasan buatan Gemini AI. " +
                        "Diciptakan khusus agar proses belajar menjadi asyik melalui interaksi obrolan tutor EduBot, " +
                        "pembuatan kuis dinamis instan di tema apa pun, dan tebak susunan kata edukatif.\n\n" +
                        "Didesain dengan visual pastel ceria, didukung database lokal Room agar data level petualanganmu " +
                        "tersimpan aman, dan dioptimalkan agar responsif di seluruh layar Android.",
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                Button(onClick = { showInfoDialog = false }) {
                    Text("Ayo Belajar! 🎉")
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }

    // Install Application Help Dialog
    if (showInstallDialog) {
        val context = LocalContext.current
        AlertDialog(
            onDismissRequest = { showInstallDialog = false },
            icon = { Text("📱✨", fontSize = 44.sp) },
            title = { Text("Petunjuk Pasang di HP Android 🚀", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center) },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "EP Knowledge Land adalah Aplikasi Android Native (.APK) 100%. Sangat ringan, aman, dan dioptimalkan penuh untuk berjalan sangat lancar di Android 9 ke atas (Android 10, 11, 12, 13, 14, 15+).",
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 18.sp
                    )

                    Text(
                        text = "📥 Cara Mengunduh & Memasang APK:",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("1️⃣  Buka link Shared App ini di browser Google Chrome ponsel Android Anda.", fontSize = 13.sp)
                        Text("2️⃣  Ketuk tombol menu **titik tiga (...)** di sudut kanan atas panel Google AI Studio di browser ponsel Anda.", fontSize = 13.sp)
                        Text("3️⃣  Pilih menu **'Export Project'** lalu pilih **'Generate APK'**. Tunggu sekitar 1 menit hingga proses pembuatan APK oleh server selesai.", fontSize = 13.sp)
                        Text("4️⃣  Setelah selesai, file APK akan terdownload secara otomatis (atau klik tombol Unduh).", fontSize = 13.sp)
                        Text("5️⃣  Buka file APK yang diunduh lalu ketuk **'Pasang/Install'**.", fontSize = 13.sp)
                    }

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.25f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                "⚠️ Solusi jika Gagal Menginstal / Error:",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error
                            )
                            Text(
                                "• HAPUS (uninstall) versi lama EP Knowledge Land / EduPlay terlebih dahulu di ponsel Anda jika sebelumnya pernah menginstal prototype versi lama. Benturan sidik jari file (signature conflict) adalah alasan utama error 'Aplikasi Tidak Terpasang'.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 15.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                "• Lewati Blokir Play Protect: Karena ini aplikasi edukasi kustom, saat menginstal mungkin muncul 'Blocked by Play Protect'. Ketuk 'Detail Selengkapnya' (More details) lalu pilih 'Tetap Instal' (Install anyway).",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 15.sp
                            )
                        }
                    }

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                "💡 Informasi Teknis PWA & Browser:",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "EP Knowledge Land didevelop sebagai aplikasi Native Android (.apk), bukan situs web PWA biasa, sehingga perintah instan browser 'beforeinstallprompt' dari web tidak dapat memicu sistem file kustom Android secara langsung. Cara paling handal adalah menu 'Export Project' -> 'Generate APK'.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val playUrl = "https://ais-pre-xgvn3nwcjbpjo257b6tky4-357180471749.asia-southeast1.run.app"
                        try {
                            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(playUrl))
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            // ignore / fallback toast
                        }
                        showInstallDialog = false
                    },
                    modifier = Modifier.testTag("open_shared_url_button")
                ) {
                    Icon(Icons.Default.Share, contentDescription = "Unduh")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Salin Link Shared App 🌐")
                }
            },
            dismissButton = {
                TextButton(onClick = { showInstallDialog = false }) {
                    Text("Tutup")
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }
}

// --- 2. Chat Screen ---

@Composable
fun EduBotChatScreen(
    viewModel: EduViewModel,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    val messages by viewModel.chatMessages.collectAsStateWithLifecycle()
    val isLoading by viewModel.isChatLoading.collectAsStateWithLifecycle()
    var textInput by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val lang = progress?.language ?: "id"

    // Suggestions chips list for quick prompt questions
    val chips = if (lang == "id") {
        listOf(
            "Kenapa langit biru? 🌌",
            "Bagaimana hujan terbentuk? 🌧️",
            "Cerita T-Rex terseram! 🦖",
            "Bumi berputar seberapa cepat? 🌍"
        )
    } else {
        listOf(
            "Why is the sky blue? 🌌",
            "How does rain form? 🌧️",
            "Scariest T-Rex story! 🦖",
            "How fast does Earth rotate? 🌍"
        )
    }

    // Auto-scroll to lowest message
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(messages.size - 1)
            }
        }
    }

    Scaffold(
        topBar = {
            EduHeader(
                progress = progress,
                onBack = { viewModel.navigateTo(Screen.Dashboard) },
                title = if (lang == "id") "Tanya EduBot 🤖" else "Ask EduBot 🤖"
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Suggestion pills row
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    chips.forEach { chipPrompt ->
                        AssistChip(
                            onClick = {
                                if (!isLoading) {
                                    viewModel.sendChatMessage(chipPrompt)
                                }
                            },
                            label = { Text(chipPrompt, fontWeight = FontWeight.SemiBold) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.primary
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
            }

            // Message Board
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(messages) { msg ->
                    val isBot = msg.sender == "edubot"
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (isBot) Arrangement.Start else Arrangement.End
                    ) {
                        Card(
                            shape = if (isBot) {
                                RoundedCornerShape(16.dp, 16.dp, 16.dp, 2.dp)
                            } else {
                                RoundedCornerShape(16.dp, 16.dp, 2.dp, 16.dp)
                            },
                            colors = CardDefaults.cardColors(
                                containerColor = if (isBot) {
                                    if (isSystemInDarkTheme()) BubbleBotDark else BubbleBot
                                } else {
                                    if (isSystemInDarkTheme()) BubbleUserDark else BubbleUser
                                }
                            ),
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .shadow(2.dp, RoundedCornerShape(16.dp))
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = if (isBot) "🧠 EduBot AI" else "🧒 ${progress?.name ?: (if (lang == "id") "Kamu" else "You")}",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 11.sp,
                                    color = if (isBot) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = msg.text,
                                    fontSize = 15.sp,
                                    lineHeight = 22.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                // If loading AI response
                if (isLoading) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(12.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (lang == "id") "EduBot sedang merangkai jawaban cerdas... 🤖⭐" else "EduBot is crafting a smart answer... 🤖⭐",
                                fontSize = 12.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Chat input control panel
            Card(
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .navigationBarsPadding()
                        .imePadding()
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    IconButton(
                        onClick = { viewModel.clearChat() },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray.copy(alpha = 0.3f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = if (lang == "id") "Bersihkan Obrolan" else "Clear Chat",
                            tint = Color.DarkGray
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    TextField(
                        value = textInput,
                        onValueChange = { textInput = it },
                        placeholder = { Text(if (lang == "id") "Ketik pertanyaan seru di sini..." else "Type an exciting question here...") },
                        maxLines = 3,
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(24.dp))
                            .testTag("chat_input_textfield"),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Send
                        ),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                if (textInput.isNotBlank() && !isLoading) {
                                    viewModel.sendChatMessage(textInput)
                                    textInput = ""
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                }
                            }
                        )
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    IconButton(
                        onClick = {
                            if (textInput.isNotBlank() && !isLoading) {
                                viewModel.sendChatMessage(textInput)
                                textInput = ""
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }
                        },
                        enabled = textInput.isNotBlank() && !isLoading,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = Color.LightGray
                        ),
                        modifier = Modifier
                            .size(48.dp)
                            .testTag("chat_send_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = if (lang == "id") "Kirim" else "Send",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

// --- 3. Quiz Settings Screen ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizSettingsScreen(
    viewModel: EduViewModel,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    var customTopicInput by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val activeAgeGroup by viewModel.selectedAgeGroup.collectAsStateWithLifecycle()
    val unlockedAgeGroupIndex by viewModel.unlockedAgeGroupIndex.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val lang = progress?.language ?: "id"

    val ageGroups = if (lang == "id") {
        listOf(
            "PAUD & TK 🦄 (Umur 3-6)",
            "SD Kelas Rendah 🎒 (Umur 7-9)",
            "SD Kelas Tinggi 📚 (Umur 10-12)",
            "SMP & Remaja 🧠 (Umur 13+)"
        )
    } else {
        listOf(
            "Preschool & Kindergarten 🦄 (Age 3-6)",
            "Elementary Lower Class 🎒 (Age 7-9)",
            "Elementary Upper Class 📚 (Age 10-12)",
            "Junior High & Teens 🧠 (Age 13+)"
        )
    }

    val prebuiltTopics = if (lang == "id") {
        listOf(
            Triple("Matematika ➗", "Hitung penjumlahan, perkalian & geometri ceria.", "Matematika Dasar Sekolah"),
            Triple("Sains & Alam 🧪", "Pelajari fotosintesis, iklim, magnet & gaya alam.", "Sains dan Biologi Dasar"),
            Triple("Inggris Kids 🇬🇧", "Kuasai kosakata sederhana hewan, warna & benda.", "Bahasa Inggris Kosakata Dasar"),
            Triple("Sejarah Kuno 🏛️", "Candi Borobudur, kisah kerajaan purba & pahlawan.", "Sejarah peradaban kuno dan pahlawan"),
            Triple("Geografi Atlas 🗺️", "Peta bumi, benua, lautan luas & kedaulatan kita.", "Geografi dunia dasar"),
            Triple("Luar Angkasa 🪐", "Misteri bintang, planet tata surya & komet.", "Astronomi bintang dan tata surya"),
            Triple("Seni & Musik 🎨", "Warna-warni lukisan & alat musik tradisional.", "Seni rupa dan musik dasar"),
            Triple("Gizi Sehat 🍎", "Zat gizi seimbang, vitamin & bugar berstamina.", "Kesehatan tubuh dan nutrisi seimbang")
        )
    } else {
        listOf(
            Triple("Mathematics ➗", "Arithmetic sum, multiplication & cheerful geometry.", "Matematika Dasar Sekolah"),
            Triple("Science & Nature 🧪", "Learn photosynthesis, climate, magnet & natural force.", "Sains dan Biologi Dasar"),
            Triple("English Kids 🇬🇧", "Master simple vocabulary of animals, colors & objects.", "Bahasa Inggris Kosakata Dasar"),
            Triple("Ancient History 🏛️", "Borobudur Temple, stories of ancient kingdoms & heroes.", "Sejarah peradaban kuno dan pahlawan"),
            Triple("Geography Atlas 🗺️", "Map of the Earth, continents, oceans & our sovereignty.", "Geografi dunia dasar"),
            Triple("Outer Space 🪐", "Mystery of stars, planets of the solar system & comets.", "Astronomi bintang dan tata surya"),
            Triple("Art & Music 🎨", "Colorful paintings & traditional musical instruments.", "Seni rupa dan musik dasar"),
            Triple("Healthy Nutrition 🍎", "Balanced nutrition, vitamins & healthy lifestyle.", "Kesehatan tubuh and nutrisi seimbang")
        )
    }

    val topicStyles = listOf(
        Triple("➗", Color(0xFFFFEBB2), Color(0xFF725C11)), // Math
        Triple("🧪", Color(0xFFDDE6C7), Color(0xFF384318)), // Science
        Triple("🇬🇧", Color(0xFFD1E8FF), Color(0xFF003F74)), // English
        Triple("🏛️", Color(0xFFF5E1D3), Color(0xFF6B4500)), // History
        Triple("🗺️", Color(0xFFE2F0D9), Color(0xFF2E4E1C)), // Geography
        Triple("🪐", Color(0xFFE8DEF8), Color(0xFF4F378B)), // Space
        Triple("🎨", Color(0xFFFFD8E4), Color(0xFF7D1212)), // Art
        Triple("🍎", Color(0xFFFFDAD6), Color(0xFFBA1A1A))  // Health
    )

    Scaffold(
        topBar = {
            EduHeader(
                progress = progress,
                onBack = { viewModel.navigateTo(Screen.Dashboard) },
                title = if (lang == "id") "Arena Kuis AI 🏆" else "AI Quiz Arena 🏆"
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            MovingShapesBackground()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("🏆", fontSize = 54.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = if (lang == "id") "Latih Otakmu dengan Kuis Dinamis!" else "Train Your Brain with Dynamic Quizzes!",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = if (lang == "id") "Asisten kecerdasan buatan Gemini akan menyusun 5 pertanyaan pilihan ganda interaktif dengan ulasan edukatif yang mengasyikkan!" else "Gemini AI assistant will compose 5 interactive multiple-choice questions with exciting educational reviews!",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Section 1: Selector Tingkat Belajar (Age selection chips)
            Text(
                text = if (lang == "id") "1. Pilih Tingkat Belajar & Usia:" else "1. Select Learning Level & Age:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = if (lang == "id") "Tingkat kognitif teka-teki kuis disesuaikan khusus sesuai pilihan usia." else "Cognitive difficulty of the quiz is dynamically adapted to the selected age group.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Selectable age levels in clean grids or lists with Lock mechanics
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                ageGroups.forEachIndexed { index, age ->
                    val isLocked = index > unlockedAgeGroupIndex
                    val isSelected = activeAgeGroup == age && !isLocked
                    Surface(
                        onClick = {
                            if (isLocked) {
                                val prevLevelName = ageGroups.getOrNull(index - 1) ?: "Tingkat sebelumnya"
                                val msg = if (lang == "id") {
                                    "Tingkat ini masih terkunci! Selesaikan & LULUS (minimal 3 benar) kuis di tingkat sebelumnya ($prevLevelName) dulu ya! 🎒🔑"
                                } else {
                                    "This level is still locked! Pass (at least 3 correct answers) on the previous level ($prevLevelName) first! 🎒🔑"
                                }
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                            } else {
                                viewModel.setAgeGroup(age)
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isLocked) {
                            Color(0xFFF3F4F6)
                        } else if (isSelected) {
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                        } else {
                            MaterialTheme.colorScheme.surface
                        },
                        border = BorderStroke(
                            width = if (isSelected) 1.8.dp else 1.dp,
                            color = if (isLocked) {
                                Color.LightGray.copy(alpha = 0.3f)
                            } else if (isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                Color.LightGray.copy(alpha = 0.6f)
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(if (isSelected) 2.dp else 0.5.dp, RoundedCornerShape(12.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (isLocked) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Terkunci",
                                    tint = Color.Gray.copy(alpha = 0.6f),
                                    modifier = Modifier.size(20.dp)
                                )
                            } else {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { 
                                        viewModel.setAgeGroup(age)
                                    },
                                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = age,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 14.sp,
                                color = if (isLocked) {
                                    Color.Gray.copy(alpha = 0.7f)
                                } else if (isSelected) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onBackground
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Section 2: Choose Subject (The academic options grid styled nicely)
            Text(
                text = if (lang == "id") "2. Pilih Bidang Akademik Terpopuler:" else "2. Select Most Popular Academic Field:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Create 2-column Grid manually by dividing cards into chunk of 2
            prebuiltTopics.chunked(2).forEachIndexed { rowIndex, rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowItems.forEachIndexed { colIndex, (title, desc, query) ->
                        val itemIndex = rowIndex * 2 + colIndex
                        val style = topicStyles.getOrNull(itemIndex) ?: Triple("📝", Color.LightGray, Color.DarkGray)

                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.4f)),
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 5.dp)
                                .shadow(2.dp, RoundedCornerShape(16.dp))
                                .clickable { viewModel.startNewQuiz(query) }
                                .testTag("quiz_topic_${itemIndex}")
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            ) {
                                // Rounded Box for visual indicator
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(style.second)
                                ) {
                                    Text(text = style.first, fontSize = 16.sp)
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = desc,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray,
                                    fontSize = 11.sp,
                                    lineHeight = 14.sp,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Section 3: Custom Text input
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.08f)),
                border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = if (lang == "id") "🚀 Subjek Akademik Kustom (Racik Sendiri)" else "🚀 Custom Academic Subject (Mix Your Own)",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (lang == "id") "Ingin kuis tentang 'Dinosaurus T-Rex', 'Sistem Tata Surya', 'Mitologi Yunani', atau 'Pulau Sumatera'? Tulis di bawah!" else "Want a quiz about 'T-Rex Dinosaur', 'Solar System', 'Greek Mythology', or 'Sumatra'? Write below!",
                        fontSize = 11.sp,
                        lineHeight = 15.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = customTopicInput,
                            onValueChange = { customTopicInput = it },
                            placeholder = { Text(if (lang == "id") "Contoh: Robot Transformers" else "Example: Transformers Robot", fontSize = 13.sp) },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("quiz_topic_input"),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                            keyboardActions = KeyboardActions(
                                onGo = {
                                    if (customTopicInput.isNotBlank()) {
                                        keyboardController?.hide()
                                        focusManager.clearFocus()
                                        viewModel.startNewQuiz(customTopicInput)
                                    }
                                }
                            )
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                if (customTopicInput.isNotBlank()) {
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                    viewModel.startNewQuiz(customTopicInput)
                                }
                            },
                            enabled = customTopicInput.isNotBlank(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .height(56.dp)
                                .testTag("quiz_generate_button")
                        ) {
                            Text(if (lang == "id") "Buat 🦾" else "Create 🦾", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
    }
}

// --- 4. Quiz Play Screen (The Arena) ---

@Composable
fun QuizPlayScreen(
    viewModel: EduViewModel,
    topic: String,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    val questions by viewModel.quizQuestions.collectAsStateWithLifecycle()
    val isLoading by viewModel.isQuizLoading.collectAsStateWithLifecycle()
    val currentIndex by viewModel.currentQuestionIndex.collectAsStateWithLifecycle()
    val selectedIndex by viewModel.selectedAnswerIndex.collectAsStateWithLifecycle()
    val isSubmitted by viewModel.isAnswerSubmitted.collectAsStateWithLifecycle()
    val activeScore by viewModel.quizScore.collectAsStateWithLifecycle()
    val explanation by viewModel.quizExplanationExplanation.collectAsStateWithLifecycle()

    val lang = progress?.language ?: "id"

    Scaffold(
        topBar = {
            EduHeader(
                progress = progress,
                onBack = { viewModel.navigateTo(Screen.QuizSettings) },
                title = if (lang == "id") "Arena Kuis: ${topic.take(15)}..." else "Quiz Arena: ${topic.take(15)}..."
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        if (isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Rocket climbing animation or simple cycle
                Text("🚀", fontSize = 72.sp)
                Spacer(modifier = Modifier.height(20.dp))
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = if (lang == "id") "Gemini AI sedang merancang 5 Soal Cerdas khusus bertema: '$topic'..." else "Gemini AI is designing 5 Smart Questions themed around: '$topic'...",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = if (lang == "id") "Sabar ya, ini butuh waktu beberapa detik agar kualitas soal kuis maksimal!" else "Patience, please! This takes a few seconds to deliver high quality quiz challenges!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        } else if (questions.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("⚠️", fontSize = 64.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text(if (lang == "id") "Koneksi gagal atau kuis ditolak oleh bot. Mohon coba kuis subjek lain ya!" else "Connection failed or quiz was denied by bot. Please try another subject!", textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.navigateTo(Screen.QuizSettings) }) {
                    Text(if (lang == "id") "Pilih Ulang Kuis Plan 🗺️" else "Retake Quiz Subject 🗺️")
                }
            }
        } else {
            val q = questions.getOrNull(currentIndex)
            if (q != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(18.dp)
                ) {
                    // Question index & progress bar
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (lang == "id") "Pertanyaan ${currentIndex + 1} dari ${questions.size}" else "Question ${currentIndex + 1} of ${questions.size}",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 14.sp
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = "Benar", tint = Color(0xFFFACC15), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(text = "Score: $activeScore", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val ratio = (currentIndex + 1).toFloat() / questions.size
                    LinearProgressIndicator(
                        progress = { ratio },
                        trackColor = Color.LightGray.copy(alpha = 0.4f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Question Card Section
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(3.dp, RoundedCornerShape(20.dp))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = q.question,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 28.sp,
                                    fontSize = 18.sp
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Answer Options List
                    q.options.forEachIndexed { optIndex, optionText ->
                        // State color calculation
                        val borderStroke = when {
                            selectedIndex == optIndex && !isSubmitted -> BorderStroke(2.5.dp, MaterialTheme.colorScheme.primary)
                            isSubmitted && optIndex == q.answerIndex -> BorderStroke(2.5.dp, EduSuccess)
                            isSubmitted && selectedIndex == optIndex && selectedIndex != q.answerIndex -> BorderStroke(2.5.dp, EduError)
                            else -> BorderStroke(1.dp, Color.LightGray)
                        }

                        val containerColor = when {
                            selectedIndex == optIndex && !isSubmitted -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                            isSubmitted && optIndex == q.answerIndex -> EduSuccess.copy(alpha = 0.15f)
                            isSubmitted && selectedIndex == optIndex && selectedIndex != q.answerIndex -> EduError.copy(alpha = 0.15f)
                            else -> MaterialTheme.colorScheme.surface
                        }

                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = containerColor),
                            border = borderStroke,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .shadow(1.dp, RoundedCornerShape(16.dp))
                                .clickable { viewModel.selectQuizAnswer(optIndex) }
                                .testTag("quiz_option_${optIndex}")
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = when (optIndex) {
                                        0 -> "🔵  "
                                        1 -> "🔴  "
                                        2 -> "🟡  "
                                        else -> "🟢  "
                                    },
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = optionText,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.weight(1f)
                                )

                                if (isSubmitted) {
                                    if (optIndex == q.answerIndex) {
                                        Text("✅", fontSize = 20.sp)
                                    } else if (selectedIndex == optIndex) {
                                        Text("❌", fontSize = 20.sp)
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Feedback Explanation and Actions
                    if (isSubmitted) {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.25f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = if (lang == "id") {
                                        if (selectedIndex == q.answerIndex) "Jawabanmu Benar! Benar-benar Hebat 🧠✨" else "Aduh, Salah Dikit! Belajar terus ya ☘️"
                                    } else {
                                        if (selectedIndex == q.answerIndex) "Your Answer is Correct! Truly Brainy 🧠✨" else "Oops, Close One! Keep learning ☘️"
                                    },
                                    fontWeight = FontWeight.Bold,
                                    color = if (selectedIndex == q.answerIndex) EduSuccess else EduError,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = explanation ?: q.explanation,
                                    fontSize = 13.sp,
                                    lineHeight = 18.sp
                                )
                            }
                        }

                        Button(
                            onClick = { viewModel.nextQuizQuestion(topic) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .testTag("quiz_next_button")
                        ) {
                            Text(
                                text = if (currentIndex + 1 == questions.size) {
                                    if (lang == "id") "Selesaikan Kuis 🏁" else "Finish Quiz 🏁"
                                } else {
                                    if (lang == "id") "Pertanyaan Selanjutnya ▶️" else "Next Question ▶️"
                                },
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    } else {
                        Button(
                            onClick = { viewModel.submitQuizAnswer() },
                            enabled = selectedIndex != null,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .testTag("quiz_submit_button")
                        ) {
                            Text(if (lang == "id") "Periksa Jawaban ☑️" else "Check Answer ☑️", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}

// --- 5. Quiz Score Screen ---

@Composable
fun QuizScoreScreen(
    viewModel: EduViewModel,
    topic: String,
    score: Int,
    total: Int,
    starsEarned: Int,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    val lang = progress?.language ?: "id"

    Scaffold(
        topBar = {
            EduHeader(
                progress = progress,
                onBack = { viewModel.navigateTo(Screen.Dashboard) },
                title = if (lang == "id") "Petualangan Kuis Tamat!" else "Quiz Adventure Finished!"
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Big Emoji Trophy Celebration
                val emoji = if (score == total) "🥇👑" else if (score >= 3) "🏅✨" else "🎖️☘️"
                Text(emoji, fontSize = 96.sp, modifier = Modifier.padding(bottom = 12.dp))

                Text(
                    text = if (lang == "id") "Kamu Berhasil Menembus Arena!" else "You Successfully Beat the Arena!",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (lang == "id") "Tema Kuis: $topic" else "Quiz Theme: $topic",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Score Banner Badge
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)),
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(vertical = 8.dp)
                        .shadow(1.dp, RoundedCornerShape(24.dp))
                        .testTag("quiz_score_container")
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = if (lang == "id") "SKOR AKHIR" else "FINAL SCORE", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = Color.Gray)
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "$score",
                                fontSize = 64.sp,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(text = "/$total", fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.6f))

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(if (lang == "id") "Bintang Dapat:  " else "Stars Earned:  ", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFACC15), modifier = Modifier.size(20.dp))
                            }
                            Text(text = "+$starsEarned Stars ⭐", fontWeight = FontWeight.Black, color = Color(0xFFEAB308), fontSize = 16.sp)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = if (lang == "id") "XP Didapat: " else "XP Earned: ", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            Text(text = "+${(score * 20) + 10} XP ⚡", fontWeight = FontWeight.ExtraBold, color = Color(0xFF10B981), fontSize = 14.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Navigation Buttons
                Button(
                    onClick = { viewModel.navigateTo(Screen.Dashboard) },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(56.dp)
                        .testTag("score_back_home_button")
                ) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (lang == "id") "Kembali ke Dashboard" else "Return to Dashboard", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = { viewModel.navigateTo(Screen.QuizSettings) },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(56.dp)
                ) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Lagi")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (lang == "id") "Tantang Kuis Baru" else "Try a New Quiz", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }

            if (score == total) {
                ConfettiRain()
            }
        }
    }
}

// --- 6. Word Puzzle / Tebak Kata Screen ---

@Composable
fun WordPuzzleScreen(
    viewModel: EduViewModel,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    val currentCategory by viewModel.currentWordCategory.collectAsStateWithLifecycle()
    val puzzleState by viewModel.currentWordPuzzle.collectAsStateWithLifecycle()
    val isLoading by viewModel.isPuzzleLoading.collectAsStateWithLifecycle()
    val textInput by viewModel.puzzleAnswerInput.collectAsStateWithLifecycle()
    val feedback by viewModel.puzzleFeedback.collectAsStateWithLifecycle()
    val streak by viewModel.scrambleStreak.collectAsStateWithLifecycle()
    val score by viewModel.scrambleScore.collectAsStateWithLifecycle()
    val hint by viewModel.puzzleHint.collectAsStateWithLifecycle()
    val isHintLoading by viewModel.isHintLoading.collectAsStateWithLifecycle()

    val lang = progress?.language ?: "id"

    val wordCategories = if (lang == "id") {
        listOf(
            "Sains Cilik 🧪🐢", "Sejarah Kerajaan 🏛️🦁", "Bahasa Sahabat 🗣️🦊"
        )
    } else {
        listOf(
            "Kids Science 🧪🐢", "Kingdom History 🏛️🦁", "Friendly Language 🗣️🦊"
        )
    }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            EduHeader(
                progress = progress,
                onBack = { viewModel.navigateTo(Screen.Dashboard) },
                title = if (lang == "id") "Tebak Kata AI 🧩" else "AI Word Guess 🧩"
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Category selectors at the top
            Text(if (lang == "id") "Pilih Kategori Puzzle:" else "Select Puzzle Category:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                wordCategories.forEach { category ->
                    FilterChip(
                        selected = currentCategory == category,
                        onClick = { viewModel.startNewPuzzle(category) },
                        label = { Text(category, fontWeight = FontWeight.Bold) },
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Play board Card
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(3.dp, RoundedCornerShape(24.dp))
            ) {
                if (isLoading) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(48.dp),
                         horizontalAlignment = Alignment.CenterHorizontally,
                         verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = if (lang == "id") "AI Puzzle Maker sedang merias kata misteri..." else "AI Puzzle Maker is styling the mystery word...",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                } else if (puzzleState == null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("🧩", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(if (lang == "id") "Puzzle gagal di-load. Klik tombol di bawah untuk memancing puzzle!" else "Failed to load puzzle. Click the button below to retrieve one!")
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(onClick = { viewModel.startNewPuzzle(currentCategory) }) {
                            Text(if (lang == "id") "Mulai Ulang" else "Retry")
                        }
                    }
                } else {
                    val p = puzzleState!!
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Word categories tags, streak, and score
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "Streak: $streak 🔥",
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontSize = 12.sp
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = if (lang == "id") "Skor: $score 🏆" else "Score: $score 🏆",
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 12.sp
                                )
                            }

                            Text(
                                text = if (lang == "id") "Hadiah: 3 ⭐" else "Reward: 3 ⭐",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFEAB308)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Clue Board
                        Text(
                            text = if (lang == "id") "PETUNJUK AI PINTAR: 🤔" else "SMART AI CLUE: 🤔",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = p.clue,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Scrambled letters board
                        Text(
                            text = if (lang == "id") "HURUF COBA REKATKAN:" else "TRY TO ARRANGE THESE LETTERS:",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        // Scrambled Letters display
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = p.scrambledWord.fold("") { acc, c -> "$acc $c" }.trim(),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 2.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.4f))
                        Spacer(modifier = Modifier.height(14.dp))

                        // Word input block
                        OutlinedTextField(
                            value = textInput,
                            onValueChange = { viewModel.updateWordPuzzleInput(it) },
                            label = { Text(if (lang == "id") "Tulis kata aslinya di sini..." else "Write the original word here...") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (textInput.isNotBlank()) {
                                        keyboardController?.hide()
                                        focusManager.clearFocus()
                                        viewModel.submitWordAnswer()
                                    }
                                }
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = Color.LightGray
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("word_puzzle_input")
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Hint Box powered by Gemini API
                        if (feedback != "CORRECT" && feedback != "HELP") {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    if (hint == null) {
                                        Button(
                                            onClick = { viewModel.requestPuzzleHint() },
                                            enabled = !isHintLoading,
                                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                                            shape = RoundedCornerShape(12.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            if (isHintLoading) {
                                                CircularProgressIndicator(
                                                    modifier = Modifier.size(18.dp),
                                                    color = Color.White,
                                                    strokeWidth = 2.dp
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(if (lang == "id") "Merumuskan Hint..." else "Formulating Hint...", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                            } else {
                                                Text(if (lang == "id") "Minta Hint Pintar Gemini 🤖💡" else "Request Gemini Smart Hint 🤖💡", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                            }
                                        }
                                    } else {
                                        Row(
                                            verticalAlignment = Alignment.Top,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text("🤖", fontSize = 24.sp)
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Column {
                                                Text(
                                                    text = if (lang == "id") "Bantuan Hint Gemini:" else "Gemini Hint Assistance:",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 12.sp,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                                Spacer(modifier = Modifier.height(2.dp))
                                                Text(
                                                    text = hint!!,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Actions and Submissions trigger
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedButton(
                                onClick = { viewModel.revealPuzzleAnswer() },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                                border = BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f)),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                            ) {
                                Text(if (lang == "id") "Nyerah / Intip 👁️" else "Reveal Answer 👁️", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = {
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                    viewModel.submitWordAnswer()
                                },
                                enabled = textInput.isNotBlank(),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                                    .testTag("word_puzzle_submit_button")
                            ) {
                                Text(if (lang == "id") "Periksa ✅" else "Check ✅", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Feedback and correction pop-banners
            AnimatedVisibility(
                visible = feedback != "NONE",
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Card(
                     modifier = Modifier.fillMaxWidth(),
                     shape = RoundedCornerShape(16.dp),
                     colors = CardDefaults.cardColors(
                         containerColor = when (feedback) {
                             "CORRECT" -> EduSuccess.copy(alpha = 0.15f)
                             "HELP" -> Color.LightGray.copy(alpha = 0.3f)
                             else -> EduError.copy(alpha = 0.15f)
                         }
                     )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val bannerTitle = when (feedback) {
                                "CORRECT" -> if (lang == "id") "🥳 BENAR! KAMU PINTAR!" else "🥳 CORRECT! YOU'RE SMART!"
                                "HELP" -> if (lang == "id") "💡 JAWABAN REVEALED" else "💡 ANSWER REVEALED"
                                else -> if (lang == "id") "☘️ BELUM TEPAT!" else "☘️ NOT QUITE RIGHT!"
                            }
                            val bannerColor = when (feedback) {
                                "CORRECT" -> EduSuccess
                                "HELP" -> Color.DarkGray
                                else -> EduError
                            }
                            Text(
                                  text = bannerTitle,
                                  fontWeight = FontWeight.Black,
                                  color = bannerColor
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        val mainFeedbackMessage = when (feedback) {
                            "CORRECT" -> if (lang == "id") "Luar biasa! Kamu berhasil menyusun kata tersebut dan mendapatkan +10 Skor Game 🏆, 3 Stars ⭐ beserta 15 XP ⚡. Teruskan bermain!" else "Awesome! You successfully unscrambled the word and gained +10 Game Score 🏆, 3 Stars ⭐ and 15 XP ⚡. Keep playing!"
                            "HELP" -> if (lang == "id") "Jawaban aslinya adalah: '${puzzleState?.correctWord ?: ""}'. Ayo latih terus daya asah imajinasimu!" else "The mystery word was: '${puzzleState?.correctWord ?: ""}'. Let's keep training your puzzle powers!"
                            else -> if (lang == "id") "Huruf-hurufnya belum tersusun dengan benar atau tiponya sedikit. Coba kaji ulang petunjuk di atas!" else "The letters are not arranged correctly or there's a little typo. Re-evaluate the clue above!"
                        }
                        Text(
                            text = mainFeedbackMessage,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )

                        if (feedback == "CORRECT" || feedback == "HELP") {
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { viewModel.startNewPuzzle(currentCategory) },
                                modifier = Modifier.fillMaxWidth().testTag("puzzle_next_button")
                            ) {
                                Text(if (lang == "id") "Ambil Puzzle Selanjutnya 🧩" else "Get Next Puzzle 🧩")
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- 7. Achievements / Riwayat Score Screen ---

@Composable
fun AchievementsRoomScreen(
    viewModel: EduViewModel,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    val history by viewModel.quizHistory.collectAsStateWithLifecycle()
    val lang = progress?.language ?: "id"

    Scaffold(
        topBar = {
            EduHeader(
                progress = progress,
                onBack = { viewModel.navigateTo(Screen.Dashboard) },
                title = if (lang == "id") "Gelar & Pencapaian" else "Titles & Achievements"
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            MovingShapesBackground()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
            // Badges Milestones Grid Card
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .shadow(3.dp, RoundedCornerShape(24.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = if (lang == "id") "🎖️ Lencana Kehormatan Petualang" else "🎖️ Explorer Badges of Honor",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (lang == "id") "Kumpulkan lencana prestisius dengan menyelesaikan kuis dan menaikkan level belajarmu!" else "Collect prestigious badges by completing quizzes and raising your learning level!",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Badge Rows
                    val level = progress?.level ?: 1
                    val stars = progress?.starsCount ?: 0

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        BadgeItem(
                            emoji = "🐣",
                            title = if (lang == "id") "Sains Cadet" else "Science Cadet",
                            unlocked = level >= 1,
                            requirement = if (lang == "id") "Otomatis Aktif" else "Automatically Active"
                        )
                        BadgeItem(
                            emoji = "🚀",
                            title = "Galaxy Guide",
                            unlocked = level >= 3,
                            requirement = if (lang == "id") "Mencapai Lvl 3" else "Reach Lvl 3"
                        )
                        BadgeItem(
                            emoji = "👑",
                            title = "Edu King",
                            unlocked = level >= 5,
                            requirement = if (lang == "id") "Mencapai Lvl 5" else "Reach Lvl 5"
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        BadgeItem(
                            emoji = "🌟",
                            title = "Star Finder",
                            unlocked = stars >= 25,
                            requirement = if (lang == "id") "Kumpulkan 25 ⭐" else "Collect 25 ⭐"
                        )
                        BadgeItem(
                            emoji = "🦄",
                            title = if (lang == "id") "Pintar Hebat" else "Super Brainy",
                            unlocked = stars >= 50,
                            requirement = if (lang == "id") "Kumpulkan 50 ⭐" else "Collect 50 ⭐"
                        )
                        BadgeItem(
                            emoji = "🦖",
                            title = if (lang == "id") "Fakta Master" else "Fact Master",
                            unlocked = history.isNotEmpty(),
                            requirement = if (lang == "id") "Selesaikan 1 Kuis" else "Complete 1 Quiz"
                        )
                    }
                }
            }

            // History List Header
            Text(
                text = if (lang == "id") "📜 Riwayat Kuis & Star Arena:" else "📜 Quiz History & Star Arena:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (history.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📜", fontSize = 36.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (lang == "id") "Belum ada riwayat kuis tersimpan." else "No quiz history saved yet.",
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (lang == "id") "Selesaikan kuis pertamamu untuk melihat riwayat petualangan di sini!" else "Complete your first quiz to see your adventure history here!",
                            fontSize = 11.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                history.forEach { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("📝", fontSize = 18.sp)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.topic,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = if (lang == "id") "Skor: ${item.score}/${item.totalQuestions}  •  Dapat +${item.starsEarned} Bintang" else "Score: ${item.score}/${item.totalQuestions}  •  Got +${item.starsEarned} Stars",
                                    fontSize = 11.sp,
                                    color = Color.Gray,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color(0xFFEAB308).copy(alpha = 0.15f))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "+${item.starsEarned} ⭐",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFCA8A04)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    }
}

@Composable
fun BadgeItem(
    emoji: String,
    title: String,
    unlocked: Boolean,
    requirement: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(90.dp)
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(
                    if (unlocked) {
                        Brush.linearGradient(listOf(Color(0xFFFEF08A), Color(0xFFFDE047)))
                    } else {
                        Brush.linearGradient(listOf(Color(0xFFE2E8F0), Color(0xFFCBD5E1)))
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emoji,
                fontSize = 28.sp,
                modifier = Modifier.alpha(if (unlocked) 1f else 0.35f)
            )

            if (!unlocked) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Terkunci",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = (-2).dp, y = (-2).dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 11.sp,
            maxLines = 1,
            color = if (unlocked) MaterialTheme.colorScheme.onSurface else Color.LightGray,
            textAlign = TextAlign.Center
        )

        Text(
            text = requirement,
            fontSize = 9.sp,
            color = Color.Gray,
            lineHeight = 10.sp,
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier.height(20.dp)
        )
    }
}

// --- 8. Hangman Word Guessing Screen ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HangmanScreen(
    viewModel: EduViewModel,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    val currentCategory by viewModel.currentHangmanCategory.collectAsStateWithLifecycle()
    val wordState by viewModel.currentHangmanWordState.collectAsStateWithLifecycle()
    val isLoading by viewModel.isHangmanLoading.collectAsStateWithLifecycle()
    val guessedLetters by viewModel.guessedLetters.collectAsStateWithLifecycle()
    val remainingLives by viewModel.remainingLives.collectAsStateWithLifecycle()
    val gameStatus by viewModel.hangmanGameStatus.collectAsStateWithLifecycle()
    val streak by viewModel.hangmanStreak.collectAsStateWithLifecycle()
    val lang = progress?.language ?: "id"

    val wordCategories = if (lang == "id") {
        listOf(
            "Sains Cilik 🧪🐢", "Sejarah Kerajaan 🏛️🦁", "Geografi Atlas 🗺️🐘", "Inggris Kids 🇬🇧🐥", "Gizi Sehat 🍎🐻"
        )
    } else {
        listOf(
            "Kids Science 🧪🐢", "Kingdom History 🏛️🦁", "Atlas Geography 🗺️🐘", "Kids English 🇬🇧🐥", "Healthy Nutrition 🍎🐻"
        )
    }

    Scaffold(
        topBar = {
            EduHeader(
                progress = progress,
                onBack = { viewModel.navigateTo(Screen.Dashboard) },
                title = if (lang == "id") "Detektif Kosakata AI 🤠" else "AI Vocab Detective 🤠"
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Category selectors at the top
            Text(
                text = if (lang == "id") "Pilih Bidang Kosakata:" else "Select Vocabulary Field:",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                wordCategories.forEach { category ->
                    FilterChip(
                        selected = currentCategory == category,
                        onClick = { viewModel.startNewHangman(category) },
                        label = { Text(category, fontWeight = FontWeight.Bold) },
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Play board Card
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(28.dp))
            ) {
                if (isLoading) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = if (lang == "id") "AI Detektif sedang meramu kata rahasia..." else "AI Detective is crafting a secret word...",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                } else if (wordState == null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("🤠", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (lang == "id") "Gagal memuat kosakata. Klik di bawah untuk memancing kosakata baru!" else "Failed to load vocabulary. Click below to retrieve a new word!",
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { viewModel.startNewHangman(currentCategory) },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(if (lang == "id") "Dapatkan Kata Baru" else "Get New Word", fontWeight = FontWeight.Bold)
                        }
                    }
                } else {
                    val secretWord = wordState!!.word.uppercase()

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Header streak stats in the board
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Streak",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (lang == "id") "Untung Beruntun: $streak 🔥" else "Winning Streak: $streak 🔥",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "Lives",
                                    tint = if (remainingLives <= 2) EduError else EduSuccess,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (lang == "id") "Sisa Nyawa: $remainingLives/6" else "Hearts Left: $remainingLives/6",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = if (remainingLives <= 2) EduError else EduSuccess
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Game Canvas: Kid friendly Space Balloon or Balloon Basket
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.size(150.dp, 120.dp)) {
                                val canvasWidth = size.width
                                val canvasHeight = size.height

                                // Draw Ground / Grass
                                drawLine(
                                    color = Color(0xFFDCC8AC), // Natural Wood Sand Color
                                    start = androidx.compose.ui.geometry.Offset(20f, canvasHeight - 10f),
                                    end = androidx.compose.ui.geometry.Offset(canvasWidth - 20f, canvasHeight - 10f),
                                    strokeWidth = 4f,
                                    cap = StrokeCap.Round
                                )

                                // Draw Gallow scaffold
                                drawLine(
                                    color = Color(0xFF8B7355), // Wood post color
                                    start = androidx.compose.ui.geometry.Offset(canvasWidth * 0.25f, canvasHeight - 10f),
                                    end = androidx.compose.ui.geometry.Offset(canvasWidth * 0.25f, 15f),
                                    strokeWidth = 6f,
                                    cap = StrokeCap.Round
                                )

                                drawLine(
                                    color = Color(0xFF8B7355), // Wood top post color
                                    start = androidx.compose.ui.geometry.Offset(canvasWidth * 0.25f, 15f),
                                    end = androidx.compose.ui.geometry.Offset(canvasWidth * 0.70f, 15f),
                                    strokeWidth = 6f,
                                    cap = StrokeCap.Round
                                )

                                drawLine(
                                    color = Color(0xFF8B7355), // Angle brace
                                    start = androidx.compose.ui.geometry.Offset(canvasWidth * 0.25f, 40f),
                                    end = androidx.compose.ui.geometry.Offset(canvasWidth * 0.40f, 15f),
                                    strokeWidth = 4f,
                                    cap = StrokeCap.Round
                                )

                                // Draw rope hanging down
                                drawLine(
                                    color = Color(0xFFA0522D), // Rope sienna
                                    start = androidx.compose.ui.geometry.Offset(canvasWidth * 0.70f, 15f),
                                    end = androidx.compose.ui.geometry.Offset(canvasWidth * 0.70f, 35f),
                                    strokeWidth = 3f,
                                    cap = StrokeCap.Round
                                )

                                // Children friendly: We hang a big cute smiley face balloon basket!
                                // If remainingLives drops, we draw less threads and pop the balloons
                                val balloonColors = listOf(
                                    Color(0xFFFF8A80), // Balloon Coral
                                    Color(0xFFFFD54F), // Balloon Warm yellow
                                    Color(0xFF81C784), // Balloon Green
                                    Color(0xFF64B5F6), // Balloon Blue
                                    Color(0xFFBA68C8), // Balloon Purple
                                    Color(0xFFFFB74D)  // Balloon Orange
                                )

                                val basketY = 85f
                                // Draw basket
                                drawRoundRect(
                                    color = Color(0xFFD2B48C), // Tan wood color
                                    topLeft = androidx.compose.ui.geometry.Offset(canvasWidth * 0.62f, basketY),
                                    size = androidx.compose.ui.geometry.Size(canvasWidth * 0.16f, 20f),
                                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
                                )

                                // Draw Smiley face in basket
                                drawCircle(
                                    color = Color(0xFFFFD54F),
                                    radius = 7f,
                                    center = androidx.compose.ui.geometry.Offset(canvasWidth * 0.70f, basketY - 2f)
                                )

                                // Draw threads to balloons
                                for (i in 0 until 6) {
                                    val isPopped = i >= remainingLives
                                    val startX = canvasWidth * 0.70f
                                    val endX = canvasWidth * (0.50f + (i * 0.08f))
                                    val endY = 45f

                                    drawLine(
                                        color = if (isPopped) Color.LightGray else Color.Gray,
                                        start = androidx.compose.ui.geometry.Offset(startX, basketY - 8f),
                                        end = androidx.compose.ui.geometry.Offset(endX, endY),
                                        strokeWidth = 1.5f,
                                        pathEffect = if (isPopped) androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(4f, 4f), 0f) else null
                                    )

                                    if (!isPopped) {
                                        // Draw colorful beautiful floats
                                        drawCircle(
                                            color = balloonColors[i],
                                            radius = 10f,
                                            center = androidx.compose.ui.geometry.Offset(endX, endY - 10f)
                                        )
                                        // Balloon knots
                                        drawLine(
                                            color = balloonColors[i],
                                            start = androidx.compose.ui.geometry.Offset(endX - 2f, endY),
                                            end = androidx.compose.ui.geometry.Offset(endX + 2f, endY),
                                            strokeWidth = 2f
                                        )
                                    } else {
                                        // Draw small gray pop sparks
                                        drawCircle(
                                            color = Color.LightGray,
                                            radius = 3f,
                                            center = androidx.compose.ui.geometry.Offset(endX, endY - 10f),
                                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.5f)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Secret Word representation (Tac-tile letters boxes)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            secretWord.forEach { char ->
                                val displayChar = if (char in 'A'..'Z') {
                                    if (guessedLetters.contains(char)) char.toString() else "_"
                                } else {
                                    char.toString()
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (displayChar == "_") {
                                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                    } else {
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                    },
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = if (displayChar == "_") {
                                            MaterialTheme.colorScheme.surfaceVariant
                                        } else {
                                            MaterialTheme.colorScheme.primary
                                        }
                                    ),
                                    modifier = Modifier
                                        .size(34.dp, 40.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = displayChar,
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 18.sp,
                                            color = if (displayChar == "_") {
                                                MaterialTheme.colorScheme.onSurfaceVariant
                                            } else {
                                                MaterialTheme.colorScheme.primary
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Clue / Hint container
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("💡", fontSize = 18.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (lang == "id") "PETUNJUK KOSAKATA:" else "VOCABULARY CLUE:",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        letterSpacing = 1.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = wordState!!.clue,
                                    fontSize = 13.sp,
                                    lineHeight = 18.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Game Over / Win Banner Overlay directly inside the card board
                        if (gameStatus != "PLAYING") {
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = if (gameStatus == "WON") {
                                    EduSuccess.copy(alpha = 0.1f)
                                } else {
                                    EduError.copy(alpha = 0.1f)
                                },
                                border = BorderStroke(
                                    width = 1.5.dp,
                                    color = if (gameStatus == "WON") EduSuccess else EduError
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = if (gameStatus == "WON") {
                                            if (lang == "id") "🥳 DETEKTIF KATA HEBAT! AWESOME!" else "🥳 GREAT WORD DETECTIVE! AWESOME!"
                                        } else {
                                            if (lang == "id") "☘️ BELUM BERHASIL, TETAP SEMANGAT!" else "☘️ NOT QUITE RIGHT, KEEP IT UP!"
                                        },
                                        fontWeight = FontWeight.Black,
                                        fontSize = 14.sp,
                                        color = if (gameStatus == "WON") EduSuccess else EduError
                                    )

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = if (gameStatus == "WON") {
                                            if (lang == "id") {
                                                "Selamat! Kamu berhasil mengungkap kata rahasia '${wordState!!.word.uppercase()}' dan mendapatkan +4 Stars ⭐ dan +15 XP ⚡."
                                            } else {
                                                "Congratulations! You successfully revealed the secret word '${wordState!!.word.uppercase()}' and earned +4 Stars ⭐ and +15 XP ⚡."
                                            }
                                        } else {
                                            if (lang == "id") {
                                                "Kata aslinya adalah: '${wordState!!.word.uppercase()}'. Ayo belajar lagi dan dapati tantangan baru!"
                                            } else {
                                                "The word was: '${wordState!!.word.uppercase()}'. Let's keep learning and try a new challenge!"
                                            }
                                        },
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    // Educational facts expander
                                    if (wordState!!.funFact.isNotBlank()) {
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Surface(
                                            shape = RoundedCornerShape(12.dp),
                                            color = MaterialTheme.colorScheme.surface,
                                            border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.4f)),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Column(modifier = Modifier.padding(10.dp)) {
                                                Text(
                                                    text = if (lang == "id") "💡 Tahukah Kamu? (Fakta Ilmu):" else "💡 Did You Know? (Fun Fact):",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 11.sp,
                                                    color = Color.DarkGray
                                                )
                                                Spacer(modifier = Modifier.height(2.dp))
                                                Text(
                                                    text = wordState!!.funFact,
                                                    fontSize = 11.sp,
                                                    lineHeight = 15.sp,
                                                    color = Color.Gray
                                                )
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Button(
                                        onClick = { viewModel.startNewHangman(currentCategory) },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (gameStatus == "WON") EduSuccess else EduError
                                        ),
                                        modifier = Modifier.testTag("hangman_continue_button")
                                    ) {
                                        Text(
                                            text = if (gameStatus == "WON") {
                                                if (lang == "id") "Main Lagi 🤠" else "Play Again 🤠"
                                            } else {
                                                if (lang == "id") "Ulangi Kata Baru 🚀" else "Retry New Word 🚀"
                                            },
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        } else {
                            // Guessed Alphabet keyboard (Guessed items got disabled, standard layout)
                            val alphabetRows = listOf(
                                "QWERTYUIOP".toList(),
                                "ASDFGHJKL".toList(),
                                "ZXCVBNM".toList()
                            )

                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                alphabetRows.forEach { rowChars ->
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        rowChars.forEach { char ->
                                            val isGuessed = guessedLetters.contains(char)
                                            val isInWord = secretWord.contains(char)

                                            // Determine letter button styling
                                            val btnBg = when {
                                                isGuessed && isInWord -> EduSuccess.copy(alpha = 0.2f)
                                                isGuessed && !isInWord -> Color.LightGray.copy(alpha = 0.2f)
                                                else -> MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                                            }

                                            val btnOutlineColor = when {
                                                isGuessed && isInWord -> EduSuccess
                                                isGuessed && !isInWord -> Color.LightGray
                                                else -> MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                            }

                                            val btnTextColor = when {
                                                isGuessed && isInWord -> EduSuccess
                                                isGuessed && !isInWord -> Color.LightGray
                                                else -> MaterialTheme.colorScheme.primary
                                            }

                                            Surface(
                                                onClick = { viewModel.guessLetter(char) },
                                                enabled = !isGuessed,
                                                shape = RoundedCornerShape(10.dp),
                                                color = btnBg,
                                                border = BorderStroke(1.dp, btnOutlineColor),
                                                modifier = Modifier
                                                    .size(width = 30.dp, height = 38.dp)
                                                    .testTag("key_$char")
                                            ) {
                                                Box(
                                                    contentAlignment = Alignment.Center,
                                                    modifier = Modifier.fillMaxSize()
                                                ) {
                                                    Text(
                                                        text = char.toString(),
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 14.sp,
                                                        color = btnTextColor
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Give up/Reveal answer button
                            TextButton(
                                onClick = { viewModel.revealHangmanAnswer() },
                                colors = ButtonDefaults.textButtonColors(contentColor = EduError),
                                modifier = Modifier.testTag("hangman_giveup_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = if (lang == "id") "Menyerah" else "Give Up",
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (lang == "id") "Nyerah & Ungkap Kata 🥺" else "Give Up & Reveal Word 🥺", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- Daily Challenge Screen ---

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DailyChallengeScreen(
    viewModel: EduViewModel,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    val challenge by viewModel.dailyChallengeState.collectAsStateWithLifecycle()
    val isLoading by viewModel.isDailyLoading.collectAsStateWithLifecycle()
    val selectedAnswer by viewModel.dailySelectedAnswer.collectAsStateWithLifecycle()
    val isSubmitted by viewModel.dailyIsAnswerSubmitted.collectAsStateWithLifecycle()
    val currentDate = remember { getCurrentDateString() }
    val lang = progress?.language ?: "id"

    var showClue by remember { mutableStateOf(false) }

    val isCompletedToday = progress?.lastDailyChallengeDate == currentDate

    Scaffold(
        topBar = {
            EduHeader(
                progress = progress,
                onBack = { viewModel.navigateTo(Screen.Dashboard) },
                title = if (lang == "id") "Misi Quest Harian" else "Daily Quest Mission"
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon header
            Text("⚔️", fontSize = 64.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (lang == "id") "TANTANGAN HARIAN" else "DAILY CHALLENGE",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Text(
                text = if (lang == "id") "Asah Pikiran & Kumpulkan Bintangmu!" else "Sharpen Your Mind & Collect Stars!",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (isLoading) {
                // Beautiful Organic Earthy Loader
                Card(
                    modifier = Modifier.fillMaxWidth().shadow(1.dp, RoundedCornerShape(24.dp)),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 4.dp,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = if (lang == "id") "Meracik pertanyaan hari ini dari Gemini AI..." else "Formulating today's question via Gemini AI...",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (lang == "id") "Sesaat lagi, misi unikmu siap meluncur!" else "In just a moment, your unique mission is ready!",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )
                    }
                }
            } else if (challenge != null) {
                val q = challenge!!

                // Display Today's Topic Card
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = EduSecondary.copy(alpha = 0.4f)),
                    border = BorderStroke(1.dp, EduPrimary.copy(alpha = 0.2f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(1.dp, RoundedCornerShape(24.dp))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(EduPrimary)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (lang == "id") "Topik: ${q.topic}" else "Topic: ${q.topic}",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = EduPrimary
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = q.question,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                lineHeight = 22.sp
                            ),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Clue Helper Button
                if (!isSubmitted && !isCompletedToday) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(
                            onClick = { showClue = !showClue },
                            colors = ButtonDefaults.textButtonColors(contentColor = EduPrimary)
                        ) {
                            Icon(Icons.Default.Star, contentDescription = "Clue", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (showClue) {
                                    if (lang == "id") "Sembunyikan Clue 🙈" else "Hide Clue 🙈"
                                } else {
                                    if (lang == "id") "Butuh Bantuan? Lihat Clue 💡" else "Need Help? View Clue 💡"
                                },
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = showClue,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = EduTertiary.copy(alpha = 0.35f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 18.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("💡", fontSize = 24.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = q.clue,
                                    style = MaterialTheme.typography.bodySmall.copy(lineHeight = 16.sp),
                                    color = Color(0xFF6B4500),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                // Options List
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    q.options.forEachIndexed { index, option ->
                        val isSelected = selectedAnswer == index
                        val isCorrectIndex = index == q.answerIndex

                        val containerColor = when {
                            isSubmitted || isCompletedToday -> {
                                when {
                                    isCorrectIndex -> EduSuccess.copy(alpha = 0.15f)
                                    isSelected -> EduError.copy(alpha = 0.15f)
                                    else -> MaterialTheme.colorScheme.surface
                                }
                            }
                            isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                            else -> MaterialTheme.colorScheme.surface
                        }

                        val borderColor = when {
                            isSubmitted || isCompletedToday -> {
                                when {
                                    isCorrectIndex -> EduSuccess
                                    isSelected -> EduError
                                    else -> Color.LightGray.copy(alpha = 0.4f)
                                }
                            }
                            isSelected -> MaterialTheme.colorScheme.primary
                            else -> Color.LightGray.copy(alpha = 0.6f)
                        }

                        val textColor = when {
                            isSubmitted || isCompletedToday -> {
                                when {
                                    isCorrectIndex -> EduSuccess
                                    isSelected -> EduError
                                    else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                }
                            }
                            else -> MaterialTheme.colorScheme.onSurface
                        }

                        Surface(
                            onClick = {
                                if (!isSubmitted && !isCompletedToday) {
                                    viewModel.selectDailyAnswer(index)
                                }
                            },
                            shape = RoundedCornerShape(16.dp),
                            color = containerColor,
                            border = BorderStroke(if (isSelected || isCorrectIndex && (isSubmitted || isCompletedToday)) 2.dp else 1.dp, borderColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(0.5.dp, RoundedCornerShape(16.dp))
                                .testTag("daily_option_$index")
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Floating bullet indicator
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray.copy(
                                                alpha = 0.3f
                                            )
                                        )
                                ) {
                                    Text(
                                        text = (65 + index).toChar().toString(), // A, B, C, D
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text = option,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 14.sp,
                                    lineHeight = 18.sp,
                                    color = textColor,
                                    modifier = Modifier.weight(1f)
                                )

                                // Success/Error icons
                                if (isSubmitted || isCompletedToday) {
                                    if (isCorrectIndex) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = if (lang == "id") "Benar" else "Correct",
                                            tint = EduSuccess,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    } else if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = if (lang == "id") "Salah" else "Incorrect",
                                            tint = EduError,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Submit/Action Button
                if (!isSubmitted && !isCompletedToday) {
                    Button(
                        onClick = { viewModel.submitDailyAnswer(currentDate) },
                        enabled = selectedAnswer != null,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .testTag("daily_submit_button")
                    ) {
                        Text(
                            text = if (lang == "id") "Kirim Jawaban ⚔️" else "Submit Answer ⚔️",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                } else {
                    // Displays deep pedagogical feedback explanations
                    val answeredCorrectly = (selectedAnswer ?: q.answerIndex) == q.answerIndex

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (answeredCorrectly || isCompletedToday) {
                                    EduSuccess.copy(alpha = 0.08f)
                                } else {
                                    EduError.copy(alpha = 0.08f)
                                }
                            ),
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (answeredCorrectly || isCompletedToday) EduSuccess.copy(alpha = 0.3f) else EduError.copy(alpha = 0.3f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Text(
                                    text = if (answeredCorrectly || isCompletedToday) {
                                        if (lang == "id") "Sempurna! Kamu Berhasil 🎉" else "Perfect! You Succeeded 🎉"
                                    } else {
                                        if (lang == "id") "Jawaban Kurang Tepat 🧩" else "Incorrect Answer 🧩"
                                    },
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 16.sp,
                                    color = if (answeredCorrectly || isCompletedToday) EduSuccess else EduError
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = q.explanation,
                                    style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        // Mind-blowing Fun Fact Card!
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = EduTertiary.copy(alpha = 0.25f)),
                            border = BorderStroke(1.dp, EduTertiary.copy(alpha = 0.4f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("🦄", fontSize = 22.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (lang == "id") "Fakta Seru Pengetahuan" else "Knowledge Fun Fact",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color(0xFF725C11)
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = q.funFact,
                                    style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        // Complete / Back Button
                        Button(
                            onClick = { viewModel.navigateTo(Screen.Dashboard) },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Text(
                                text = if (lang == "id") "Kembali ke Beranda 🏠" else "Return to Home 🏠",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            } else {
                // Handle fallback/error states gracefully
                Card(
                     modifier = Modifier.fillMaxWidth(),
                     shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("⚠️", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (lang == "id") "Gagal memuat kuis tantangan hari ini" else "Failed to load today's challenge quiz",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { viewModel.startDailyChallenge(currentDate) }
                        ) {
                            Text(if (lang == "id") "Coba Lagi" else "Retry")
                        }
                    }
                }
            }
        }
    }
}

// --- 6. Math Settings Screen ---

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MathSettingsScreen(
    viewModel: EduViewModel,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    val activeGrade by viewModel.selectedMathGrade.collectAsStateWithLifecycle()
    val activeCategory by viewModel.selectedMathCategory.collectAsStateWithLifecycle()
    val lang = progress?.language ?: "id"

    val categories = if (lang == "id") {
        listOf(
            Triple("Perkalian", "🦁✖️", "Kali-Kalian Singa"),
            Triple("Pembagian", "🐒➗", "Bagi-Bagi Monyet"),
            Triple("Pecahan", "🍕🍰", "Pecahan Ceria"),
            Triple("Geometri", "📐🔺", "Geometri & Bangun"),
            Triple("Campuran", "🐨➕➖", "Tambah Kurang Koala"),
            Triple("Cerita", "🐼📖", "Petualangan Panda")
        )
    } else {
        listOf(
            Triple("Perkalian", "🦁✖️", "Lion Multiplication"),
            Triple("Pembagian", "🐒➗", "Monkey Division"),
            Triple("Pecahan", "🍕🍰", "Cheerful Fractions"),
            Triple("Geometri", "📐🔺", "Geometry & Shapes"),
            Triple("Campuran", "🐨➕➖", "Koala Mix Ops"),
            Triple("Cerita", "🐼📖", "Panda Word Stories")
        )
    }

    Scaffold(
        topBar = {
            EduHeader(
                progress = progress,
                onBack = { viewModel.navigateTo(Screen.Dashboard) },
                title = if (lang == "id") "Tantangan Matematika 🔢" else "Math Challenge 🔢"
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🔢", fontSize = 36.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = if (lang == "id") "Selamat Datang di Arena Matematika!" else "Welcome to the Math Arena!",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (lang == "id") "Petualangan interaktif dari tingkat Kelas 1 SD sampai Kelas 12 SMA. Asah kemampuan logikamu sekarang!" else "Interactive adventures from Grade 1 Elementary to Grade 12 High School. Sharpen your logic skills now!",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            // 1. Kategori Matematika
            Text(
                text = if (lang == "id") "Pilih Kategori Kuis: 🎯" else "Select Quiz Category: 🎯",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                maxItemsInEachRow = 2
            ) {
                categories.forEach { (catId, catEmoji, catLabel) ->
                    val isSelected = catId == activeCategory
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 10.dp)
                            .testTag("math_category_$catId")
                            .clickable { viewModel.selectMathCategory(catId) },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                        ),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(
                            2.dp,
                            if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(catEmoji, fontSize = 28.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = catLabel,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Tingkat Level
            Text(
                text = if (lang == "id") "Pilih Level: 🏆" else "Select Level: 🏆",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Grid of levels from 1 to 15
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                maxItemsInEachRow = 5
            ) {
                for (g in 1..15) {
                    val isSelected = g == activeGrade
                    val badgeColor = when {
                        g <= 5 -> Color(0xFF2E7D32) // Beginner Green
                        g <= 10 -> Color(0xFF1565C0) // Intermediate Blue
                        else -> Color(0xFFC62828)     // Advanced Red
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 8.dp)
                            .testTag("math_grade_$g")
                            .clickable { viewModel.selectMathGrade(g) },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) badgeColor else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(
                            2.dp,
                            if (isSelected) badgeColor else Color.Transparent
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$g",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "Lvl",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 10.sp,
                                    color = if (isSelected) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Start Button
            Button(
                onClick = { viewModel.startNewMathQuiz(activeGrade, activeCategory) },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .testTag("math_start_button"),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = if (lang == "id") "Main" else "Play")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (lang == "id") "Mulai Tantangan Matematika! 🚀" else "Start Math Challenge! 🚀",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}


// --- 7. Math Play Screen (The Arena) ---

@Composable
fun MathPlayScreen(
    viewModel: EduViewModel,
    grade: Int,
    category: String,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    val isLoading by viewModel.isMathLoading.collectAsStateWithLifecycle()
    val questions by viewModel.mathQuestions.collectAsStateWithLifecycle()
    val currentIndex by viewModel.currentMathQuestionIndex.collectAsStateWithLifecycle()
    val selectedAnswerIndex by viewModel.selectedMathAnswerIndex.collectAsStateWithLifecycle()
    val isAnswerSubmitted by viewModel.isMathAnswerSubmitted.collectAsStateWithLifecycle()
    val explanation by viewModel.mathExplanation.collectAsStateWithLifecycle()
    val mathScore by viewModel.mathScore.collectAsStateWithLifecycle()
    val lang = progress?.language ?: "id"

    Scaffold(
        topBar = {
            EduHeader(
                progress = progress,
                onBack = { viewModel.navigateTo(Screen.MathSettings) },
                title = if (lang == "id") "Kelas $grade - $category 🔢" else "Grade $grade - $category 🔢"
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = if (lang == "id") "Guru Matematika AI sedang meracik kuis untuk Kelas $grade... 🤖📏" else "AI Math Teacher is formulating a quiz for Grade $grade... 🤖📏",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (lang == "id") "Harap bersabar, asisten penuh kecerdasan sedang bekerja!" else "Please wait, the smart assistant is at work!",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else if (questions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text("⚠️", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (lang == "id") "Gagal memuat pertanyaan matematika." else "Failed to load math questions.",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = { viewModel.startNewMathQuiz(grade, category) }) {
                        Text(if (lang == "id") "Coba Lagi" else "Retry")
                    }
                }
            }
        } else {
            val currentQuestion = questions.getOrNull(currentIndex)
            if (currentQuestion != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Progress Indicator Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (lang == "id") "Pertanyaan ${currentIndex + 1} dari ${questions.size}" else "Question ${currentIndex + 1} of ${questions.size}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Text(
                                text = if (lang == "id") "Skor: $mathScore" else "Score: $mathScore",
                                fontWeight = FontWeight.ExtraBold,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Progress Bar
                    val progressRatio = (currentIndex + 1).toFloat() / questions.size.toFloat()
                    LinearProgressIndicator(
                        progress = { progressRatio },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        strokeCap = StrokeCap.Round,
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Question Card
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Category Badge
                            Surface(
                                shape = RoundedCornerShape(100.dp),
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                modifier = Modifier.padding(bottom = 12.dp)
                            ) {
                                Text(
                                    text = if (lang == "id") "SOAL KAMPUS MATEMATIKA 📐" else "MATH CAMPUS CHALLENGE 📐",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }

                            Text(
                                text = currentQuestion.question,
                                style = MaterialTheme.typography.titleLarge.copy(lineHeight = 28.sp),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

                    // Options list
                    Text(
                        text = if (lang == "id") "Pilih Salah Satu Jawaban: 👇" else "Select One Answer: 👇",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    currentQuestion.options.forEachIndexed { optIndex, optionText ->
                        val isSelected = selectedAnswerIndex == optIndex
                        val isCorrect = optIndex == currentQuestion.answerIndex

                        val borderStroke = when {
                            isAnswerSubmitted && isCorrect -> BorderStroke(2.dp, Color(0xFF4C6B2E))
                            isAnswerSubmitted && isSelected && !isCorrect -> BorderStroke(2.dp, Color(0xFFB3261E))
                            isSelected -> BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                            else -> BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        }

                        val containerColor = when {
                            isAnswerSubmitted && isCorrect -> Color(0xFFE8F2DD)
                            isAnswerSubmitted && isSelected && !isCorrect -> Color(0xFFF9EAE8)
                            isSelected -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            else -> MaterialTheme.colorScheme.surface
                        }

                        val contentColor = when {
                            isAnswerSubmitted && isCorrect -> Color(0xFF2A4214)
                            isAnswerSubmitted && isSelected && !isCorrect -> Color(0xFF721C1A)
                            else -> MaterialTheme.colorScheme.onBackground
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 52.dp)
                                .padding(bottom = 10.dp)
                                .testTag("math_option_$optIndex")
                                .clickable(enabled = !isAnswerSubmitted) {
                                    viewModel.selectMathAnswer(optIndex)
                                },
                            shape = RoundedCornerShape(16.dp),
                            border = borderStroke,
                            colors = CardDefaults.cardColors(containerColor = containerColor)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp, horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = ('A' + optIndex).toString(),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = optionText,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = contentColor
                                    )
                                }

                                if (isAnswerSubmitted) {
                                    if (isCorrect) {
                                        Text("✅", fontSize = 16.sp)
                                    } else if (isSelected) {
                                        Text("❌", fontSize = 16.sp)
                                    }
                                }
                            }
                        }
                    }

                    // Explanation Box if submitted
                    if (isAnswerSubmitted && explanation != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("💡", fontSize = 24.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (lang == "id") "Penjelasan EP Knowledge Land:" else "EP Knowledge Land Explanation:",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = explanation!!,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.9f)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Next / Check Action Button
                    if (!isAnswerSubmitted) {
                        Button(
                            onClick = { viewModel.submitMathAnswer() },
                            enabled = selectedAnswerIndex != null,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .testTag("math_submit_button")
                        ) {
                            Text(
                                text = if (lang == "id") "Periksa Jawaban" else "Check Answer",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    } else {
                        Button(
                            onClick = { viewModel.nextMathQuestion(grade, category) },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .testTag("math_next_button")
                        ) {
                            Text(
                                text = if (lang == "id") {
                                    if (currentIndex + 1 < questions.size) "Soal Berikutnya ➡️" else "Selesaikan Kuis 🏁"
                                } else {
                                    if (currentIndex + 1 < questions.size) "Next Question ➡️" else "Complete Quiz 🏁"
                                },
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}


// --- 8. Math Score Screen ---

@Composable
fun MathScoreScreen(
    viewModel: EduViewModel,
    grade: Int,
    category: String,
    score: Int,
    total: Int,
    starsEarned: Int,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    val lang = progress?.language ?: "id"

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("🎉", fontSize = 72.sp, modifier = Modifier.padding(bottom = 12.dp))

                Text(
                    text = if (lang == "id") "Tantangan Selesai!" else "Challenge Completed!",
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = if (lang == "id") "Kamu telah menyelesaikan Kuis Matematika Kelas $grade!" else "You have completed the Grade $grade Math Quiz!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 32.dp),
                    textAlign = TextAlign.Center
                )

                // Dynamic result status card
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (lang == "id") "Skor Kamu: ✨" else "Your Score: ✨",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "$score / $total",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(if (lang == "id") "⭐ Bintang" else "⭐ Stars", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                Text(
                                    text = "+$starsEarned Stars",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFFBA5A31)
                                )
                            }

                            VerticalDivider(modifier = Modifier.height(36.dp))

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(if (lang == "id") "⚡ XP Diperoleh" else "⚡ XP Earned", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                Text(
                                    text = "+${score * 10} XP",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF4C6B2E)
                                )
                            }
                        }
                    }
                }

                // High praise message based on score
                val praiseMessage = if (lang == "id") {
                    when {
                        score == total -> "Luar biasa sempurna! Otak jeniusmu bekerja sangat brilian! 🧠🏆"
                        score >= 3 -> "Bagus sekali! Kemampuan matematikamu terus bertumbuh hebat! 🌟"
                        else -> "Tetap semangat! Matematika itu asyik, terus latih logikamu! 🌱"
                    }
                } else {
                    when {
                        score == total -> "Absolutely perfect! Your genius brain worked brilliantly! 🧠🏆"
                        score >= 3 -> "Very good! Your math abilities continue to grow beautifully! 🌟"
                        else -> "Keep your spirits high! Math is fun, keep training your logic! 🌱"
                    }
                }

                Text(
                    text = praiseMessage,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 32.dp)
                )

                if (grade < 15) {
                    Button(
                        onClick = { viewModel.startNewMathQuiz(grade + 1, category) },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .testTag("math_score_next_level"),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = if (lang == "id") "Lanjut" else "Next")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (lang == "id") "Lanjut ke Level ${grade + 1} 🚀" else "Continue to Level ${grade + 1} 🚀",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Replay or return
                OutlinedButton(
                    onClick = { viewModel.navigateTo(Screen.MathSettings) },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("math_score_continue"),
                    border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = if (lang == "id") "Ulang" else "Retry")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (lang == "id") "Coba Tingkat Lain" else "Try Another Grade",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = { viewModel.navigateTo(Screen.Dashboard) },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("math_score_dashboard")
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = if (lang == "id") "Menu Utama" else "Main Menu")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (lang == "id") "Kembali ke Beranda" else "Return to Home",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            if (score == total) {
                ConfettiRain()
            }
        }
    }
}


// --- 9. English Settings Screen ---

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EnglishSettingsScreen(
    viewModel: EduViewModel,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    val activeGrade by viewModel.selectedEnglishGrade.collectAsStateWithLifecycle()
    val activeCategory by viewModel.selectedEnglishCategory.collectAsStateWithLifecycle()
    val lang = progress?.language ?: "id"

    val categories = listOf(
        Triple("Vocabulary", "🔤🐱", if (lang == "id") "Kosa Kata Kucing" else "Vocabulary"),
        Triple("Grammar", "🧩🦄", if (lang == "id") "Grammar Unicorn" else "Grammar"),
        Triple("Conversation", "💬🦊", if (lang == "id") "Percakapan Rubah" else "Conversation"),
        Triple("Reading", "📖🐻", if (lang == "id") "Membaca Beruang" else "Reading")
    )

    Scaffold(
        topBar = {
            EduHeader(
                progress = progress,
                onBack = { viewModel.navigateTo(Screen.Dashboard) },
                title = if (lang == "id") "Tantangan Inggris 🇬🇧" else "English Challenge 🇬🇧"
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🇬🇧", fontSize = 36.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = if (lang == "id") "Selamat Datang di Arena Bahasa Inggris!" else "Welcome to the English Arena!",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (lang == "id") "Kuis interaktif jenius dari tingkat Kelas 1 SD sampai Kelas 12 SMA. Asah kelancaran komunikasimu dengan AI!" else "Interactive genius quizzes from Grade 1 to Grade 12. Polish your English fluency with AI!",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            // 1. Kategori Bahasa Inggris
            Text(
                text = if (lang == "id") "Pilih Kategori Kuis: 🎯" else "Select Quiz Category: 🎯",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                maxItemsInEachRow = 2
            ) {
                categories.forEach { (catId, catEmoji, catLabel) ->
                    val isSelected = catId == activeCategory
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 10.dp)
                            .testTag("english_category_$catId")
                            .clickable { viewModel.selectEnglishCategory(catId) },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                        ),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(
                            2.dp,
                            if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(catEmoji, fontSize = 28.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = catLabel,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Tingkat Level
            Text(
                text = if (lang == "id") "Pilih Level: 🏆" else "Select Level: 🏆",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Grid of levels from 1 to 15
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                maxItemsInEachRow = 5
            ) {
                for (g in 1..15) {
                    val isSelected = g == activeGrade
                    val badgeColor = when {
                        g <= 5 -> Color(0xFF2E7D32) // Beginner Green
                        g <= 10 -> Color(0xFF1565C0) // Intermediate Blue
                        else -> Color(0xFFC62828)     // Advanced Red
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 8.dp)
                            .testTag("english_grade_$g")
                            .clickable { viewModel.selectEnglishGrade(g) },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) badgeColor else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(
                            2.dp,
                            if (isSelected) badgeColor else Color.Transparent
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$g",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "Lvl",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 10.sp,
                                    color = if (isSelected) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Start Button
            Button(
                onClick = { viewModel.startNewEnglishQuiz(activeGrade, activeCategory) },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .testTag("english_start_button"),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Main")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (lang == "id") "Mulai Tantangan Inggris! 🚀" else "Start English Challenge! 🚀",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}


// --- 10. English Play Screen ---

@Composable
fun EnglishPlayScreen(
    viewModel: EduViewModel,
    grade: Int,
    category: String,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    val isLoading by viewModel.isEnglishLoading.collectAsStateWithLifecycle()
    val questions by viewModel.englishQuestions.collectAsStateWithLifecycle()
    val currentIndex by viewModel.currentEnglishQuestionIndex.collectAsStateWithLifecycle()
    val selectedAnswerIndex by viewModel.selectedEnglishAnswerIndex.collectAsStateWithLifecycle()
    val isAnswerSubmitted by viewModel.isEnglishAnswerSubmitted.collectAsStateWithLifecycle()
    val explanation by viewModel.englishExplanation.collectAsStateWithLifecycle()
    val englishScore by viewModel.englishScore.collectAsStateWithLifecycle()
    val lang = progress?.language ?: "id"

    Scaffold(
        topBar = {
            EduHeader(
                progress = progress,
                onBack = { viewModel.navigateTo(Screen.EnglishSettings) },
                title = if (lang == "id") "Kelas $grade - $category 🇬🇧" else "Grade $grade - $category 🇬🇧"
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = if (lang == "id") "Guru Inggris AI sedang meracik kuis untuk Kelas $grade... 🤖💬" else "AI English Teacher is formulating a quiz for Grade $grade... 🤖💬",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (lang == "id") "Harap bersabar, kecerdasan buatan sedang menyiapkan materi seru!" else "Please wait, the artificial intelligence is preparing exciting material!",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else if (questions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text("⚠️", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (lang == "id") "Gagal memuat pertanyaan bahasa Inggris." else "Failed to load English questions.",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = { viewModel.startNewEnglishQuiz(grade, category) }) {
                        Text(if (lang == "id") "Coba Lagi" else "Retry")
                    }
                }
            }
        } else {
            val currentQuestion = questions.getOrNull(currentIndex)
            if (currentQuestion != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Progress Indicator Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (lang == "id") "Pertanyaan ${currentIndex + 1} dari ${questions.size}" else "Question ${currentIndex + 1} of ${questions.size}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Text(
                                text = if (lang == "id") "Skor: $englishScore" else "Score: $englishScore",
                                fontWeight = FontWeight.ExtraBold,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Progress Bar
                    val progressRatio = (currentIndex + 1).toFloat() / questions.size.toFloat()
                    LinearProgressIndicator(
                        progress = { progressRatio },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        strokeCap = StrokeCap.Round,
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Question Card
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Category Badge
                            Surface(
                                shape = RoundedCornerShape(100.dp),
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                modifier = Modifier.padding(bottom = 12.dp)
                            ) {
                                Text(
                                    text = "ENGLISH ACADEMY QUIZ 🇬🇧",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }

                            Text(
                                text = currentQuestion.question,
                                style = MaterialTheme.typography.titleLarge.copy(lineHeight = 28.sp),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

                    // Options list
                    Text(
                        text = if (lang == "id") "Pilih Salah Satu Jawaban: 👇" else "Select One Answer: 👇",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    currentQuestion.options.forEachIndexed { optIndex, optionText ->
                        val isSelected = selectedAnswerIndex == optIndex
                        val isCorrect = optIndex == currentQuestion.answerIndex

                        val borderStroke = when {
                            isAnswerSubmitted && isCorrect -> BorderStroke(2.dp, Color(0xFF4C6B2E))
                            isAnswerSubmitted && isSelected && !isCorrect -> BorderStroke(2.dp, Color(0xFFB3261E))
                            isSelected -> BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                            else -> BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        }

                        val containerColor = when {
                            isAnswerSubmitted && isCorrect -> Color(0xFFE8F2DD)
                            isAnswerSubmitted && isSelected && !isCorrect -> Color(0xFFF9EAE8)
                            isSelected -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            else -> MaterialTheme.colorScheme.surface
                        }

                        val contentColor = when {
                            isAnswerSubmitted && isCorrect -> Color(0xFF2A4214)
                            isAnswerSubmitted && isSelected && !isCorrect -> Color(0xFF721C1A)
                            else -> MaterialTheme.colorScheme.onBackground
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 52.dp)
                                .padding(bottom = 10.dp)
                                .testTag("english_option_$optIndex")
                                .clickable(enabled = !isAnswerSubmitted) {
                                    viewModel.selectEnglishAnswer(optIndex)
                                },
                            shape = RoundedCornerShape(16.dp),
                            border = borderStroke,
                            colors = CardDefaults.cardColors(containerColor = containerColor)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp, horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = ('A' + optIndex).toString(),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = optionText,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = contentColor
                                    )
                                }

                                if (isAnswerSubmitted) {
                                    if (isCorrect) {
                                        Text("✅", fontSize = 16.sp)
                                    } else if (isSelected) {
                                        Text("❌", fontSize = 16.sp)
                                    }
                                }
                            }
                        }
                    }

                    // Explanation Box if submitted
                    if (isAnswerSubmitted && explanation != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("💡", fontSize = 24.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (lang == "id") "Penjelasan EP Knowledge Land:" else "EP Knowledge Land Explanation:",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = explanation!!,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.9f)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Next / Check Action Button
                    if (!isAnswerSubmitted) {
                        Button(
                            onClick = { viewModel.submitEnglishAnswer() },
                            enabled = selectedAnswerIndex != null,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .testTag("english_submit_button")
                        ) {
                            Text(
                                text = if (lang == "id") "Periksa Jawaban" else "Check Answer",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    } else {
                        Button(
                            onClick = { viewModel.nextEnglishQuestion(grade, category) },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .testTag("english_next_button")
                        ) {
                            Text(
                                text = if (lang == "id") {
                                    if (currentIndex + 1 < questions.size) "Soal Berikutnya ➡️" else "Selesaikan Kuis 🏁"
                                } else {
                                    if (currentIndex + 1 < questions.size) "Next Question ➡️" else "Complete Quiz 🏁"
                                },
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}


// --- 11. English Score Screen ---

@Composable
fun EnglishScoreScreen(
    viewModel: EduViewModel,
    grade: Int,
    category: String,
    score: Int,
    total: Int,
    starsEarned: Int,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    val lang = progress?.language ?: "id"

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("🎉", fontSize = 72.sp, modifier = Modifier.padding(bottom = 12.dp))

                Text(
                    text = if (lang == "id") "Tantangan Selesai!" else "Challenge Completed!",
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = if (lang == "id") "Kamu telah menyelesaikan Kuis Bahasa Inggris Kelas $grade!" else "You have completed the Grade $grade English Quiz!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 32.dp),
                    textAlign = TextAlign.Center
                )

                // Dynamic result status card
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (lang == "id") "Skor Kamu: ✨" else "Your Score: ✨",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "$score / $total",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(if (lang == "id") "⭐ Bintang" else "⭐ Stars", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                Text(
                                    text = "+$starsEarned Stars",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFFBA5A31)
                                )
                            }

                            VerticalDivider(modifier = Modifier.height(36.dp))

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(if (lang == "id") "⚡ XP Diperoleh" else "⚡ XP Earned", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                Text(
                                    text = "+${score * 10} XP",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF4C6B2E)
                                )
                            }
                        }
                    }
                }

                // High praise message based on score
                val praiseMessage = if (lang == "id") {
                    when {
                        score == total -> "Keren! Kemampuan bahasa Inggrismu sempurna tanpa celah! 🧠🇬🇧"
                        score >= 3 -> "Kerja yang luar biasa! Teruskan berbicara dan berlatih bahasa Inggris! 🌟"
                        else -> "Jangan menyerah! Latihan membuat sempurna, pertahankan usahamu! 🌱"
                    }
                } else {
                    when {
                        score == total -> "Great Job! Your English is completely flawless! 🧠🇬🇧"
                        score >= 3 -> "Excellent work! Keep talking and typing in English! 🌟"
                        else -> "Never give up! Practice makes perfect, keep up the effort! 🌱"
                    }
                }

                Text(
                    text = praiseMessage,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 32.dp)
                )

                if (grade < 15) {
                    Button(
                        onClick = { viewModel.startNewEnglishQuiz(grade + 1, category) },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .testTag("english_score_next_level"),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = if (lang == "id") "Lanjut" else "Next")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (lang == "id") "Lanjutkan ke Level ${grade + 1} 🚀" else "Continue to Level ${grade + 1} 🚀",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Replay or return
                OutlinedButton(
                    onClick = { viewModel.navigateTo(Screen.EnglishSettings) },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("english_score_continue"),
                    border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = if (lang == "id") "Ulang" else "Retry")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (lang == "id") "Coba Tingkat Lain" else "Try Another Level",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = { viewModel.navigateTo(Screen.Dashboard) },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("english_score_dashboard")
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = if (lang == "id") "Menu Utama" else "Main Menu")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (lang == "id") "Kembali ke Beranda" else "Return to Home",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            if (score == total) {
                ConfettiRain()
            }
        }
    }
}


// --- 12. Islamic Settings Screen ---

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun IslamicSettingsScreen(
    viewModel: EduViewModel,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    val activeGrade by viewModel.selectedIslamicGrade.collectAsStateWithLifecycle()
    val activeCategory by viewModel.selectedIslamicCategory.collectAsStateWithLifecycle()
    val lang = progress?.language ?: "id"

    val categories = listOf(
        Triple("Aqidah & Akhlak", "💖🤗", if (lang == "id") "Aqidah & Akhlak" else "Creed & Character"),
        Triple("Fiqih & Ibadah", "🕋🕌", if (lang == "id") "Fiqih & Ibadah" else "Jurisprudence & Worship"),
        Triple("Al-Qur'an & Tajwid", "📖🕊️", if (lang == "id") "Al-Qur'an & Tajwid" else "Quran & Tajweed"),
        Triple("Sejarah Islam", "🐫🐪", if (lang == "id") "Sejarah Islam" else "Islamic History")
    )

    Scaffold(
        topBar = {
            EduHeader(
                progress = progress,
                onBack = { viewModel.navigateTo(Screen.Dashboard) },
                title = if (lang == "id") "Agama Islam & Al-Qur'an 🕌" else "Islamic Studies & Quran 🕌"
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE6F4EA)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🕌", fontSize = 36.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = if (lang == "id") "Arena Pendidikan Agama Islam & Al-Qur'an" else "Islamic Education & Quran Arena",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF0F5132)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (lang == "id") "Tantangan kuis interaktif seru dari Kelas 1 SD sampai Kelas 12 SMA. Asah ilmu Aqidah, Fiqih, Tajwid, dan Tarikh didampingi AI!" else "Interactive quiz challenges from Grade 1 up to Grade 12. Deepen your understanding of Creed, Jurisprudence, Tajweed, and History with AI!",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF198754)
                        )
                    }
                }
            }

            // 1. Kategori Agama Islam
            Text(
                text = if (lang == "id") "Pilih Kategori Kuis: 🎯" else "Select Quiz Category: 🎯",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                maxItemsInEachRow = 2
            ) {
                categories.forEach { (catId, catEmoji, catLabel) ->
                    val isSelected = catId == activeCategory
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 10.dp)
                            .testTag("islamic_category_$catId")
                            .clickable { viewModel.selectIslamicCategory(catId) },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) Color(0xFF0F5132) else MaterialTheme.colorScheme.surfaceVariant
                        ),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(
                            2.dp,
                            if (isSelected) Color(0xFF0F5132) else MaterialTheme.colorScheme.outlineVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(catEmoji, fontSize = 28.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = catLabel,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Tingkat Level
            Text(
                text = if (lang == "id") "Pilih Level: 🏆" else "Select Level: 🏆",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Grid of levels from 1 to 15
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                maxItemsInEachRow = 5
            ) {
                for (g in 1..15) {
                    val isSelected = g == activeGrade
                    val badgeColor = when {
                        g <= 5 -> Color(0xFF2E7D32) // Beginner Green
                        g <= 10 -> Color(0xFF1565C0) // Intermediate Blue
                        else -> Color(0xFFC62828)     // Advanced Red
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 8.dp)
                            .testTag("islamic_grade_$g")
                            .clickable { viewModel.selectIslamicGrade(g) },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) badgeColor else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(
                            2.dp,
                            if (isSelected) badgeColor else Color.Transparent
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$g",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "Lvl",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 10.sp,
                                    color = if (isSelected) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Start Button
            Button(
                onClick = { viewModel.startNewIslamicQuiz(activeGrade, activeCategory) },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .testTag("islamic_start_button"),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F5132))
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Mulai")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (lang == "id") "Mulai Tantangan Agama! 🚀" else "Start Islamic Challenge! 🚀",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}


// --- 13. Islamic Play Screen ---

@Composable
fun IslamicPlayScreen(
    viewModel: EduViewModel,
    grade: Int,
    category: String,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    val isLoading by viewModel.isIslamicLoading.collectAsStateWithLifecycle()
    val questions by viewModel.islamicQuestions.collectAsStateWithLifecycle()
    val currentIndex by viewModel.currentIslamicQuestionIndex.collectAsStateWithLifecycle()
    val selectedAnswerIndex by viewModel.selectedIslamicAnswerIndex.collectAsStateWithLifecycle()
    val isAnswerSubmitted by viewModel.isIslamicAnswerSubmitted.collectAsStateWithLifecycle()
    val explanation by viewModel.islamicExplanation.collectAsStateWithLifecycle()
    val islamicScore by viewModel.islamicScore.collectAsStateWithLifecycle()
    val lang = progress?.language ?: "id"

    Scaffold(
        topBar = {
            EduHeader(
                progress = progress,
                onBack = { viewModel.navigateTo(Screen.IslamicSettings) },
                title = if (lang == "id") "Kelas $grade - $category 🕌" else "Grade $grade - $category 🕌"
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    CircularProgressIndicator(color = Color(0xFF0F5132))
                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = if (lang == "id") "Ustadz AI sedang merangkai kuis Agama Islam untuk Kelas $grade... 🤖🕌" else "AI Ustadh is constructing an Islamic Studies Quiz for Grade $grade... 🤖🕌",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (lang == "id") "Merumuskan soal-soal kurikulum dengan teliti menggunakan kecerdasan buatan!" else "Carefully formulating curriculum questions using artificial intelligence!",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else if (questions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text("⚠️", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (lang == "id") "Gagal memuat pertanyaan Agama Islam." else "Failed to load Islamic Studies questions.",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { viewModel.startNewIslamicQuiz(grade, category) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F5132))
                    ) {
                        Text(if (lang == "id") "Coba Lagi" else "Retry")
                    }
                }
            }
        } else {
            val currentQuestion = questions.getOrNull(currentIndex)
            if (currentQuestion != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Progress Indicator Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (lang == "id") "Pertanyaan ${currentIndex + 1} dari ${questions.size}" else "Question ${currentIndex + 1} of ${questions.size}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F5132)
                        )
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFE6F4EA),
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Text(
                                text = if (lang == "id") "Skor: $islamicScore" else "Score: $islamicScore",
                                fontWeight = FontWeight.ExtraBold,
                                style = MaterialTheme.typography.labelMedium,
                                color = Color(0xFF0F5132),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Progress Bar
                    val progressRatio = (currentIndex + 1).toFloat() / questions.size.toFloat()
                    LinearProgressIndicator(
                        progress = { progressRatio },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        strokeCap = StrokeCap.Round,
                        color = Color(0xFF0F5132),
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Question Card
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Category Badge
                            Surface(
                                shape = RoundedCornerShape(100.dp),
                                color = Color(0xFFE6F4EA),
                                modifier = Modifier.padding(bottom = 12.dp)
                            ) {
                                Text(
                                    text = "ISLAMIC ACADEMY STUDY 🕌📖",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 11.sp,
                                    color = Color(0xFF0F5132),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }

                            Text(
                                text = currentQuestion.question,
                                style = MaterialTheme.typography.titleLarge.copy(lineHeight = 28.sp),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

                    // Options list
                    Text(
                        text = if (lang == "id") "Pilih Salah Satu Jawaban: 👇" else "Select One Answer: 👇",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    currentQuestion.options.forEachIndexed { optIndex, optionText ->
                        val isSelected = selectedAnswerIndex == optIndex
                        val isCorrect = optIndex == currentQuestion.answerIndex

                        val borderStroke = when {
                            isAnswerSubmitted && isCorrect -> BorderStroke(2.dp, Color(0xFF4C6B2E))
                            isAnswerSubmitted && isSelected && !isCorrect -> BorderStroke(2.dp, Color(0xFFB3261E))
                            isSelected -> BorderStroke(2.dp, Color(0xFF0F5132))
                            else -> BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        }

                        val containerColor = when {
                            isAnswerSubmitted && isCorrect -> Color(0xFFE8F2DD)
                            isAnswerSubmitted && isSelected && !isCorrect -> Color(0xFFF9EAE8)
                            isSelected -> Color(0xFFE6F4EA)
                            else -> MaterialTheme.colorScheme.surface
                        }

                        val contentColor = when {
                            isAnswerSubmitted && isCorrect -> Color(0xFF2A4214)
                            isAnswerSubmitted && isSelected && !isCorrect -> Color(0xFF721C1A)
                            else -> MaterialTheme.colorScheme.onBackground
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 52.dp)
                                .padding(bottom = 10.dp)
                                .testTag("islamic_option_$optIndex")
                                .clickable(enabled = !isAnswerSubmitted) {
                                    viewModel.selectIslamicAnswer(optIndex)
                                },
                            shape = RoundedCornerShape(16.dp),
                            border = borderStroke,
                            colors = CardDefaults.cardColors(containerColor = containerColor)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp, horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (isSelected) Color(0xFF0F5132) else MaterialTheme.colorScheme.surfaceVariant
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = ('A' + optIndex).toString(),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = optionText,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = contentColor
                                    )
                                }

                                if (isAnswerSubmitted) {
                                    if (isCorrect) {
                                        Text("✅", fontSize = 16.sp)
                                    } else if (isSelected) {
                                        Text("❌", fontSize = 16.sp)
                                    }
                                }
                            }
                        }
                    }

                    // Explanation Box if submitted
                    if (isAnswerSubmitted && explanation != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("💡", fontSize = 24.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (lang == "id") "Penjelasan EP Knowledge Land:" else "EP Knowledge Land Explanation:",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = explanation!!,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.9f)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Next / Check Action Button
                    if (!isAnswerSubmitted) {
                        Button(
                            onClick = { viewModel.submitIslamicAnswer() },
                            enabled = selectedAnswerIndex != null,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .testTag("islamic_submit_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F5132))
                        ) {
                            Text(
                                text = if (lang == "id") "Periksa Jawaban" else "Check Answer",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    } else {
                        Button(
                            onClick = { viewModel.nextIslamicQuestion(grade, category) },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .testTag("islamic_next_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F5132))
                        ) {
                            Text(
                                text = if (lang == "id") {
                                    if (currentIndex + 1 < questions.size) "Soal Berikutnya ➡️" else "Selesaikan Kuis 🏁"
                                } else {
                                    if (currentIndex + 1 < questions.size) "Next Question ➡️" else "Complete Quiz 🏁"
                                },
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}


// --- 14. Islamic Score Screen ---

@Composable
fun IslamicScoreScreen(
    viewModel: EduViewModel,
    grade: Int,
    category: String,
    score: Int,
    total: Int,
    starsEarned: Int,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    val lang = progress?.language ?: "id"

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("🎉", fontSize = 72.sp, modifier = Modifier.padding(bottom = 12.dp))

                Text(
                    text = if (lang == "id") "Tantangan Selesai!" else "Challenge Completed!",
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF0F5132)
                )

                Text(
                    text = if (lang == "id") "Kamu telah menyelesaikan Kuis Agama Islam Kelas $grade!" else "You have completed the Grade $grade Islamic Studies Quiz!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 32.dp),
                    textAlign = TextAlign.Center
                )

                // Dynamic result status card
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, Color(0xFF0F5132).copy(alpha = 0.2f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (lang == "id") "Skor Kamu: ✨" else "Your Score: ✨",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "$score / $total",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF0F5132)
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(if (lang == "id") "⭐ Bintang" else "⭐ Stars", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                Text(
                                    text = "+$starsEarned Stars",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFFBA5A31)
                                )
                            }

                            VerticalDivider(modifier = Modifier.height(36.dp))

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(if (lang == "id") "⚡ XP Diperoleh" else "⚡ XP Earned", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                Text(
                                    text = "+${score * 10} XP",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF4C6B2E)
                                )
                            }
                        }
                    }
                }

                // High praise message based on score
                val praiseMessage = if (lang == "id") {
                    when {
                        score == total -> "Subhanallah! Pemahaman ilmu agamamu sempurna luar biasa! 🧠🕌"
                        score >= 3 -> "Alhamdulillah, amalan dan pemahaman yang mantap! Teruslah istiqomah! 🌟"
                        else -> "Tetap semangat belajar! Menuntut ilmu adalah amalan mulia, ayo coba lagi! 🌱"
                    }
                } else {
                    when {
                        score == total -> "Subhanallah! Your understanding of Islamic studies is perfectly outstanding! 🧠🕌"
                        score >= 3 -> "Alhamdulillah, solid actions and understanding! Stay firm on the right path! 🌟"
                        else -> "Keep your learning spirits high! Seeking knowledge is a noble behavior, let's try again! 🌱"
                    }
                }

                Text(
                    text = praiseMessage,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 32.dp)
                )

                if (grade < 15) {
                    Button(
                        onClick = { viewModel.startNewIslamicQuiz(grade + 1, category) },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .testTag("islamic_score_next_level"),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F5132))
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = if (lang == "id") "Lanjut" else "Next")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (lang == "id") "Lanjut ke Level ${grade + 1} 🕌🚀" else "Continue to Level ${grade + 1} 🕌🚀",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Replay or return
                OutlinedButton(
                    onClick = { viewModel.navigateTo(Screen.IslamicSettings) },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("islamic_score_continue"),
                    border = BorderStroke(1.5.dp, Color(0xFF0F5132))
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = if (lang == "id") "Ulang" else "Retry")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (lang == "id") "Coba Tingkat Lain" else "Try Another Level",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = { viewModel.navigateTo(Screen.Dashboard) },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("islamic_score_dashboard")
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = if (lang == "id") "Menu Utama" else "Main Menu")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (lang == "id") "Kembali ke Beranda" else "Return to Home",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            if (score == total) {
                ConfettiRain()
            }
        }
    }
}

// ==========================================
// --- 10. MATH LAND ADVENTURE SCREENS ---
// ==========================================

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MathLandMapScreen(
    viewModel: EduViewModel,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    val unlockedLevel by viewModel.unlockedMathLandLevel.collectAsStateWithLifecycle()
    val levelStars by viewModel.mathLandLevelStars.collectAsStateWithLifecycle()
    val lang = progress?.language ?: "id"

    Scaffold(
        topBar = {
            EduHeader(
                progress = progress,
                onBack = { viewModel.navigateTo(Screen.Dashboard) },
                title = "Math Land 🏰"
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFFCF8F2), Color(0xFFF0E5D8))
                    )
                )
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header Map Card
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF78350F)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🗺️", fontSize = 48.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = if (lang == "id") "Benua Math Land" else "Math Land Continent",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp,
                            color = Color(0xFFFDE68A)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (lang == "id") "Kuasai matematika, pecahkan puzzle kuno, dan selamatkan seluruh kepulauan dari badai kegelapan!" else "Master mathematics, solve ancient puzzles, and save the entire archipelago from the dark storm!",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFFEF3C7)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        // Progress Info
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = "Bintang", tint = Color(0xFFFBBF24), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            val totalStarsEarned = levelStars.values.sum()
                            Text(
                                text = if (lang == "id") {
                                    "Bintang Terkumpul: $totalStarsEarned/30  |  Level Unlocked: $unlockedLevel/10"
                                } else {
                                    "Stars Collected: $totalStarsEarned/30  |  Levels Unlocked: $unlockedLevel/10"
                                },
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            // Islands on the map
            val levelsByIsland = com.example.data.MathLandGenerator.levels.groupBy { it.island }
            val islandGradients = listOf(
                listOf(Color(0xFF0D9488), Color(0xFF14B8A6)), // Teal
                listOf(Color(0xFF059669), Color(0xFF10B981)), // Emerald
                listOf(Color(0xFF312E81), Color(0xFF4F46E5)), // Indigo Deep
                listOf(Color(0xFF0F172A), Color(0xFF334155))  // Dark Slate
            )

            var idx = 0
            levelsByIsland.forEach { (islandName, islandLevels) ->
                val colors = islandGradients.getOrElse(idx) { listOf(Color(0xFF4B5563), Color(0xFF6B7280)) }
                idx++

                Text(
                    text = islandName.uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF78350F),
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        islandLevels.forEach { lvl ->
                            val isUnlocked = lvl.id <= unlockedLevel
                            val starsCount = levelStars[lvl.id] ?: 0

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        if (isUnlocked) Color(0xFFFEF3C7).copy(alpha = 0.3f) else Color(0xFFF3F4F6)
                                    )
                                    .clickable(enabled = isUnlocked) {
                                        viewModel.startPlayMathLandLevel(lvl.id)
                                    }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Level Indicator Ball
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isUnlocked) Brush.linearGradient(colors) else Brush.linearGradient(listOf(Color(0xFF9CA3AF), Color(0xFF6B7280)))
                                        )
                                ) {
                                    if (isUnlocked) {
                                        if (lvl.isBoss) {
                                            Text(lvl.bossEmoji, fontSize = 22.sp)
                                        } else {
                                            Text(lvl.id.toString(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                        }
                                    } else {
                                        Icon(Icons.Default.Lock, contentDescription = "Locked", tint = Color.LightGray, modifier = Modifier.size(20.dp))
                                    }
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = lvl.name,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp,
                                            color = if (isUnlocked) Color(0xFF1F2937) else Color(0xFF9CA3AF)
                                        )
                                        if (lvl.isBoss && isUnlocked) {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(Color.Red)
                                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                              ) {
                                                Text("BOSS", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Black)
                                            }
                                        }
                                    }
                                    Text(
                                        text = if (isUnlocked) lvl.description else {
                                            if (lang == "id") "Selesaikan level sebelumnya untuk membuka pulau ini." else "Complete the previous level to unlock this island."
                                        },
                                        style = MaterialTheme.typography.bodySmall,
                                        color = if (isUnlocked) Color(0xFF4B5563) else Color(0xFF9CA3AF),
                                        maxLines = 2
                                    )
                                    if (isUnlocked) {
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            // Render Stars Earned
                                            for (s in 1..3) {
                                                Icon(
                                                    imageVector = Icons.Default.Star,
                                                    contentDescription = "Bintang",
                                                    tint = if (s <= starsCount) Color(0xFFFBBF24) else Color(0xFFE5E7EB),
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Text(
                                                text = "Reward: +${lvl.rewardStars} ⭐ & +${lvl.rewardXp} XP",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = Color(0xFFB45309)
                                            )
                                        }
                                    }
                                    if (isUnlocked) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                            contentDescription = "Main",
                                            tint = Color(0xFF78350F),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Developer Reset Option
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedButton(
                onClick = { viewModel.resetMathLandProgress() },
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = if (lang == "id") "Reset Petualangan Math Land" else "Reset Math Land Progress",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MathLandPlayScreen(
    viewModel: EduViewModel,
    levelId: Int,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    val isLoading by viewModel.isMathLandLoading.collectAsStateWithLifecycle()
    val questions by viewModel.mathLandQuestions.collectAsStateWithLifecycle()
    val currentIndex by viewModel.currentMathLandQuestionIndex.collectAsStateWithLifecycle()
    val selectedAnswerIndex by viewModel.selectedMathLandAnswerIndex.collectAsStateWithLifecycle()
    val isAnswerSubmitted by viewModel.isMathLandAnswerSubmitted.collectAsStateWithLifecycle()
    val mathLandScore by viewModel.mathLandScore.collectAsStateWithLifecycle()
    val explanation by viewModel.mathLandExplanation.collectAsStateWithLifecycle()
    val bossHp by viewModel.bossHp.collectAsStateWithLifecycle()
    val playerHp by viewModel.playerHp.collectAsStateWithLifecycle()
    val lang = progress?.language ?: "id"

    val levelInfo = com.example.data.MathLandGenerator.getLevelById(levelId)

    Scaffold(
        topBar = {
            EduHeader(
                progress = progress,
                onBack = { viewModel.navigateTo(Screen.MathLandMap) },
                title = levelInfo?.name ?: "Math Land"
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = Color(0xFFD97706))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = if (lang == "id") "Mempersiapkan Arena Pertempuran..." else "Preparing Battle Arena...",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        } else if (questions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Card(modifier = Modifier.padding(24.dp)) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("⚠️", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = if (lang == "id") "Gagal memuat peta." else "Failed to load map.",
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(onClick = { viewModel.startPlayMathLandLevel(levelId) }) {
                            Text(if (lang == "id") "Coba Lagi" else "Retry")
                        }
                    }
                }
            }
        } else {
            val currentQuestion = questions.getOrNull(currentIndex)
            if (currentQuestion != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(
                            if (levelInfo?.isBoss == true) {
                                Brush.verticalGradient(listOf(Color(0xFF450A0A), Color(0xFF111827)))
                            } else {
                                Brush.verticalGradient(listOf(Color(0xFFFFFBEB), Color(0xFFFEF3C7)))
                            }
                        )
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Boss Battle Layout
                    if (levelInfo?.isBoss == true) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1F2937)),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Hero
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = if (lang == "id") "Sobat Edu ⚔️" else "Edu Pal ⚔️",
                                            fontWeight = FontWeight.Black,
                                            color = Color.White,
                                            fontSize = 12.sp
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text("👦", fontSize = 32.sp)
                                        Spacer(modifier = Modifier.height(6.dp))
                                        // HP Bar
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            LinearProgressIndicator(
                                                progress = playerHp,
                                                color = Color.Green,
                                                trackColor = Color.Red.copy(alpha = 0.2f),
                                                modifier = Modifier
                                                    .width(70.dp)
                                                    .height(8.dp)
                                                    .clip(RoundedCornerShape(4.dp))
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("${(playerHp * 100).toInt()}%", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }

                                    // VS EMOJI
                                    Text("⚡VS⚡", fontSize = 18.sp, color = Color.Yellow, fontWeight = FontWeight.Black)

                                    // Boss
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("${levelInfo.bossName} 👑", fontWeight = FontWeight.Black, color = Color.Red, fontSize = 12.sp)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(levelInfo.bossEmoji, fontSize = 32.sp)
                                        Spacer(modifier = Modifier.height(6.dp))
                                        // HP Bar
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            LinearProgressIndicator(
                                                progress = bossHp,
                                                color = Color.Red,
                                                trackColor = Color.White.copy(alpha = 0.2f),
                                                modifier = Modifier
                                                    .width(70.dp)
                                                    .height(8.dp)
                                                    .clip(RoundedCornerShape(4.dp))
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("${(bossHp * 100).toInt()}%", color = Color.Red, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                val narrativeText = if (bossHp <= 0f) {
                                    if (lang == "id") "Musuh telah tumbang! Klik Lanjut untuk klaim hadiahmu! 🎉" else "The enemy has fallen! Click Continue to claim your reward! 🎉"
                                } else if (playerHp <= 0f) {
                                    if (lang == "id") "Aduh! HP kamu habis! Berjuanglah sekali lagi! 😢" else "Ouch! Your HP is depleted! Try once more! 😢"
                                } else {
                                    if (lang == "id") {
                                        "Serang ${levelInfo.bossName} dengan menjawab pertanyaan matematika di bawah dengan benar dan cepat!"
                                    } else {
                                        "Attack ${levelInfo.bossName} by answering the math questions below correctly and quickly!"
                                    }
                                }
                                Text(
                                    text = narrativeText,
                                    fontSize = 11.sp,
                                    color = Color.White.copy(alpha = 0.8f),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }

                    // Progress bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (lang == "id") "Pertanyaan ${currentIndex + 1} dari ${questions.size}" else "Question ${currentIndex + 1} of ${questions.size}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = if (levelInfo?.isBoss == true) Color.White else Color(0xFF78350F)
                        )
                        Text(
                            text = if (lang == "id") "Akurasi: $mathLandScore/${questions.size}" else "Accuracy: $mathLandScore/${questions.size}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = if (levelInfo?.isBoss == true) Color.Yellow else Color(0xFFB45309)
                        )
                    }

                    LinearProgressIndicator(
                        progress = (currentIndex + 1).toFloat() / questions.size.toFloat(),
                        color = Color(0xFFD97706),
                        trackColor = Color(0xFFE5E7EB),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Question Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (levelInfo?.isBoss == true) Color(0xFF1E293B) else Color.White
                        ),
                        border = BorderStroke(2.dp, Color(0xFFFBBF24)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = currentQuestion.question,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = if (levelInfo?.isBoss == true) Color.White else Color(0xFF111827),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Options Grid/List
                    currentQuestion.options.forEachIndexed { optIndex, optionText ->
                        val isSelected = selectedAnswerIndex == optIndex
                        val isCorrect = optIndex == currentQuestion.answerIndex

                        val containerColor = when {
                            isAnswerSubmitted && isCorrect -> Color(0xFFD1FAE5) // Light Emerald for correct
                            isAnswerSubmitted && isSelected && !isCorrect -> Color(0xFFFEE2E2) // Light Red for incorrect
                            isSelected -> Color(0xFFFEF3C7) // Selected color
                            else -> if (levelInfo?.isBoss == true) Color(0xFF334155) else Color.White
                        }

                        val borderColor = when {
                            isAnswerSubmitted && isCorrect -> Color(0xFF10B981)
                            isAnswerSubmitted && isSelected && !isCorrect -> Color(0xFFEF4444)
                            isSelected -> Color(0xFFF59E0B)
                            else -> Color(0xFFE5E7EB)
                        }

                        val textColor = when {
                            isAnswerSubmitted && isCorrect -> Color(0xFF065F46)
                            isAnswerSubmitted && isSelected && !isCorrect -> Color(0xFF991B1B)
                            else -> if (levelInfo?.isBoss == true) Color.White else Color(0xFF1F2937)
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                                .heightIn(min = 54.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .testTag("math_land_option_$optIndex")
                                .clickable(enabled = !isAnswerSubmitted) {
                                    viewModel.selectMathLandAnswer(optIndex)
                                },
                            colors = CardDefaults.cardColors(containerColor = containerColor),
                            border = BorderStroke(2.dp, borderColor)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) Color(0xFFFEF3C7) else Color(0xFFF3F4F6))
                                ) {
                                    Text(
                                        text = when (optIndex) {
                                            0 -> "A"
                                            1 -> "B"
                                            2 -> "C"
                                            else -> "D"
                                        },
                                        fontWeight = FontWeight.Black,
                                        fontSize = 15.sp,
                                        color = if (isSelected) Color(0xFFD97706) else Color.Gray
                                    )
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Text(
                                    text = optionText,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = textColor,
                                    modifier = Modifier.weight(1f)
                                )

                                if (isAnswerSubmitted) {
                                    if (isCorrect) {
                                        Text("✔️", fontSize = 18.sp)
                                    } else if (isSelected) {
                                        Text("❌", fontSize = 18.sp)
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Submission / Navigation Button
                    if (!isAnswerSubmitted) {
                        Button(
                            onClick = { viewModel.submitMathLandAnswer() },
                            enabled = selectedAnswerIndex != null,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .testTag("math_land_submit_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (levelInfo?.isBoss == true) Color(0xFFFBBF24) else Color(0xFFD97706),
                                disabledContainerColor = Color.LightGray
                            )
                        ) {
                            Text(
                                text = if (lang == "id") "Kirim Jawaban ⚔️" else "Submit Answer ⚔️",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = if (levelInfo?.isBoss == true) Color.Black else Color.White
                            )
                        }
                    } else {
                        // Explanation block
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedAnswerIndex == currentQuestion.answerIndex) Color(0xFFECFDF5) else Color(0xFFFEF2F2)
                            ),
                            border = BorderStroke(
                                1.dp,
                                if (selectedAnswerIndex == currentQuestion.answerIndex) Color(0xFFA7F3D0) else Color(0xFFFECACA)
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = if (selectedAnswerIndex == currentQuestion.answerIndex) {
                                        if (lang == "id") "Jawaban Tepat! 🎉" else "Correct Answer! 🎉"
                                    } else {
                                        if (lang == "id") "Jawaban Kurang Tepat 😢" else "Incorrect Answer 😢"
                                    },
                                    fontWeight = FontWeight.Bold,
                                    color = if (selectedAnswerIndex == currentQuestion.answerIndex) Color(0xFF047857) else Color(0xFFB91C1C),
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = explanation ?: (if (lang == "id") "Penjelasan rumus matematika kuis." else "Quiz formula explanation."),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF1F2937)
                                )
                            }
                        }

                        // Determine Next Button Text / Boss Dead Text
                        val bossDead = levelInfo?.isBoss == true && bossHp <= 0f
                        val playerDead = levelInfo?.isBoss == true && playerHp <= 0f

                        val buttonText = if (lang == "id") {
                            when {
                                bossDead -> "Hore! Musuh Tumbang 🏆"
                                playerDead -> "Hadapi Kekalahan 💔"
                                currentIndex + 1 >= questions.size -> "Selesaikan Pertempuran 🏁"
                                else -> "Pertanyaan Berikutnya ➡️"
                            }
                        } else {
                            when {
                                bossDead -> "Hooray! Enemy Defeated 🏆"
                                playerDead -> "Accept Defeat 💔"
                                currentIndex + 1 >= questions.size -> "Finish Battle 🏁"
                                else -> "Next Question ➡️"
                            }
                        }

                        Button(
                            onClick = { viewModel.nextMathLandQuestion() },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .testTag("math_land_next_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (levelInfo?.isBoss == true) Color(0xFFEF4444) else Color(0xFF059669)
                            )
                        ) {
                            Text(buttonText, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MathLandScoreScreen(
    viewModel: EduViewModel,
    levelId: Int,
    score: Int,
    total: Int,
    starsEarned: Int,
    succeeded: Boolean,
    progress: UserProgress?,
    modifier: Modifier = Modifier
) {
    val levelInfo = com.example.data.MathLandGenerator.getLevelById(levelId)
    val lang = progress?.language ?: "id"

    Scaffold(
        topBar = {
            EduHeader(
                progress = progress,
                onBack = { viewModel.navigateTo(Screen.MathLandMap) },
                title = if (lang == "id") "Skor Akhir Level" else "Final Level Score"
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        colors = if (succeeded) {
                            listOf(Color(0xFFFFFBEB), Color(0xFFFEF3C7))
                        } else {
                            listOf(Color(0xFFFEF2F2), Color(0xFFFCA5A5).copy(alpha = 0.5f))
                        }
                    )
                )
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Chest / Crown Icon
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(if (succeeded) Color(0xFFFEF3C7) else Color(0xFFFEE2E2))
            ) {
                if (succeeded) {
                    Text("🔓🎁", fontSize = 72.sp)
                } else {
                    Text("🥀🍂", fontSize = 72.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = if (succeeded) {
                    if (lang == "id") "KEMENANGAN GEMILANG! 🏆" else "GLORIOUS VICTORY! 🏆"
                } else {
                    if (lang == "id") "KAMU DIKALAHKAN! 💔" else "DEFEATED! 💔"
                },
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Black,
                color = if (succeeded) Color(0xFFB45309) else Color(0xFF991B1B),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            val levelResultMsg = if (succeeded) {
                if (lang == "id") {
                    "Selamat! Pengetahuan matematikamu yang luar biasa berhasil mengamankan level: **${levelInfo?.name}**. Mari terus taklukkan benua Math Land!"
                } else {
                    "Congratulations! Your amazing mathematics knowledge successfully secured level: **${levelInfo?.name}**. Let's keep conquering the Math Land continent!"
                }
            } else {
                if (lang == "id") {
                    "Oh tidak! Usaha yang baik, tetapi kekuasaan kegelapan masih menutupi **${levelInfo?.name}**. Asah kembali konsentrasimu dan hancurkan mereka di percobaan berikutnya!"
                } else {
                    "Oh no! A good attempt, but the power of darkness still shrouds **${levelInfo?.name}**. Sharpen your concentration and defeat them in the next attempt!"
                }
            }

            Text(
                text = levelResultMsg,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = Color(0xFF374151),
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Score details card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(2.dp, if (succeeded) Color(0xFFFBBF24) else Color(0xFFFCA5A5)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (lang == "id") "STATISTIK PETUALANGAN" else "ADVENTURE STATISTICS",
                        fontWeight = FontWeight.Black,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = if (lang == "id") "Akurasi Soal" else "Accuracy",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text("$score / $total", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1F2937))
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = if (lang == "id") "Bintang Khas" else "Level Stars",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Row {
                                for (s in 1..3) {
                                    Text(
                                        text = "★",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (s <= starsEarned) Color(0xFFFBBF24) else Color(0xFFE5E7EB)
                                    )
                                }
                            }
                        }
                    }

                    if (succeeded && levelInfo != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(color = Color(0xFFF3F4F6))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = if (lang == "id") "HADIAH PETUALANGAN KAMU" else "YOUR ADVENTURE REWARDS",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = Color(0xFFB45309)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFFEF3C7))
                                    .padding(8.dp)
                             ) {
                                Text(
                                    text = if (lang == "id") "⭐ +${levelInfo.rewardStars} Bintang" else "⭐ +${levelInfo.rewardStars} Stars",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFB45309)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFECFDF5))
                                    .padding(8.dp)
                            ) {
                                Text("⚡ +${levelInfo.rewardXp} XP", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF047857))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Navigation Buttons
            Button(
                onClick = { viewModel.navigateTo(Screen.MathLandMap) },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .testTag("math_land_score_continue"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (succeeded) Color(0xFFD97706) else Color(0xFFDC2626)
                )
            ) {
                Icon(if (succeeded) Icons.Default.Check else Icons.Default.Refresh, contentDescription = "Peta")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (succeeded) {
                        if (lang == "id") "Kembali ke Peta" else "Back to Map"
                    } else {
                        if (lang == "id") "Mulai Ulang Pertempuran" else "Restart Battle"
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { viewModel.navigateTo(Screen.Dashboard) },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .testTag("math_land_score_dashboard")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Beranda")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (lang == "id") "Kembali ke Beranda" else "Return to Home",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

