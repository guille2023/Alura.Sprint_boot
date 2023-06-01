package med.vll.api.domain.medico;

import med.vll.api.domain.direccion.DatosDireccion;

public record DatosRespuestaMedico(Long id, String nombre, String email, String telefono, String documento,
                                   DatosDireccion direccion) {
}
