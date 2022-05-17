package util.result;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private String code;
    private String redName;
    private String blueName;
    private String winnerName;
    private int moveCount;
    private LocalDate date;


}
