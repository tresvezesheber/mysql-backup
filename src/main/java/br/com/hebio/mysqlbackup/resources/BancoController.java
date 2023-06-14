package br.com.hebio.mysqlbackup.resources;

import br.com.hebio.mysqlbackup.model.Banco;
import br.com.hebio.mysqlbackup.service.BancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/bancos")
public class BancoController {

    @Autowired
    private BancoService bancoService;

    @GetMapping()
    public ResponseEntity<Iterable<Banco>> pageableListBancos(@RequestParam(required = false) Integer size) {
        return ResponseEntity.ok().body(bancoService.listaPaginadaDeBancos(size));
    }

    @PostMapping()
    public ResponseEntity<Void> saveBanco(@RequestBody Banco banco) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(bancoService.salvarBanco(banco)).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> searchBanco(@PathVariable("id") Long id) {
        Optional<Banco> banco = bancoService.buscarBanco(id);
        return ResponseEntity.ok().body(banco);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanco(@PathVariable("id") Long id) {
        bancoService.deleteBanco(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBanco(@RequestBody Banco banco, @PathVariable("id") Long id) {
        banco.setId(id);
        bancoService.atualizarBanco(banco);
        return ResponseEntity.noContent().build();
    }
}
