package automation.petservice.pet.submodules;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CategoryName.Builder.class)
public class CategoryName {

    @JsonProperty("id")
    public int id;
    @JsonProperty("name")
    public String name;

    private CategoryName(Builder builder) {
        id = builder.id;
        name = builder.name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @JsonPOJOBuilder
    public static final class Builder {
        public int id;
        public String name = "Hound";

        public Builder withId(int id){
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
