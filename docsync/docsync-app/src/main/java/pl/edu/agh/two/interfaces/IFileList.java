package pl.edu.agh.two.interfaces;

import java.util.List;

import pl.edu.agh.two.file.DocSyncFile;
import pl.edu.agh.two.ws.IMetadata;

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
	 * Dodanie pliku do listy oraz wysłanie go na serwer
	 *
	 * @param file
	 */
	public void addAndSend(DocSyncFile file);

	/**
	 * Sprawdzenie czy plik jest juz na liscie
	 *
	 * @param file
	 */
	public boolean contains(DocSyncFile file);

	/**
	 * Aktualizacja metadanych dla pliku
	 *
	 * @param file
	 * @param metadata
	 */
	public void updateFile(DocSyncFile file, IMetadata metadata);

	public List<DocSyncFile> getDocSyncFileList();
	
	public void clear();
}
