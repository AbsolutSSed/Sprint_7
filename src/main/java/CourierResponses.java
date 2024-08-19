import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierResponses {
    private boolean ok;
    private int id;
    private int courierId;
    private int code;
}
