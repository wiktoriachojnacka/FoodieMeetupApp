import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.foodiemeetup.models.Message

class ChatViewModel : ViewModel() {
    // Zmienna do przechowywania nazwy użytkownika
    var username: String by mutableStateOf("")
        private set

    // Lista przechowująca wiadomości
    var messages: List<Message> by mutableStateOf(emptyList())
        private set

    // Funkcja do aktualizacji nazwy użytkownika
    fun updateUsername(newUsername: String) {
        username = newUsername
    }

    // Funkcja do wysyłania nowej wiadomości
    fun sendMessage(message: Message) {
        messages += message
    }

    // Możesz dodać tutaj inne funkcje związane z czatem, takie jak odbieranie wiadomości
}