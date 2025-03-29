package com.Bookstore_Application.Bookstore_Application.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    private Object id;

    @NonNull
    private String email;
    @NonNull
    private String password;
    private List<String> roles;

}
