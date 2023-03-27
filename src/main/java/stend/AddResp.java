package stend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AddResp {

    @JsonProperty("id")
    public int id;
    @JsonProperty("username")
    public String username;
    @JsonProperty("token")
    public String token;
    @JsonProperty("roles")
    private List<String> roles;
}
