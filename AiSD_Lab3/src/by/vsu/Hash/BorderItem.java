package by.vsu.Hash;

public class BorderItem<V extends Comparable> extends Item{

	public BorderItem(Integer key, Comparable value) {
		super(key, value);
	}
	
	
	public BorderItem()
	{
		
	}
	
	@Override
	public boolean isBorder()
	{
		return true;
	}
	
	
	
	@Override
	public Integer getValue()
	{
		return null;
	}

}
