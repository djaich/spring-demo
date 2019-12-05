package hr.sedamit.demo.dao;

import hr.sedamit.demo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {

    @Query("from Author u join fetch u.books where u.nationality = ?1 and u.yearOfBirth = ?2")
    List<Author> findByNationality(String nationality);

}
