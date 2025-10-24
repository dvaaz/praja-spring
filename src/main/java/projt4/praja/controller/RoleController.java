package projt4.praja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projt4.praja.service.RoleService;

@RestController
@RequestMapping("/api/role")
public class RoleController {
		private RoleService service;
		@Autowired
		public void setRoleService(RoleService roleService) {
			this.service = roleService;
		}

		@DeleteMapping("/destroy/{id}")
		public ResponseEntity<Boolean> destroy(
				@PathVariable Integer id){
				boolean dto = service.delete(id);
				if (dto == true) {
						return ResponseEntity.ok(true);
				} else
					return ResponseEntity.ok(false);
		}
}
