// DetailFragment.kt
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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    @Composable
    fun DetailScreen(item: ListItem?) {
        var visible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            delay(200) // Небольшая задержка перед анимацией
            visible = true
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp) // Добавим отступы между элементами
        ) {
            item?.let {
                // Обернул картинку и текст в одну анимацию
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(1000)) + expandIn(),
                    exit = fadeOut(animationSpec = tween(500)) + shrinkOut()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp) // Отступы между картинкой и текстом
                    ) {
                        // Картинка с рамкой
                        val image: Painter = painterResource(id = it.imageResId)
                        Image(
                            painter = image,
                            contentDescription = "Detail Image",
                            modifier = Modifier
                                .size(300.dp) // Фиксированный размер для квадрата
                                .border(2.dp, Color.Green, RoundedCornerShape(16.dp))
                                .clip(RoundedCornerShape(16.dp))
                        )

                        // Блок с текстом, расположенным ниже картинки
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(2.dp, Color.Green, RoundedCornerShape(8.dp))
                                .padding(16.dp)
                                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Заголовок: ${it.title}",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                                Text(
                                    text = "Подзаголовок: ${it.subtitle}",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                            }
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

            // Добавление Lottie анимации снизу
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(1000)) + expandIn(),
                exit = fadeOut(animationSpec = tween(500)) + shrinkOut()
            ) {
                LottieAnimation(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(top = 32.dp),
                    resId = R.raw.line // Укажите путь к вашему Lottie JSON файлу
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
        val progress by animateLottieCompositionAsState(composition)

        com.airbnb.lottie.compose.LottieAnimation(
            modifier = modifier,
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
