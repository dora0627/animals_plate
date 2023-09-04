package com.example.animals_plate;

public class Post_new {
    public int post_id;
    public String author_name;
    public String post_title;
    public String post_content;
    public String posttime;
    public Post_new(String author_name,String post_title,String post_content,String posttime){
        this.author_name = author_name;
        this.post_title = post_title;
        this.post_content = post_content;
        this.posttime=posttime;
    }
    public int getPost_id(){return post_id;}
    public String getAuthor_name(){return author_name;}
    public String getPost_title(){return post_title;}
    public String getPost_content(){return post_content;}

    public String getPosttime(){return posttime;}
}
