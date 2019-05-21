package org.jeesl.controller.processor;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionCodeProcessor
{
	final static Logger logger = LoggerFactory.getLogger(TransactionCodeProcessor.class);
	
	private static Random rnd = new Random();
	private static int offset = 65;
	
	public static String transactionCode(int[] size)
	{
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<size.length;i++)
		{
			build(sb,size[i]);
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}
	
	private static void build(StringBuilder sb, int size)
	{
		for(int i=0;i<size;i++)
		{
			int random = rnd.nextInt(26)+offset;
			sb.append(Character.toString((char)random));
		}
		sb.append("-");
	}


	public static void main(String[] args)
	{
		int[] size = {5,5};
		System.out.println(TransactionCodeProcessor.transactionCode(size));
		
		for(int i=0;i<26;i++)
		{
			int value = i+offset;
			System.out.println(Character.toString((char)value));
		}
	}
}