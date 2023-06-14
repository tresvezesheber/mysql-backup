package br.com.hebio.mysqlbackup.resources;

import br.com.hebio.mysqlbackup.model.Servidor;
import br.com.hebio.mysqlbackup.service.ServidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/servidores")
public class ServidorController {

    @Autowired
    private ServidorService servidorService;

    @GetMapping()
    public ResponseEntity<Iterable<Servidor>> pageableListServidors(@RequestParam(required = false) Integer size) {
        return ResponseEntity.ok().body(servidorService.listaPaginadaDeServidores(size));
    }

    @PostMapping()
    public ResponseEntity<Void> saveServidor(@RequestBody Servidor servidor) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(servidorService.salvarServidor(servidor)).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> searchServidor(@PathVariable("id") Long id) {
        Optional<Servidor> servidor = servidorService.buscarServidor(id);
        return ResponseEntity.ok().body(servidor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServidor(@PathVariable("id") Long id) {
        servidorService.deleteServidor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateServidor(@RequestBody Servidor servidor, @PathVariable("id") Long id) {
        servidor.setId(id);
        servidorService.atualizarServidor(servidor);
        return ResponseEntity.noContent().build();
    }
}
