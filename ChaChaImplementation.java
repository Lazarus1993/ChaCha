package com.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChaChaImplementation {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the key");
		String key = br.readLine();
		
		String[] words = converttoWord(key);   // split into chunks of words
		String[] bwords = new String[words.length];
		int [] lbwords = new int[words.length];
		int[] output = new int[bwords.length];
		
		for(int i=0;i<words.length;i++)
			bwords[i] = convertToBinary(words[i]);      //converting string words into binary words
		
		
		for(int i=0;i<lbwords.length;i++)
			lbwords[i] = Integer.parseInt(bwords[i], 2);  //converting binary words into int words
		
		//rowround
		output = rowRound(output,lbwords);
		lbwords = output;
		
		//columnround
		output = columnRound(output,lbwords);
			
		String finalKey = padding(output);
		
		System.out.println("Size of key "+finalKey.length());
		
		System.out.println("Enter 1.Encryption 2.Decryption");
		int choice = Integer.parseInt(br.readLine());
		
		switch(choice) {
		
		case 1:
			System.out.println("Enter the plaintext");
			String plaintext = br.readLine();
			plaintext = convertToBinary(plaintext);
			
			System.out.println("Size of the plaintext is " + plaintext.length());
			
			String ciphertext = finalOperation(plaintext,finalKey);
			System.out.println("key	     is "+finalKey);
			System.out.println("PLain   	text is "+plaintext);
			System.out.println("Ciphert 	text is "+ciphertext);
			System.out.println("Size of cipher text is "+ ciphertext.length());
			break;
			
		case 2:
			System.out.println("Enter the ciphertext");
			String ciphert = br.readLine();
			
			String plaint = finalOperation(ciphert,finalKey);
			System.out.println("key	     is "+finalKey);
			System.out.println("Cipher  text is "+ciphert);
			System.out.println("Plain 	text is "+plaint);
			System.out.println("Size of plain text is "+ plaint.length());
			
			
			displayPlaintext(plaint);
			break;
		}
	}
	private static void displayPlaintext(String plaintext) {
	String finalPlain="";
	for(int i=0;i<plaintext.length();i+=8) {
		String letter = plaintext.substring(i, i+8);
		int tempint = Integer.parseInt(letter,2);
		//tempint+=65;
		char c = (char)tempint;
		finalPlain = finalPlain + c;
	}
	
	System.out.println("The plaintext is "+finalPlain);
		
	}
