package ma.ensias.ejb3.tp2;

import java.util.ArrayList;

import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateful;

import org.jboss.annotation.ejb.cache.simple.CacheConfig;

/**
 * Session Bean implementation class ShoppingCartBean
 */
@Stateful
@CacheConfig(idleTimeoutSeconds = 5, removalTimeoutSeconds = 15)
public class ShoppingCartBean implements ShoppingCartBeanRemote, ShoppingCartBeanLocal {

	private ArrayList<String> cartItems = new ArrayList<String>();
	int bookOrder = 0;

	/**
	 * Default constructor.
	 */
	public ShoppingCartBean() {
		// TODO Auto-generated constructor stub
	}

	public void addBookItem(String book) {
		bookOrder++;
		cartItems.add(book + "( Book number = " + bookOrder + ")");
	}

	public void removeBookItem(String book) {
		cartItems.remove(book);
	}

	public void setCartItems(ArrayList<String> cartItems) {
		this.cartItems = cartItems;
	}

	public ArrayList<String> getCartItems() {
		return cartItems;
	}

	@PreDestroy
	public void goodBye() {
		System.out.println("From goodBye method with @PreDestroy annotation");
	}

	@PrePassivate
	public void passivateShoppingCart() {
		System.out.println("From passivateShoppingCart method with @PrePassivate annotation");
	}

	@PostActivate
	public void activateShoppingCart() {
		System.out.println("From activateShoppingCart method with @PostActivate annotation");
	}
}
