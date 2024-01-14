package ma.projet.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import ma.projet.entities.Author;
import ma.projet.entities.Book;
import ma.projet.repositories.AuthorRepository;
import ma.projet.repositories.BookRepository;

@Controller
@AllArgsConstructor
public class AuthorController {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository ;

    @QueryMapping
    public List<Author> authors() {
        return authorRepository.findAll();
    }

    @QueryMapping
    public Optional<Author> authorById(@Argument Long id) {
        return authorRepository.findById(id);

    }

    @MutationMapping
    public Book addBook(@Argument BookInput book) {
        Author author = authorRepository.findById(book.authorId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));

        Book b = new Book(book.title(), book.publisher(), author);

        return bookRepository.save(b);
    }
    
    @MutationMapping
	public Boolean deleteBook(@Argument Long id) {
		try {
			bookRepository.deleteById(id);
			return true;
		} catch (Exception err) {
			System.out.println("The id to delete is " + id);
			return false;
		}
	}


    @MutationMapping
	public Boolean updateBook(@Argument Long id, @Argument BookInput bookInput) {
		try {
            Author author = authorRepository.findById(bookInput.authorId())
            .orElseThrow(() -> new IllegalArgumentException("Author not found"));
           
            Book b = new Book(bookInput.title(), bookInput.publisher(), author);
			updated(b, id);
			return true;
		} catch (Exception err) {
			err.printStackTrace();
			return false;
		}
	}



    record BookInput(String title, String publisher, Long authorId) {}
    record ServiceIdentifier(Long id) {
	}


    public void updated(Book newBook, Long id) {
		Book oldBook = bookRepository.findById(id).get();

		if (oldBook != null) {
            
			String newTitle = newBook.getTitle();
			String newPublisher = newBook.getPublisher();
            Author newAuthor=newBook.getAuthor();
            if (newTitle != null && !newTitle.isEmpty()) {
				oldBook.setTitle(newTitle);
			}
			if (newPublisher != null && !newPublisher.isEmpty()) {
				oldBook.setPublisher(newPublisher);
			}
			if (newAuthor != null) {
				oldBook.setAuthor(newAuthor);
			}
			bookRepository.save(oldBook);
		}
	}

}