private static String finalOperation(String plaintext, String finalKey) {
		//This is an XOR operation
		StringBuilder sb = new StringBuilder();
		
		
		for(int i=0;i<plaintext.length();i++)
			sb.append((plaintext.charAt(i))^(finalKey.charAt(i)));
		
		String result = sb.toString();
		
		return result;
	}

	private static String padding(int[] output) {
		String[] boutput = new String[output.length];
	  for(int i=0;i<output.length;i++)
	    boutput[i] = String.format("%32s",Integer.toBinaryString(output[i])).replace(' ', '0');
	  
	  String fin = boutput[0];
	  for(int i=1;i<boutput.length;i++)
		  fin = fin + boutput[i]; 
	  
	  return fin;
		
	}

	private static int[] columnRound(int[] output, int[] bwords) {
		System.out.println("Column Round Begins !!!!");
		int[] tempoutput = new int[4];
		for(int i=0;i<output.length; i+=4) {
			
				if(i==0) {
				tempoutput = quarterRound(bwords[i],bwords[i+4],bwords[i+8],bwords[i+12]);
				for(int j=0;j<tempoutput.length;j++) 
				output[i+j] = tempoutput[j];
				}
				if(i==4) {
					tempoutput = quarterRound(bwords[i+1],bwords[i+5],bwords[i+9],bwords[i-3]);
					for(int j=0;j<tempoutput.length;j++) 
					output[i+j] = tempoutput[j];
					}
				if(i==8) {
					tempoutput = quarterRound(bwords[i+2],bwords[i+6],bwords[i-6],bwords[i-2]);
					for(int j=0;j<tempoutput.length;j++) 
					output[i+j] = tempoutput[j];
					}
				if(i==12) {
					tempoutput = quarterRound(bwords[i+3],bwords[i-9],bwords[i-5],bwords[i-1]);
					for(int j=0;j<tempoutput.length;j++) 
					output[i+j] = tempoutput[j];
					}
		}
		
		output = columnshuffle(output);
		
		return output;
	}

	private static int[] columnshuffle(int[] output) {
		int[] temp = output;
		
		temp[0] = output[0];
		temp[4] = output[1];
		temp[8] = output[2];
		temp[12] = output[3];
		
		temp[5] = output[4];
		temp[9] = output[5];
		temp[13] = output[6];
		temp[1] = output[7];
		
		temp[10] = output[8];
		temp[14] = output[9];
		temp[2] = output[10];
		temp[6] = output[11];
		
		temp[15] = output[12];
		temp[3] = output[13];
		temp[7] = output[14];
		temp[11] = output[15];
		
		
		return temp;
	}

	private static int[] rowRound(int[] output, int[] bwords) {
		
		System.out.println("Row Round Begins !!!!");
		
		
		int[] tempoutput = new int[4];
		for(int i=0;i<output.length; i+=4) {
			
				if(i==0) {
					
				tempoutput = quarterRound(bwords[i],bwords[i+1],bwords[i+2],bwords[i+3]);
				for(int j=0;j<tempoutput.length;j++)
				output[i+j] = tempoutput[j];
				}
				if(i==4) {
					
					tempoutput = quarterRound(bwords[i+1],bwords[i+2],bwords[i+3],bwords[i]);
					for(int j=0;j<tempoutput.length;j++)
					output[i+j] = tempoutput[j];
					}
				if(i==8) {
					
					tempoutput = quarterRound(bwords[i+2],bwords[i+3],bwords[i],bwords[i+1]);
					for(int j=0;j<tempoutput.length;j++)
					output[i+j] = tempoutput[j];
					}
				if(i==12) {
					tempoutput = quarterRound(bwords[i+3],bwords[i],bwords[i+1],bwords[i+2]);
					for(int j=0;j<tempoutput.length;j++)
					output[i+j] = tempoutput[j];
					}
		}		
		output = rowshuffle(output);
		return output;
	}

	private static int[] rowshuffle(int[] output) {
		int[] temp = output;
		
		temp[5] = output[4];
		temp[6] = output[5];
		temp[7] = output[6];
		temp[4] = output[7];
		
		temp[10] = output[8];
		temp[11] = output[9];
		temp[8] = output[10];
		temp[9] = output[11];
		
		temp[15] = output[12];
		temp[12] = output[13];
		temp[13] = output[14];
		temp[14] = output[15];

		return temp;
	}

	private static int[] quarterRound(int x0, int x1, int x2, int x3) {
		
		int[] twords = new int[4];
		
		int y1 = x1^(((x0 + x3)%(int)(Math.pow(2, 32))) << 7);
		int y2 = x2^(((y1 + x0)%(int)(Math.pow(2, 32))) << 9);
		int y3 = x3^(((y2 + y1)%(int)(Math.pow(2, 32))) << 13);
		int y0 = x0^(((y3 + y2)%(int)(Math.pow(2, 32))) << 18);
		
		twords[1] = y1;
		twords[2] = y2;
		twords[3] = y3;
		twords[0] = y0;

		return twords;
		
		
	}

	private static String[] converttoWord(String plaintext) {
		String[] words = new String [plaintext.length()/4];
		int j=0;
		for(int i=0; i<plaintext.length(); i=i+4){
			 
			words[j] = plaintext.substring(i, (4+i));
			j++;
			}
		return words;
	}

	private static String convertToBinary(String plaintext) {
		byte[] plain = plaintext.getBytes();
		int[] plain1 = new int[plain.length];
		for(int i=0;i<plain.length;i++) {
			plain1[i] = (int)plain[i];
		}
		
		String binaryplain = "0"+Integer.toBinaryString(plain1[0]);
		
		for(int i=1;i<plain1.length;i++)
			binaryplain = binaryplain + "0"+Integer.toBinaryString(plain1[i]);
		
		return binaryplain;
	}
}
