import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.MenuViewModel

@Composable
fun DetailScreen(itemId: Long, viewModel: MenuViewModel) {
    val item = viewModel.getItemById(itemId)

    if (item != null) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(text = "Город: ${item.name}", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Описание: ${item.description}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))  // Исправленный вариант
        }
    } else {
        Text(text = "Элемент не найден", style = MaterialTheme.typography.bodyLarge)
    }
}
