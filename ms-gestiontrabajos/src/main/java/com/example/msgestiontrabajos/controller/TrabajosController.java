package com.example.msgestiontrabajos.controller;



import com.example.msgestiontrabajos.entity.Trabajos;
import com.example.msgestiontrabajos.service.TrabajosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trabajos")


public class TrabajosController {
    @Autowired
    private TrabajosService trabajosService;

    @GetMapping()
    public ResponseEntity<List<Trabajos>> list(){
        return ResponseEntity.ok().body(trabajosService.listar());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Trabajos> findById(@PathVariable(required = true) Integer id){
        return ResponseEntity.ok().body(trabajosService.listarPorId(id).get());
    }
    @PostMapping()
    public ResponseEntity<Trabajos> save(@RequestBody Trabajos trabajos){
        return ResponseEntity.ok().body(trabajosService.guardar(trabajos));
    }
    @PutMapping()
    public ResponseEntity<Trabajos> update(@RequestBody Trabajos trabajos){
        return ResponseEntity.ok().body(trabajosService.actualizar(trabajos));
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable(required = true) Integer id){
        trabajosService.eliminar(id);
        return "eliminacion correcta";
    }
}
