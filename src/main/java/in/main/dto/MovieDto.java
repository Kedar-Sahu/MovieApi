package in.main.dto;

import java.util.Set;


import jakarta.persistence.CollectionTable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieDto {

	
    private Integer movieId;
	
    @NotBlank(message="please inset movie title !")
	private String title;
	
	@NotBlank(message="please inset director name !")
	private String director;
	
	@NotBlank(message="please inset studio name !")
	private String studio;
	
	@NotBlank(message="please inset studio name !")
	private String movieCast;
	
	
	private Integer releaseYear;
	
	@NotBlank(message="please inset poster !")
	private String poster;
    
	@NotBlank(message="please inset posterUrl !")
    private String posterUrl;
}
