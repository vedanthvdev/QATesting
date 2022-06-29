package automation.petservice.pet;

import automation.petservice.pet.submodules.CategoryName;
import automation.petservice.pet.submodules.TagName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

public class CreatePet {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("category")
    public CategoryName categoryName;
    @JsonProperty("name")
    public String petName;
    @JsonProperty("photoUrls")
    public String[] photoUrls;
    @JsonProperty("tags")
    public List<TagName> tagName;
    @JsonProperty("status")
    public String status;

    private CreatePet(Builder builder) {
        id = builder.id;
        categoryName = builder.categoryName;
        petName = builder.petName;
        photoUrls = builder.photoUrls;
        tagName = builder.tagName;
        status = builder.status;
    }

    public Integer getId() {
        return id;
    }

    public CategoryName getCategoryName() {
        return categoryName;
    }

    public String getPetName() {
        return petName;
    }

    public List<TagName> getTagName() {
        return tagName;
    }

    public String getStatus() {
        return status;
    }

    public String[] getPhotoUrls() {
        return photoUrls;
    }


    @JsonPOJOBuilder
    public static final class Builder {
        public Integer id;
        public CategoryName categoryName;
        public String petName;
        public String status;
        public List<TagName> tagName;
        public String[] photoUrls;

        public Builder withId(Integer id){
            this.id = id;
            return this;
        }

        public Builder withCategoryName(CategoryName categoryName){
            this.categoryName = categoryName;
            return this;
        }

        public Builder withPetName(String petName){
            this.petName = petName;
            return this;
        }

        public Builder withPhotoUrls(String[] photoUrls){
            this.photoUrls = photoUrls;
            return this;
        }

        public Builder withTagName(List<TagName> tagName){
            this.tagName = tagName;
            return this;
        }

        public Builder withStatus(String status){
            this.status = status;
            return this;
        }

        public CreatePet build() {
            return new CreatePet(this);
        }

    }
}
