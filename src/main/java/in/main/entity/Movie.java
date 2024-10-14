package in.main.entity;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer movieId;
	
	@Column(nullable=false,length=200)
	@NotBlank(message="please inset movie title !")
	private String title;
	
	@Column(nullable=false)
	@NotBlank(message="please inset director name !")
	private String director;
	
	@Column(nullable=false)
	@NotBlank(message="please inset studio name !")
	private String studio;
	
	@Column(nullable=false)
	@NotBlank(message="please inset movieCast name !")
	private String movieCast;
	
	@Column(nullable=false)
	private Integer releaseYear;
	
	@Column(nullable=false)
	@NotBlank(message="please inset movie poster !")
	private String poster;
}
