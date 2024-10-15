# Puntos clave:

## Spring

Un framework de inversión de control (delegamos el flujo de la app a Spring).
Qué me aporta eso?
- Facilita una arquitectura de componentes desacoplados al facilitar el uso de un patrón de Inyección de dependencias.
- Uso de un lenguaje DECLARATIVO (NOS ENCANTA, nos facilita la vida). En contra del imperativo. el declarativo me centra en mi objetivo, no en cómo hacerlo. El cómo hacerlo lo delego a Spring.

## Inyección de dependencias

Es un patrón de diseño que dice:
Una clase no crea instancias de los objetos que necesita, sino que se le son suministrados desde el exterior (en nuestro caso, desde Spring).

Para qué? Respetar el principio de Inversión de la dependencia

## Inversión de la dependencia

Uno de los 5 grandes principios SOLID, que dice:
Los módulos de alto nivel no deben depender de implementaciones de los módulos de bajo nivel. Ambos deben depender de abstracciones:

    No pongas en tus clases imports de otras clases... solo de Interfaces!!!

Al respetar los principios SOLID, tenemos muchas más probabilidades de que nuestro código sea mantenible, escalable y testeable.

## Contexto de uso de Spring

Spring no es Spring y ya! Debo usar en combinación con muchas otras cosas:
- TDD : Test Driven Development (Desarrollo guiado por pruebas)
- Maven (módulos)
- Arquitecturas: De componentes desacoplados: capas(clear architecture, onion, hexagonal), microservicios, etc.
- Aplicación de principios SOLID
- Encaja guay con el uso de metodologías ágiles
- Encaja guay con el uso de contenedores (Docker, Kubernetes)
- Encaja guay con la adopción de una cultura DevOps

# Pruebas

## Tipos de pruebas en base al nivel de la prueba:

- Pruebas unitarias:                AISLADO
- Pruebas de integración:           COMUNICACION entre componentes
- Pruebas sistema (end-to-end):     COMPORTAMIENTO

## Para definir pruebas:

Metodología: Given,When,Then (Dado, Cuando, Entonces)
    Given: Contexto para la prueba
    When: Acción que se va a realizar
    Then: Resultado esperado

## Principios para el diseño de pruebas: FIRST

- Fast: Rápidas
- Independent: Independientes de otras pruebas
- Repeatable: Repetibles sin que cambie el resultado
- Self-Validating: Que incluyan TODAS LAS VALIDACIONES necesarias
- Timely: Que se escriban en el momento adecuado (OPORTUNAS)

---



---

- Entender cómo aplicar ese patrón de inyección de dependencias en Spring
- Entender el uso de MAVEN
- Vamos a comenzar con nuestro proyecto