package med.vll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.vll.api.domain.direccion.DatosDireccion;

public record DatosActualizarMedico(@NotNull Long id, String nombre, String documento, DatosDireccion direccion) {
}
