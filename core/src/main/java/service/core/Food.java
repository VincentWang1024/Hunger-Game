package service.core;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = McdFood.class, name = "mcdFood"),
  @JsonSubTypes.Type(value = BgkFood.class, name = "bgkFood")
})
public interface Food extends Serializable {

    int getQuantity();
}
