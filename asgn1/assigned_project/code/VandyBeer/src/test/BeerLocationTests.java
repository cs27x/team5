package test;

import org.junit.Test;

import junit.framework.TestCase;

import com.example.vandybeer.BeerLocation;
import com.example.vandybeer.Beer;
//import java.util.ArrayList;


public class BeerLocationTests extends TestCase {
	
	@Test
	public void testDefaultConstructor(){
		BeerLocation loc = new BeerLocation();
		
		assertTrue(loc.getAddress().equals(""));
		assertTrue(loc.getBusinessName().equals(""));
		assertTrue(loc.getBusinessOwner().equals(""));
		assertTrue(loc.getState().equals(""));
		assertTrue(loc.getCity().equals(""));
		assertTrue(loc.getMappedLocation().equals("(0.0,0.0)"));
		assertTrue(loc.getPermitType().equals(""));
		assertTrue(loc.getLatitude() == 0);
		assertTrue(loc.getLongitude() == 0);
		assertTrue(loc.getZipCode() == 0);
	}
	
	@Test
	public void testStringConstructor(){
		/*BeerLocation loc = new BeerLocation("");
		assertTrue(loc.getAddress().equals(""));
		assertTrue(loc.getBusinessName().equals(""));
		assertTrue(loc.getBusinessOwner().equals(""));
		assertTrue(loc.getState().equals(""));
		assertTrue(loc.getCity().equals(""));
		assertTrue(loc.getMappedLocation().equals("(0.0,0.0)"));
		assertTrue(loc.getPermitType().equals(""));
		assertTrue(loc.getLatitude() == 0);
		assertTrue(loc.getLongitude() == 0);
		assertTrue(loc.getZipCode() == 0);*/
		String beerList = "<Miller\tIt sucks><Bud Light\tDelicious!!!><Red Stripe\tToo small><Modelo\tIDK>";
		String construct = "Name\tOwner Last\t2301 Vanderbilt Place\tcity\tTN\t55566\tON-SALE BEER\t33.45566\t-43.215" + beerList;
		BeerLocation loc2 = new BeerLocation(construct);
		assertTrue(loc2.getAddress().equals("2301 Vanderbilt Place"));
		assertTrue(loc2.getBusinessName().equals("Name"));
		assertTrue(loc2.getBusinessOwner().equals("Owner Last"));
		assertTrue(loc2.getState().equals("TN"));
		assertTrue(loc2.getCity().equals("city"));
		assertTrue(loc2.getPermitType().equals("ON-SALE BEER"));
		assertTrue(loc2.getLatitude() == 33.45566);
		assertTrue(loc2.getLongitude() == -43.215);
		assertTrue(loc2.getZipCode() == 55566);
		assertTrue(loc2.getBeers().size() == 4);
		assertTrue(loc2.getBeers().get(0).getName().equals("Miller"));
		assertTrue(loc2.getBeers().get(0).getComments().equals("It sucks"));
		assertTrue(loc2.getBeers().get(3).getName().equals("Modelo"));
		assertTrue(loc2.getBeers().get(3).getComments().equals("IDK"));
		assertTrue(loc2.toString().equals(construct));
		
	}
	
	@Test
	public void testJSONConstructor(){
		/* JsonBuilderFactory factory = Json.createBuilderFactory(config);
		 JsonObject value = factory.createObjectBuilder()
		     .add("firstName", "John")
		     .add("lastName", "Smith")
		     .add("age", 25)
		     .add("address", factory.createObjectBuilder()
		         .add("streetAddress", "21 2nd Street")
		         .add("city", "New York")
		         .add("state", "NY")
		         .add("postalCode", "10021"))
		     .add("phoneNumber", factory.createArrayBuilder()
		         .add(factory.createObjectBuilder()
		             .add("type", "home")
		             .add("number", "212 555-1234"))
		         .add(factory.createObjectBuilder()
		             .add("type", "fax")
		             .add("number", "646 555-4567")))
		     .build();*/
		
		String address = "AABBCC"; String state = "TN";
		String busName = "Mapco"; String busOwn = "Kathy"; String city = "Nash"; 
		int zip = 37235; String permitType = "ON-SALE BEER"; double latitude = 37;
		double longitude = -65.5;
		BeerLocation loc = new BeerLocation(busName, busOwn, zip, permitType, address, state, city, latitude, longitude);
		assertTrue(address.equals(loc.getAddress()));
		assertTrue(busName.equals(loc.getBusinessName()));
		assertTrue(busOwn.equals(loc.getBusinessOwner()));
		assertTrue(state.equals(loc.getState()));
		assertTrue(city.equals(loc.getCity()));
		assertTrue(permitType.equals(loc.getPermitType()));
		assertTrue(loc.getLatitude() == latitude);
		assertTrue(loc.getLongitude() == longitude);
		assertTrue(loc.getZipCode() == zip);
		 
	}
	
