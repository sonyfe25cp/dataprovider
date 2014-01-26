package dataprovider.utils;

import org.junit.Test;

public class CharsetUtilsTest {

	@Test
	public void test() {
		String str = "北京";
		String string = CharsetUtils.toUtf8(str);
		System.out.println(string);
	}

}
