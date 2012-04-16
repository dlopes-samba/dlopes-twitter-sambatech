package br.com.sambatech.selecao.twitterapp.util;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * 
 * @author Carlos Lopes
 */
public class PersistenceFactoryHelper {

	private static final PersistenceManagerFactory factoryInstance =
			JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	private PersistenceFactoryHelper() {
	}
	
	public static PersistenceManagerFactory getFactory() {
		return factoryInstance;
	}
}
