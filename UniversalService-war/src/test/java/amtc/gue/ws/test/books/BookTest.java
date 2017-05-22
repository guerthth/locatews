package amtc.gue.ws.test.books;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;

import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.persistence.EMF;
import amtc.gue.ws.base.persistence.ProductiveEMF;
import amtc.gue.ws.base.persistence.dao.user.UserDAO;
import amtc.gue.ws.base.persistence.dao.user.jpa.UserJPADAOImpl;
import amtc.gue.ws.base.persistence.dao.user.objectify.UserObjectifyDAOImpl;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.jpa.GAEJPAUserEntity;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;
import amtc.gue.ws.base.util.mapper.UserServiceEntityMapper;
import amtc.gue.ws.base.util.mapper.jpa.UserServiceJPAEntityMapper;
import amtc.gue.ws.base.util.mapper.objectify.UserServiceObjectifyEntityMapper;
import amtc.gue.ws.books.inout.Book;
import amtc.gue.ws.books.inout.Books;
import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.persistence.dao.book.BookDAO;
import amtc.gue.ws.books.persistence.dao.book.jpa.BookJPADAOImpl;
import amtc.gue.ws.books.persistence.dao.book.objectify.BookObjectifyDAOImpl;
import amtc.gue.ws.books.persistence.dao.tag.TagDAO;
import amtc.gue.ws.books.persistence.dao.tag.jpa.TagJPADAOImpl;
import amtc.gue.ws.books.persistence.dao.tag.objectify.TagObjectifyDAOImpl;
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.book.jpa.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.book.objectify.GAEObjectifyBookEntity;
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;
import amtc.gue.ws.books.persistence.model.tag.jpa.GAEJPATagEntity;
import amtc.gue.ws.books.persistence.model.tag.objectify.GAEObjectifyTagEntity;
import amtc.gue.ws.books.util.mapper.BookServiceEntityMapper;
import amtc.gue.ws.books.util.mapper.jpa.BookServiceJPAEntityMapper;
import amtc.gue.ws.books.util.mapper.objectify.BookServiceObjectifyEntityMapper;
import amtc.gue.ws.test.base.BaseTest;

/**
 * Class holding common data for all BookService Tests
 * 
 * @author Thomas
 *
 */
public class BookTest extends BaseTest {
	protected static UserDAO<GAEUserEntity, GAEObjectifyUserEntity, String> userObjectifyDAO;
	protected static BookDAO<GAEBookEntity, GAEObjectifyBookEntity, String> bookObjectifyDAO;
	protected static BookDAO<GAEBookEntity, GAEObjectifyBookEntity, String> noUserBookObjectifyDAO;
	protected static TagDAO<GAETagEntity, GAEObjectifyTagEntity, String> tagObjectifyDAO;
	protected static BookDAO<GAEBookEntity, GAEJPABookEntity, String> bookJPADAO;
	protected static BookDAO<GAEBookEntity, GAEJPABookEntity, String> noUserBookJPADAO;
	protected static BookDAO<GAEBookEntity, GAEJPABookEntity, String> failureBookJPADAO;
	protected static TagDAO<GAETagEntity, GAEJPATagEntity, String> tagJPADAO;
	protected static TagDAO<GAETagEntity, GAEJPATagEntity, String> failureTagJPADAO;
	protected static UserDAO<GAEUserEntity, GAEJPAUserEntity, String> userJPADAO;

	protected static User user;

	protected static Tags tagsA;
	protected static Tags tagsAB;
	protected static Tags tagsC;
	protected static List<String> tagListA;

	protected static Book book1;
	protected static Book book2;
	protected static Book book3;
	protected static Book book4;
	protected static Books books;
	protected static Books booksWithId;
	protected static Books booksWithTag;
	protected static Books emptyBooks;

	protected static Roles roles;

