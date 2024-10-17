package com.curso.diccionarios.controlador.rest.v1;

import com.curso.diccionarios.AplicacionDePrueba;
import com.curso.diccionarios.service.ContextoServicio;
import com.curso.diccionarios.service.exception.GenericServiceException;
import com.curso.diccionarios.service.dto.ContextoDTO;
import com.curso.diccionarios.service.exception.InvalidArgumentServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = AplicacionDePrueba.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Me arranca la app y hasta un tomcat embebido en un puerto, el que sea
@AutoConfigureMockMvc // Quiero que configures un cliente de pruebas HTTP contra ese tomcat. Tù sabrás donde está!
@ExtendWith(SpringExtension.class) // Quiero que uses la extensión de JUnit 5 para Spring y poder hacer que JUnit 5 sepa que Spring está en uso y le pida cosas a él.
class ContextoRestV1Test {

    @MockBean
    private ContextoServicio servicioDeMentirijilla;

    private final MockMvc clienteHttp; // Quiero tener disponible ese cliente que se ha configurado

    public ContextoRestV1Test(@Autowired MockMvc clienteHttp) {
        this.clienteHttp = clienteHttp;
    }

    @Test
    @DisplayName("Poder obtener TODOS los contextos al hacer GET al endpoint /api/v1/contexto")
    void obtenerTodosLosContexto() throws Exception {
        // Configuro el servicio de Mentirijilla para que cuando le pidan todos los contextos devuelva una lista con dos contextos
        List<ContextoDTO> listaQueDuevuelveElServicio = List.of(
                new ContextoDTO("contexto1", UUID.randomUUID().toString()),
                new ContextoDTO("contexto2", UUID.randomUUID().toString()));

        when(servicioDeMentirijilla.getContextos())
                .thenReturn(listaQueDuevuelveElServicio);

        clienteHttp.perform(MockMvcRequestBuilders.get("/api/v1/contexto"))
                .andExpect(status().isOk())  // Http: 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Misma longitud jsonPath
                .andExpect(jsonPath("$").isArray())
                // Y Los datos que se devuelven corresponden a los que le devuelve el sevicio.
                // No hay servicio ya montado... Pues tengo que montar un servicio de Mentirijilla
                // Como se los pide el controlador al servicio: Dame todos los contextos
                .andExpect(jsonPath("$.length()").value(listaQueDuevuelveElServicio.size()))
                .andExpect(jsonPath("$[0].contexto").value(listaQueDuevuelveElServicio.get(0).getContexto()))
                .andExpect(jsonPath("$[0].descripcion").value(listaQueDuevuelveElServicio.get(0).getDescripcion()))
                .andExpect(jsonPath("$[1].contexto").value(listaQueDuevuelveElServicio.get(1).getContexto()))
                .andExpect(jsonPath("$[1].descripcion").value(listaQueDuevuelveElServicio.get(1).getDescripcion()));

        // Tengo que verificar otra cosa.. para cerrarle ya por todos sitios: Realmente el controlador haya llamado al servicio
        verify(servicioDeMentirijilla, times(1)).getContextos();
/*
        // Obtengo el contenido (String)
        String contenido = respuesta.getResponse().getContentAsString();
        // Lo convierto a una lista de objetos JSON
        ObjectMapper mapper = new ObjectMapper();
        JsonNode nodo = mapper.readTree(contenido);
        for (int i = 0; i < nodo.size(); i++) {
            JSONObject contexto = new JSONObject(nodo.get(i).toString());
            assertNotNull(contexto.getString("contexto"));
            assertNotNull(contexto.getString("descripcion"));
        }*/
    }

    @Test
    @DisplayName("Llamar con GET al endPoint /api/v1/contexto cuando hay un error en Servicio (por ejemplo BBDD No disponible)")
    void obtenerTodosLosContextoConServicioErroneo() throws Exception {
        // Configuro el servicio de Mentirijilla para que cuando le pidan todos los contextos devuelva una lista con dos contextos
        when(servicioDeMentirijilla.getContextos())
                .thenThrow(new GenericServiceException("BBDD no disponible"));

        clienteHttp.perform(MockMvcRequestBuilders.get("/api/v1/contexto"))
                .andExpect(status().isInternalServerError())  // Http: 500 ERROR
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mensaje").value("Error en la capa de servicio: BBDD no disponible"));

        // Tengo que verificar otra cosa.. para cerrarle ya por todos sitios: Realmente el controlador haya llamado al servicio
        verify(servicioDeMentirijilla, times(1)).getContextos();
    }

