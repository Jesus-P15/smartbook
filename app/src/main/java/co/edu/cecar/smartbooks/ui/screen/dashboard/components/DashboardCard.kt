package co.edu.cecar.smartbooks.ui.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional

data class DashboardCardItem(

    val titulo: String,

    val valor: String,

    val icono: ImageVector
)

@Composable
fun DashboardCard(
    item: DashboardCardItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(RojoInstitucional)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = item.titulo,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = item.valor,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = RojoInstitucional.copy(alpha = 0.12f),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icono,
                            contentDescription = null,
                            tint = RojoInstitucional,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}