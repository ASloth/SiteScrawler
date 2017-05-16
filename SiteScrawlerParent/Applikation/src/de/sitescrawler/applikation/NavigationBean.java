package de.sitescrawler.applikation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.sitescrawler.jpa.Filterprofilgruppe;

/**
 * NavigationBean, stellt Daten die in der Navigationsleiste angezeigt werden bereit.
 * @author robin 
 */
@SessionScoped
@Named("navigation")
public class NavigationBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DataBean dataBean;

	/**
	 * Gibt alle Filtergruppen exklusive der aktuell ausgewählten zurück.
	 * @return
	 */
	public List<Filterprofilgruppe> getSelectableFiltergruppen() {

		return dataBean.getFiltergruppen().stream().filter(p -> p != dataBean.getFiltergruppe())
				.collect(Collectors.toList());
	}
}
