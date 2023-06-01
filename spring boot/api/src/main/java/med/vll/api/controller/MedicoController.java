package med.vll.api.controller;

import jakarta.validation.Valid;
import med.vll.api.domain.direccion.DatosDireccion;
import med.vll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    //System.out.println("El request llega correctamente");
    //System.out.println(datosRegistroMedico);
    // medicoRepository.save(new medico(datosRegistroMedico));

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder){
        medico medicos = medicoRepository.save(new medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medicos.getId(), medicos.getNombre(), medicos.getEmail(),
                medicos.getTelefono(), medicos.getEspecialidad().toString(),
                new DatosDireccion(medicos.getDireccion().getCalle(), medicos.getDireccion().getDistrito(),
                        medicos.getDireccion().getCiudad(),medicos.getDireccion().getNumero(),
                        medicos.getDireccion().getDepartamento()));

        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medicos.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>>  listadoMedico(@PageableDefault(size = 2) Pageable paginacion){
       // return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        medico medicos = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medicos.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medicos.getId(), medicos.getNombre(), medicos.getEmail(),
                medicos.getTelefono(), medicos.getEspecialidad().toString(),
                new DatosDireccion(medicos.getDireccion().getCalle(), medicos.getDireccion().getDistrito(),
                        medicos.getDireccion().getCiudad(),medicos.getDireccion().getNumero(),
                        medicos.getDireccion().getDepartamento())));
    }

    //Delete logico
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id) {
        medico medicos = medicoRepository.getReferenceById(id);
        medicos.desactivarMedicos();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")

    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id) {
        medico medicos = medicoRepository.getReferenceById(id);
        var datosMedicos =new DatosRespuestaMedico(medicos.getId(), medicos.getNombre(), medicos.getEmail(),
                medicos.getTelefono(), medicos.getEspecialidad().toString(),
                new DatosDireccion(medicos.getDireccion().getCalle(), medicos.getDireccion().getDistrito(),
                        medicos.getDireccion().getCiudad(),medicos.getDireccion().getNumero(),
                        medicos.getDireccion().getDepartamento()));
        return ResponseEntity.ok(datosMedicos);
    }
}

//    borrar desde la base de datos
    /*@DeleteMapping("/{id}")
    @Transactional
    public void eliminarMedico(@PathVariable Long id){
        medico medicos = medicoRepository.getReferenceById(id);
        medicoRepository.delete(medicos);
    }*/

