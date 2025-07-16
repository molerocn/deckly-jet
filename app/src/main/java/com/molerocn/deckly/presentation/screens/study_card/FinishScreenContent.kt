import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.Angle
import java.util.concurrent.TimeUnit
import com.molerocn.deckly.R

@Composable
fun FinishScreenContent() {
    var showConfetti by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize().padding(30.dp)) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.good_job),
                contentDescription = "No hay tarjetas",
                modifier = Modifier.size(300.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "¡Felicidades! Estudiaste todas las tarjetas de hoy, sigue así",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 8.dp),
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }

        if (showConfetti) {
            KonfettiView(
                modifier = Modifier.fillMaxSize(),
                parties = listOf(
                    Party(
                        emitter = Emitter(duration = 5, TimeUnit.SECONDS).perSecond(30)
                    )
                )
            )
            // Para que se muestre una vez y no siempre
            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(3000)
                showConfetti = false
            }
        }
    }
}