	@Test
	public void testGettersandSetters(){
		BeerLocation loc = new BeerLocation();
		String address = "AABBCC"; String state = "TN";
		String busName = "Mapco"; String busOwn = "Kathy"; String city = "Nash"; 
		int zip = 37235; String permitType = "ON-SALE BEER"; double latitude = 37;
		double longitude = -65.5;
		loc.setAddress(address);
		assertTrue(address.equals(loc.getAddress()));
		loc.setBusinessName(busName);
		assertTrue(busName.equals(loc.getBusinessName()));
		loc.setBusinessOwner(busOwn);
		assertTrue(busOwn.equals(loc.getBusinessOwner()));
		loc.setState(state);
		assertTrue(state.equals(loc.getState()));
		loc.setCity(city);
		assertTrue(city.equals(loc.getCity()));
		loc.setPermitType(permitType);
		assertTrue(permitType.equals(loc.getPermitType()));
		loc.setLatitude(latitude);
		assertTrue(loc.getLatitude() == latitude);
		loc.setLongitude(longitude);
		assertTrue(loc.getLongitude() == longitude);
		loc.setZipCode(zip);
		assertTrue(loc.getZipCode() == zip);
	}
	
	@Test
	public void testCompareBusName(){
		BeerLocation loc = new BeerLocation();
		BeerLocation aLoc = new BeerLocation();
		BeerLocation bLoc = new BeerLocation();
		BeerLocation cLoc = new BeerLocation();
		BeerLocation dLoc = new BeerLocation();
		loc.setBusinessName("Mapco Mart");
		aLoc.setBusinessName("Alphabet Soup");
		bLoc.setBusinessName("55th Avenue Jam");
		cLoc.setBusinessName("Logan's RoadHouse");
		dLoc.setBusinessName("Logan's RoadHouse");
		
		assertTrue(loc.compareBusinessName(aLoc) > 0);
		assertTrue(loc.compareBusinessName(bLoc) > 0);
		assertTrue(loc.compareBusinessName(cLoc) > 0);
		assertTrue(cLoc.compareBusinessName(dLoc) == 0);
		assertTrue(aLoc.compareBusinessName(cLoc) < 0);
	}
	
	@Test
	public void testOtherAccessors(){
		BeerLocation loc = new BeerLocation();
		Beer bud = new Beer("Bud Light");
		Beer pbr = new Beer("Pabst Blue Ribbon");
		Beer red = new Beer("Red Stripe");
		assertTrue(loc.getBeers().isEmpty());
		loc.addBeer(bud);
		loc.addBeer(pbr);
		loc.addBeer(red);
		
		assertTrue(loc.getBeers().size() == 3);
		assertTrue(loc.getBeers().contains(bud));
		assertTrue(loc.getBeers().contains(pbr));
		assertTrue(loc.getBeers().contains(red));
		
		loc.removeBeer(0);
		assertTrue(loc.getBeers().size() == 2);
		loc.removeBeer(0); loc.removeBeer(0);
		assertTrue(loc.getBeers().isEmpty());
		
		assertTrue(loc.getComments().equals(""));
		loc.setLocComments("Good beer, bad food");
		assertTrue(loc.getComments().equals("Good beer, bad food"));
		loc.setLocComments("Music on Fridays");
		assertTrue(loc.getComments().equals("Music on Fridays"));
		
	}
}
