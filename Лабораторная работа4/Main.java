import java.util.ArrayList;
import java.util.Scanner;

class Library {

    ArrayList<Book> books = new ArrayList<Book>();

    public Library(ArrayList<Book> books) {
        this.books = books;
    }

    void addBook(Book book) {
        books.add(book);
    }

    void displayAvailable() {
        for (Book book : books){
            if (!book.isCheckedOut){
                System.out.println("Book " + book.title + " - Available");
            }
        }
    }
}

class Book {

    String title;
    String author;
    float price;
    boolean isCheckedOut;

    public Book(String title, String author, float price) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.isCheckedOut = false;
    }

    void checkout() {
        if (isCheckedOut) {
            System.out.println("The book is checked out: " + title);
        } else {
            isCheckedOut = true;
            System.out.println("You checked out the book: " + title);
        }
    }

    void checkin() {
        if (isCheckedOut) {
            isCheckedOut = false;
            System.out.println("The book has been returned: " + title);
        } else {
            System.out.println("The book is already available: " + title);
        }
    }

    void displayInfo() {
        System.out.printf("Title: %s \t Author: %s \t Price: %.2f\n", title, author, price);
    }
}

class Reader {

    String name;
    ArrayList<Book> checkedOutBooks = new ArrayList<Book>();

    public Reader(String name) {
        this.name = name;
    }

    void checkedOutBook(Book book) {
        if (!book.isCheckedOut) {
            book.checkout();
            checkedOutBooks.add(book);
            System.out.println("Book " + book.title + " added to your cart.");
        } else {
            System.out.println("The book is checked out: " + book.title);
        }
    }

    void checkinBook(Book book) {
        if (checkedOutBooks.contains(book)) {
            book.checkin();
            checkedOutBooks.remove(book);
            System.out.println("Book " + book.title + " returned.");
        } else {
            System.out.println("You don't have this book.");
        }
    }

    void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("List of checked out books:");
        for (Book book : checkedOutBooks){
            book.displayInfo();
        }
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("Harry Potter", "J.K. Rowling", 15.99f));
        books.add(new Book("The Lord of the Rings", "J.R.R. Tolkien", 20.99f));
        books.add(new Book("1984", "George Orwell", 12.50f));
        books.add(new Book("The Master and Margarita", "Mikhail Bulgakov", 18.00f));
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", 14.50f));

        Library library = new Library(books);
        Reader reader = new Reader("Rustam");

        while (true) {
            System.out.println("Hello! Welcome to the Library.\nWho are you?\n\n1.Reader\n2.Admin\n3.Exit");
            int choice = in.nextInt();

            switch (choice) {
                case 1:
                    readerFunctions(library, reader, in);
                    break;

                case 2:
                    adminFunctions(library, in);
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    in.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void adminFunctions(Library library, Scanner in) {
        System.out.println("Welcome, Admin!");
        System.out.println("1. Add a new book");
        System.out.println("2. View available books");
        System.out.println("Choose an action: ");
        int choice = in.nextInt();
        in.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Enter the title of the new book: ");
                String title = in.nextLine();
                System.out.println("Enter the author: ");
                String author = in.nextLine();
                System.out.println("Enter the price: ");
                float price = in.nextFloat();
                library.addBook(new Book(title, author, price));
                System.out.println("New book added to the library.");
                break;

            case 2:
                System.out.println("Available books:");
                library.displayAvailable();
                break;

            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    public static void readerFunctions(Library library, Reader reader, Scanner in) {
        System.out.println("Welcome, Reader!");
        System.out.println("1. View available books");
        System.out.println("2. Checkout a book");
        System.out.println("3. Return a book");
        System.out.println("Choose an action: ");
        int choice = in.nextInt();
        
        switch (choice) {
            case 1:
                System.out.println("Available books:");
                library.displayAvailable();
                break;

            case 2:
                System.out.println("Enter the number of the book you want to checkout (1-5): ");
                int bookChoice = in.nextInt();
                if (bookChoice >= 1 && bookChoice <= library.books.size()) {
                    reader.checkedOutBook(library.books.get(bookChoice - 1));
                } else {
                    System.out.println("Invalid book choice.");
                }
                break;

            case 3:
                System.out.println("Enter the number of the book you want to return (1-5): ");
                int returnChoice = in.nextInt();
                if (returnChoice >= 1 && returnChoice <= reader.checkedOutBooks.size()) {
                    reader.checkinBook(reader.checkedOutBooks.get(returnChoice - 1));
                } else {
                    System.out.println("Invalid book choice.");
                }
                break;

            default:
                System.out.println("Invalid option.");
                break;
        }
    }
}
