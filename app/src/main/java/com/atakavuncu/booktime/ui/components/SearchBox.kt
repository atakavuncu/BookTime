import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atakavuncu.booktime.ui.theme.DarkGrey
import com.atakavuncu.booktime.ui.theme.StickGrey
import com.atakavuncu.booktime.ui.theme.TextBlack

@Composable
fun SearchBox(
    hint: String = "Search...",
    onSearch: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        color = StickGrey,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                modifier = Modifier.padding(8.dp),
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon",
                tint = DarkGrey
            )
            BasicTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    onSearch(searchQuery)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 12.dp, start = 5.dp, bottom = 5.dp),
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                singleLine = true,
                decorationBox = { innerTextField ->
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = hint,
                            color = TextBlack,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}
