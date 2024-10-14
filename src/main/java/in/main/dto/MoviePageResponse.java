package in.main.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MoviePageResponse {
	
	private List<MovieDto> listmovieDto;
	private Integer pageNumber;
	private Integer pageSize;
	private long totalElement;
	private int totalPages;
	private Boolean isLast;
	
//	public MoviePageResponse(List<MovieDto> listmovieDto, Integer pageNumber, Integer pageSize, long totalElement,
//			int totalPages, Boolean isLast) {
//		super();
//		this.listmovieDto = listmovieDto;
//		this.pageNumber = pageNumber;
//		this.pageSize = pageSize;
//		this.totalElement = totalElement;
//		this.totalPages = totalPages;
//		this.isLast = isLast;
//	}


}

