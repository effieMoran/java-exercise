# Call Center

**Consigna**

Existe un call center donde hay 3 tipos de empleados: operador, supervisor
y director. El proceso de la atención de una llamada telefónica en primera
instancia debe ser atendida por un operador, si no hay ninguno libre debe
ser atendida por un supervisor, y de no haber tampoco supervisores libres
debe ser atendida por un director.

**Requerimientos**

- [x] Debe existir una clase Dispatcher encargada de manejar las
llamadas, y debe contener el método dispatchCall para que las
asigne a los empleados disponibles.

- [ ] El método dispatchCall puede invocarse por varios hilos al mismo
tiempo.

- [x] La clase Dispatcher debe tener la capacidad de poder procesar 10
llamadas al mismo tiempo (de modo concurrente).

- [ ] Cada llamada puede durar un tiempo aleatorio entre 5 y 10
segundos.

- [ ] Debe tener un test unitario donde lleguen 10 llamadas.

**Extras/Plus**

- [x] Dar alguna solución sobre qué pasa con una llamada cuando no hay
ningún empleado libre.

`De no haber ningún empleado libre las llamadas quedan en una lista de espera hasta que se desocupa 
algún empleado`

- [x] Dar alguna solución sobre qué pasa con una llamada cuando entran
más de 10 llamadas concurrentes.



- [ ] Agregar los tests unitarios que se crean convenientes.

- [ ] Agregar documentación de código.

**Tener en Cuenta**

- [x] El proyecto debe ser creado con Maven.
- [x] De ser necesario, anexar un documento con la explicación del cómo
y porqué resolvió los puntos extras, o comentarlo en las clases
donde se encuentran sus tests unitarios respectivos.