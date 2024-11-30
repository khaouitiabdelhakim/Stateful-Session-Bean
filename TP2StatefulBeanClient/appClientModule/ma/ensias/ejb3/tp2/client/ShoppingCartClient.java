package ma.ensias.ejb3.tp2.client;

import java.util.ArrayList;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ma.ensias.ejb3.tp2.ShoppingCartBeanRemote;

public class ShoppingCartClient {
	public ShoppingCartClient() {
	}

	public static void main(String[] args) {
		ShoppingCartClient shoppingCartTest = new ShoppingCartClient();
		shoppingCartTest.doTest();
	}

	InitialContext getInitialContext() throws javax.naming.NamingException {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		p.put(Context.URL_PKG_PREFIXES, "jboss.naming:org.jnp.interfaces");
		p.put(Context.PROVIDER_URL, "jnp://localhost:1099");
		return new InitialContext(p);
	}

	void doTest() {
		try {
			InitialContext ic = getInitialContext();
			System.out.println("ShoppingCart Lookup");
			ShoppingCartBeanRemote shoppingCart = (ShoppingCartBeanRemote) ic.lookup("ShoppingCartBean/remote");

			System.out.println("Adding Book Items...");
			shoppingCart.addBookItem("Java coding rules");
			shoppingCart.addBookItem("EJB3 developers guide");

			System.out.println("Printing Cart Items:");
			ArrayList<String> cartItems = shoppingCart.getCartItems();
			for (String book : cartItems) {
				System.out.println(book);
			}

			// Simulate Passivation
			System.out.println("Waiting for passivation...");
			Thread.sleep(6000); // Wait for idle timeout to trigger passivation

			System.out.println("Adding another Book Item...");
			shoppingCart.addBookItem("Java Concurrency in Practice");

			// Simulate Removal
			System.out.println("Waiting for removal...");
			Thread.sleep(10000); // Wait for removal timeout to trigger bean removal

			System.out.println("Done.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
