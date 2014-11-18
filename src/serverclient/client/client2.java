package serverclient.client;

/**
 * Created by RTCCD on 04.06.14.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import serverclient.server.other.server.Library;
import serverclient.server.other.server.ServerClient;

public class client2 {
    public client2(){
        super();
    }

    private static void print(ArrayList<Library> tmp)
    {
        for(Library bk : tmp)
        {
            System.out.println("----------------------------------");
            System.out.println(" Автор "+bk.getAuthor());
            System.out.println(" Название "+bk.getName());
            System.out.println(" Жанр "+ bk.getGenre());
            System.out.println(" Количество " + bk.getCount());
            System.out.println(" Id " + bk.getId());
            System.out.println("Дата" + bk.getDate());
            System.out.println("----------------------------------");
        }
    }

    public static void main(String[] args) throws  IOException{
        LinkedList<Library> lib;
        int id = 1;
        try{
            Registry registry = LocateRegistry.getRegistry("localhost",1099);
            ServerClient srv = (ServerClient) registry.lookup("LibraryServer2");

            int choice;
            Library book;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            do{
                System.out.println("1 - Добавить");
                System.out.println("2 - Поиск по автору");
                System.out.println("3 - Поиск по названию");
                System.out.println("4 - Поиск по жанру");
                System.out.println("5 - Все книги");
                System.out.println("6 - Удалить");
                System.out.println("7 - Выход");
                choice = Integer.parseInt(reader.readLine());
                switch(choice)
                {
                    case 1:{
                        System.out.println("Введите название");
                        String name = reader.readLine();
                        System.out.println("Введите Автора");
                        String author = reader.readLine();
                        System.out.println("Введите Количество");
                        int count = Integer.parseInt(reader.readLine());
                        System.out.println("Введите дату публикации");
                        int date = Integer.parseInt(reader.readLine());
                        System.out.println("Введите жанр");
                        String genre = reader.readLine();
                        Library data = new Library(author,name,genre, count, date, id);
                        ArrayList<Library> lb = new ArrayList<Library>();
                        lb.add(data);
                        srv.add(lb );
                        id++;
                        break;
                    }

                    case 2:{
                        ArrayList<Library>  tmp = new ArrayList<Library>();
                        System.out.println("Введите Автора");
                        String aut = reader.readLine();
                        tmp = srv.findByAuthor(aut);
                        print(tmp);
                        break;
                    }

                    case 3: {
                        ArrayList<Library>  tmp = new ArrayList<Library>();
                        System.out.println("Введите название");
                        String name = reader.readLine();
                        tmp = srv.findByName(name);
                        print(tmp);
                        break;
                    }

                    case 4:{
                        ArrayList<Library>  tmp = new ArrayList<Library>();
                        System.out.println("Введите жанр");
                        String genre = reader.readLine();
                        tmp = srv.findByGenre(genre);
                        print(tmp);
                        break;
                    }

                    case 5:{
                        ArrayList<Library> tmp = new ArrayList<Library>();
                        tmp = srv.getAll();
                        //srv.update();
                        print(tmp);
                        break;
                    }
                    case 6:{
                        System.out.println("Введите название");
                        String name = reader.readLine();
                        srv.remove(name);
                        break;
                    }
                    case 7: break;
                    default: System.err.println("err");


                }
            }while(choice != 7);
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }

}
