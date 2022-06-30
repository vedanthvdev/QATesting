package automation.petapi.submodules;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Builder.Default
    private int id = 1;
    @Builder.Default
    private String name = "Hound";

}
