import androidx.compose.runtime.MutableState

data class Response(
    val message: String = ""
)

data class Request(
    val str:MutableState<String>
)