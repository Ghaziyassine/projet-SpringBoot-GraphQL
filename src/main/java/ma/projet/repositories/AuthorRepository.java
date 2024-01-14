package ma.projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.projet.entities.Author;

/**
 * AuthorRepository
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    
}