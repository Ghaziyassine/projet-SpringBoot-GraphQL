package ma.projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.projet.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    
    
}
