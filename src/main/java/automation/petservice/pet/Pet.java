package automation.petservice.pet;

import automation.petservice.pet.submodules.Category;
import automation.petservice.pet.submodules.Tags;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pet {

    @Builder.Default
    private int id = 12345;
    @Builder.Default
    private Category category = new Category();
    @Builder.Default
    private String name = "chintu";
    @Builder.Default
    private List<String> photoUrls = Arrays.asList("url1","url2");
    @Builder.Default
    private List<Tags> tags = new ArrayList<> (Arrays.asList(new Tags()));
    @Builder.Default
    private String status = "Available";

}
