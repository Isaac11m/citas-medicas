package co.edu.comfenalco.citas.web;
import co.edu.comfenalco.citas.modelo.Cita;
import co.edu.comfenalco.citas.servicio.AgendaService;
import co.edu.comfenalco.citas.servicio.HorarioNoDisponibleException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CitaController.class)
class CitaControllerTest {
 @Autowired MockMvc mvc;
 @MockitoBean AgendaService agenda;
 private static final String JSON_VALIDO = "{\"profesional\":\"Dr. PÃ©rez\",\"fechaHora\":\"2030-11-04T08:00:00\",\"pacienteId\":\"paciente-001\"}";
 @Test void debeResponder201AlReservar() throws Exception {
  when(agenda.reservar(any(),any(),any())).thenReturn(new Cita("Dr. PÃ©rez",LocalDateTime.of(2030,11,4,8,0),"paciente-001"));
  mvc.perform(post("/api/citas").contentType(MediaType.APPLICATION_JSON).content(JSON_VALIDO))
   .andExpect(status().isCreated()).andExpect(jsonPath("$.profesional").value("Dr. PÃ©rez"));
 }
 @Test void debeResponder409SiElHorarioEstaOcupado() throws Exception {
  when(agenda.reservar(any(),any(),any())).thenThrow(new HorarioNoDisponibleException("Horario ocupado"));
  mvc.perform(post("/api/citas").contentType(MediaType.APPLICATION_JSON).content(JSON_VALIDO))
   .andExpect(status().isConflict()).andExpect(jsonPath("$.error").exists());
 }
 @Test void debeResponder400SiFaltanDatos() throws Exception {
  mvc.perform(post("/api/citas").contentType(MediaType.APPLICATION_JSON).content("{\"profesional\":\"\",\"fechaHora\":\"2030-11-04T08:00:00\",\"pacienteId\":\"p-1\"}"))
   .andExpect(status().isBadRequest());
  verifyNoInteractions(agenda);
 }
 @Test void debeResponder400SiLaFechaEsPasada() throws Exception {
  mvc.perform(post("/api/citas").contentType(MediaType.APPLICATION_JSON).content("{\"profesional\":\"Dr. PÃ©rez\",\"fechaHora\":\"2020-11-04T08:00:00\",\"pacienteId\":\"p-1\"}"))
   .andExpect(status().isBadRequest());
  verifyNoInteractions(agenda);
 }
}

