package by.vsu.Hash;

import java.util.LinkedList;

public class HashTable<V extends Comparable>
{
    protected int size;
    protected LinkedList<Item<V>>[] array;

    public HashTable(int size)
    {
        array = new LinkedList[size];
        
        this.size = size;
        
        for(int i = 0; i < size; i++)
        {
        	array[i] = new LinkedList<Item<V>>();
        	array[i].addLast(new BorderItem<V>());
        }
    }

    public void put(Item<V> item)
    {
        if (this.get(item.getKey()) == null)
        {
            int pos = item.getKey() % size;
            array[pos].addFirst(item);
        }
    }

    public V get(Integer key)
    {
        int pos = key % size;
        Item<V> current = new Item<V>();
        array[pos].getLast().setKey(key);
        
        for (Item<V> item : array[pos])
        {
			current = item;
			if (current.getKey() == key)
			{
				break;
			}
		}
        
        return current.getValue();
    }

    public void pop(Integer key)
    {
        int pos = key % size;
        Item<V> current = new Item<V>();
        array[pos].getLast().setKey(key);
        
        for (Item<V> item : array[pos])
        {
			current = item;
			if (current.getKey() == key)
			{
				break;
			}
		}
        
        if(!current.isBorder())
        {
        	array[pos].remove(current);
        }
    }
    
    @Override
    public String toString()
    {
    	StringBuilder res = new StringBuilder();
    	
    	for (LinkedList<Item<V>> l : array)
    	{
    		for (Item<V> i : l)
    		{
    			if(!i.isBorder())
    			{
    			res.append(i);
    			res.append(" ");
    			}
    		}
    		res.append("\n");
    	}
    	
    	return res.toString();
    }
}
