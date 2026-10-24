# Cplusplus-Program
# CS 320 — Garantía de calidad y pruebas de software

Portafolio de Diego Machuca Hurtado.

Este repositorio reúne el trabajo de los dos proyectos finales del curso: el servicio de
contactos con sus pruebas unitarias y el informe de síntesis y reflexiones.

## Contenido

```
ContactService/
  Contact.java              Objeto de dominio con las cinco reglas de validación
  ContactService.java       Servicio en memoria: agregar, eliminar y actualizar por ID
  ContactTest.java          20 pruebas unitarias del objeto
  ContactServiceTest.java   14 pruebas unitarias del servicio

informe-sintesis-reflexiones.docx   Informe del Proyecto final II
```

El proyecto completo abarcó tres servicios (contactos, tareas y citas) con 89 pruebas en
JUnit 5. Aquí se incluye el servicio de contactos, que es el que pide la actividad de
portafolio.

---

## Reflexión

### ¿Cómo me aseguro de que mi código sea funcional y seguro?

Mi punto de partida es que una prueba que solo confirma el camino feliz no prueba casi nada.
Por eso, para cada regla del cliente escribí al menos un caso válido y uno inválido, y
ejercité los bordes de cada restricción. En el objeto `Task`, por ejemplo, la constante
`NAME_20` verifica el límite exacto de veinte caracteres y `NAME_20 + "X"` verifica el primer
valor rechazado; ese par detecta el error clásico de confundir `>` con `>=`, que una prueba
con un valor cualquiera del rango dejaría pasar.

La parte de seguridad vino de no confiar en la buena fe del llamador. La validación vive en
los setters y los constructores delegan en ellos, así que una entrada inválida se rechaza
igual al crear que al actualizar, sin reglas duplicadas que puedan divergir. Las pruebas no
solo comprueban que se lance la excepción, sino que el objeto quede intacto después del
fallo: en `testSetPhoneInvalido`, el `assertThrows` va seguido de un `assertEquals` que
confirma que el teléfono anterior sigue ahí. El caso más instructivo fue `Appointment`:
como `java.util.Date` es mutable, guardar la referencia recibida habría permitido que el
llamador modificara la fecha después de validarla y dejara la cita en el pasado. La solución
fue devolver copias defensivas, y `testCopiaDefensivaEnGetter` prueba exactamente ese ataque.

También aprendí que la cobertura es una condición necesaria y no suficiente. En la revisión
final detecté que los tres métodos `toString()` no los ejercitaba ninguna prueba; el
porcentaje global ya superaba el umbral exigido, pero agregué las pruebas igual, porque el
objetivo era el código correcto y no el número que satisface una rúbrica.

### ¿Cómo interpreto las necesidades del usuario y las incorporo en un programa?

Trato el requisito como el contrato y lo traduzco cláusula por cláusula, resistiendo la
tentación de leerlo a través de la implementación que ya tengo en la cabeza. El ejemplo
concreto es el teléfono: el cliente pidió "exactamente diez dígitos", que no es lo mismo que
"diez caracteres". Las cadenas `"945-005-47"` y `"945 005 47"` tienen la longitud correcta y
son inválidas, así que la validación terminó usando la expresión regular `\d{10}` en lugar
de comparar solo `length()`. Ese caso de prueba nació de volver a leer el requisito, no el
código.

La misma disciplina aplica a lo que el requisito *no* pide. Para las citas, el cliente pidió
agregar y eliminar, pero no actualizar, así que `AppointmentService` no expone métodos
`update`. Agregarlos "por si acaso" habría significado código sin respaldo en el contrato y
superficie sin probar.

### ¿Cómo abordo el diseño de software?

Busco que cada regla viva en un solo lugar. Los identificadores son campos `final`, de modo
que el requisito de "no actualizable" lo garantiza el compilador y no una convención que
alguien pueda olvidar. La comprobación de existencia que comparten `deleteContact` y los
cuatro métodos de actualización está centralizada en un método privado `requireExisting`,
lo que elimina cinco copias de la misma validación y un lugar menos donde una corrección
pueda aplicarse a medias.

Las estructuras las elijo por la operación dominante. Como todo el acceso es por
identificador, los tres servicios usan un `HashMap` con el ID como clave: la unicidad la
garantiza la estructura misma y la búsqueda es de tiempo constante, en vez del recorrido
lineal que exigiría una lista.

Finalmente, diseño pensando en el cambio. El trabajo real de este curso no fue escribir las
clases, que son cortas, sino dejar una suite que permita modificarlas sin miedo. Mi criterio
para adelante es mantener esa suite verde como condición para integrar cualquier cambio y
reproducir todo defecto con una prueba que falle antes de corregirlo, para que el defecto no
pueda volver en silencio.

---

## Ejecución de las pruebas

Las clases usan JUnit 5 (`org.junit.jupiter.api`) y no declaran paquete, por lo que van en
la raíz del directorio de fuentes.

```bash
javac -cp junit-platform-console-standalone.jar *.java
java -jar junit-platform-console-standalone.jar --class-path . --scan-class-path
```
