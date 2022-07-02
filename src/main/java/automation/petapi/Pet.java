package automation.petapi;

import automation.petapi.enums.Status;
import automation.petapi.submodules.Category;
import automation.petapi.submodules.Tags;
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
//@JsonIgnoreProperties(ignoreUnknown = true)
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
    private Status status = Status.pending;

}
