package pl.edu.agh.two.interfaces;

import pl.edu.agh.two.file.DocSyncFile;

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
	 *
	 * @param file
	 */
	public void open(DocSyncFile file);

	/**
	 * Usunięci pliku z listy
	 *
	 * @param file
	 */
	public void delete(DocSyncFile file);

	/**
	 * Dodanie pliku do listy
	 *
	 * @param file
	 */
	public void add(DocSyncFile file);

	/**
	 * Aktualizacja metadanych dla pliku
	 *
	 * @param file
	 * @param metadata
	 */
	public void updateFile(DocSyncFile file, IMetadata metadata);
}
