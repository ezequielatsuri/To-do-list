# Gestor de Tareas JEE2

## Descripción
Este proyecto es una aplicación de gestión de tareas desarrollada utilizando Java Enterprise Edition (JEE). Permite a los usuarios gestionar proyectos, tareas, listas de tareas y etiquetas de manera eficiente. La aplicación está estructurada en módulos EJB y WAR, siguiendo las mejores prácticas de desarrollo JEE.

## Tecnologías Utilizadas
- **Java EE**: Plataforma principal para el desarrollo de la aplicación.
- **JSF (JavaServer Faces)**: Para la interfaz de usuario web.
- **EJB (Enterprise JavaBeans)**: Para la lógica de negocio y gestión de datos.
- **Servlets**: Para el manejo de solicitudes HTTP.
- **NetBeans**: IDE utilizado para el desarrollo.
- **Ant**: Herramienta de construcción y despliegue.

## Estructura del Proyecto
El proyecto está dividido en los siguientes módulos principales:

- **gestorTareas_jee2-ejb**: Contiene los Enterprise JavaBeans y la lógica de negocio.
  - `bean/`: Beans gestionados (BoardBean, LoginBean, ProjectBean, SessionController).
  - `datos/`: Clases de datos (Project, Tag, Task, TaskList, User).

- **gestorTareas_jee2-war**: Contiene la interfaz web y los servlets.
  - `bean/`: Beans adicionales para la capa web.
  - `control/`: Servlets (BoardServlet, LoginServlet, ProjectServlet).
  - `web/`: Páginas XHTML (board.xhtml, index.xhtml, login.xhtml).

- **build.xml**: Archivo de configuración de Ant para la construcción del proyecto.

## Requisitos Previos
- JDK 8 o superior.
- NetBeans IDE (opcional, pero recomendado).
- Servidor de aplicaciones JEE como GlassFish o WildFly.
- Ant instalado (viene incluido con NetBeans).

## Instalación y Configuración
1. Clona el repositorio:
   ```
   git clone https://github.com/ezequielatsuri/To-do-list.git
   ```

2. Abre el proyecto en NetBeans:
   - Selecciona "File" > "Open Project" y navega hasta la carpeta del proyecto.

3. Construye el proyecto:
   - Haz clic derecho en el proyecto raíz y selecciona "Build" o ejecuta `ant` en la terminal desde la raíz del proyecto.

## Ejecución
1. Configura un servidor de aplicaciones (ej. GlassFish) en NetBeans.
2. Despliega la aplicación:
   - Haz clic derecho en el proyecto WAR y selecciona "Deploy".
3. Accede a la aplicación en tu navegador web en la URL proporcionada por el servidor (generalmente `http://localhost:8080/gestorTareas_jee2-war`).

## Funcionalidades Principales
- Gestión de usuarios y autenticación.
- Creación y administración de proyectos.
- Organización de tareas en listas.
- Asignación de etiquetas a tareas.
- Interfaz web intuitiva con JSF.

## Contribución
Si deseas contribuir al proyecto:
1. Haz un fork del repositorio.
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y haz commit (`git commit -am 'Agrega nueva funcionalidad'`).
4. Haz push a la rama (`git push origin feature/nueva-funcionalidad`).
5. Abre un Pull Request.

## Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo LICENSE para más detalles.

## Contacto
Para preguntas o soporte, contacta al maintainer del repositorio.