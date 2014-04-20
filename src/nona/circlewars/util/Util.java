package nona.circlewars.util;

import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;

import nona.circlewars.Game;

public class Util {

	public static int centerTextX(String s, Rectangle2D r) {
		int strWidth = (int) r.getWidth();

		int centerX = Game.WIDTH / 2;

		int strx = centerX - (strWidth / 2);

		return strx;
	}
	
	public static int centerTextY(String s, Rectangle2D r) {
		int strHeight = (int) r.getHeight();
		
		int centerY = Game.HEIGHT / 2;
		
		int stry = centerY - (strHeight / 2);
		
		return stry;
	}
	
	public static int getHeight(String s, FontMetrics metrics) {
		return (int) metrics.getStringBounds(s, Game.getGraphics2D()).getHeight();
	}
	
	public static int getWidth(String s, FontMetrics metrics) {
		return (int) metrics.getStringBounds(s, Game.getGraphics2D()).getWidth();
	}
	
	public static int[] getYPositions(String title, FontMetrics titleMetrics, String[] strings, FontMetrics metrics) {
		int[] positions = new int[strings.length];
		int heightLeft = Game.HEIGHT - getHeight(title, titleMetrics);
		int fontHeight = getHeight(" ", metrics);
		int totalBuffer = heightLeft - (fontHeight * strings.length);
		int buffer = totalBuffer / (strings.length + 1);
				
		for(int i = 0; i < strings.length; i++) {
			positions[i] = getTitleY(title, titleMetrics) + ((i + 1) * buffer);
		}
		
		return positions;
	}
	
	public static int getTitleY(String title, FontMetrics titleMetrics) {
		return getHeight(title, titleMetrics) * 3 / 2;
	}
	
}
