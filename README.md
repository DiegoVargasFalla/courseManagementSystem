# 📚 Sistema de Gestión de Cursos

Este proyecto es una aplicación de escritorio construida con Java que permite la gestión de cursos, estudiantes y sus calificaciones. Diseñada bajo principios de **Software de calidad**, sigue una arquitectura sólida y escalable, con enfoque en buenas prácticas de diseño, organización del código y uso eficiente de tecnologías locales.

---

## 🧩 Arquitectura y Diseño

El sistema está desarrollado bajo una arquitectura **MVC (Modelo-Vista-Controlador)** y emplea patrones como **Repository** para la persistencia de datos, asegurando así una separación clara de responsabilidades y facilitando el mantenimiento y la evolución del sistema.

### 🏗️ Características técnicas:

- ✅ **Lenguaje**: Java
- ✅ **Arquitectura**: MVC (Modelo - Vista - Controlador)
- ✅ **Persistencia**: Archivos locales en formato **JSON** utilizando la librería **Jackson**
- ✅ **Interfaz gráfica**: Desarrollada con **Swing**
- ✅ **Patrones aplicados**: Repository
- ✅ **Principios de OOP**: Encapsulamiento, relaciones de composición y agregación entre entidades
- ✅ **Escalable y modular**: Diseñado para ser fácilmente adaptable a otros dominios (por ejemplo, gestión de empleados, productos, clientes, etc.)

---

## ⚙️ Funcionalidad principal

Este sistema permite gestionar cursos, estudiantes y sus calificaciones. A continuación se describen sus principales capacidades:

### 📌 Gestión de cursos y estudiantes

- Crear nuevos **cursos**
- Agregar **estudiantes** a uno o varios cursos (relación muchos a muchos)
- Ver la **lista de estudiantes** asociados a cada curso
- Visualizar la **información detallada de cada estudiante**
- Eliminar estudiantes de un curso específico

### 📝 Gestión de notas (calificaciones)

- Agregar **notas** (calificaciones numéricas) a cada estudiante en un curso determinado
- Visualizar todas las notas de un estudiante en un curso
- Eliminar calificaciones individuales
- Consultar las notas de todos los estudiantes en un curso

---

## 🖥️ Interfaz de usuario

La interfaz gráfica fue construida utilizando **Java Swing**, con menús y formularios claros para facilitar la navegación por el sistema. Las vistas están orientadas al usuario y permiten:

- Agregar cursos y estudiantes fácilmente
- Ver rápidamente la relación entre cursos y estudiantes
- Acceder a la información académica de forma organizada

---

## 🔐 Persistencia de datos

La persistencia de datos es gestionada por medio de JPA y el ORM Hibernate para el mapeo de clases hacia la base de datos por medio de anotaciones sin necesidad de utilizar codigo sql.

---


## 🧠 Principios aplicados

- Diseño orientado a objetos
- Separación de responsabilidades
- Alta cohesión y bajo acoplamiento
- Flexibilidad y mantenibilidad del código
- Pensado para ser extensible a nuevos tipos de entidades o lógicas de negocio

---

## 🧑‍💻 Autor

**Diego Vargas Falla**  
Estudiante de Ingeniería Informática  
Desarrollador backend y entusiasta del diseño de software

---

## 📄 Licencia

Este proyecto está disponible con fines educativos y de demostración.  
Para más información, consultá el archivo [LICENSE](./LICENSE).

