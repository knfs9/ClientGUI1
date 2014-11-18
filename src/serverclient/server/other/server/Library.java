package serverclient.server.other.server;

import java.io.Serializable;

/**
 * Created by RTCCD on 04.06.14.
 */
public class Library implements Serializable {
    private String name;
    private String author;
    private String genre;
    private int count;
    private int date;
    private int id;

    public Library(){};

    Library(int id){
        this.id = id;
    }
    public Library(String name, String author, String genre,int count,int date, int id){
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.count = count;
        this.id = id;
        this.date = date;

    }
    public void setName(String name){
        this.name = name;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public void setGenre(String genre){
        this.author = genre;
    }
    public void setId(int id){ this.id = id; }
    public void setCount(int count) { this.count = count ;};
    public void setDate(int date) {this.date = date ;};

    public String getName() { return this.name; };
    public String getAuthor() { return this.author; };
    public String getGenre() { return this.genre; };
    public int getCount() { return this.count; };
    public int getId() { return  this.id; }
    public int getDate() { return  this.date; }
}
