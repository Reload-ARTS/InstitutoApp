# InstitutoApp

InstitutoApp es una aplicación diseñada para estudiantes de institutos o universidades, permitiendo gestionar de manera
sencilla diversas funcionalidades relacionadas con su vida académica.

## Funcionalidades

### 1. **Calendario**
- Muestra el horario del alumno logueado.
- Permite agregar notas personalizadas relacionadas con eventos o actividades académicas.
- Utiliza SQLite para el almacenamiento y manejo de datos del calendario con soporte CRUD (Crear, Leer, Actualizar, Eliminar).

### 2. **Concentración de Notas (Ver Notas)**
- Permite al alumno ver las notas de las evaluaciones correspondientes a cada asignatura.

### 3. **Evaluación Docente**
- Los alumnos pueden evaluar a sus docentes en los períodos correspondientes.
- Las evaluaciones se realizan con un sistema de calificación por estrellas.

### 4. **Busca tu Sede**
- Muestra un mapa con la ubicación en tiempo real del alumno.
- Permite agregar marcadores personalizados con un título.
- Soporta las acciones de un CRUD (Crear, Leer, Modificar, Eliminar) para la gestión de los marcadores.

### 5. **Certificados**
- Permite al alumno realizar solicitudes de certificados, tales como:
  - Certificado de Alumno Regular.
  - Certificado de Notas.
  - Certificado de Asistencia.
- Los datos de la solicitud se almacenan en Firestore.
- Cada solicitud genera automáticamente un código único.
- La solicitud también se envía a un servidor MQTT (HiveMQ) para su procesamiento o monitoreo adicional.
- Incluye una funcionalidad para buscar el estado de una solicitud ingresando el RUT del alumno.

## Integraciones Técnicas

### **Firebase**
- Se utiliza Firebase Firestore para almacenar y gestionar los datos de las solicitudes de certificados.

### **MQTT**
- Se integra con un servidor MQTT (HiveMQ) para enviar mensajes con los detalles de cada solicitud realizada en la app.
- Configuración segura mediante TLS y autenticación con nombre de usuario y contraseña.

### **Google Maps**
- Implementación de mapas interactivos con Google Maps API.
- Funcionalidad de ubicación en tiempo real.

### **SQLite**
- Manejo local de datos para el calendario con soporte CRUD.

---

### **Requisitos**
- Android Studio (versión Koala o superior).
- Conexión a Internet para funcionalidades relacionadas con Firebase y MQTT.

---

### **Cómo usar**
1. Clonar este repositorio:
   ```bash
   git clone https://github.com/Reload-ARTS/InstitutoApp.git
   ```
2. Abrir el proyecto en Android Studio.
3. Configurar las credenciales necesarias para Firebase y MQTT en los archivos correspondientes.
4. Ejecutar la app en un emulador o dispositivo físico.
