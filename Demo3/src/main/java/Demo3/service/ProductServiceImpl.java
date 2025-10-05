package Demo3.service;

import Demo3.entity.Product;
import Demo3.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductServices {
    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Product> listAll() { return repo.findAll(); }

    @Override
    public Product save(Product product) { return repo.save(product); }

    @Override
    public Product get(Long id) { return repo.findById(id).get(); }

    @Override
    public void delete(Long id) { repo.deleteById(id); }
}
