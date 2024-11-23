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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.ListItemEntity
import kotlinx.coroutines.launch

class MenuFragment : Fragment() {
    private val TAG = "MenuFragment"

    // Используем ViewModel для управления данными
    private val viewModel: MenuViewModel by viewModels { MenuViewModel.Factory(requireActivity().application) }

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
                    MenuScreen(
                        items = viewModel.items.collectAsState(initial = emptyList()).value, // Подключаем поток данных
                        onItemClick = { item ->
                            (activity as MainActivity).onItemSelected(item.toListItem()) // Обработка клика
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun MenuScreen(
        items: List<ListItemEntity>, // Список данных из базы
        onItemClick: (ListItemEntity) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Элементы из базы данных",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            if (items.isEmpty()) {
                Text(
                    text = "Нет данных для отображения",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            } else {
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
    }

    @Composable
    fun MenuItem(item: ListItemEntity, onItemClick: (ListItemEntity) -> Unit) {
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
}
