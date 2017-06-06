package by.vsu.Hash;

import by.vsu.TableOverflowException;

//Хэш-таблица с закрытой адресацией, хэш-функция h(x)= x mod SIZE;
public class CloseTable<V>
{
	private CloseItem [] items;
	
	public CloseTable(int size)
	{
		this.items = new CloseItem[size];
	}
	
	public int getSize()
	{
		return items.length;
	}
	
	//Помещает item на позицию h(x),если та не занята, иначе сдвигает позицию вправо. 
	//Если достигли конца таблицы, а место не найдено, переходим в начало.
	public void put(int key, V value) throws TableOverflowException 
	{
		int size = this.getSize();
		int pos = key%size;
		int quantityUncheckedPositions = size;
		while(quantityUncheckedPositions != 0)
		{
			
			if(items[pos%size] == null)
			{
			     items[pos%size] = new CloseItem<V>(key, value);
			     return;
			}
			pos++;
			quantityUncheckedPositions--;			
		}
		throw new TableOverflowException();
	}
	
	//Ищем значение по ключу, до тех пор пока не встретим null
	public V get(int key)
	{
		int pos = key%this.getSize();
		int quantityUncheckedPositions = this.getSize();
		while(quantityUncheckedPositions != 0 && items[pos] != null)
		{
			if(items[pos].getKey() == key)
			{
				return (V)items[pos].getValue();
			}
			pos++;
			pos %= this.getSize();
			quantityUncheckedPositions--;
		}
		return null;
	}
	
	//Находит, а затем удаляет значение по ключу.
	//Из-за удаления может образоваться дыра, которую придётся затыкать следующим, стояще не на своём месте item'ом
	public void pop(int key)
	{
		int size = this.getSize();
		int pos = key%size;
		int quantityUncheckedPositions = size; //Граница будет использоваться, для того чтобы мы не пробегали по второму кругу
		int deletePosition = 0;
		boolean isDelete = false;
		while(quantityUncheckedPositions != 0)
		{			
			if(items[pos%size] != null)
			{
				if(items[pos%size].getKey() == key)//Удаляем значение, и запоминаем позоцию на случай, если образоввалась дыра
				{
					items[pos%size] = null;
					deletePosition = pos;
					isDelete = true;
					pos++;
					quantityUncheckedPositions--;
					break;
				}
			}
			else //Искомого ключа нет, удаление не происходит
			{
				break;
			}
			pos++;
			quantityUncheckedPositions--;
		}
		quantityUncheckedPositions = size;
		
		while(isDelete)//Если произошло удаление, затыкаем дыры, которые могли образоваться
		{	
			
			if(items[pos%size] != null)
			{
				if(items[pos%size].getKey()%size != pos%size)
				{
					if((pos - deletePosition) > (pos - items[pos%size].getKey() % size))
					//Проверка на тот случай, если старая позиция будет левее, чем истинная позиция item'a
					{
						pos++;
						continue;
					}
					items[deletePosition%size] = items[pos%size];
					//items[pos%size].setPos(deletePosition);
					items[pos%size] = null;
					deletePosition = pos;
				}
			}else
			{
				break;
			}
			pos++;
			quantityUncheckedPositions--;
		}
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder bodyBuilder = new StringBuilder();
		int size = items.length;
		bodyBuilder.append("Размер: " + size + "\n\n");
		for(int i = 0; i < size; i++)
		{
			if(this.items[i] != null)
			{
				bodyBuilder.append(this.items[i]);
				bodyBuilder.append('\n');
			}
		}
		return bodyBuilder.toString();
	}
}
