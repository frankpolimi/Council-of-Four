package cg2;

import java.awt.Color;
import java.util.Random;

public class SupportClass 
{
	public static Color giveRandomColor()
	{
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		return new Color(r, g, b);
	}
}
