package Resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
	static RequestSpecification req;
	public io.restassured.specification.RequestSpecification requestSpecification() throws IOException {
		if(req==null) {
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			req = new RequestSpecBuilder().setBaseUri(getGlobalProperties("baseUri"))
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log))
					.setContentType(ContentType.JSON).addQueryParam("key", "qaclick123").build();
			return req;
		}
		return req;
	}
	public static String getGlobalProperties(String key) throws IOException {
		System.out.println(System.getProperty("user.dir")+"\\src\\test\\java\\Resources\\global.properties");
		FileInputStream pfile = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\Resources\\global.properties");
		Properties prop = new Properties();
		prop.load(pfile);
		return prop.getProperty(key);
	}
	public String getJsonPath(Response rrsp, String Key) {
		String resp = rrsp.asString();
		JsonPath rspjson = new JsonPath(resp);
		return rspjson.get(Key).toString();
	}
}
