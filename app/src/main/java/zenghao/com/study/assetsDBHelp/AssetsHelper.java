package zenghao.com.study.assetsDBHelp;

import android.content.res.AssetManager;
import android.support.annotation.RequiresPermission; 
import android.util.Log;
 
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
 
/** 
 * Helper class for copying asset files to a storage directory. 
 */ 
public class AssetsHelper {
	/**
	 * Used during debugging to identify this class. 
	 */ 
	@SuppressWarnings("unused") 
	private static final String TAG = "[AssetsHelper]";
 
	/** 
	 * Copies specified asset resources to the supplied directory. 
	 * 
	 * @param assetsManager 
	 * 		provides access to the application's assets, not null 
	 * @param assetFiles 
	 * 		the filenames of the asset files to copy, not null 
	 * @param targetDirectory 
	 * 		the directory to copy the asset files to, not null 
	 * @throws IOException 
	 * 		if a general IO based error occurs while copying the files 
	 * @throws IllegalArgumentException 
	 * 		if either {@code context}, {@code assetFiles} or {@code targetDirectory} is null 
	 */ 
	@RequiresPermission(allOf = android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
	public static void copyAssetsToDirectory(final AssetManager assetsManager,
			final String[] assetFiles,
			final File targetDirectory) throws IOException {
		if (assetsManager == null) {
			throw new IllegalArgumentException("assetsManager cannot be null");
		} else if (assetFiles == null) {
			throw new IllegalArgumentException("assetFiles cannot be null");
		} else if (targetDirectory == null) {
			throw new IllegalArgumentException("targetDirectory cannot be null");
		} 
 
		for (final String filename : assetFiles) {
			// Create a new file in the output directory to receive the asset data 
			final File fileInTargetDirectory = new File(targetDirectory, filename);
 
			// Initialise streams outside of try block so that they can be closed later 
			InputStream streamFromAssets = null;
			OutputStream streamToTargetFile = null;
 
			try { 
				// IOExceptions may be thrown 
				streamToTargetFile = new FileOutputStream(fileInTargetDirectory);
				streamFromAssets = assetsManager.open(filename);
				copyFile(streamFromAssets, streamToTargetFile);
			} finally { 
				// An IOException is probably unrecoverable so just abort and close the streams 
				closeStream(streamFromAssets);
				closeStream(streamToTargetFile);
			} 
		} 
	} 
 
	/** 
	 * Copies a file from the source stream to the target stream. 
	 * 
	 * @param source 
	 * 		the source of the data to copy, not null 
	 * @param target 
	 * 		the target to copy data to, not null 
	 * @throws IOException 
	 * 		if a general IO based error occurs while transferring data 
	 * @throws IllegalArgumentException 
	 * 		if either {@code source} or {@code target} is null 
	 */ 
	private static void copyFile(final InputStream source, final OutputStream target) throws
			IOException {
		if (source == null) {
			throw new IllegalArgumentException("source cannot be null");
		} else if (target == null) {
			throw new IllegalArgumentException("target cannot be null");
		} 
 
		// Data is moved from the input stream to the output stream through buffer 
		final byte[] buffer = new byte[1024];
 
		// Read data into the buffer from the source 
		int numberOfBytesRead = source.read(buffer);
 
		// If the buffer received data, write data from the buffer to the output stream 
		while (numberOfBytesRead != -1) {
			target.write(buffer, 0, numberOfBytesRead);
			numberOfBytesRead = source.read(buffer); // read the next "lot" of data
		} 
	} 
 
	/** 
	 * Closes a stream. Exceptions are logged if the stream cannot be closed. 
	 * 
	 * @param stream 
	 * 		the stream to close, not null 
	 * @throws IllegalArgumentException 
	 * 		if {@code stream} is null 
	 */ 
	private static void closeStream(final Closeable stream) {
		if (stream == null) {
			throw new IllegalArgumentException("stream cannot be null");
		} 
 
		try { 
			stream.close();
		} catch (IOException e) {
			Log.e(TAG, "[Error closing stream: " + stream + "]", e);
		} 
	} 
} 