package in.main.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.main.dto.MovieDto;
import in.main.dto.MoviePageResponse;
import in.main.exception.EmptyFileException;
import in.main.service.MovieService;
import in.main.utils.AppConstant;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

	private final MovieService movieService;

	public MovieController(MovieService movieService) {
		super();
		this.movieService = movieService;
	}
	
	@PostMapping("/add-movie")
	public ResponseEntity<MovieDto> addMovieHandler(@RequestPart MultipartFile file,@RequestPart String movieDto) throws IOException, EmptyFileException{
		if(file.isEmpty()) {
			throw new EmptyFileException("file is empty! please send another file");
		}
		MovieDto dto=convertToMovieDto(movieDto);
		return new ResponseEntity<>(movieService.addMovie(dto, file),HttpStatus.CREATED);
	}
	
	private MovieDto convertToMovieDto(String movieDtoObject) throws JsonProcessingException {
		ObjectMapper objectMapper=new ObjectMapper();
		return objectMapper.readValue(movieDtoObject, MovieDto.class);
	}
	
	@GetMapping("/getMovie/{movieId}")
	public ResponseEntity<MovieDto> getMovie(@PathVariable Integer movieId){
		MovieDto movieDto=movieService.getMovie(movieId);
		return new ResponseEntity<>(movieDto,HttpStatus.OK);
	}
	
	@GetMapping("/getAllMovie")
	public ResponseEntity<List<MovieDto>> getAll(){
		List<MovieDto> listMovieDto=movieService.getAllMovies();
		return new ResponseEntity<>(listMovieDto,HttpStatus.OK);
	}
	
	@PutMapping("/updateMovie/{movieId}")
	public ResponseEntity<MovieDto> update(@PathVariable Integer movieId,@RequestPart MultipartFile file,@RequestPart String movieDto)
			throws IOException{
		MovieDto dto=convertToMovieDto(movieDto);
		return new ResponseEntity<>(movieService.updateMovie(movieId, dto,file),HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteMovie/{movieId}")
	public ResponseEntity<String> delete(@PathVariable Integer movieId){
		Boolean delete=movieService.deleteMovie(movieId);
		if(!delete) {
			return ResponseEntity.ok("movie is not deleted");
		}
		return  ResponseEntity.ok("movie id "+movieId+" is deleted successfully");
	}
	
	@GetMapping("/allMoviePage")
	public ResponseEntity<MoviePageResponse> moviePagination(
			@RequestParam(defaultValue=AppConstant.PAGE_NUMBER,required=false) Integer pageNumber,
			@RequestParam(defaultValue=AppConstant.PAGE_SIZE,required=false) Integer pageSize
			){
		return ResponseEntity.ok(movieService.getAllMovieWithPagination(pageNumber, pageSize));
	}
	
	@GetMapping("/allMoviePageSort")
	public ResponseEntity<MoviePageResponse> moviePaginationAndSorting(
			@RequestParam(defaultValue=AppConstant.PAGE_NUMBER,required=false) Integer pageNumber,
			@RequestParam(defaultValue=AppConstant.PAGE_SIZE,required=false) Integer pageSize,
			@RequestParam(defaultValue=AppConstant.SORT_BY,required=false)String sortBy,
			@RequestParam(defaultValue=AppConstant.SORT_DIR,required=false)String sortDir
			){
		return ResponseEntity.ok(movieService.getAllMovieWithPaginationAndSorting(pageNumber, pageSize,sortBy,sortDir));
	}
}
