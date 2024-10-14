package in.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.main.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie,Integer> {

}
