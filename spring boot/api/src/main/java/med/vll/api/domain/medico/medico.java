package med.vll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.vll.api.domain.direccion.Direccion;

@Table(name ="medicos")
@Entity(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String documento;
    private Boolean activo;
    private String telefono;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;

    public medico(DatosRegistroMedico datosRegistroMedico) {
        this.activo = true;
        this.nombre = datosRegistroMedico.nombre();
        this.email = datosRegistroMedico.email();
        this.telefono = datosRegistroMedico.telefono();
        this.documento = datosRegistroMedico.documento();
        this.especialidad = datosRegistroMedico.especialidad();
        this.direccion = new Direccion(datosRegistroMedico.direccion()) ;

    }

    public void actualizarDatos(DatosActualizarMedico datosActualizarMedico) {

        if(datosActualizarMedico.nombre() !=null){
            this.nombre = datosActualizarMedico.nombre();
        }
        if(datosActualizarMedico.documento() !=null){
            this.documento = datosActualizarMedico.documento();
        }
        if(datosActualizarMedico.direccion() !=null){
            this.direccion = direccion.actulizarDatos(datosActualizarMedico.direccion());
        }


    }

    public void desactivarMedicos() {
        this.activo = false;
    }
}