    @Test
    @DisplayName("Crear un contexto nuevo (happy path)")
    void crearContexto() throws Exception, InvalidArgumentServiceException {
        String nombreContexto = "contexto1";
        String descripcion = UUID.randomUUID().toString();
        // Configuro el servicio de Mentirijilla para que cuando le pidan crear un contexto devuelva el contexto
        ContextoDTO contexto = new ContextoDTO(nombreContexto, descripcion);
        when(servicioDeMentirijilla.crearContexto(contexto))
                .thenReturn(contexto);

        clienteHttp.perform(
                    MockMvcRequestBuilders.post("/api/v1/contexto")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"contexto\":\"" + nombreContexto + "\",\"descripcion\":\"" + descripcion + "\"}")
                )
                .andExpect(status().isCreated())  // Http: 201 CREATED
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.contexto").value(contexto.getContexto()))
                .andExpect(jsonPath("$.descripcion").value(contexto.getDescripcion()));

        // Tengo que verificar otra cosa.. para cerrarle ya por todos sitios: Realmente el controlador haya llamado al servicio
        verify(servicioDeMentirijilla, times(1)).crearContexto(contexto);
    }

    @Test
    @DisplayName("Crear un contexto nuevo si el servicio está mal")
    void crearContextoConServicioErroneo() throws Exception, InvalidArgumentServiceException {
        String nombreContexto = "contexto1";
        String descripcion = UUID.randomUUID().toString();
        // Configuro el servicio de Mentirijilla para que cuando le pidan crear un contexto devuelva el contexto
        ContextoDTO contexto = new ContextoDTO(nombreContexto, descripcion);
        when(servicioDeMentirijilla.crearContexto(contexto))
                .thenThrow(new GenericServiceException("BBDD no disponible"));

        clienteHttp.perform(
                MockMvcRequestBuilders.post("/api/v1/contexto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"contexto\":\"" + nombreContexto + "\",\"descripcion\":\"" + descripcion + "\"}")
        )
                .andExpect(status().isInternalServerError())  // Http: 500 ERROR
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mensaje").value("Error en la capa de servicio: BBDD no disponible"));

        // Tengo que verificar otra cosa.. para cerrarle ya por todos sitios: Realmente el controlador haya llamado al servicio
        verify(servicioDeMentirijilla, times(1)).crearContexto(contexto);
    }
    @Test
    @DisplayName("Crear un contexto nuevo si le paso un nombre de más de 10 caracteres... que no está permitido...")
    void crearContextoConNombreLargo() throws Exception, InvalidArgumentServiceException {
        String nombreContexto = "contexto1contexto1";
        String descripcion = UUID.randomUUID().toString();
        // Configuro el servicio de Mentirijilla para que cuando le pidan crear un contexto devuelva el contexto
        ContextoDTO contexto = new ContextoDTO(nombreContexto, descripcion);
        when(servicioDeMentirijilla.crearContexto(contexto))
                .thenThrow(new InvalidArgumentServiceException("El nombre del contexto no puede tener más de 10 caracteres"));

        clienteHttp.perform(
                MockMvcRequestBuilders.post("/api/v1/contexto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"contexto\":\"" + nombreContexto + "\",\"descripcion\":\"" + descripcion + "\"}")
        )
                .andExpect(status().isBadRequest())  // Http: 400 BAD REQUEST
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mensaje").value("Error en la capa de servicio: El nombre del contexto no puede tener más de 10 caracteres"));

        // Tengo que verificar otra cosa.. para cerrarle ya por todos sitios: Realmente el controlador haya llamado al servicio
        verify(servicioDeMentirijilla, times(1)).crearContexto(contexto);
    }

}