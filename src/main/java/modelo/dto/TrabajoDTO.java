/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelo.entidad.Solicitud;
import modelo.entidad.UsuarioDeEmpresa;

/**
 *
 * @author KEVIN
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrabajoDTO {
    private int id;
    private LocalDateTime fechaHoraInicioActividad;
    private LocalDateTime fechaHoraTerminoActividad;
    private String descripción;
    private Solicitud solicitud;
    private UsuarioDeEmpresa usuario;
}
