package pl.edu.agh.two.interfaces;

import pl.edu.agh.two.file.File;

public interface IFileList {
	/**
	 * Serializacja na dysk.
	 */
	public void saveList();

	/**
	 * Pobranie listy z serwera
	 */
	public void getList();

	/**
	 * Otwarcie pliku z listy.
	 * @param file
	 */
	public void open(File file);

	/**
	 * Usunięci pliku z listy
	 * @param file
	 */
	public void delete(File file);

	/**
	 * Dodanie pliku do listy
	 * @param file
	 */
	public void add(File file);

	/**
	 * Aktualizacja metadanych dla pliku
	 * @param file
	 * @param metadata
	 */
	public void updateFile(File file, IMetadata metadata);
}
