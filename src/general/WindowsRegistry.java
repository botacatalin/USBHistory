package general;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
/***
 * Author Bota Catalin
 * Email  bota.catalin@gmail.com
 * Desc This software can read all the USB devices that were connected to a computer. 
 */
public class WindowsRegistry {

	public static final String REG_KEY = "USBSTOR";

	public static Set<String> readRegistry(String location, String key) {
		Process getKeys;
		BufferedReader foundKeys;
		Set<String> usbdevices = null;
		//cmd line:  reg query "HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Enum\USBSTOR"// /s | FIND "USBSTOR"
		try {
			getKeys = Runtime.getRuntime().exec(
				"reg query " + "\"" + location + "\"" + " /s");

			System.out.println("Query: " + "reg query " + "\"" + location
					+ "\"" + " /s");
			foundKeys = new BufferedReader(new InputStreamReader(
					getKeys.getInputStream()));
			usbdevices = getUSBDevices(foundKeys);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return usbdevices;
	}

	public static Set<String> getUSBDevices(BufferedReader foundKeys) {
		Set<String> usbdevices = new HashSet<String>();
		String pattern = ".*FriendlyName.*";
		String line = null;

		try {
			while ((line = foundKeys.readLine()) != null) {
				if (Pattern.matches(pattern, line)) {
					usbdevices.add(line.toString());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(usbdevices);
		return usbdevices;

	}
}
