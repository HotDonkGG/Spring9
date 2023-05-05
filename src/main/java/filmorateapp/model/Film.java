package filmorateapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    private int id;
    private String name;
    private String description;
    private Date releaseDate;
    private Long duration;
}