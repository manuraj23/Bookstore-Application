package com.Bookstore_Application.Bookstore_Application.Entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Books {

    @Id
    private ObjectId id;

    @NonNull
    private String title;
    @NonNull
    private String author;
    private List<String> category= new ArrayList<>();
    private double price;
    private double rating;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishedDate;

}
