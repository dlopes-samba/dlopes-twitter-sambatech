package br.com.sambatech.selecao.twitterapp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOUserException;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

import br.com.sambatech.selecao.twitterapp.model.User;
import br.com.sambatech.selecao.twitterapp.util.AppResourceBundle;
import br.com.sambatech.selecao.twitterapp.util.PersistenceFactoryHelper;
import br.com.sambatech.selecao.twitterapp.util.TwitterHelper;

@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {
	
	private static final ResourceBundle bundle = AppResourceBundle.getResourceBundle("twitter-sambatech");
	private static final Logger log = Logger.getLogger(RegisterServlet.class.getName());
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Map<String, String> errors = new HashMap<String, String>();
		
		String name = req.getParameter("name");
		String phone = req.getParameter("phone");
		String email = req.getParameter("email");
		String twitter = req.getParameter("twitter");
		
		if(name == null || name.isEmpty())
			errors.put("name", "O nome do usuário não pode ser vazio.");
		
		if(phone == null || phone.isEmpty())
			errors.put("phone", "O telefone do usuário não pode ser vazio.");
		
		if(email == null || email.isEmpty()) {
			errors.put("email", "O e-mail do usuário não pode ser vazio.");
		} else {
			if(isEmailAlreadyUsed(email))
				errors.put("email", "O e-mail (" + email + ") já está cadastrado na aplicação.");
		}
				
		if(!errors.isEmpty()) {
			req.setAttribute("errors", errors);
	        req.getRequestDispatcher("register.jsp").forward(req, resp);
	        return;
		}
		
		if(!doRegister(name, phone, email, twitter)) {
			errors.put("general", "Não foi possível registrar o usuário.");
			req.setAttribute("errors", errors);
	        req.getRequestDispatcher("register.jsp").forward(req, resp);
	        return;
		}
		
		String status;
		String site = bundle.getString("site.url");
		if(twitter != null && !twitter.isEmpty()) {
			status = "@" + twitter + " acabou de se cadastrar no site " + site;
		} else {
			status = name + " acabou de se cadastrar no site " + site;
		}
		TwitterHelper.getInstance().updateStatus(status);
		
		/**
		 * redirect to the page with a list of all registered users
		 */
		resp.sendRedirect("/list.jsp");
	}

	/**
	 * Return true if the a User is correctly persisted or false otherwise.
	 * 
	 * @param name
	 * @param phone
	 * @param email
	 * @param twitter
	 */
	private boolean doRegister(String name, String phone, String email, String twitter) {
		log.log(Level.INFO, "Persisting User ('" + name + "', '" + phone + "', '" + email + "', '" + twitter + "').");
		
		PersistenceManager persistenceManager = PersistenceFactoryHelper.getFactory().getPersistenceManager();
		User user = new User(name, phone, email, twitter);

		try {
			persistenceManager.makePersistent(user);
			
			
		} catch (JDOUserException e) {
			log.log(Level.SEVERE, "doRegister: Unable to persiste User. Cause: \n" + e.getMessage());
			return false;
			
		} finally {
			persistenceManager.flush();
			persistenceManager.close();
		}
		return true;
	}
	
	/**
	 * Return true if the informed email is already registered within the  application
	 * 
	 * @param email
	 * @return
	 */
	private boolean isEmailAlreadyUsed(String email) {	
		Query q = new Query("User");
		q.addFilter("email", FilterOperator.EQUAL, email);
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		List<Entity> entities = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(1));
		
		if(entities == null || entities.isEmpty())
			return false;
		return true;
	}
}

