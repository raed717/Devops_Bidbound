package tn.esprit.devops_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.Stock;


public interface StockRepository extends JpaRepository<Stock, Long> {
    boolean addStock(Stock stock, Product newProduct);

    Stock retrieveStock(long idStock);
}

