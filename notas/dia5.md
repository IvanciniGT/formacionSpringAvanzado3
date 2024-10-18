# Cambios recientes en la industria del software

- Automatización! (DEVOPS... esto no es IA, aunque evidentemente hoy en día estamos empezando a usar IAs para automatizar)
   -> KUBERNETES
- IAs generativas! Realmente es un cambio importante. Nos ayuda a escribir una cantidad ingente de código.
  -> Yo ya no aporto valor escribiendo código.. ni quiero aportarlo.


----

Desarrollador hace commit -> PRODUCCION en auto.
Y hoy en día el que lleva el busca en el culo es el desarrollador.
Y hoy en día el desarrollador TIENE QUE TENER MUCHO conocimiento de cómo funciona un entorno de producción.
Entre otras cosas por que el diseño del sistema condiciona la forma en la que se puede desplegar/operar en producción.
STATEFULL -> STATELESS

---

# Seguridad en el mundo web es compleja.

Estamos expuestos a multitud de potenciales ataques.
Lo más básico en seguridad web es usar TLS: https: S = TLS = Transport Layer Security

Esto ayuda a frustrar 2 tipos de ataques (en algunas ocasiones un tercero):
- Phishing: Suplantación de identidad: Comprobar que el servidor es quien dice ser!
            El certificado que presenta un servidor me ayuda a saber que es quien dice ser.
                Dentro del certificado van datos del servidor y una firma digital de una entidad de confianza = DNI de una persona.
- Man in the middle: Ataque en el que un tercero se pone en medio de la comunicación entre 2 partes.
            Si la comunicación es encriptada, el tercero no puede entender nada aunque lo lea.
            Para encriptar se usa un algoritmo de clave SIMETRICA (Muy rápido). El problema es que me pueden vulnerar esa clave por fuerza bruta.
            Y lo que se hace es generar una clave SIMETRICA bajo demanda para cada clientes que conecta con el servidor que se regenera cada 30 minutos.
            Y ese clave se comparte entre cliente y servidor encriptada mediante un algoritmo de clave ASIMETRICA (más lento).
- No repudio: Que una vez que un cliente ha hecho una petición, no pueda negar que la ha hecho. Para eso le AUTENTICO.. con un procedimiento fuerte: CERTIFICADO DIGITAL.

Este ataque se daba cuando usábamos apps web STATEFULL.
El usuario hacía login: Se le asignaba un JSESSIONID que se mandaba como cookie, que queda en el navegador. El navegador, buena gente, manda esa cookie en cada petición al servidor.
Un malaje, podía mandar un email a un usuario con un enlace que decía PULSA AQUI QUE TE HA TOCADO LA LOTERIA!!! y en realidad era un enlace a una petición a la web del banco que hacía una transferencia de dinero. Al hacer clic el usuario, se abría el navegador.. y mandaba la cookie con la sesión del usuario al servidor del banco. Ya que el usuario estaba logeado.



CSRF: Cross Site Request Forgery
El servidor, cuando mandaba un formulario ... en general algo a lo que se le vaya a hacer un POST!... mandaba un token en un campo oculto del formulario.
Y si el servidor recibía una petición POST sin ese token... la rechazaba.

CORs: Cross Origin Resource Sharing
El servidor publica una lista de las aplicaciones que son las que deberían usar un determinado servicio.
El navegador mira esa lista... y si alguien que no está en la lista intenta usar el servicio... el navegador no le deja.
El servidor no revisa nada... Él no sabe de donde la petición... solo el navegador.


---

# En nuestro ejemplo de seguridad:

- Crear una BBDD de Usuarios, con unos usuarios ya precreados y sus contraseñas y roles.
- Configurar un controlador REST (enpoint) que permita hacer login -> JWT (que vamos a generar nosotros)
        /authenticate POST {username, password} -> JWT (usuario, fecha de expiración, roles)
            Ese controlador, hará AUTENTICACION del usuario (comprobar que existe en la BBDD y que la contraseña es correcta).
            Eso lo delega a Spring: AuthenticationProvider
- Crear un filtro que se aplique en la cadena de filtros de seguridad que revise si la petición viene con un JWT... y si es válido.
  - Además, ese filtro, lee la información del JWT: usuario -> Vuelve a hacer query a BBDD (opcional) y inyecta el usuario y sus roles (obtenidos de BBDD) en el contexto de seguridad de Spring para la petición actual.
- Creando una política de seguridad para la aplicación, que haga uso de:
   - Ese filtro
   - Y que configure un mecanismo de autenticación basado en usuario/contraseña, que trabaje contra nuestra BBDD de usuarios. < AuthenticationProvider 

En una app real, si voy a trabajar contra un IAM externo, los IAMs externos me suelen ofrecer librerías de integración con Spring Security que me facilitan mucho la vida.
Y todas esas cosas casi no tendríamos ni que configurarlas... Muy poquitas.

Lo que os doy yo es IDEAL para montar un entorno de TEST, con una app arrancada. CUIDADO no para hacer test unitarios, ni de sistema, ni de integración (eso lo resolvemos con Spring Security Test - WithMockUser, etc...).

También serviría como base para desarrollar un sistema PROPIO de gestión de usuarios y roles.

Os voy a dar el flujo completo... Hay cosas que podrían simplicarse: LA PARTE DEL FILTRO DE SEGURIDAD. Yo la voy a montar de CERO. Pero, hay una librería de Spring Security que se llama Spring Security OAuth2 que nos regala ese filtro. Y si lo usamos, nos ahorramos crear el filtro nosotros... aunque tenemos menos control.

Quiero volver a hacer una query a BBDD de los roles... por si han cambiado... PERO... entonces... para que quiero guardarlos en el JWT? PARA EL FRONTAL

TOKENS:                                     EL QUE GENERA EL TOKEN                      EL QUE RECIBE EL TOKEN, QUE HACE?
    datos del usuario                       datos -> HASH                                   datos -> HASH
    firma digital                                      v encriptarla CLAVE PRIVADA                          Y voy a comprobar si el HASH que yo genero es igual al que
                                                                                                                    firmo el que lo generó.
        QUE ES ESO DE LA FIRMA?                     FIRMA                                   firma -> desencriptarla? -> CLAVE PUBLICA del que GENERA EL TOKEN > HUELLA QUE FIRMO
                                                                                                                                                                el que lo generó

Por qué uso una clave PUBLICA / PRIVADA para la FIRMA? Más seguro... En ningún momento se expone la CLAVE PRIVADA que se usa para firmar el token.
Pregunta... y si soy yo quien genera el token... y lo valida? Necesito usar clave privada/pública para la encripción? NO... No se va a exponer la clave de encripción (ahora solo hay una pero no se expone..) YO ME LO GUISO YO COMO!


eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcyOTI0NjkwMiwiZXhwIjoxNzI5MjgyOTAyfQ.s0NyZSaUgbHMuqX0pneZ8iI1N5JagovfMHS_XPIEdWQ