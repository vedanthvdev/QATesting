package automation.petservice.pet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Status {
    AVAILABLE("available"),
    PENDING("pending"),
    SOLD("sold");

    @Getter
    private String value;
}
