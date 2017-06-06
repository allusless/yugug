package by.vsu.Hash;

public class Item<V extends Comparable>
{
    private Integer key;
    private V value;
    private Item<V> next = null;

    public Item()
    {
    	
    }
    
    public Item(Integer key, V value)
    {
        this.key = key;
        this.value = value;
    }

    public void setKey(Integer key)
    {
    	this.key = key;
    }
    
    public Integer getKey()
    {
        return key;
    }

    public V getValue()
    {
        return value;
    }

    public void setNext(Item<V> next)
    {
    	this.next = next;
    }
    
    public Item<V> getNext()
    {
        return next;
    }

    public boolean isBorder()
    {
    	return false;
    }
    
    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Ключ: ");
        stringBuilder.append(getKey());
        stringBuilder.append("\n");
        stringBuilder.append("Значение: ");
        stringBuilder.append(getValue());

        if (next != null)
        {
            stringBuilder.append("\n");
            stringBuilder.append(next.toString());
        }

        return stringBuilder.toString();
    }
}