	protected static GAEObjectifyBookEntity objectifyBookEntity1;
	protected static GAEObjectifyBookEntity objectifyBookEntity2;
	protected static GAEObjectifyBookEntity objectifyBookEntity3;
	protected static GAEObjectifyBookEntity objectifyBookEntity4;
	protected static GAEObjectifyTagEntity objectifyTagEntity1;
	protected static GAEObjectifyTagEntity objectifyTagEntity2;
	protected static GAEObjectifyTagEntity objectifyTagEntity3;
	protected static GAEObjectifyUserEntity objectifyUserEntity1;

	protected static List<GAEBookEntity> objectifyBookEntityList;
	protected static List<GAEBookEntity> objectifyBookEntityEmptyList;
	protected static List<GAEBookEntity> objectifyBookEntityListWithID;
	protected static List<GAETagEntity> objectifyTagEntityList;
	protected static List<GAETagEntity> objectifyTagEntityEmptyList;
	protected static List<GAEUserEntity> objectifyUserEntityList;
	protected static List<GAEUserEntity> objectifyUserEntityEmptyList;

	protected static GAEJPABookEntity JPABookEntity1;
	protected static GAEJPABookEntity JPABookEntity2;
	protected static GAEJPABookEntity JPABookEntity3;
	protected static GAEJPABookEntity JPABookEntity4;
	protected static GAEJPABookEntity JPABookEntity5;
	protected static GAEJPABookEntity JPABookEntity6;
	protected static GAEJPATagEntity JPATagEntity1;
	protected static GAEJPATagEntity JPATagEntity2;
	protected static GAEJPATagEntity JPATagEntity3;
	protected static GAEJPATagEntity JPATagEntity4;
	protected static GAEJPATagEntity JPATagEntity5;
	protected static GAEJPAUserEntity JPAUserEntity1;

	protected static List<GAEBookEntity> JPABookEntityList;
	protected static List<GAEBookEntity> JPABookEntityTagAList;
	protected static List<GAEBookEntity> JPABookEntityTagNullList;
	protected static List<GAEBookEntity> JPABookEntityEmptyList;
	protected static List<GAEBookEntity> JPABookEntityNullValueList;
	protected static List<GAETagEntity> JPATagEntityList;
	protected static List<GAETagEntity> JPATagEntityEmptyList;
	protected static List<GAETagEntity> JPATagEntityNullValueList;
	protected static Set<GAETagEntity> JPATagEntitySet;
	protected static List<GAEUserEntity> JPAUserEntityList;

	protected static final String BOOKKEY = "1";
	protected static final String AUTHOR = "testAuthor";
	protected static final String DESCRIPTION = "testDescription";
	protected static final String DESCRIPTION_B = "testDescriptionB";
	protected static final String ISBN = "testISBN";
	protected static final String TITLE = "testTitle";
	protected static final String PRICE = "100";
	protected static final String TAG = "testTag";
	protected static final String TAG_B = "testTagB";
	protected static final String TAG_C = "testTagC";

	protected static final String USERNAME = "testUserName";
	protected static final String ROLE = "testRole";
	protected static final String PASSWORD = "userPasswordA";

	protected static UserServiceEntityMapper JPAUserEntityMapper;
	protected static UserServiceEntityMapper objectifyUserEntityMapper;
	protected static BookServiceEntityMapper JPABookEntityMapper;
	protected static BookServiceEntityMapper objectifyBookEntityMapper;
	
	@Before
	public void setUp() {
		setupDBHelpers();
		setupUserEntities();
		setupTagEntities();
		setupBookEntities();
	}

	@After
	public void tearDown() {
		tearDownDBHelpers();
	}

