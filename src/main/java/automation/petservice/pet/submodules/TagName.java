package automation.petservice.pet.submodules;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;


@JsonDeserialize(builder = TagName.Builder.class)
public class TagName {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("name")
    public String name;

    private TagName(Builder builder) {
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

        public TagName build() {
            return new TagName(this);
        }

    }
}
