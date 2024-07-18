package raihanhori.security_jwt.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorApiResponseHelper {
	
	private Integer statusCode;
	
	private String message;
	
}
