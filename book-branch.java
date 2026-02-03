// Book.java
class Book {
    int bookID;
    String bookTitle;
    boolean available;
    
    // Constructor
    public Book(int bookID, String bookTitle, boolean available) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.available = available;
    }
    
    // Borrow book
    public boolean borrowBook() {
        if (available) {
            available = false;
            return true;
        }
        return false;
    }
    
    // Return book
    public boolean returnBook() {
        if (!available) {
            available = true;
            return true;
        }
        return false;
    }
    
    // Display book info
    public void displayBook() {
        System.out.println("  Book ID: " + bookID + 
                         ", Title: " + bookTitle + 
                         ", Available: " + available);
    }
}
// LibraryBranch.java
class LibraryBranch {
    int branchID;
    Book[] books;
    int bookCount;
    static final int MAX_BOOKS = 20;
    
    // Constructor
    public LibraryBranch(int branchID) {
        this.branchID = branchID;
        this.books = new Book[MAX_BOOKS];
        this.bookCount = 0;
    }
    
    // Add book to branch
    public boolean addBook(int bookID, String bookTitle, boolean available) {
        if (bookCount >= MAX_BOOKS) {
            System.out.println("Cannot add more books. Maximum limit reached.");
            return false;
        }
        books[bookCount] = new Book(bookID, bookTitle, available);
        bookCount++;
        return true;
    }
    
    // Find book by ID
    private Book findBook(int bookID) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].bookID == bookID) {
                return books[i];
            }
        }
        return null;
    }
    
    // Borrow book
    public void borrowBook(int bookID) {
        Book book = findBook(bookID);
        if (book == null) {
            System.out.println("Book not found.");
        } else if (book.borrowBook()) {
            System.out.println("Book borrowed successfully.");
        } else {
            System.out.println("Book is not available.");
        }
    }
    
    // Return book
    public void returnBook(int bookID) {
        Book book = findBook(bookID);
        if (book == null) {
            System.out.println("Book not found.");
        } else if (book.returnBook()) {
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Book is already available.");
        }
    }
    
    // Display all books in this branch
    public void displayBranch() {
        System.out.println("Branch ID: " + branchID);
        if (bookCount == 0) {
            System.out.println("No books available.");
        } else {
            for (int i = 0; i < bookCount; i++) {
                books[i].displayBook();
            }
        }
        System.out.println();
    }
}
// Main.java
import java.util.Scanner;

class Main {
    static final int MAX_BRANCHES = 10;
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide number of branches as command-line argument.");
            return;
        }
        
        int numBranches = Integer.parseInt(args[0]);
        
        if (numBranches > MAX_BRANCHES) {
            System.out.println("Maximum " + MAX_BRANCHES + " branches allowed.");
            return;
        }
        
        Scanner sc = new Scanner(System.in);
        LibraryBranch[] branches = new LibraryBranch[numBranches];
        
        // Initialize branches and add books
        for (int i = 0; i < numBranches; i++) {
            branches[i] = new LibraryBranch(i + 1);
            
            System.out.println("How many books to add to branch " + (i + 1) + "?");
            int numBooks = sc.nextInt();
            
            for (int j = 0; j < numBooks; j++) {
                System.out.println("Enter Book ID:");
                int bookID = sc.nextInt();
                sc.nextLine(); // consume newline
                
                System.out.println("Enter Book Title:");
                String bookTitle = sc.nextLine();
                
                System.out.println("Is book available? (true/false):");
                boolean available = sc.nextBoolean();
                
                branches[i].addBook(bookID, bookTitle, available);
            }
        }
        
        // Menu loop
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Borrow a Book");
            System.out.println("2. Return a Book");
            System.out.println("3. Display All Branch Info");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            
            int choice = sc.nextInt();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter Branch ID: ");
                    int borrowBranchID = sc.nextInt();
                    System.out.print("Enter Book ID: ");
                    int borrowBookID = sc.nextInt();
                    
                    if (borrowBranchID > 0 && borrowBranchID <= numBranches) {
                        branches[borrowBranchID - 1].borrowBook(borrowBookID);
                    } else {
                        System.out.println("Invalid Branch ID.");
                    }
                    break;
                    
                case 2:
                    System.out.print("Enter Branch ID: ");
                    int returnBranchID = sc.nextInt();
                    System.out.print("Enter Book ID: ");
                    int returnBookID = sc.nextInt();
                    
                    if (returnBranchID > 0 && returnBranchID <= numBranches) {
                        branches[returnBranchID - 1].returnBook(returnBookID);
                    } else {
                        System.out.println("Invalid Branch ID.");
                    }
                    break;
                    
                case 3:
                    for (int i = 0; i < numBranches; i++) {
                        branches[i].displayBranch();
                    }
                    break;
                    
                case 4:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                    
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
              }
