package ntilde.com.learnenglishnumbers;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Clase para trabajar con numeros en ingles
 */
public class EnglishNumber {
	
	private static Map<Long,String> numbers=new HashMap<Long,String>();
	private static Map<String,Long> revnumbers=new HashMap<String,Long>();
	static{
		numbers.put(0L, "");
		numbers.put(1L, "one");
		numbers.put(2L, "two");
		numbers.put(3L, "three");
		numbers.put(4L, "four");
		numbers.put(5L, "five");
		numbers.put(6L, "six");
		numbers.put(7L, "seven");
		numbers.put(8L, "eight");
		numbers.put(9L, "nine");
		numbers.put(10L, "ten");
		numbers.put(11L, "eleven");
		numbers.put(12L, "twelve");
		numbers.put(13L, "thirteen");
		numbers.put(14L, "fourteen");
		numbers.put(15L, "fifteen");
		numbers.put(16L, "sixteen");
		numbers.put(17L, "seventeen");
		numbers.put(18L, "eighteen");
		numbers.put(19L, "nineteen");
		numbers.put(20L, "twenty");
		numbers.put(30L, "thirty");
		numbers.put(40L, "forty");
		numbers.put(50L, "fifty");
		numbers.put(60L, "sixty");
		numbers.put(70L, "seventy");
		numbers.put(80L, "eighty");
		numbers.put(90L, "ninety");
		numbers.put(100L, "hundred");
		numbers.put(1000L, "thousand");
		numbers.put(1000000L, "million");
		for(Entry<Long, String> number:numbers.entrySet()){
			revnumbers.put(number.getValue(), number.getKey());
		}
	}
	
	/**
	 * Convierte un numero (en cifras) a texto (ingles)
	 * @param digits Cifra a convertir a ingles
	 * @return Cadena que representa las cifras en ingles
	 */
	public static String digitsToEnglish(long digits){
		String english="I'm sorry, the number is too big u_u";
		if(digits>=0&&digits<=19){
			english=numbers.get(digits);
		}
		else if(digits>=20&&digits<=99){
			long big=((int)digits/10)*10;
			english=numbers.get(big)+" "+numbers.get(digits-big);
		}
		else if(digits>=100&&digits<=999){
			long big=((int)digits/100)*100;
			english=digitsToEnglish(big/100)+" "+numbers.get(100L)+" "+digitsToEnglish(digits-big);
		}
		else if(digits>=1000&&digits<=999999){
			long big=((int)digits/1000)*1000;
			english=digitsToEnglish(big/1000)+" "+numbers.get(1000L)+" "+digitsToEnglish(digits-big);
		}
		else if(digits>=1000000&&digits<=999999999){
			long big=((int)digits/1000000)*1000000;
			english=digitsToEnglish(big/1000000)+" "+numbers.get(1000000L)+" "+digitsToEnglish(digits-big);
		}
		return english.replaceAll("\\s+", " ");
	}
	
	/**
	 * Convierte un numero (en ingles) a cifras
	 * @param english Cadena en ingles que representa un numero
	 * @return Numero (en cifras) con que se corresponde el parametro de entrada
	 */
	public static long englishToDigits(String english){
		String digits="";
		String[] englishSplitted=english.trim().split(" ");
		long d;
		for(int i=0;i<englishSplitted.length;i++) {
			Long number=revnumbers.get(englishSplitted[i]);
			if(number>=20&&number<=90){
				digits+=i==englishSplitted.length-1||englishSplitted[i+1].equalsIgnoreCase(numbers.get(1000L))?number:number/10;
			}
			else if(number==100){
				d=englishToDigits(TextUtils.join(" ",Arrays.copyOfRange(englishSplitted, i+1, englishSplitted.length)));
				if(i+1<englishSplitted.length&&englishSplitted[i+1].equalsIgnoreCase(numbers.get(1000L))){
					digits+="00";
				}
				else{
					digits+=d==0||d>=100&&d<=999?"00":d>=1&&d<=9||d>=1000&&d<=9999?"0":"";
				}
			}
			else if(number==1000||number==1000000){
				d=englishToDigits(TextUtils.join(" ",Arrays.copyOfRange(englishSplitted, i+1, englishSplitted.length)));
				digits+=new String(new char[String.valueOf(number).length()-1-String.valueOf(d).length()]).replace("\0", "0");
				return Long.parseLong(digits+d);
			}
			else{
				digits+=number;
			}
		}
		return Long.parseLong(digits);
	}
	
	/**
	 * Comprueba si un numero (en cifras) y en texto (ingles) corresponden
	 * @param digits Cifra con la que comparar la cadena
	 * @param english Cadena a comparar con las cifras
	 * @return Verdadero si coinciden, falso en caso contrario
	 */
	public static boolean checkDigits(long digits, String english){
		return english.replaceAll("\\s+", "").equals(digitsToEnglish(digits).replaceAll("\\s+", ""));
	}
	
}