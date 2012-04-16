package br.com.sambatech.selecao.twitterapp.util;

import java.util.ResourceBundle;

public class AppResourceBundle {
	public static ResourceBundle getResourceBundle(String bundle) {
		ResourceBundle resource = ResourceBundle.getBundle(bundle);
		return resource;
	}
}
