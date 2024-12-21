package cz.uhk.kppro.repository;

import cz.uhk.kppro.model.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuisineRepository extends JpaRepository<Cuisine, Long> {
}
