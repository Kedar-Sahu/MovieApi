package in.main.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.main.dto.MovieDto;
import in.main.dto.MoviePageResponse;
import in.main.entity.Movie;
import in.main.exception.FileExistsException;
import in.main.exception.MovieNotFoundException;
import in.main.repository.MovieRepository;
import in.main.service.FileService;
import in.main.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {

	private final MovieRepository movieRepository;
	
	private final FileService fileService;

	public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
		super();
		this.movieRepository = movieRepository;
		this.fileService = fileService;
	}
	
	@Value("${project.poster}")
	private String path;
	
	@Value("${base.url}")
	private String baseUrl;

	@Override
	public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
		// upload the file
		if(Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
			throw new FileExistsException("file already exists please enter another file name !");
		}
		String uploadedFile=fileService.uploadFile(path, file);
		
		// set the value of field 'poster' as filename
		movieDto.setPoster(uploadedFile);
		
		// map dto to movie object
		Movie movie=MovieServiceImpl(movieDto);
		
		// save the movie object -> saved movie object
		Movie saveMovie=movieRepository.save(movie);
		
		// map movie object to dto object and return 
		MovieDto movieDto1= MovieServiceImpl(movie);
		
		// generate the posterUrl
		String posterUlr=baseUrl + "/file/" + uploadedFile;
		movieDto1.setPosterUrl(posterUlr);
		
		return movieDto1;
	}

	@Override
	public MovieDto getMovie(Integer movieId) {
		// check the data in db
		Movie movie=movieRepository.findById(movieId).orElseThrow(()-> new MovieNotFoundException("movie not found with id "+movieId));
		
		// map movie object to dto object and return 
		MovieDto movieDto= MovieServiceImpl(movie);
		String posterUlr=baseUrl + "/file/" + movie.getPoster();
		movieDto.setPosterUrl(posterUlr);
		
		return movieDto;
	}

	@Override
	public List<MovieDto> getAllMovies() {
		List<Movie> movies=movieRepository.findAll();
		List<MovieDto> listMovieDto=new ArrayList<>();
		
		for(Movie movie:movies) {
			String posterUlr=baseUrl + "/file/" + movie.getPoster();
			MovieDto movieDto= MovieServiceImpl(movie);
			movieDto.setPosterUrl(posterUlr);
			listMovieDto.add(movieDto);
		}
		return listMovieDto;
	}
	
	@Override
	public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {
		Movie movie=movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException("movie not found with id "+movieId));
		String uploadFile=fileService.uploadFile(path, file);
		
		movie.setTitle(movieDto.getTitle());
		movie.setDirector(movieDto.getDirector());
		movie.setStudio(movieDto.getStudio());
		movie.setMovieCast(movieDto.getMovieCast());
		movie.setReleaseYear(movieDto.getReleaseYear());
		movie.setPoster(uploadFile);
		Movie saveMovie=movieRepository.save(movie);
		
		MovieDto movieDto1=MovieServiceImpl(saveMovie);
		String posterUlr=baseUrl + "/file/" + saveMovie.getPoster();
		movieDto1.setPosterUrl(posterUlr);
		
		return movieDto1;
	}
	
	@Override
	public Boolean deleteMovie(Integer movieId) {
		Movie movie=movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException("movie not found with id "+movieId));
		movieRepository.delete(movie);
		return true;
	}
	
	
	@Override
	public MoviePageResponse getAllMovieWithPagination(Integer pageNumber, Integer pageSize) {
		Pageable pageable=PageRequest.of(pageNumber, pageSize);
		Page<Movie> moviePages=movieRepository.findAll(pageable);
		List<Movie> movies=moviePages.getContent();
		
        List<MovieDto> listMovieDto=new ArrayList<>();
		
		for(Movie movie:movies) {
			String posterUlr=baseUrl + "/file/" + movie.getPoster();
			MovieDto movieDto= MovieServiceImpl(movie);
			movieDto.setPosterUrl(posterUlr);
			listMovieDto.add(movieDto);
		}
		
		 MoviePageResponse response=new MoviePageResponse();
		 response.setListmovieDto(listMovieDto);
		 response.setPageNumber(moviePages.getNumber());
		 response.setPageSize(moviePages.getSize());
		 response.setTotalElement(moviePages.getTotalElements());
		 response.setTotalPages(moviePages.getTotalPages());
		 response.setIsLast(moviePages.isLast());
		 
		return response;
	}

	@Override
	public MoviePageResponse getAllMovieWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir) {
		
		Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable page=PageRequest.of(pageNumber, pageSize,sort);
		Page<Movie> moviePages=movieRepository.findAll(page);
		List<Movie> movies=moviePages.getContent();
		
		 List<MovieDto> listMovieDto=new ArrayList<>();
		 for(Movie movie:movies) {
				String posterUlr=baseUrl + "/file/" + movie.getPoster();
				MovieDto movieDto= MovieServiceImpl(movie);
				movieDto.setPosterUrl(posterUlr);
				listMovieDto.add(movieDto);
			}
		 MoviePageResponse response=new MoviePageResponse();
		 response.setListmovieDto(listMovieDto);
		 response.setPageNumber(moviePages.getNumber());
		 response.setPageSize(moviePages.getSize());
		 response.setTotalElement(moviePages.getTotalElements());
		 response.setTotalPages(moviePages.getTotalPages());
		 response.setIsLast(moviePages.isLast());
		 
		 return response;
	}
	
	
	private Movie MovieServiceImpl(MovieDto movieDto) {
		Movie movie=new Movie();
		movie.setMovieId(movieDto.getMovieId());
		movie.setTitle(movieDto.getTitle());
		movie.setDirector(movieDto.getDirector());
		movie.setStudio(movieDto.getStudio());
		movie.setMovieCast(movieDto.getMovieCast());
		movie.setReleaseYear(movieDto.getReleaseYear());
		movie.setPoster(movieDto.getPoster());	
		return movie;
	}
	
	private MovieDto MovieServiceImpl(Movie movie) {
		MovieDto movieDto=new MovieDto();
		movieDto.setMovieId(movie.getMovieId());
		movieDto.setTitle(movie.getTitle());
		movieDto.setDirector(movie.getDirector());
		movieDto.setStudio(movie.getStudio());
		movieDto.setMovieCast(movie.getMovieCast());
		movieDto.setReleaseYear(movie.getReleaseYear());
		movieDto.setPoster(movie.getPoster());
		return movieDto;
	}


}
