package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

class MenuFragment : Fragment() {
    private val TAG = "MenuFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Используем ComposeView для отображения Compose UI внутри Fragment
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    MenuScreen { item -> (activity as MainActivity).onItemSelected(item) }
                }
            }
        }
    }

    @Composable
    fun MenuScreen(onItemClick: (ListItem) -> Unit) {
        // Список элементов
        val items = listOf(
            ListItem("Носок", "Дырявый", Calendar.getInstance().time, R.drawable.sock_image),
            ListItem("Расходы", "Большие", Calendar.getInstance().time, R.drawable.expense_image),
            ListItem("Лес", "С грибами", Calendar.getInstance().time, R.drawable.forest_image)
        )

        // Колонка для отображения элементов меню
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Мои любимые вещи",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            items.forEachIndexed { index, item ->
                MenuItem(item = item, onItemClick = onItemClick)
                if (index < items.size - 1) {
                    Divider(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun MenuItem(item: ListItem, onItemClick: (ListItem) -> Unit) {
        // Элемент меню с текстом и возможностью клика
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick(item) }
                .padding(vertical = 8.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }
}