package by.vsu;

import by.vsu.Hash.HashTable;
import by.vsu.Hash.Item;
import by.vsu.arrays.ArrayUniDirectionalList;
import by.vsu.*;

public class Main
{
	static int size = 7;
	static int count = 9991;
    public static void main(String[] args)
    {
    	HashTable<Integer> H= new HashTable<Integer>(size);
    	/*
    	long timer=System.currentTimeMillis();
    	for(int i=0; i<count; i++)
    	{
    		try
    		{
    		H.put(i, i); //put(key, value)
    		}catch(TableOverflowException e)
    		{
    			System.err.println("Таблица переполнена.");
    			return;
    		}
    	}
    	long newTimer=System.currentTimeMillis()-timer;
    	System.out.println("Запись в таблицу: "+newTimer);
    	
    	timer=System.currentTimeMillis();
    	for(int i=0; i<count; i++)
    	{
    		H.get(i); //get(key)
    	}
    	newTimer=System.currentTimeMillis()-timer;
    	System.out.println("Поиск: "+newTimer);
    	
    	timer=System.currentTimeMillis();
    	for(int i=0; i<count; i++)
    	{
    		H.pop(i); //pop(key)
    	}
    	newTimer=System.currentTimeMillis()-timer;
    	System.out.println("Удаление: "+newTimer);
    	*/
    	//try
    	/*
    	{
    		H.put(new Item<Integer>(0,0));
    		H.put(new Item<Integer>(7,7));
    		H.put(new Item<Integer>(14,14));
    		H.put(new Item<Integer>(3,3));
    		H.put(new Item<Integer>(4,4));
    		H.put(new Item<Integer>(5,5));
    		H.put(new Item<Integer>(6,6));
    		
    		System.out.println(H.get(0));
    		System.out.println(H.get(7));
    		System.out.println(H.get(14));
    		System.out.println(H.get(3));
    		System.out.println(H.get(4));
    		System.out.println(H.get(5));
    		System.out.println(H.get(6));
    		H.pop(7);
    		System.out.println(H.get(0));
    		System.out.println(H.get(7));
    		System.out.println(H.get(14));
    		System.out.println(H.get(3));
    		System.out.println(H.get(4));
    		System.out.println(H.get(5));
    		System.out.println(H.get(6));
    	}
    	*/
    	/*
    	catch(TableOverflowException e)
    	{
    		System.err.println("Произошло переполнение таблицы.");
    	}
    	*/
    	
    	ArrayUniDirectionalList<Integer> AUL = new ArrayUniDirectionalList<Integer>(10);
    	
    	AUL.pushBack(1);
    	AUL.pushBack(2);
    	System.out.println(AUL.nextValue());
    	System.out.println(AUL.nextValue());
    	AUL.pushAfter(3);
    	
    	
    	System.out.println(AUL);
    }
}
