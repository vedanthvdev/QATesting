package automation.petservice.pet.submodules;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CategoryName.Builder.class)
public class CategoryName {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("name")
    public String name;

    private CategoryName(Builder builder) {
        id = builder.id;
        name = builder.name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @JsonPOJOBuilder
    public static final class Builder {
        public Integer id;
        public String name;

        public Builder withId(Integer id){
            this.id = id;
            return this;
        }

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public CategoryName build() {
            return new CategoryName(this);
        }

    }

}
