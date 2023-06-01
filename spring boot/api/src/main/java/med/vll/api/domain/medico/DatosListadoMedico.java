package med.vll.api.domain.medico;

public record DatosListadoMedico(Long id, String nombre, String especialidad, String documento, String email) {


    public DatosListadoMedico(medico medicos){
        this(medicos.getId(), medicos.getNombre(), medicos.getEspecialidad().toString(), medicos.getDocumento(),medicos.getEmail());
    }
}