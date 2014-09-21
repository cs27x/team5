package test;

import org.junit.Test;

import junit.framework.TestCase;

import com.example.vandybeer.BeerLocation;
public class BeerLocationTests extends TestCase {
	
	@Test
	public void testConstructor(){
		BeerLocation loc = new BeerLocation();
		
		assertTrue(loc.getAddress().equals(""));
		assertTrue(loc.getBusinessName().equals(""));
		assertTrue(loc.getBusinessOwner().equals(""));
		assertTrue(loc.getState().equals(""));
		assertTrue(loc.getCity().equals(""));
		assertTrue(loc.getMappedLocation().equals("(0.0,0.0)"));
		assertTrue(loc.getPermitType() == 0);
		assertTrue(loc.getLatitude() == 0);
		assertTrue(loc.getLongitude() == 0);
		assertTrue(loc.getZipCode() == 0);
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
		assertTrue(loc.getPermitType() == 1);
		permitType = "OFF-SALE BEER";
		loc.setPermitType(permitType);
		assertTrue(loc.getPermitType() == 0);
		permitType = "ON/OFF-SALE BEER";
		loc.setPermitType(permitType);
		assertTrue(loc.getPermitType() == 2);
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
}
