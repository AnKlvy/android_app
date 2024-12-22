package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.delay

class DetailFragment : Fragment() {
    private val TAG = "DetailFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView() called")
        return ComposeView(requireContext()).apply {
            setContent {
                val item = arguments?.getParcelable<ListItem>("item")
                DetailScreen(item)
            }
        }
    }

    @Composable
    fun DetailScreen(item: ListItem?) {
        var visible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            delay(200) // Small delay for the animation
            visible = true
        }

        // Добавляем состояние для прокрутки
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(scrollState) // Прокрутка для содержимого
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item?.let {
                // Animated entry for the content
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(1000)) + expandIn(),
                    exit = fadeOut(animationSpec = tween(500)) + shrinkOut()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Image display (no borders, fit style)
                        val image: Painter = painterResource(id = it.imageResId)
                        val intrinsicSize = image.intrinsicSize

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height((intrinsicSize.height / intrinsicSize.width * 360).dp) // Maintain aspect ratio
                                .background(Color.White)
                        ) {
                            Image(
                                painter = image,
                                contentDescription = "Detail Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit // Ensure image is fully displayed
                            )
                        }

                        // Title and subtitle below the image
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
//                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = it.title,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = it.subtitle,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(bottom = 0.dp)
                            )
                            // Lottie animation directly below the subtitle
                            LottieAnimation(
                                resId = R.raw.line // Use your Lottie JSON file
                            )
                            // Дополнительный текст из объекта item
                            Text(
                                text = it.text,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontSize = 30.sp,
                                    lineHeight = 40.sp
                                ),
                            )

                        }
                    }
                }
            } ?: run {
                Text(
                    text = "Элемент не выбран",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }




    @Composable
    fun LottieAnimation(
        modifier: Modifier = Modifier,
        resId: Int
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resId))
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever // This will make the animation repeat indefinitely
        )

        com.airbnb.lottie.compose.LottieAnimation(
            modifier = modifier
                .fillMaxWidth()
                .requiredHeight(30.dp)
                .scale(10f),
//                .requiredWidth(30.dp), // Stretch horizontally
            composition = composition,
            progress = progress
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().supportFragmentManager.popBackStack()
        }
        Log.d(TAG, "onViewCreated() called")
    }
}
