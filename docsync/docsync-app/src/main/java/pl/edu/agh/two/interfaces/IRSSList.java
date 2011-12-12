package pl.edu.agh.two.interfaces;

import java.util.List;

import pl.edu.agh.two.ws.RSSItem;

/**
 * TODO: add comments.
 * <p/>
 * Creation date: 2011.12.01
 *
 * @author Tomasz Zdybał
 */
public interface IRSSList {
	/**
	 * Pobranie listy z serwera
	 */
	void getList();

	/**
	 * Zwraca RSSItem z wiersza i
	 *
	 * @param i numer wiersza
	 * @return RSSItem
	 */
	RSSItem getItem(int i);
	
	void addItems(List<RSSItem> items);

	public List<RSSItem> getRSSItemList();

}
