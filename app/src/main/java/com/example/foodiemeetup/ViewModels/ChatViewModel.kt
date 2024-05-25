import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.foodiemeetup.models.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatViewModel : ViewModel() {
    val username: String = "current_user" // Przykładowa nazwa użytkownika
    private val _messages = MutableStateFlow<List<Message>>(emptyList())

    // Funkcja do pobierania wiadomości dla danego użytkownika
    fun getMessages(username: String): StateFlow<List<Message>> {
        // Tutaj dodaj logikę pobierania wiadomości z bazy danych lub innego źródła
        return _messages.asStateFlow()
    }

    // Funkcja do wysyłania wiadomości
    fun sendMessage(message: Message, toUsername: String) {
        // Tutaj dodaj logikę wysyłania wiadomości
        _messages.value = _messages.value + message
    }
}
