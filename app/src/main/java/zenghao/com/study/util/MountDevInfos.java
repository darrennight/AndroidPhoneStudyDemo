package zenghao.com.study.util;

import android.os.Environment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MountDevInfos {
    private final String HEAD = "dev_mount";
	// public final String LABEL = "<label>"; 
	// public final String MOUNT_POINT = "<mount_point>"; 
	// public final String PATH = "<part>"; 
	// public final String SYSFS_PATH = "<sysfs_path1...>"; 
 
	private final int DEV_INTERNAL = 0;
	private final int DEV_EXTERNAL = 1;
	private final int DEV_ALL = 2;
 
	private ArrayList<String> cache = new ArrayList<String>();
 
	private final File VOLD_FSTAB = new File(Environment.getRootDirectory()
			.getAbsoluteFile()
			+ File.separator
			+ "etc" 
			+ File.separator
			+ "vold.fstab"); 
 
	private ArrayList<DevInfo> getInfo(int type) {
		ArrayList<DevInfo> devInfos = new ArrayList<DevInfo>();

        if (cache.size() == 0) {
            try {
                initVoldFstabToCache();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
		for (String deviceInfo : cache) {
			String[] sinfo = deviceInfo.split(" ");
			boolean isAuto = sinfo[3].equalsIgnoreCase("auto");
 
			if (type == DEV_ALL || (type == DEV_INTERNAL && !isAuto)
					|| (type == DEV_EXTERNAL && isAuto)) {
				DevInfo info = new DevInfo();
				info.label = sinfo[1];
				info.mount_point = sinfo[2];
				info.path = sinfo[3];
				info.sysfs_path = sinfo[4];
				devInfos.add(info);
			} 
		} 
 
		return devInfos;
	} 
 
	/** 
	 * init the words into the cache array 
	 *  
	 * @throws IOException 
	 */ 
	private void initVoldFstabToCache() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(VOLD_FSTAB));
		String tmp = null;
		while ((tmp = br.readLine()) != null) {
			// the words startsWith "dev_mount" are the SD info 
			if (tmp.startsWith(HEAD)) {
				cache.add(tmp);
			} 
		} 
		br.close();
		cache.trimToSize();
	} 
 
	public class DevInfo { 
		public String label, mount_point, path, sysfs_path;
	} 
 
	public ArrayList<DevInfo> getInternalInfo() {
		return getInfo(DEV_INTERNAL);
	} 
 
	public ArrayList<DevInfo> getExternalInfo() {
		return getInfo(DEV_EXTERNAL);
	} 
 
	public ArrayList<DevInfo> getAllInfo() {
		return getInfo(DEV_ALL);
	} 
 
} 
