package automation.petservice.pet.submodules;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tags {

    @Builder.Default
    public int id = 1;
    @Builder.Default
    public String name = "TName";

}
