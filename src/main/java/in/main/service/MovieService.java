package in.main.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import in.main.dto.MovieDto;
import in.main.dto.MoviePageResponse;

public interface MovieService {

	MovieDto addMovie(MovieDto movieDto,MultipartFile file) throws IOException;
	
	MovieDto getMovie(Integer movieId);
	
	List<MovieDto> getAllMovies();
	
	MovieDto updateMovie(Integer movieId,MovieDto movieDto,MultipartFile file)throws IOException;
	
	Boolean deleteMovie(Integer movieId);
	
	MoviePageResponse getAllMovieWithPagination(Integer pageNumber,Integer pageSize);
	
	MoviePageResponse getAllMovieWithPaginationAndSorting(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
}