	/**
	 * Setting up basic book environment
	 * 
	 */
	protected static void setUpBasicBookEnvironment() {
		setupUsers();

		// Objectify setup
		ObjectifyService.setFactory(new ObjectifyFactory());
		ObjectifyService.register(GAEObjectifyBookEntity.class);
		ObjectifyService.register(GAEObjectifyUserEntity.class);
		ObjectifyService.register(GAEObjectifyTagEntity.class);

		userObjectifyDAO = new UserObjectifyDAOImpl();
		bookObjectifyDAO = new BookObjectifyDAOImpl(user);
		noUserBookObjectifyDAO = new BookObjectifyDAOImpl(null);
		tagObjectifyDAO = new TagObjectifyDAOImpl();

		// JPA setup
		EMF emf = new ProductiveEMF();
		bookJPADAO = new BookJPADAOImpl(emf, user);
		noUserBookJPADAO = new BookJPADAOImpl(emf, null);
		failureBookJPADAO = new BookJPADAOImpl(null, null);
		tagJPADAO = new TagJPADAOImpl(emf);
		failureTagJPADAO = new TagJPADAOImpl(null);
		userJPADAO = new UserJPADAOImpl(emf);

		setupTags();
		setupBooks();
		setupRoles();
		setupTagEntities();
		setupBookEntities();
		setupEntityMappers();
	}

	/**
	 * Setting up all required tag entities
	 */
	private static void setupTagEntities() {
		// Objectify Tagentities
		objectifyTagEntity1 = new GAEObjectifyTagEntity();
		objectifyTagEntity1.setKey(TAG);
		objectifyTagEntity2 = new GAEObjectifyTagEntity();
		objectifyTagEntity2.setKey(TAG_B);
		objectifyTagEntity3 = new GAEObjectifyTagEntity();
		objectifyTagEntity3.setKey(TAG_C);

		objectifyTagEntityList = new ArrayList<>();
		objectifyTagEntityList.add(objectifyTagEntity1);
		objectifyTagEntityList.add(objectifyTagEntity2);
		objectifyTagEntityEmptyList = new ArrayList<>();

		// JPA Tagentities
		JPATagEntity1 = new GAEJPATagEntity();
		JPATagEntity1.setKey(TAG);
		JPATagEntity2 = new GAEJPATagEntity();
		JPATagEntity2.setKey(TAG_B);
		JPATagEntity3 = new GAEJPATagEntity();
		JPATagEntity3.setKey(TAG_C);
		JPATagEntity4 = new GAEJPATagEntity();
		JPATagEntity5 = new GAEJPATagEntity();
		JPATagEntity5.addToBooksOnly(new GAEJPABookEntity());

		JPATagEntityList = new ArrayList<>();
		JPATagEntityList.add(JPATagEntity1);

		JPATagEntitySet = new HashSet<>();
		JPATagEntitySet.add(JPATagEntity2);

		JPATagEntityEmptyList = new ArrayList<>();

		JPATagEntityNullValueList = new ArrayList<>();
		JPATagEntityNullValueList.add(null);
	}

	/**
	 * Setting up all required book entities
	 */
	private static void setupBookEntities() {
		// Objectify Bookentities
		objectifyBookEntity1 = new GAEObjectifyBookEntity();
		objectifyBookEntity1.setAuthor(AUTHOR);
		objectifyBookEntity1.setDescription(DESCRIPTION);
		objectifyBookEntity1.setISBN(ISBN);
		objectifyBookEntity1.setTitle(TITLE);
		objectifyBookEntity1.setPrice(PRICE);
		objectifyBookEntity2 = new GAEObjectifyBookEntity();
		objectifyBookEntity2.setAuthor(AUTHOR);
		objectifyBookEntity3 = new GAEObjectifyBookEntity();
		objectifyBookEntity3.setISBN(ISBN);
		objectifyBookEntity4 = new GAEObjectifyBookEntity();
		objectifyBookEntity4.setKey(BOOKKEY);

		objectifyBookEntityList = new ArrayList<>();
		objectifyBookEntityList.add(objectifyBookEntity1);
		
		objectifyBookEntityListWithID = new ArrayList<>();
		objectifyBookEntityListWithID.add(objectifyBookEntity4);

		objectifyBookEntityEmptyList = new ArrayList<>();

		// JPA Bookentities
		JPABookEntity1 = new GAEJPABookEntity();
		JPABookEntity1.setAuthor(AUTHOR);
		JPABookEntity1.setDescription(DESCRIPTION);
		JPABookEntity1.setISBN(ISBN);
		JPABookEntity1.setTitle(TITLE);
		JPABookEntity1.setPrice(PRICE);
		JPABookEntity2 = new GAEJPABookEntity();
		JPABookEntity2.setAuthor(AUTHOR);
		JPABookEntity3 = new GAEJPABookEntity();
		JPABookEntity3.setISBN(ISBN);
		JPABookEntity4 = new GAEJPABookEntity();
		JPABookEntity4.addToTagsOnly(JPATagEntity1);
		JPABookEntity5 = new GAEJPABookEntity();
		JPABookEntity5.setKey(BOOKKEY);
		JPABookEntity5.setTags(null, false);

		JPABookEntityList = new ArrayList<>();
		JPABookEntityList.add(JPABookEntity1);

		JPABookEntityTagAList = new ArrayList<>();
		JPABookEntityTagAList.add(JPABookEntity4);

		JPABookEntityTagNullList = new ArrayList<>();
		JPABookEntityTagNullList.add(JPABookEntity5);

		JPABookEntityEmptyList = new ArrayList<>();

		JPABookEntityNullValueList = new ArrayList<>();
		JPABookEntityNullValueList.add(null);
	}

