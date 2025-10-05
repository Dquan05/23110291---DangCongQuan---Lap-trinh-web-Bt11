package Demo1.controller;

import Demo1.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	private final List<Customer> customers = List.of(
			Customer.builder().id("001").name("Nguyễn Hữu Trung").email("trungnhspkt@gmail.com").build(),
			Customer.builder().id("002").name("Hữu Trung").email("trunghuu@gmail.com").build());

	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')") // ✅ dùng hasRole thay vì hasAuthority('ROLE_ADMIN')
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return ResponseEntity.ok(customers);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
		return customers.stream().filter(c -> c.getId().equals(id)).findFirst().map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
}
