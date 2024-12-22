package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.data.ListItemEntity

class MenuFragment : Fragment() {
    private val TAG = "MenuFragment"

    private val viewModel: MenuViewModel by viewModels { MenuViewModel.Factory(requireActivity().application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    Column(modifier = Modifier.fillMaxSize()) {
                        TopAppBar(
                            title = { Text("Новости") },
                            actions = {
                                IconButton(onClick = { showMenu() }) {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert,
                                        contentDescription = "More options"
                                    )
                                }
                            }
                        )
                        NewsScreen(
                            items = viewModel.items.collectAsState(initial = emptyList()).value,
                            onItemClick = { item ->
                                (activity as MainActivity).onItemSelected(item.toListItem())
                            }
                        )
                    }
                }
            }
        }
    }

    private fun showMenu() {
        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Меню")
            .setItems(arrayOf("Очистить базу данных")) { _, _ ->
                viewModel.clearDatabase()
            }
            .create()
        alertDialog.show()
    }

    @Composable
    fun NewsScreen(items: List<ListItemEntity>, onItemClick: (ListItemEntity) -> Unit) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items) { item ->
                NewsItem(item = item, onItemClick = onItemClick)
            }
        }
    }

    @Composable
    fun NewsItem(item: ListItemEntity, onItemClick: (ListItemEntity) -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick(item) }
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(9.9.dp)
        ) {
            val image = painterResource(id = item.imageResId)
            val intrinsicSize = image.intrinsicSize

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((intrinsicSize.height / intrinsicSize.width * 360).dp) // Вычисление высоты
                    .background(Color.White)
            ) {
                Image(
                    painter = image,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit // Изображение полностью вмещается
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}
