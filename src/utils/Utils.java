package utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Utils 
{
	public static String PathCombine(String... paths)
	{
		File file = new File(paths[0]);

		for (int i = 1; i < paths.length ; i++) {
			file = new File(file, paths[i]);
		}

		return file.getPath();
	}

	public static void saveUrl(String filename, String urlString, boolean bUnzip) throws MalformedURLException, IOException
	{
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try
		{
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
			InputStream stream = connection.getInputStream();
			
			if (bUnzip)
			{
				in = new BufferedInputStream(new GZIPInputStream(stream));
			}
			else
			{
				in = new BufferedInputStream(stream);
			}
			fout = new FileOutputStream(filename);

			byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1)
			{
				fout.write(data, 0, count);
			}
		}
		finally
		{
			if (in != null)
				in.close();
			if (fout != null)
				fout.close();
		}
	}
	
	public static void ZipDir(String dir2zip, String zipOutPath)
	{
		try
		{
		//create a ZipOutputStream to zip the data to
		ZipOutputStream zos = new
		ZipOutputStream(new FileOutputStream(zipOutPath));
		//assuming that there is a directory named inFolder (If there
		//isn't create one) in the same directory as the one the code runs from,
		//call the zipDir method
		zipDir(dir2zip, dir2zip, zos);
		//close the stream
		zos.close();
		}
		catch(Exception e)
		{
		//handle exception
		}
	}
	

	public static void zipDir(String dir2zip, String rootPath, ZipOutputStream zos)
	{
		try
		{
			//create a new File object based on the directory we	have to zip File
			File zipDir = new File(dir2zip);
			//get a listing of the directory content
			String[] dirList = zipDir.list();
			byte[] readBuffer = new byte[2156];
			int bytesIn = 0;
			//loop through dirList, and zip the files
			for(int i=0; i<dirList.length; i++)
			{
				File f = new File(zipDir, dirList[i]);
				if(f.isDirectory())
				{
					//if the File object is a directory, call this
					//function again to add its content recursively
					String filePath = f.getPath();
					zipDir(filePath, rootPath, zos);
					//loop again
					continue;
				}
				//if we reached here, the File object f was not	a directory
				//create a FileInputStream on top of f
				FileInputStream fis = new FileInputStream(f);
				//create a new zip entry
				String strEntryName=f.getPath().replace(rootPath, "").substring(1);
				ZipEntry anEntry = new ZipEntry(strEntryName);
				//place the zip entry in the ZipOutputStream object
				zos.putNextEntry(anEntry);
				//now write the content of the file to the ZipOutputStream
				while((bytesIn = fis.read(readBuffer)) != -1)
				{
					zos.write(readBuffer, 0, bytesIn);
				}
				//close the Stream
				fis.close();
			}
		}
		catch(Exception e)
		{
			//handle exception
		}
	}
}
