package utils;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Map.Entry;

import auto.Resources;

public class NumberUtils {
	public static double round(double value) {
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(0, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	

	public static int detectNumberCash(BufferedImage img) {
		try {			
			StringBuilder builder = new StringBuilder();			
			for(int x = 0; x < img.getWidth() - 7; x++) {
				for(Entry<Integer, BufferedImage> entry : Resources.getCashDigitsMap().entrySet()) {
					if(ImageUtils.compareImage(entry.getValue(), ImageUtils.getSubImage(img, x, 0, 7, 9), true)) {
						builder.append(entry.getKey());
					}
				}
			}
			return Integer.parseInt(builder.toString());
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static int detectNumberPoints(BufferedImage img) {
		try {			
			StringBuilder builder = new StringBuilder();
			for(int x = 0; x < img.getWidth() - 5; x++) {
				for(Entry<Integer, BufferedImage> entry : Resources.getPointDigitsMap().entrySet()) {
					if(ImageUtils.compareImage(entry.getValue(), ImageUtils.getSubImage(img, x, 0, 5, 10), true)) {
						builder.append(entry.getKey());
					}
				}
			}
			return Integer.parseInt(builder.toString());
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static void updateCoors(Map<String, Integer[]> map, int windowX, int windowY) {
		for(Entry<String, Integer[]> entry : map.entrySet()) {
			Integer[] arr = entry.getValue();
			arr[0] += windowX;
			arr[1] += windowY;
			entry.setValue(arr);
		}
	}
}