	/**
	 * Setting up all required user entities
	 */
	private static void setupUserEntities() {
		// Objectify Userentities
		objectifyUserEntity1 = new GAEObjectifyUserEntity();
		objectifyUserEntity1.setKey(USERNAME);
		objectifyUserEntity1.setPassword(PASSWORD);

		objectifyUserEntityList = new ArrayList<>();
		objectifyUserEntityList.add(objectifyUserEntity1);
		objectifyUserEntityEmptyList = new ArrayList<>();

		// JPA Userentities
		JPAUserEntity1 = new GAEJPAUserEntity();
		JPAUserEntity1.setKey(USERNAME);
		JPAUserEntity1.setPassword(PASSWORD);

		JPAUserEntityList = new ArrayList<>();
		JPAUserEntityList.add(JPAUserEntity1);
	}

	/**
	 * Setting up all required Book objects
	 */
	private static void setupBooks() {
		book1 = new Book();
		book1.setAuthor(AUTHOR);
		book1.setDescription(DESCRIPTION);
		book1.setISBN(ISBN);
		book1.setPrice(PRICE);
		book1.setTags(tagsA.getTags());
		book1.setTitle(TITLE);

		book2 = new Book();
		book2.setTags(tagsA.getTags());

		book3 = new Book();
		book3.setId(BOOKKEY);
		book4 = new Book();
		book3.setTags(tagListA);

		books = new Books();
		books.getBooks().add(book1);
		books.getBooks().add(book2);

		booksWithId = new Books();
		booksWithId.getBooks().add(book3);
		booksWithTag = new Books();
		booksWithTag.getBooks().add(book4);

		emptyBooks = new Books();
	}

	/**
	 * Setting up all Tag objects
	 */
	private static void setupTags() {
		tagListA = new ArrayList<>();
		tagListA.add(TAG);
		tagsA = new Tags();
		tagsA.setTags(tagListA);

		List<String> tagListAB = new ArrayList<>();
		tagListAB.add(TAG);
		tagListAB.add(TAG_B);
		tagsAB = new Tags();
		tagsAB.setTags(tagListAB);

		List<String> tagListC = new ArrayList<>();
		tagListC.add(TAG_C);
		tagsC = new Tags();
		tagsC.setTags(tagListC);
	}

	/**
	 * Setting up all required Role objects
	 */
	private static void setupRoles() {
		List<String> roleList = new ArrayList<>();
		roleList.add(ROLE);
		roles = new Roles();
		roles.setRoles(roleList);
	}

	/**
	 * Method setting up User objects
	 */
	private static void setupUsers() {
		user = new User();
		user.setId(USERNAME);
	}
	
	/**
	 * Method setting up entity mappers
	 */
	private static void setupEntityMappers() {
		JPAUserEntityMapper = new UserServiceJPAEntityMapper();
		objectifyUserEntityMapper = new UserServiceObjectifyEntityMapper();
		JPABookEntityMapper = new BookServiceJPAEntityMapper();
		objectifyBookEntityMapper = new BookServiceObjectifyEntityMapper();
	}
}
