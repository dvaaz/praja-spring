//package projt4.praja.controller;
//
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import projt4.praja.entity.dto.request.usuario.UsuarioDTORequest;
//import projt4.praja.entity.dto.response.usuario.UsuarioDTOResponse;
//import projt4.praja.service.UsuarioService;
//
//@RestController
//@RequestMapping("/api/usuario")
//@Tag(name="Usuario", description="API para gerenciamento de usuarios")
//public class UsuarioController {
//    private final UsuarioService usuarioService;
//
//    public UsuarioController(UsuarioService usuarioService) {
//        this.usuarioService = usuarioService;
//    }
//
//
//    @PostMapping("/criar")
//    public ResponseEntity<UsuarioDTOResponse> criar(@RequestBody UsuarioDTORequest dtoRequest){
//        return  ResponseEntity.ok(usuarioService.criar(dtoRequest));
//    }
//
//}