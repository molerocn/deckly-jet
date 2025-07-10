package com.molerocn.deckly.domain.usecase

import com.molerocn.deckly.core.NotificationHelper
import javax.inject.Inject

class SendWelcomeNotificationUseCase @Inject constructor(
    private val notificationHelper: NotificationHelper
) {

    operator fun invoke(nombreUsuario: String) {
        val title = "Bienvenido $nombreUsuario"
        val message = "Tu cuenta fue creada con exito"
        notificationHelper.sendNotification(title, message)
    }
}