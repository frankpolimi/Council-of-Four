package cg2;

import static org.junit.Assert.*;

import org.junit.Test;

import controller.ErrorChange;

public class ErrorChangeTest {

	@Test
	public void testConstructor() {
		String error = "Error";
		ErrorChange ec = new ErrorChange(error);
		assertEquals(error, ec.getMessage());
	}

}
