package backup;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;

import truesculpt.managers.WebManager;
import truesculpt.parser.*;
import utils.Utils;

public class Backup {
	
	private String mOutputDir= "D:\\Android\\OnlineLibraryBackupsArchive";
	
	public void Work() throws MalformedURLException, IOException
	{
	    System.out.println("Getting data listing");
		Date date = new Date();
		@SuppressWarnings("deprecation")
		String archiveName=date.toGMTString().replace(" ", "_").replace(":", "_");
		String archiveOutputPathString=Utils.PathCombine(mOutputDir,archiveName);
		File archiveOutputPath = new File(archiveOutputPathString);
		archiveOutputPath.mkdirs();

		String xmlDataPath=Utils.PathCombine(archiveOutputPathString,"data.xml");
		Utils.saveUrl(xmlDataPath, WebManager.GetBaseWebLibraryAdress()+"/xml",false);		
		ArrayList<WebEntry> mEntries=WebLibraryParser.getWebLibrary();
		System.out.println("Found " + mEntries.size() +" objects to save");
		int index=1;
		for(WebEntry entry : mEntries)	
		{
			System.out.println("Saving " + index + "/" + mEntries.size() + " = " + entry.getTitle());
			String entryOutputPathString=Utils.PathCombine(archiveOutputPathString,Integer.toString(index) + "_" + entry.getTitle());
			File entryOutputPath = new File(entryOutputPathString);
			entryOutputPath.mkdirs();
			Utils.saveUrl(Utils.PathCombine(entryOutputPathString,"Image.png"),entry.getImageURL().toString(),false);
			Utils.saveUrl(Utils.PathCombine(entryOutputPathString,"Thumbnail.png"),entry.getImageThumbnailURL().toString(),false);
			Utils.saveUrl(Utils.PathCombine(entryOutputPathString,"Object.obj"),WebManager.GetBaseWebLibraryAdress()+entry.getObjectURL(),true);
			index++;
			//if (index>=4) break;//temp for test
		}
		
		String zipArchivePath=archiveOutputPathString+".zip";
		System.out.println("Zipping archive " + zipArchivePath);
		Utils.ZipDir(archiveOutputPathString, zipArchivePath);
		System.out.println("Backup done");
	}
	
}
