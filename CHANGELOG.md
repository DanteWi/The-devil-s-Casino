# Changelog

## v0.6.0 - 2026-02-23

### Added

* Implementación de un **manejo de excepciones más completo y estructurado** en backend y frontend.
* Configuración de **entorno de desarrollo y entorno de producción**.
* Nueva **interfaz artística para Blackjack**:
  * Generación visual dinámica de cartas.
  * Reemplazo de los antiguos placeholders por cartas personalizadas.

### Improved

* Uniformización de todas las pantallas:
  * Reglas de diseño consistentes en toda la aplicación.
  * Comportamiento estandarizado entre vistas.
* Mejora en el tiempo de espera en **Blackjack**.
* Deshabilitación automática de botones cuando el jugador finaliza su turno.
* Fluidez general del juego optimizada.
* Mayor estabilidad ante errores inesperados.

### Notes

* La arquitectura ahora está preparada para escalar con mayor facilidad.
* Mejor base técnica para futuras implementaciones (incluyendo Devil Dice).
* Experiencia de juego más sólida y consistente en toda la aplicación.

## v0.5.0 - 2026-02-09

### Added
- Sección de **Settings** para personalizar la experiencia del usuario:
  - Cambio de **idioma**.
  - Selección de **música de fondo**.
- Música de fondo disponible en la aplicación.
- **Oferta gratis** en Wallet: 100 Devil Coins disponibles cada 24 horas.

### Improved
- Integración de ajustes de usuario con la interfaz.
- Mejor experiencia de usuario al interactuar con Wallet y música.

### Notes
- La música de fondo ahora se puede activar/desactivar y seleccionar desde Settings.
- La oferta de 100 Devil Coins tiene un temporizador de 24 horas para ser reclamada.
-Devil Dice sigue sin funcionalidad
## v0.4.0 - 2026-01-26

### Added

* Comunicación completa entre Perfil y Wallet con el servidor.
* Actualización automática de datos del usuario y balance de Devil Coins (DC) en la interfaz.
* Sincronización de cambios en tiempo real entre frontend y backend.

### Improved

* Estabilidad en la gestión de sesiones y datos del usuario.
* Mejor manejo de errores al actualizar perfil y wallet.

### Notes

* Login y creación de usuario ahora funcionan correctamente con la actualización de perfil y wallet.
* Base para futuras mejoras en otros juegos y funcionalidades de usuario.


## v0.3.0 - 2026-01-12

### Added

* Crear Usuario functional

### Notes
* Login roto en el proceso de creacion de nuevo usuario

## v0.2.0 - 2025-12-13

### Added

* Blackjack completamente funcional.
* Conexión Frontend (Android) <-> Backend (Spring Boot).
* API REST para el juego de Blackjack.
* Sistema de apuestas con Devil Coins (DC).
* Wallet persistente en base de datos MySQL.
* Guardado de partidas de Blackjack en base de datos.
* Control de sesiones activas por usuario.

### Improved

* Flujo de juego de Blackjack.
* Gestión del estado del juego entre cliente y servidor.

### Notes

* Primera versión con lógica real de juego.
* Dados Mentirosos sigue siendo solo interfaz.
* El proyecto ya funciona end-to-end para Blackjack.

---

## v0.1.0 - 2025-12-01

### Added

* Interfaz inicial de *The Devil's Casino*.
* Pantallas de Login y Crear Usuario.
* Pantallas principales: Lobby, User y Wallet.
* Pantallas preliminares de juegos: Blackjack y Dados Mentirosos.
* Sistema de navegación entre todas las pantallas.

### Notes

* Esta versión solo incluye la UI y la navegación.
* Próximas versiones añadirán la lógica de los juegos, control de DC y gestión completa del usuario.

---


